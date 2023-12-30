package ru.pkozlov.brackets.excel.core.service

import org.apache.poi.ss.usermodel.WorkbookFactory
import ru.pkozlov.brackets.excel.core.dto.BracketDto
import ru.pkozlov.brackets.excel.core.service.fill.fill8
import ru.pkozlov.brackets.excel.core.util.addSheet

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
                WorkbookFactory.create(template)
                    .apply {
                        setSheetName(TEMPLATE_SHEET_INDEX, bracket.category.weightCategory.weightLimit.toString())
                        getSheetAt(TEMPLATE_SHEET_INDEX)
                            .fill8(bracket)
                            .run(mainWorkbook::addSheet)
                    }
                    .close()
            }
        }
        fileService.output().use { output ->
            mainWorkbook.write(output)
            mainWorkbook.close()
        }
    }
}