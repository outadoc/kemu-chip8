plugins {
    kotlin("js") version "1.4.20"
}

group = "fr.outadoc.kemu.chip8"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":lib-chip8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.4.1")
}

kotlin {
    js {
        browser {
            webpackTask {
                cssSupport.enabled = true
            }

            runTask {
                cssSupport.enabled = true
            }

            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
        binaries.executable()
    }
}