package ru.pkozlov.brackets.excel.core.di

import com.typesafe.config.ConfigFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.pkozlov.brackets.excel.core.config.TemplatesConfig
import ru.pkozlov.brackets.excel.core.service.BracketGenerationService
import ru.pkozlov.brackets.excel.core.service.TemplateDefinitionComponent

@OptIn(ExperimentalSerializationApi::class)
val di = DI {
    bindSingleton<TemplatesConfig> {
        ConfigFactory.parseResources("application.conf").getConfig("templates").run(Hocon::decodeFromConfig)
    }
    bindSingleton { TemplateDefinitionComponent(instance()) }
    bindSingleton { BracketGenerationService(instance()) }
}