package ru.pkozlov.brackets.excel

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.kodein.di.instance
import ru.pkozlov.brackets.excel.core.di.di
import ru.pkozlov.brackets.excel.core.dto.BracketDto
import ru.pkozlov.brackets.excel.core.dto.Category
import ru.pkozlov.brackets.excel.core.dto.ParticipantDto
import ru.pkozlov.brackets.excel.core.dto.WeightCategory
import ru.pkozlov.brackets.excel.core.service.BracketGenerationService
import ru.pkozlov.brackets.excel.core.service.TemplateDefinitionComponent
import ru.pkozlov.brackets.excel.core.service.TemplateService
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Year
import java.util.Calendar

@Preview
@Composable
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
    }
}

//fun main() = application {
//    Window(onCloseRequest = ::exitApplication) {
//        App()
//    }
//}

fun main(): Unit {
    val participants: List<ParticipantDto> = listOf(
        ParticipantDto(
            lastName = "Григорьев",
            firstName = "Игорь",
            birthDate = LocalDate.of(2009, Calendar.MARCH, 15),
            weight = BigDecimal("51.8"),
            category = Category(
                birthYearRange = Year.of(2009)..Year.of(2010),
                weightCategory = WeightCategory(52)
            ),
            team = "Кирпич"
        ),
        ParticipantDto(
            lastName = "Алтанин",
            firstName = "Сергей",
            birthDate = LocalDate.of(2009, Calendar.MARCH, 15),
            weight = BigDecimal("51.8"),
            category = Category(
                birthYearRange = Year.of(2009)..Year.of(2010),
                weightCategory = WeightCategory(52)
            ),
            team = "Кирпич"
        ),
        ParticipantDto(
            lastName = "Матросов",
            firstName = "Константин",
            birthDate = LocalDate.of(2009, Calendar.MARCH, 15),
            weight = BigDecimal("51.8"),
            category = Category(
                birthYearRange = Year.of(2009)..Year.of(2010),
                weightCategory = WeightCategory(52)
            ),
            team = "Колючка"
        ),
        ParticipantDto(
            lastName = "Ильин",
            firstName = "Геннадий",
            birthDate = LocalDate.of(2009, Calendar.MARCH, 15),
            weight = BigDecimal("51.8"),
            category = Category(
                birthYearRange = Year.of(2009)..Year.of(2010),
                weightCategory = WeightCategory(52)
            ),
            team = "Антрант"
        ),
        ParticipantDto(
            lastName = "Зайцев",
            firstName = "Антон",
            birthDate = LocalDate.of(2009, Calendar.MARCH, 15),
            weight = BigDecimal("51.8"),
            category = Category(
                birthYearRange = Year.of(2009)..Year.of(2010),
                weightCategory = WeightCategory(52)
            ),
            team = "Лесополоса"
        ),
        ParticipantDto(
            lastName = "Василевский",
            firstName = "Николай",
            birthDate = LocalDate.of(2009, Calendar.MARCH, 15),
            weight = BigDecimal("51.8"),
            category = Category(
                birthYearRange = Year.of(2009)..Year.of(2010),
                weightCategory = WeightCategory(52)
            ),
            team = "Дренаж"
        ),
        ParticipantDto(
            lastName = "Ирпатин",
            firstName = "Никита",
            birthDate = LocalDate.of(2009, Calendar.MARCH, 15),
            weight = BigDecimal("51.8"),
            category = Category(
                birthYearRange = Year.of(2009)..Year.of(2010),
                weightCategory = WeightCategory(52)
            ),
            team = "Дренаж"
        ),
        ParticipantDto(
            lastName = "Валканин",
            firstName = "Алексей",
            birthDate = LocalDate.of(2009, Calendar.MARCH, 15),
            weight = BigDecimal("51.8"),
            category = Category(
                birthYearRange = Year.of(2009)..Year.of(2010),
                weightCategory = WeightCategory(52)
            ),
            team = "Румчик"
        ),
        ParticipantDto(
            lastName = "Афанасьев",
            firstName = "Артём",
            birthDate = LocalDate.of(2009, Calendar.MARCH, 15),
            weight = BigDecimal("51.8"),
            category = Category(
                birthYearRange = Year.of(2009)..Year.of(2010),
                weightCategory = WeightCategory(52)
            ),
            team = "Кинг"
        )
    )

    val templateDefinitionComponent: TemplateDefinitionComponent by di.instance()

    val brackets: List<BracketDto> = listOf(
        BracketGenerationService(templateDefinitionComponent).generate(
            tournamentName = "Первенство Московской области по Панкратиону",
            category = Category(
                birthYearRange = Year.of(2009)..Year.of(2010),
                weightCategory = WeightCategory(52)
            ),
            participants = participants
        )
    )

    val templateService: TemplateService by di.instance()
    templateService.process(brackets)
}