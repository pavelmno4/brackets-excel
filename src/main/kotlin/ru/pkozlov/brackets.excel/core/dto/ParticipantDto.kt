package ru.pkozlov.brackets.excel.core.dto

import ru.pkozlov.brackets.excel.core.dictionary.Category
import java.math.BigDecimal
import java.time.LocalDate

data class ParticipantDto(
    val lastName: String,
    val firstName: String,
    val birthDate: LocalDate,
    val weight: BigDecimal,
    val category: Category,
    val team: Team
)