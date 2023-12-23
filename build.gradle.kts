val kotlin_version = "1.9.21"
val kodein_version = "7.19.0"
val logback_version = "1.4.11"

plugins {
    kotlin("jvm") version "1.9.21"
    application
}

group = "ru.pkozlov"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    /** DI */
    implementation("org.kodein.di:kodein-di-jvm:$kodein_version")

    /** Logging */
    implementation("ch.qos.logback:logback-classic:$logback_version")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("ru.pkozlov.brackets.excel.AppKt")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}