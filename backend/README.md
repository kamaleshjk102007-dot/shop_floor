# ShopFloor local + Atlas sync backend

This service receives every app mutation immediately, writes an atomic JSON mirror to
`backend/data/shopfloor-state.json`, and upserts each record into its own MongoDB Atlas
collection. Records removed from an incoming full snapshot are moved to `recycle_bin`.
Atlas automatically removes recycle-bin documents after 30 days through a TTL index.

## Setup

1. Create a least-privilege Atlas database user for only the `shopfloor` database.
2. Copy `.env.example` values into secure server environment variables. Never put the
   Atlas URI in the Android app.
3. Run `npm install` and then `npm start` from this folder.
4. Expose this API through HTTPS (reverse proxy, VPN, or a managed host).
5. Build the APK with `MONGODB_SYNC_BASE_URL=https://your-api.example` and the matching
   per-device `MONGODB_SYNC_TOKEN`. The app saves locally even when this API is offline.

MongoDB Compass can connect directly to the same Atlas cluster and inspect the
`salesOrders`, `employees`, `departments`, `categories`, `assignments`, `logs`, and
`recycle_bin` collections.

## Management-only email notifications

Set `MANAGEMENT_ALERT_EMAIL`, `SMTP_HOST`, `SMTP_PORT`, `SMTP_SECURE`, `SMTP_USER`,
`SMTP_PASSWORD`, and `SMTP_FROM` in the server environment. The Android app calls
`POST /api/v1/alerts/threshold` when actual cost or hours reaches the planned limit.
The endpoint sends one email per threshold crossing and deduplicates delivery IDs.

The recipient address is compiled into the app only for read-only display in Profile.
SMTP credentials remain server-side and must never be added to the APK. For Gmail,
enable two-step verification and use a Google app password as `SMTP_PASSWORD`.
