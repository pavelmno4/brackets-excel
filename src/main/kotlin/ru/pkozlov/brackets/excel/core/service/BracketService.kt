package ru.pkozlov.brackets.excel.core.service

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import ru.pkozlov.brackets.excel.core.dto.Category
import ru.pkozlov.brackets.excel.core.dto.ParticipantDto
import ru.pkozlov.brackets.excel.core.dto.WeightCategory
import java.time.Year

class BracketService(
    private val fileService: FileService,
    private val bracketGenerationService: BracketGenerationService,
    private val templateService: TemplateService
) {
    fun createBrackets(filePaths: Collection<String>) {
        fileService.readData(filePaths).forEach { inputFile ->
            WorkbookFactory.create(inputFile).map { sheet ->
                bracketGenerationService.generate(
                    tournamentName = sheet.tournamentName,
                    category = sheet.category,
                    participants = sheet.extractParticipants()
                )
            }.run(templateService::process)
        }
    }

    private val Sheet.tournamentName: String
        get() = getRow(0).getCell(0).stringCellValue

    private val Sheet.category: Category
        get() = Category(
            birthYearRange = getRow(4).getCell(3).stringCellValue.run(::parseRange),
            weightCategory = getRow(4).getCell(6).weightCategoryValue
        )

    private fun Sheet.extractParticipants(): List<ParticipantDto> = mapIndexedNotNull { index, row ->
        takeIf { index > 5 && row.getCell(1)?.stringCellValue?.isNotBlank() ?: false }?.run {
            ParticipantDto(
                fullName = row.getCell(1).stringCellValue,
                team = row.getCell(6).stringCellValue
            )
        }
    }

    private fun parseRange(rowValue: String): ClosedRange<Year> =
        rowValue.split('-').run { Year.parse(first())..Year.parse(last()) }

    private val Cell.weightCategoryValue: WeightCategory
        get() = when (cellType) {
            CellType.NUMERIC -> numericCellValue.toInt().toString().run(::WeightCategory)
            CellType.STRING -> stringCellValue.run(::WeightCategory)
            else -> WeightCategory(weightLimit = "0")
        }
}