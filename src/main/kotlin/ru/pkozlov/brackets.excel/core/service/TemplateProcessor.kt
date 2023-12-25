package ru.pkozlov.brackets.excel.core.service

import ru.pkozlov.brackets.excel.core.dto.Node
import java.io.FileOutputStream

class TemplateProcessor {
    fun process(graph: Node): Unit {
        TemplateProcessor::class.java.getResourceAsStream("/template_8_(1).xlsx")?.use { template ->
            FileOutputStream("filled_participants.xlsx").use { output ->

            }
        }
    }
}