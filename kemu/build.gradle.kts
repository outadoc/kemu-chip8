plugins {
    kotlin("multiplatform")
}

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
    maven {
        url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}

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

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        all {
            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
        }
    }
}