package ru.pkozlov.brackets.excel.core.service

import org.apache.poi.ss.usermodel.WorkbookFactory
import org.jxls.transform.poi.JxlsPoiTemplateFillerBuilder
import ru.pkozlov.brackets.excel.core.dto.BracketDto
import ru.pkozlov.brackets.excel.core.dto.Category
import ru.pkozlov.brackets.excel.core.util.addSheet
import java.io.ByteArrayInputStream

class TemplateService(
    private val fileService: FileService
) {
    companion object Companion {
        private const val XSSF = true
        private const val TEMPLATE_SHEET_INDEX = 0
    }

    fun process(brackets: List<BracketDto>): Unit = WorkbookFactory.create(XSSF).let { mainWorkbook ->
        brackets.forEach { bracket ->
            bracket.template.run(fileService::readData).let { template ->
                bracket.graph.flat(HashMap()) { it.level.name }.let { flatGraph ->
                    HashMap<String, Any>().let { context ->
                        context["tournamentName"] = bracket.tournamentName
                        context["birthYearRange"] = "${bracket.category.birthYearRange.start}-${bracket.category.birthYearRange.endInclusive}"
                        context["weightCategory"] = bracket.category.weightCategory.weightLimit
                        context["graph"] = flatGraph

                        JxlsPoiTemplateFillerBuilder.newInstance()
                            .withTemplate(template)
                            .buildAndFill(context)
                            .run(::ByteArrayInputStream)
                            .use(WorkbookFactory::create)
                            .apply {
                                setSheetName(TEMPLATE_SHEET_INDEX, bracket.category.weightCategory.weightLimit.toString())
                                getSheetAt(TEMPLATE_SHEET_INDEX).run(mainWorkbook::addSheet)
                                close()
                            }
                    }
                }
            }
        }
        brackets.firstOrNull()?.category?.outputFileName?.run(fileService::output).use { output ->
            mainWorkbook.write(output)
            mainWorkbook.close()
        }
    }

    private val Category.outputFileName: String
        get() = birthYearRange.run { "$start-$endInclusive.xlsx" }
}