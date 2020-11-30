import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

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

        ios.deploymentTarget = "14.0"

        podfile = project.file("../app-chip8-ios/Podfile")
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

    // Required to fix duplicate symbol issue on iOS w/ 2 dependent modules
    targets.withType<KotlinNativeTarget> {
        binaries.withType<Framework> {
            isStatic = false
        }
    }
}