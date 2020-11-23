plugins {
    kotlin("multiplatform") version "1.4.20"
}

kotlin {
    jvm {
        val main by compilations.getting {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":chip8"))

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.4.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.4.1")
            }
        }

        all {
            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
        }
    }
}