package ru.pkozlov.brackets.excel.core.service.fill

import org.apache.poi.ss.usermodel.Sheet
import ru.pkozlov.brackets.excel.core.dictionary.regularFont
import ru.pkozlov.brackets.excel.core.dictionary.regularStyle
import ru.pkozlov.brackets.excel.core.dictionary.titleStyle
import ru.pkozlov.brackets.excel.core.dto.BracketDto
import ru.pkozlov.brackets.excel.core.dto.Node
import java.util.*

fun Sheet.fill8(bracket: BracketDto): Sheet = apply {
    // Заголовок
    getRow(0).getCell(2).apply {
        setCellValue(bracket.tournamentName)
        cellStyle = workbook.titleStyle
    }

    // Возрастная категория
    getRow(2).getCell(14).apply {
        setCellValue("${bracket.category.birthYearRange.start.value}-${bracket.category.birthYearRange.endInclusive.value}")
        cellStyle = workbook.regularStyle
    }

    // Весовая категория
    getRow(4).getCell(14).apply {
        setCellValue(bracket.category.weightCategory.weightLimit.toDouble())
        cellStyle = workbook.regularStyle.apply {
            setFont(workbook.regularFont.apply { fontHeightInPoints = 10 })
        }
    }

    // Сетки
    val flatGraph: TreeMap<Node.Level, LinkedList<Node>> =
        bracket.graph.flat(TreeMap<Node.Level, LinkedList<Node>>(Node.Level.comporator)) { it.level }

    // Первый круг
    for (i in 27 downTo 11 step 2) {
        if (i != 19)
            getRow(i).getCell(1).apply {
                flatGraph[Node.Level.THREE]?.poll()?.participant?.let { participant ->
                    setCellValue("${participant.lastName} ${participant.firstName} (${participant.team.name})")
                }
                cellStyle = workbook.regularStyle
            }
    }
}