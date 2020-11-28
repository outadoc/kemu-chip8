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

        val test by compilations.getting {
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
        summary = "kemu CHIP-8 library"
        homepage = "https://github.com/outadoc/kemu-chip8"

        ios.deploymentTarget = "14.0"

        pod("lib_kemu") {
            version = "1.0"
            source = path(project.file("../lib-kemu"))
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":lib-kemu"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.4.1")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }

        all {
            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
        }
    }
}