package ru.pkozlov.brackets.excel.core.service

import ru.pkozlov.brackets.excel.core.dto.Template
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class FileService(
    private val outputFilePath: String = "filled_template.xlsx"
) {
    fun readData(template: Template): InputStream =
        FileService::class.java.getResourceAsStream(template.path) ?: throw FileNotFoundException()

    fun output(): OutputStream = FileOutputStream(outputFilePath)
}