package fr.outadoc.kemu.logging

object Logger {

    val loggingMethod: LoggingMethod? = SystemLogger()

    fun d(message: () -> String) {
        loggingMethod?.log(Level.DEBUG, message)
    }

    fun i(message: () -> String) {
        loggingMethod?.log(Level.INFO, message)
    }

    fun w(message: () -> String) {
        loggingMethod?.log(Level.WARNING, message)
    }

    fun e(message: () -> String) {
        loggingMethod?.log(Level.ERROR, message)
    }

    enum class Level {
        DEBUG, INFO, WARNING, ERROR
    }
}