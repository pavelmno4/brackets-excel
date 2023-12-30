package ru.pkozlov.brackets.excel.core.dictionary

import org.apache.poi.ss.usermodel.*

val Workbook.titleFont: Font
    get() = createFont().apply {
        fontHeightInPoints = 18
        fontName = "Arial"
    }

val Workbook.titleStyle: CellStyle
    get() = createCellStyle().apply {
        verticalAlignment = VerticalAlignment.BOTTOM
        alignment = HorizontalAlignment.CENTER
        setFont(titleFont)
    }

val Workbook.regularFont: Font
    get() = createFont().apply {
        fontHeightInPoints = 8
        fontName = "Arial"
        bold = true
    }

val Workbook.regularStyle: CellStyle
    get() = createCellStyle().apply {
        verticalAlignment = VerticalAlignment.BOTTOM
        alignment = HorizontalAlignment.LEFT
        borderBottom = BorderStyle.MEDIUM
        setFont(regularFont)
    }