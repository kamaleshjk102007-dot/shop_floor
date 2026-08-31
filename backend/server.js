import crypto from "node:crypto";
import fs from "node:fs/promises";
import path from "node:path";
import { fileURLToPath } from "node:url";
import dotenv from "dotenv";
import express from "express";
import { MongoClient } from "mongodb";
import nodemailer from "nodemailer";

const backendDirectory = path.dirname(fileURLToPath(import.meta.url));
dotenv.config({ path: path.join(backendDirectory, "..", ".env") });
dotenv.config({ path: path.join(backendDirectory, ".env"), override: true });

const app = express();
const port = Number(process.env.PORT || 8787);
const syncToken = process.env.SYNC_TOKEN || "";
const mongoUri = process.env.MONGODB_URI || "";
const databaseName = process.env.MONGODB_DATABASE || "shopfloor";
const managementAlertEmail = process.env.MANAGEMENT_ALERT_EMAIL || "";
const smtpHost = process.env.SMTP_HOST || "";
const smtpPort = Number(process.env.SMTP_PORT || 465);
const smtpSecure = String(process.env.SMTP_SECURE || "true").toLowerCase() === "true";
const smtpUser = process.env.SMTP_USER || "";
const smtpPassword = process.env.SMTP_PASSWORD || "";
const smtpFrom = process.env.SMTP_FROM || smtpUser;
const dataDirectory = path.resolve("data");
const snapshotPath = path.join(dataDirectory, "shopfloor-state.json");
const recordTypes = ["salesOrders", "employees", "departments", "categories", "assignments", "logs"];
const idFields = { salesOrders: "id", employees: "empId", departments: "code", categories: "code" };
let mongo;
const deliveredAlertIds = new Set();

const mailTransport = smtpHost && smtpUser && smtpPassword
  ? nodemailer.createTransport({
      host: smtpHost,
      port: smtpPort,
      secure: smtpSecure,
      auth: { user: smtpUser, pass: smtpPassword }
    })
  : null;

app.use(express.json({ limit: "10mb" }));
app.use((request, response, next) => {
  if (!syncToken || request.get("authorization") !== `Bearer ${syncToken}`) {
    return response.status(401).json({ error: "Unauthorized" });
  }
  next();
});

function recordId(type, record, index) {
  if (idFields[type]) return String(record[idFields[type]] || index);
  if (type === "assignments") return `${record.employeeId || index}:${record.salesOrderId || ""}`;
  return crypto.createHash("sha256").update(JSON.stringify(record)).digest("hex").slice(0, 24);
}

async function database() {
  if (!mongoUri) return null;
  if (!mongo) {
    mongo = new MongoClient(mongoUri);
    await mongo.connect();
    const db = mongo.db(databaseName);
    await db.collection("recycle_bin").createIndex({ expiresAt: 1 }, { expireAfterSeconds: 0 });
  }
  return mongo.db(databaseName);
}

async function writeLocalSnapshot(snapshot) {
  await fs.mkdir(dataDirectory, { recursive: true });
  const temporary = `${snapshotPath}.tmp`;
  await fs.writeFile(temporary, JSON.stringify(snapshot, null, 2), "utf8");
  await fs.rename(temporary, snapshotPath);
}

async function syncCollection(db, type, incoming, retentionDays) {
  const collection = db.collection(type);
  const normalized = incoming.map((record, index) => ({
    ...record,
    _id: recordId(type, record, index),
    updatedAt: new Date()
  }));
  const incomingIds = new Set(normalized.map(record => String(record._id)));
  const existing = await collection.find({}, { projection: { _id: 1 } }).toArray();
  const removedIds = existing.map(record => String(record._id)).filter(id => !incomingIds.has(id));
  if (removedIds.length) {
    const removed = await collection.find({ _id: { $in: removedIds } }).toArray();
    const deletedAt = new Date();
    const expiresAt = new Date(deletedAt.getTime() + retentionDays * 24 * 60 * 60 * 1000);
    if (removed.length) {
      await db.collection("recycle_bin").insertMany(removed.map(record => ({
        recordType: type,
        recordId: String(record._id),
        payload: record,
        deletedAt,
        expiresAt
      })));
    }
    await collection.deleteMany({ _id: { $in: removedIds } });
  }
  for (const record of normalized) {
    await collection.replaceOne({ _id: record._id }, record, { upsert: true });
  }
}

