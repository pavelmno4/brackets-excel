package ru.pkozlov.brackets.excel.core.util

import org.kodein.di.instance
import ru.pkozlov.brackets.excel.core.config.TemplatesConfig
import ru.pkozlov.brackets.excel.core.di.di
import ru.pkozlov.brackets.excel.core.dto.BracketDto
import ru.pkozlov.brackets.excel.core.dto.Category
import ru.pkozlov.brackets.excel.core.dto.Node
import ru.pkozlov.brackets.excel.core.dto.Template

private val templatesConfig: TemplatesConfig by di.instance()

fun emptyBracketDto(
    tournamentName: String,
    category: Category
): BracketDto = BracketDto(
    tournamentName = tournamentName,
    category = category,
    template = Template(size = 0, path = templatesConfig.templatePathEmpty),
    graph = Node(level = Node.Level.ZERO)
)