package ru.pkozlov.brackets.excel.core.service

import ru.pkozlov.brackets.excel.core.dto.Template
import java.io.*

class FileService(
    private val outputFilePath: String = "filled_template.xlsx"
) {
    fun readData(template: Template): File = FileService::class.java.getResource(template.path)?.file?.run(::File) ?: throw FileNotFoundException()

    fun output(): OutputStream = FileOutputStream(outputFilePath)
}