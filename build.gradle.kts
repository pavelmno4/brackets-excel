import org.jetbrains.compose.desktop.application.dsl.TargetFormat

val kodein_version = "7.21.1"
val apache_poi_version = "5.2.5"
val jxls_version = "3.0.0-beta1"
val logback_version = "1.4.12"

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

compose.desktop {
    application {
        mainClass = "AppKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = rootProject.name
            packageVersion = version as String
        }
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}