package com.example.reports

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.PrintManager
import android.print.PageRange
import android.provider.MediaStore
import android.provider.DocumentsContract
import com.example.R
import com.example.dashboard.EmployeeActivity
import com.example.dashboard.SalesOrder
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object ReportExporter {
    private const val PDF_MIME = "application/pdf"
    private const val A4_LANDSCAPE_WIDTH_POINTS = 842
    private const val A4_LANDSCAPE_HEIGHT_POINTS = 595
    private const val DOCX_MIME =
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document"

    private class PdfFilePrintAdapter(private val file: File) : PrintDocumentAdapter() {
        override fun onLayout(
            oldAttributes: PrintAttributes?,
            newAttributes: PrintAttributes?,
            cancellationSignal: CancellationSignal?,
            callback: LayoutResultCallback,
            extras: Bundle?
        ) {
            if (cancellationSignal?.isCanceled == true) {
                callback.onLayoutCancelled()
                return
            }
            callback.onLayoutFinished(
                PrintDocumentInfo.Builder(file.name)
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
                    .build(),
                true
            )
        }

        override fun onWrite(
            pages: Array<out PageRange>?,
            destination: ParcelFileDescriptor?,
            cancellationSignal: CancellationSignal?,
            callback: WriteResultCallback
        ) {
            if (destination == null || cancellationSignal?.isCanceled == true) {
                callback.onWriteCancelled()
                return
            }
            runCatching {
                FileInputStream(file).use { input ->
                    FileOutputStream(destination.fileDescriptor).use { output ->
                        input.copyTo(output)
                    }
                }
            }.onSuccess {
                callback.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
            }.onFailure {
                callback.onWriteFailed(it.message)
            }
        }
    }

    fun exportPdf(
        context: Context,
        fileName: String,
        template: String,
        filter: String,
        dateRange: String,
        salesOrders: List<SalesOrder>,
        employees: List<EmployeeActivity>
    ): Result<String> = runCatching {
        val destination = createDestination(context, fileName, PDF_MIME)
        context.contentResolver.openOutputStream(destination.uri)?.use { output ->
            writePdf(
                context,
                output,
                template,
                filter,
                dateRange,
                salesOrders,
                employees
            )
        } ?: error("Unable to open the PDF destination")
        destination.displayPath
    }

    fun exportDocx(
        context: Context,
        fileName: String,
        template: String,
        filter: String,
        dateRange: String,
        salesOrders: List<SalesOrder>,
        employees: List<EmployeeActivity>
    ): Result<String> = runCatching {
        val destination = createDestination(context, fileName, DOCX_MIME)
        context.contentResolver.openOutputStream(destination.uri)?.use { output ->
            writeDocx(
                context,
                output,
                template,
                filter,
                dateRange,
                salesOrders,
                employees
            )
        } ?: error("Unable to open the Word destination")
        destination.displayPath
    }

    fun exportCsv(
        context: Context,
        fileName: String,
        template: String,
        filter: String,
        dateRange: String,
        salesOrders: List<SalesOrder>,
        employees: List<EmployeeActivity>
    ): Result<String> = runCatching {
        val destination = createDestination(context, fileName, "text/csv")
        val (headers, rows) = reportRows(template, filter, salesOrders, employees)
        context.contentResolver.openOutputStream(destination.uri)?.bufferedWriter(Charsets.UTF_8)?.use { writer ->
            writer.write("\uFEFF")
            writer.appendLine(csvRow(listOf(template)))
            writer.appendLine(csvRow(listOf("Filter", filter, "Date Range", dateRange, "Generated", timestamp())))
            writer.appendLine()
            writer.appendLine(csvRow(headers))
            rows.forEach { writer.appendLine(csvRow(it)) }
        } ?: error("Unable to open the CSV destination")
        destination.displayPath
    }

    fun exportXlsx(
        context: Context,
        fileName: String,
        template: String,
        filter: String,
        dateRange: String,
        salesOrders: List<SalesOrder>,
        employees: List<EmployeeActivity>
    ): Result<String> = runCatching {
        val destination = createDestination(
            context,
            fileName,
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        )
        val (headers, rows) = reportRows(template, filter, salesOrders, employees)
        context.contentResolver.openOutputStream(destination.uri)?.use { output ->
            writeXlsx(output, template, filter, dateRange, headers, rows)
        } ?: error("Unable to open the Excel destination")
        destination.displayPath
    }

    fun printPdf(
        context: Context,
        jobName: String,
        template: String,
        filter: String,
        dateRange: String,
        salesOrders: List<SalesOrder>,
        employees: List<EmployeeActivity>
    ): Result<Unit> = runCatching {
        val printFile = File(context.cacheDir, "print_${System.currentTimeMillis()}.pdf")
        FileOutputStream(printFile).use { output ->
            writePdf(context, output, template, filter, dateRange, salesOrders, employees)
        }
        val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
        printManager.print(
            jobName,
            PdfFilePrintAdapter(printFile),
            PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.ISO_A4.asLandscape())
                .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                .setColorMode(PrintAttributes.COLOR_MODE_COLOR)
                .build()
        )
    }

    private data class Destination(val uri: Uri, val displayPath: String)

    private fun csvRow(values: List<String>): String =
        values.joinToString(",") { value -> "\"${value.replace("\"", "\"\"")}\"" }

    private fun writeXlsx(
        output: OutputStream,
        template: String,
        filter: String,
        dateRange: String,
        headers: List<String>,
        rows: List<List<String>>
    ) {
        ZipOutputStream(output).use { zip ->
            zip.addText("[Content_Types].xml", xlsxContentTypes())
            zip.addText("_rels/.rels", xlsxRootRelationships())
            zip.addText("xl/workbook.xml", xlsxWorkbook())
            zip.addText("xl/_rels/workbook.xml.rels", xlsxWorkbookRelationships())
            zip.addText("xl/styles.xml", xlsxStyles())
            zip.addText(
                "xl/worksheets/sheet1.xml",
                xlsxSheet(template, filter, dateRange, headers, rows)
            )
        }
    }

    private fun xlsxSheet(
        template: String,
        filter: String,
        dateRange: String,
        headers: List<String>,
        rows: List<List<String>>
    ): String {
        val lastColumn = excelColumn(headers.size.coerceAtLeast(1))
        val sheetRows = buildString {
            append(xlsxRow(1, listOf(template), 1))
            append(xlsxRow(2, listOf("Filter: $filter | Range: $dateRange | Generated: ${timestamp()}"), 3))
            append(xlsxRow(4, headers, 2))
            rows.forEachIndexed { index, values -> append(xlsxRow(index + 5, values, 0)) }
        }
        val lastRow = (rows.size + 4).coerceAtLeast(4)
        return """
            <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
            <worksheet xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <sheetViews><sheetView workbookViewId="0" showGridLines="0"><pane ySplit="4" topLeftCell="A5" activePane="bottomLeft" state="frozen"/></sheetView></sheetViews>
              <cols>${headers.indices.joinToString("") { "<col min=\"${it + 1}\" max=\"${it + 1}\" width=\"${if (it == 1) 25 else 18}\" customWidth=\"1\"/>" }}</cols>
              <sheetData>$sheetRows</sheetData>
              <mergeCells count="2"><mergeCell ref="A1:${lastColumn}1"/><mergeCell ref="A2:${lastColumn}2"/></mergeCells>
              <autoFilter ref="A4:${lastColumn}$lastRow"/>
              <pageSetup orientation="landscape" fitToWidth="1" fitToHeight="0"/>
            </worksheet>
        """.trimIndent().trimStart()
    }

    private fun xlsxRow(rowNumber: Int, values: List<String>, style: Int): String =
        buildString {
            append("<row r=\"$rowNumber\"${if (rowNumber == 1) " ht=\"28\" customHeight=\"1\"" else ""}>")
            values.forEachIndexed { index, value ->
                val cell = "${excelColumn(index + 1)}$rowNumber"
                append("<c r=\"$cell\" t=\"inlineStr\" s=\"$style\"><is><t xml:space=\"preserve\">${xml(value)}</t></is></c>")
            }
            append("</row>")
        }

    private fun excelColumn(number: Int): String {
        var value = number
        val result = StringBuilder()
        while (value > 0) {
            value--
            result.insert(0, ('A'.code + value % 26).toChar())
            value /= 26
        }
        return result.toString()
    }

    private fun createDestination(context: Context, fileName: String, mimeType: String): Destination {
        val preferences = context.getSharedPreferences("shop_floor_ui", Context.MODE_PRIVATE)
        val savedTreeUri = preferences.getString("report_tree_uri", null)
        if (!savedTreeUri.isNullOrBlank()) {
            val customDestination = runCatching {
                val treeUri = Uri.parse(savedTreeUri)
                val directoryUri = DocumentsContract.buildDocumentUriUsingTree(
                    treeUri,
                    DocumentsContract.getTreeDocumentId(treeUri)
                )
                DocumentsContract.createDocument(
                    context.contentResolver,
                    directoryUri,
                    mimeType,
                    fileName
                ) ?: error("Unable to create the report in the selected folder")
            }.getOrNull()
            if (customDestination != null) {
                val location = preferences.getString("report_location_label", "Selected folder")
                    ?: "Selected folder"
                return Destination(customDestination, "$location/$fileName")
            }

            preferences.edit()
                .remove("report_tree_uri")
                .putString("report_location_label", "Downloads/OCS Reports")
                .apply()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, fileName)
                put(MediaStore.Downloads.MIME_TYPE, mimeType)
                put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/OCS Reports")
                put(MediaStore.Downloads.IS_PENDING, 0)
            }
            val uri = context.contentResolver.insert(
                MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                values
            ) ?: error("Unable to create download")
            return Destination(uri, "Downloads/OCS Reports/$fileName")
        }

        val directory = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
            "OCS Reports"
        ).apply { mkdirs() }
        val file = File(directory, fileName)
        return Destination(Uri.fromFile(file), file.absolutePath)
    }

    private fun reportRows(
        template: String,
        filter: String,
        salesOrders: List<SalesOrder>,
        employees: List<EmployeeActivity>
    ): Pair<List<String>, List<List<String>>> {
        val orders = if (filter == "All Orders") salesOrders else salesOrders.filter { it.id == filter }
        return when (template) {
            "Employee Wise Timesheet" -> {
                val filteredEmployees =
                    if (filter == "All Orders") employees else employees.filter { it.task == filter }
                listOf("EMP ID", "NAME", "CATEGORY", "ASSIGNMENT", "HOURS", "RATE", "TOTAL") to
                    filteredEmployees.map { employee ->
                        listOf(
                            employee.empId.ifBlank { "-" },
                            employee.name,
                            employee.category,
                            employee.task,
                            String.format(Locale.US, "%.2f", employee.hoursClocked),
                            currency(employee.hourlyRate),
                            currency(employee.hoursClocked * employee.hourlyRate)
                        )
                    }
            }

            "Departmental Utilization Report" -> {
                val departments = (orders.map { it.department } + employees.map { it.department })
                    .filter { it.isNotBlank() }
                    .distinct()
                listOf("DEPARTMENT", "ORDERS", "EMPLOYEES", "ACTUAL HOURS", "ACTUAL COST") to
                    departments.map { department ->
                        val departmentOrders = orders.filter {
                            it.department.equals(department, ignoreCase = true)
                        }
                        val departmentEmployees = employees.filter {
                            it.department.equals(department, ignoreCase = true)
                        }
                        listOf(
                            department,
                            departmentOrders.size.toString(),
                            departmentEmployees.size.toString(),
                            String.format(Locale.US, "%.2f", departmentEmployees.sumOf { it.hoursClocked }),
                            currency(departmentEmployees.sumOf { it.hoursClocked * it.hourlyRate })
                        )
                    }
            }

            else -> {
                listOf("SALES ORDER", "CUSTOMER / ITEM", "DEPARTMENT", "STATUS", "PLANNED HRS", "ACTUAL HRS", "ACTUAL COST") to
                    orders.map { order ->
                        val assigned = employees.filter { it.task == order.id }
                        listOf(
                            order.id,
                            order.item,
                            order.department,
                            order.status,
                            String.format(Locale.US, "%.2f", order.plannedManhours),
                            String.format(Locale.US, "%.2f", assigned.sumOf { it.hoursClocked }),
                            currency(assigned.sumOf { it.hoursClocked * it.hourlyRate })
                        )
                    }
            }
        }
    }

    private fun writePdf(
        context: Context,
        output: OutputStream,
        template: String,
        filter: String,
        dateRange: String,
        salesOrders: List<SalesOrder>,
        employees: List<EmployeeActivity>
    ) {
        val (headers, rows) = reportRows(template, filter, salesOrders, employees)
        val document = PdfDocument()
        val pageWidth = A4_LANDSCAPE_WIDTH_POINTS
        val pageHeight = A4_LANDSCAPE_HEIGHT_POINTS
        val margin = 36f
        val rowHeight = 25f
        val contentWidth = pageWidth - margin * 2
        val columnWidth = contentWidth / headers.size
        val logo = BitmapFactory.decodeResource(context.resources, R.drawable.ocs_logo)
        val titlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(46, 16, 101)
            textSize = 20f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        val metaPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(91, 58, 117)
            textSize = 9f
        }
        val headerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            textSize = 8f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        val cellPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(46, 16, 101)
            textSize = 8f
        }
        val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(216, 180, 254)
            strokeWidth = 0.8f
            style = Paint.Style.STROKE
        }

        var rowIndex = 0
        var pageNumber = 1
        do {
            val page = document.startPage(
                PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
            )
            val canvas = page.canvas
            canvas.drawColor(Color.WHITE)
            drawPdfWatermark(canvas, logo, pageWidth.toFloat(), pageHeight.toFloat())
            drawPdfHeaderLogo(canvas, logo, pageWidth.toFloat(), margin)

            canvas.drawText(template, margin, 42f, titlePaint)
            canvas.drawText(
                "Filter: $filter  |  Range: $dateRange  |  Generated: ${timestamp()}",
                margin,
                59f,
                metaPaint
            )
            canvas.drawText("OCS - Delivering Commitments, Crafting Excellence", margin, 74f, metaPaint)

            var top = 92f
            val purple = Paint().apply { color = Color.rgb(109, 40, 217) }
            canvas.drawRect(margin, top, pageWidth - margin, top + rowHeight, purple)
            headers.forEachIndexed { index, header ->
                drawClippedText(
                    canvas,
                    header,
                    margin + index * columnWidth + 5f,
                    top + 16f,
                    columnWidth - 10f,
                    headerPaint
                )
            }
            top += rowHeight

            while (rowIndex < rows.size && top + rowHeight < pageHeight - 35f) {
                if (rowIndex % 2 == 1) {
                    canvas.drawRect(
                        margin,
                        top,
                        pageWidth - margin,
                        top + rowHeight,
                        Paint().apply { color = Color.argb(150, 247, 242, 255) }
                    )
                }
                rows[rowIndex].forEachIndexed { index, value ->
                    drawClippedText(
                        canvas,
                        value,
                        margin + index * columnWidth + 5f,
                        top + 16f,
                        columnWidth - 10f,
                        cellPaint
                    )
                }
                canvas.drawRect(margin, top, pageWidth - margin, top + rowHeight, linePaint)
                top += rowHeight
                rowIndex++
            }

            if (rows.isEmpty()) {
                canvas.drawText("No records match the selected report filters.", margin, top + 24f, cellPaint)
            }
            canvas.drawText(
                "Page $pageNumber",
                pageWidth - margin - 42f,
                pageHeight - 16f,
                metaPaint
            )
            document.finishPage(page)
            pageNumber++
        } while (rowIndex < rows.size)

        document.writeTo(output)
        document.close()
        logo.recycle()
    }

    private fun drawPdfWatermark(canvas: Canvas, logo: Bitmap, pageWidth: Float, pageHeight: Float) {
        val targetWidth = pageWidth * 0.62f
        val ratio = logo.height.toFloat() / logo.width.toFloat()
        val targetHeight = targetWidth * ratio
        val destination = RectF(
            (pageWidth - targetWidth) / 2f,
            (pageHeight - targetHeight) / 2f,
            (pageWidth + targetWidth) / 2f,
            (pageHeight + targetHeight) / 2f
        )
        canvas.drawBitmap(
            logo,
            null,
            destination,
            Paint(Paint.ANTI_ALIAS_FLAG).apply { alpha = 24 }
        )
    }

    private fun drawPdfHeaderLogo(canvas: Canvas, logo: Bitmap, pageWidth: Float, margin: Float) {
        val targetWidth = 142f
        val targetHeight = targetWidth * logo.height.toFloat() / logo.width.toFloat()
        val left = pageWidth - margin - targetWidth
        canvas.drawBitmap(
            logo,
            null,
            RectF(left, 18f, left + targetWidth, 18f + targetHeight),
            Paint(Paint.ANTI_ALIAS_FLAG)
        )
    }

    private fun drawClippedText(
        canvas: Canvas,
        text: String,
        x: Float,
        y: Float,
        maxWidth: Float,
        paint: Paint
    ) {
        val safeText = if (paint.measureText(text) <= maxWidth) {
            text
        } else {
            var clipped = text
            while (clipped.isNotEmpty() && paint.measureText("$clipped...") > maxWidth) {
                clipped = clipped.dropLast(1)
            }
            "$clipped..."
        }
        canvas.drawText(safeText, x, y, paint)
    }

    private fun writeDocx(
        context: Context,
        output: OutputStream,
        template: String,
        filter: String,
        dateRange: String,
        salesOrders: List<SalesOrder>,
        employees: List<EmployeeActivity>
    ) {
        val (headers, rows) = reportRows(template, filter, salesOrders, employees)
        val watermarkBytes = createWatermarkPng(context)
        val logoBytes = createLogoPng(context)
        ZipOutputStream(output).use { zip ->
            zip.addText("[Content_Types].xml", contentTypesXml())
            zip.addText("_rels/.rels", rootRelationshipsXml())
            zip.addText("word/document.xml", documentXml(template, filter, dateRange, headers, rows))
            zip.addText("word/_rels/document.xml.rels", documentRelationshipsXml())
            zip.addText("word/header1.xml", headerXml())
            zip.addText("word/_rels/header1.xml.rels", headerRelationshipsXml())
            zip.putNextEntry(ZipEntry("word/media/ocs-watermark.png"))
            zip.write(watermarkBytes)
            zip.closeEntry()
            zip.putNextEntry(ZipEntry("word/media/ocs-logo.png"))
            zip.write(logoBytes)
            zip.closeEntry()
        }
    }

    private fun createLogoPng(context: Context): ByteArray {
        val source = BitmapFactory.decodeResource(context.resources, R.drawable.ocs_logo)
        return ByteArrayOutputStream().use { bytes ->
            source.compress(Bitmap.CompressFormat.PNG, 100, bytes)
            source.recycle()
            bytes.toByteArray()
        }
    }

    private fun createWatermarkPng(context: Context): ByteArray {
        val source = BitmapFactory.decodeResource(context.resources, R.drawable.ocs_logo)
        val width = 1000
        val height = (width * source.height.toFloat() / source.width.toFloat()).toInt()
        val result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        Canvas(result).drawBitmap(
            source,
            null,
            RectF(0f, 0f, width.toFloat(), height.toFloat()),
            Paint(Paint.ANTI_ALIAS_FLAG).apply { alpha = 28 }
        )
        return ByteArrayOutputStream().use { bytes ->
            result.compress(Bitmap.CompressFormat.PNG, 100, bytes)
            source.recycle()
            result.recycle()
            bytes.toByteArray()
        }
    }

    private fun documentXml(
        template: String,
        filter: String,
        dateRange: String,
        headers: List<String>,
        rows: List<List<String>>
    ): String {
        val tableRows = buildString {
            append(wordTableRow(headers, header = true))
            if (rows.isEmpty()) {
                append(wordTableRow(listOf("No records match the selected report filters."), header = false))
            } else {
                rows.forEach { append(wordTableRow(it, header = false)) }
            }
        }
        return """
            <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
            <w:document xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
                xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
                xmlns:v="urn:schemas-microsoft-com:vml"
                xmlns:o="urn:schemas-microsoft-com:office:office">
              <w:body>
                ${wordLogo()}
                ${wordParagraph(template, 32, "6D28D9", true)}
                ${wordParagraph("Filter: $filter | Range: $dateRange | Generated: ${timestamp()}", 18, "5B3A75", false)}
                ${wordParagraph("OCS - Delivering Commitments, Crafting Excellence", 18, "5B3A75", true)}
                <w:tbl>
                  <w:tblPr>
                    <w:tblW w:w="9360" w:type="dxa"/>
                    <w:tblBorders>
                      <w:top w:val="single" w:sz="4" w:color="D8B4FE"/>
                      <w:left w:val="single" w:sz="4" w:color="D8B4FE"/>
                      <w:bottom w:val="single" w:sz="4" w:color="D8B4FE"/>
                      <w:right w:val="single" w:sz="4" w:color="D8B4FE"/>
                      <w:insideH w:val="single" w:sz="4" w:color="EDE9FE"/>
                      <w:insideV w:val="single" w:sz="4" w:color="EDE9FE"/>
                    </w:tblBorders>
                  </w:tblPr>
                  $tableRows
                </w:tbl>
                <w:sectPr>
                  <w:headerReference w:type="default" r:id="rIdHeader"/>
                  <w:pgSz w:w="15840" w:h="12240" w:orient="landscape"/>
                  <w:pgMar w:top="720" w:right="720" w:bottom="720" w:left="720" w:header="360" w:footer="360" w:gutter="0"/>
                </w:sectPr>
              </w:body>
            </w:document>
        """.trimIndent().trimStart()
    }

    private fun wordTableRow(values: List<String>, header: Boolean): String {
        val fill = if (header) "6D28D9" else "FFFFFF"
        val textColor = if (header) "FFFFFF" else "2E1065"
        return buildString {
            append("<w:tr>")
            values.forEach { value ->
                append(
                    """
                    <w:tc>
                      <w:tcPr><w:shd w:val="clear" w:color="auto" w:fill="$fill"/><w:tcMar><w:top w:w="90" w:type="dxa"/><w:left w:w="90" w:type="dxa"/><w:bottom w:w="90" w:type="dxa"/><w:right w:w="90" w:type="dxa"/></w:tcMar></w:tcPr>
                      <w:p><w:r><w:rPr><w:color w:val="$textColor"/>${if (header) "<w:b/>" else ""}<w:sz w:val="17"/></w:rPr><w:t>${xml(value)}</w:t></w:r></w:p>
                    </w:tc>
                    """.trimIndent()
                )
            }
            append("</w:tr>")
        }
    }

    private fun wordParagraph(
        text: String,
        size: Int,
        color: String,
        bold: Boolean
    ): String =
        "<w:p><w:pPr><w:spacing w:after=\"120\"/></w:pPr><w:r><w:rPr>" +
            (if (bold) "<w:b/>" else "") +
            "<w:color w:val=\"$color\"/><w:sz w:val=\"$size\"/></w:rPr><w:t>${xml(text)}</w:t></w:r></w:p>"

    private fun wordLogo() = """
        <w:p>
          <w:pPr><w:jc w:val="right"/><w:spacing w:after="80"/></w:pPr>
          <w:r>
            <w:pict>
              <v:rect id="OCSReportLogo"
                  style="width:170pt;height:55pt" filled="f" stroked="f">
                <v:imagedata r:id="rIdLogo" o:title="OCS"/>
              </v:rect>
            </w:pict>
          </w:r>
        </w:p>
    """.trimIndent()

    private fun xlsxContentTypes() = """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types">
          <Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/>
          <Default Extension="xml" ContentType="application/xml"/>
          <Override PartName="/xl/workbook.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml"/>
          <Override PartName="/xl/worksheets/sheet1.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml"/>
          <Override PartName="/xl/styles.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.styles+xml"/>
        </Types>
    """.trimIndent()

    private fun xlsxRootRelationships() = """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
          <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="xl/workbook.xml"/>
        </Relationships>
    """.trimIndent()

    private fun xlsxWorkbook() = """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <workbook xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main"
            xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships">
          <sheets><sheet name="OCS Report" sheetId="1" r:id="rId1"/></sheets>
          <calcPr calcId="191029" fullCalcOnLoad="1"/>
        </workbook>
    """.trimIndent()

    private fun xlsxWorkbookRelationships() = """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
          <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet" Target="worksheets/sheet1.xml"/>
          <Relationship Id="rId2" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles" Target="styles.xml"/>
        </Relationships>
    """.trimIndent()

    private fun xlsxStyles() = """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <styleSheet xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <fonts count="3">
            <font><sz val="10"/><name val="Calibri"/></font>
            <font><b/><sz val="16"/><color rgb="FF6D28D9"/><name val="Calibri"/></font>
            <font><b/><sz val="10"/><color rgb="FFFFFFFF"/><name val="Calibri"/></font>
          </fonts>
          <fills count="3">
            <fill><patternFill patternType="none"/></fill>
            <fill><patternFill patternType="gray125"/></fill>
            <fill><patternFill patternType="solid"><fgColor rgb="FF6D28D9"/><bgColor indexed="64"/></patternFill></fill>
          </fills>
          <borders count="1"><border><left/><right/><top/><bottom/><diagonal/></border></borders>
          <cellStyleXfs count="1"><xf numFmtId="0" fontId="0" fillId="0" borderId="0"/></cellStyleXfs>
          <cellXfs count="4">
            <xf numFmtId="0" fontId="0" fillId="0" borderId="0" xfId="0"/>
            <xf numFmtId="0" fontId="1" fillId="0" borderId="0" xfId="0"/>
            <xf numFmtId="0" fontId="2" fillId="2" borderId="0" xfId="0"><alignment horizontal="center"/></xf>
            <xf numFmtId="0" fontId="0" fillId="0" borderId="0" xfId="0"><alignment wrapText="1"/></xf>
          </cellXfs>
          <cellStyles count="1"><cellStyle name="Normal" xfId="0" builtinId="0"/></cellStyles>
        </styleSheet>
    """.trimIndent()

    private fun contentTypesXml() = """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types">
          <Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/>
          <Default Extension="xml" ContentType="application/xml"/>
          <Default Extension="png" ContentType="image/png"/>
          <Override PartName="/word/document.xml" ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml"/>
          <Override PartName="/word/header1.xml" ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.header+xml"/>
        </Types>
    """.trimIndent()

    private fun rootRelationshipsXml() = """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
          <Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="word/document.xml"/>
        </Relationships>
    """.trimIndent()

    private fun documentRelationshipsXml() = """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
          <Relationship Id="rIdHeader" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/header" Target="header1.xml"/>
          <Relationship Id="rIdLogo" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/image" Target="media/ocs-logo.png"/>
        </Relationships>
    """.trimIndent()

    private fun headerRelationshipsXml() = """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
          <Relationship Id="rIdWatermark" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/image" Target="media/ocs-watermark.png"/>
        </Relationships>
    """.trimIndent()

    private fun headerXml() = """
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <w:hdr xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
            xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
            xmlns:v="urn:schemas-microsoft-com:vml"
            xmlns:o="urn:schemas-microsoft-com:office:office">
          <w:p>
            <w:pPr><w:jc w:val="center"/></w:pPr>
            <w:r>
              <w:pict>
                <v:rect id="OCSWatermark" o:spid="_x0000_s2049"
                    style="position:absolute;margin-left:0;margin-top:190pt;width:440pt;height:145pt;z-index:-251654144;mso-wrap-edited:f;mso-position-horizontal:center;mso-position-horizontal-relative:page;mso-position-vertical:center;mso-position-vertical-relative:page"
                    filled="f" stroked="f">
                  <v:imagedata r:id="rIdWatermark" o:title="OCS Watermark"/>
                  <w10:wrap xmlns:w10="urn:schemas-microsoft-com:office:word" type="none"/>
                </v:rect>
              </w:pict>
            </w:r>
          </w:p>
        </w:hdr>
    """.trimIndent()

    private fun ZipOutputStream.addText(path: String, text: String) {
        putNextEntry(ZipEntry(path))
        write(text.toByteArray(Charsets.UTF_8))
        closeEntry()
    }

    private fun currency(value: Double): String =
        NumberFormat.getCurrencyInstance(Locale("en", "IN")).format(value)

    private fun timestamp(): String =
        SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date())

    private fun xml(value: String): String =
        value.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&apos;")
}
