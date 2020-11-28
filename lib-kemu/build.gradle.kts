plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
}

version = "1.0"

kotlin {
    jvm {
        val main by compilations.getting {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    js {
        browser()
    }

    ios()

    cocoapods {
        summary = "kemu common library"
        homepage = "https://github.com/outadoc/kemu-chip8"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")
            }
        }

        all {
            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
        }
    }
}