package ru.pkozlov.brackets.excel.core.service

import org.jxls.common.Context
import org.jxls.util.JxlsHelper
import ru.pkozlov.brackets.excel.core.dto.ParticipantDto
import java.io.FileOutputStream

class TemplateProcessor {
    fun process(participants: Collection<ParticipantDto>): Unit {
        TemplateProcessor::class.java.getResourceAsStream("/participants_template.xlsx")?.use { template ->
            FileOutputStream("bracket_excel/filled_participants.xlsx").use { output ->
                Context().apply { putVar("participants", participants) }.let { context ->
                    JxlsHelper.getInstance().processTemplate(template, output, context)
                }
            }
        }
    }
}