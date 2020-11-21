buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.20")
    }
}

group = "fr.outadoc.kemu.chip8"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
