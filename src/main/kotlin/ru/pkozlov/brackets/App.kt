package ru.pkozlov.brackets

import io.ktor.server.application.*
import io.ktor.server.netty.*
import ru.pkozlov.brackets.plugin.configureRouting
import ru.pkozlov.brackets.plugin.configureSerialization

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureRouting()
    configureSerialization()
}