package ru.pkozlov.brackets.excel.core.dto

data class BracketDto(
    val tournamentName: String,
    val category: Category,
    val template: Template,
    val graph: Node
)