package ru.pkozlov.brackets.excel.core.config

import kotlinx.serialization.Serializable

@Serializable
data class TemplatesConfig(
    val templatePathEmpty: String,
    val templatePath1: String,
    val templatePath2: String,
    val templatePath3: String,
    val templatePath4: String,
    val templatePath8: String,
    val templatePath16: String
)