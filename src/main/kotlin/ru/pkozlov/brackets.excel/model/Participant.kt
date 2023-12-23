package ru.pkozlov.brackets.excel.model

import java.math.BigDecimal

data class Participant(
    val lastName: String,
    val firstName: String,
    val age: Int,
    val weight: BigDecimal
)