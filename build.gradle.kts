import org.jetbrains.compose.desktop.application.dsl.TargetFormat

val kodein_version = "7.19.0"
val apache_poi_version = "5.2.5"
val logback_version = "1.4.11"

plugins {
    kotlin("jvm") version "1.9.20"
    id("org.jetbrains.compose") version "1.5.10"
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
    implementation("org.kodein.di:kodein-di-jvm:$kodein_version")

    /** Excel */
    implementation("org.apache.poi:poi:$apache_poi_version")
    implementation("org.apache.poi:poi-ooxml:$apache_poi_version")

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