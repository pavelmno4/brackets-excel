val kotlin_version = "1.9.21"
val ktor_version = "2.3.7"
val logback_version = "1.4.11"

plugins {
    kotlin("jvm") version "1.9.21"
    id("io.ktor.plugin") version "2.3.4"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.21"
}

group = "ru.pkozlov"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    /** Ktor */
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-partial-content:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")

    /** Logging */
    implementation("ch.qos.logback:logback-classic:$logback_version")

    /** Test */
    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("ru.pkozlov.brackets.AppKt")
}

ktor {
    docker {
        jreVersion.set(JavaVersion.VERSION_17)
        localImageName.set("brackets")
        imageTag.set("1.0")
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}