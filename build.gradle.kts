import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.30"
}

group = "me.crawford"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test-junit"))
    implementation ("com.fasterxml.jackson.core:jackson-core:2.12.+")
    implementation ("com.fasterxml.jackson.core:jackson-annotations:2.12.+")
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.12.+")
    implementation ("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.12.+")

    implementation ("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.+")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}