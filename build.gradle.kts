val kotlin_version = "1.9.21"
val kodein_version = "7.19.0"
val jxls_version = "2.14.0"
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

    /** Excel templates */
    implementation("org.jxls:jxls-poi:$jxls_version")

    /** Logging */
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")
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