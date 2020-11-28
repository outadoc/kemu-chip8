buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.20")
    }
}

allprojects {
    repositories {
        jcenter()
        mavenCentral()
        maven {
            url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
        }
    }

    tasks.withType<Test> {
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}
