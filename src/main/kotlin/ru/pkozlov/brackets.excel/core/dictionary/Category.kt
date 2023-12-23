package ru.pkozlov.brackets.excel.core.dictionary

import java.time.Year

data class Category(
    val birthYearRange: ClosedRange<Year>,
    val weightCategory: WeightCategory
)