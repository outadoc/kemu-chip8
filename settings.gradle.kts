rootProject.name = "CHIP-8"

include(":chip8")
include(":app-chip8-desktop")
include(":kemu")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        jcenter()
        mavenCentral()
    }
}