app.get("/api/v1/snapshot", async (_request, response) => {
  try {
    const db = await database();
    if (db) {
      const stored = await db.collection("state_snapshots").findOne({ _id: "primary" });
      if (stored?.snapshot) return response.json(stored.snapshot);
    }
    const raw = await fs.readFile(snapshotPath, "utf8");
    response.type("application/json").send(raw);
  } catch (error) {
    if (error?.code === "ENOENT") return response.status(404).json({ error: "No snapshot" });
    response.status(500).json({ error: "Unable to load snapshot" });
  }
});

app.put("/api/v1/snapshot", async (request, response) => {
  try {
    const snapshot = request.body;
    const retentionDays = Math.min(365, Math.max(1, Number(snapshot.recycleRetentionDays || 30)));
    await writeLocalSnapshot(snapshot);
    const db = await database();
    if (db) {
      await db.collection("recycle_bin").updateMany(
        {},
        [{ $set: { expiresAt: { $dateAdd: { startDate: "$deletedAt", unit: "day", amount: retentionDays } } } }]
      );
      for (const type of recordTypes) await syncCollection(db, type, snapshot[type] || [], retentionDays);
      await db.collection("state_snapshots").replaceOne(
        { _id: "primary" },
        { _id: "primary", snapshot, updatedAt: new Date() },
        { upsert: true }
      );
    }
    response.json({ ok: true, revision: snapshot.revision || Date.now() });
  } catch (error) {
    console.error(error);
    response.status(500).json({ error: "Unable to synchronize snapshot" });
  }
});

app.post("/api/v1/alerts/threshold", async (request, response) => {
  try {
    if (!managementAlertEmail) {
      return response.status(503).json({ error: "MANAGEMENT_ALERT_EMAIL is not configured" });
    }
    if (!mailTransport) {
      return response.status(503).json({ error: "SMTP delivery is not configured" });
    }

    const alert = request.body || {};
    const deliveryId = String(alert.deliveryId || "").trim();
    const orderId = String(alert.orderId || "").trim();
    const alertType = alert.alertType === "cost" ? "cost" : "hours";
    if (!deliveryId || !orderId) {
      return response.status(400).json({ error: "deliveryId and orderId are required" });
    }

    const db = await database();
    const persistedDelivery = db
      ? await db.collection("threshold_alert_deliveries").findOne({ _id: deliveryId })
      : null;
    if (deliveredAlertIds.has(deliveryId) || persistedDelivery) {
      return response.json({ ok: true, deduplicated: true, recipient: managementAlertEmail });
    }

    const actual = Number(alert.actual || 0);
    const planned = Number(alert.planned || 0);
    const variance = actual - planned;
    const isCost = alertType === "cost";
    const metric = isCost ? "Actual cost" : "Actual hours";
    const formatValue = value => isCost ? `₹${value.toFixed(2)}` : `${value.toFixed(4)} hrs`;
    const subject = `[OCS ShopFloor] ${orderId}: ${metric} exceeded`;
    const text = [
      "OCS SHOPFLOOR — PLANNED LIMIT ALERT",
      "",
      `Sales Order: ${orderId}`,
      `Alert: ${metric} exceeded`,
      "",
      "Details",
      `Planned: ${formatValue(planned)}`,
      `Actual: ${formatValue(actual)}`,
      `Exceeded by: ${formatValue(variance)}`,
      "",
      "Action required: Review this sales order and update the work plan if necessary.",
      `Generated at: ${new Date().toISOString()}`
    ].join("\n");

    const result = await mailTransport.sendMail({ from: smtpFrom, to: managementAlertEmail, subject, text });
    const deliveredAt = new Date();
    deliveredAlertIds.add(deliveryId);
    if (db) {
      await db.collection("threshold_alert_deliveries").insertOne({
        _id: deliveryId,
        orderId,
        alertType,
        actual,
        planned,
        recipient: managementAlertEmail,
        messageId: result.messageId,
        deliveredAt
      });
    }
    response.json({ ok: true, recipient: managementAlertEmail, messageId: result.messageId });
  } catch (error) {
    console.error("Threshold email failed", error);
    response.status(502).json({ error: "Unable to send threshold email" });
  }
});

app.get("/health", (_request, response) => response.json({
  ok: true,
  atlas: Boolean(mongoUri),
  managementEmailConfigured: Boolean(managementAlertEmail),
  smtpConfigured: Boolean(mailTransport)
}));

app.listen(port, () => console.log(`ShopFloor sync backend listening on ${port}`));
