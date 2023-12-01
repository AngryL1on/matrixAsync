plugins {
    kotlin("jvm") version "1.9.21"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}