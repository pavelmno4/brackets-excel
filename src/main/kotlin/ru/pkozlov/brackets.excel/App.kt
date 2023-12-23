package ru.pkozlov.brackets.excel

import io.github.oshai.kotlinlogging.KotlinLogging
import ru.pkozlov.brackets.excel.model.Participant
import ru.pkozlov.brackets.excel.service.TemplateProcessor
import java.math.BigDecimal

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>): Unit {
    val participants: List<Participant> = listOf(
        Participant(
            lastName = "Григорьев",
            firstName = "Игорь",
            age = 12,
            weight = BigDecimal("52.4")
        ),
        Participant(
            lastName = "Клопанов",
            firstName = "Аркадий",
            age = 11,
            weight = BigDecimal("50.3")
        ),
        Participant(
            lastName = "Мироничев",
            firstName = "Павел",
            age = 12,
            weight = BigDecimal("51.8")
        ),
        Participant(
            lastName = "Алкелин",
            firstName = "Тимур",
            age = 12,
            weight = BigDecimal("51.4")
        )
    )

    TemplateProcessor().process(participants)
}