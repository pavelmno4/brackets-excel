package ru.pkozlov.brackets.excel.core.service

import ru.pkozlov.brackets.excel.core.config.TemplatesConfig
import ru.pkozlov.brackets.excel.core.dto.Template
import ru.pkozlov.brackets.excel.core.exception.TooLargeSizeException

class TemplateDefinitionComponent(
    private val templatesConfig: TemplatesConfig
) {
    fun define(participantsSize: Int): Template =
        when {
            participantsSize == 1 -> Template(size = 1, path = templatesConfig.templatePath1)
            participantsSize == 2 -> Template(size = 2, path = templatesConfig.templatePath2)
            participantsSize == 3 -> Template(size = 3, path = templatesConfig.templatePath3)
            participantsSize == 4 -> Template(size = 4, path = templatesConfig.templatePath4)
            participantsSize <= 8 -> Template(size = 8, path = templatesConfig.templatePath8)
            participantsSize <= 16 -> Template(size = 16, path = templatesConfig.templatePath16)
            else -> throw TooLargeSizeException("Count of participants is $participantsSize. Max grid is 16.")
        }
}