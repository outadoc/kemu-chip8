package fr.outadoc.kemu.logging

interface LoggingMethod {
    fun log(level: Logger.Level, message: () -> String)
}