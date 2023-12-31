package ru.pkozlov.brackets.excel.core.service

import org.apache.poi.ss.usermodel.WorkbookFactory
import org.jxls.common.Context
import org.jxls.util.JxlsHelper
import ru.pkozlov.brackets.excel.core.dto.BracketDto
import ru.pkozlov.brackets.excel.core.util.addSheet
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class TemplateService(
    private val fileService: FileService
) {
    companion object Companion {
        const val XSSF = true
        const val TEMPLATE_SHEET_INDEX = 0
    }

    fun process(brackets: List<BracketDto>): Unit = WorkbookFactory.create(XSSF).let { mainWorkbook ->
        brackets.forEach { bracket ->
            bracket.template.run(fileService::readData).let { template ->
                bracket.graph.flat(HashMap()) { it.level.name }.let { flatGraph ->
                    ByteArrayOutputStream().use { outputStream ->
                        Context().apply {
                            putVar("tournamentName", bracket.tournamentName)
                            putVar("birthYearRange", "${bracket.category.birthYearRange.start}-${bracket.category.birthYearRange.endInclusive}")
                            putVar("weightCategory", bracket.category.weightCategory.weightLimit)
                            putVar("graph", flatGraph)
                        }.let { context -> JxlsHelper.getInstance().processTemplate(template, outputStream, context) }

                        ByteArrayInputStream(outputStream.toByteArray()).use(WorkbookFactory::create).apply {
                            setSheetName(TEMPLATE_SHEET_INDEX, bracket.category.weightCategory.weightLimit.toString())
                            getSheetAt(TEMPLATE_SHEET_INDEX).run(mainWorkbook::addSheet)
                            close()
                        }
                    }
                }
            }
        }
        fileService.output().use { output ->
            mainWorkbook.write(output)
            mainWorkbook.close()
        }
    }
}