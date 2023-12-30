package ru.pkozlov.brackets.excel.core.dto

import java.time.Year

data class Category(
    val birthYearRange: ClosedRange<Year>,
    val weightCategory: WeightCategory
)