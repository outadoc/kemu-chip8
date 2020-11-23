package fr.outadoc.kemu.logging

actual class SystemLogger actual constructor() : LoggingMethod {

    override fun log(level: Logger.Level, message: () -> String) {
        val output = "[${level.name}] ${message()}"
        when (level) {
            Logger.Level.ERROR -> System.err.println(output)
            else -> println(output)
        }
    }
}