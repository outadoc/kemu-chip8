buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
}

group = "fr.outadoc.kemu.chip8"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        jcenter()
        mavenCentral()
        maven {
            url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
        }
    }
}
