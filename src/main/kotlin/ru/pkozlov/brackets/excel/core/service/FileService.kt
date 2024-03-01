package ru.pkozlov.brackets.excel.core.service

import ru.pkozlov.brackets.excel.core.dto.Template
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.createDirectory
import kotlin.io.path.exists

class FileService(
    private val defaultOutputPath: String = "/BracketCreator"
) {
    private val userHomePath: Path = Path(System.getProperty("user.home"))

    fun readData(paths: Collection<String>): List<File> = paths.map(::File)

    fun readData(template: Template): InputStream =
        FileService::class.java.getResourceAsStream(template.path) ?: throw FileNotFoundException()

    fun output(fileName: String): OutputStream = FileOutputStream(fileName)
}