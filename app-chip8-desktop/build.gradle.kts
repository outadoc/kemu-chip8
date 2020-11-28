import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    application
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}

application {
    mainClassName = "fr.outadoc.kemu.chip8.desktop.EntryPointKt"
}

dependencies {
    implementation(project(":lib-chip8"))
}
