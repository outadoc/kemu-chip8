package fr.outadoc.kemu.logging

actual class SystemLogger : LoggingMethod {

    override fun log(level: Logger.Level, message: () -> String) {
        println("[${level.name}] ${message()}")
    }
}