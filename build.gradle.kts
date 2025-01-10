plugins {
    kotlin("jvm") version "2.0.20"
}

group = "dev.kumchatka"
version = "1.1.3"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    // Test libraries
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("com.google.zxing:core:3.5.3")
    testImplementation("com.google.zxing:javase:3.5.3")

    // Telegram Bot API
    implementation("io.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:6.1.0")

    // QR codes libraries
    implementation("io.nayuki:qrcodegen:1.8.0")

    // Logging
    implementation("org.slf4j:slf4j-simple:2.0.0")

    // Config file
    implementation("com.natpryce:konfig:1.6.10.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(16)
}
