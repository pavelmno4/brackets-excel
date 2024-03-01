import org.jetbrains.compose.desktop.application.dsl.TargetFormat

val kodein_version = "7.21.1"
val apache_poi_version = "5.2.5"
val jxls_version = "3.0.0-beta1"
val log4j_version = "2.22.1"

plugins {
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.serialization") version "1.9.21"
    id("org.jetbrains.compose") version "1.5.11"
}

group = "ru.pkozlov"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    /** DI */
    implementation("org.kodein.di:kodein-di-framework-compose:$kodein_version")

    /** Excel */
    implementation("org.apache.poi:poi:$apache_poi_version")
    implementation("org.apache.poi:poi-ooxml:$apache_poi_version")
    implementation("org.jxls:jxls-poi:$jxls_version")

    /** Desktop */
    implementation(compose.desktop.currentOs)

    /** UI */
    implementation("androidx.graphics:graphics-shapes:1.0.0-alpha04")

    /** Config serialization */
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-hocon:1.6.2")

    /** Logging */
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")
    implementation("commons-logging:commons-logging:1.3.0")
    implementation("org.apache.logging.log4j:log4j-core:$log4j_version")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

compose.desktop {
    application {
        mainClass = "ru.pkozlov.brackets.excel.AppKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Brackets Excel"
            packageVersion = version as String
        }
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}