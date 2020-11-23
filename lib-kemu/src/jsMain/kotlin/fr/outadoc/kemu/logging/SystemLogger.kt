package fr.outadoc.kemu.logging

actual class SystemLogger actual constructor() : LoggingMethod {

    override fun log(level: Logger.Level, message: () -> String) {
        val output = "[${level.name}] ${message()}"
        when (level) {
            Logger.Level.DEBUG -> console.log(output)
            Logger.Level.INFO -> console.info(output)
            Logger.Level.WARNING -> console.warn(output)
            Logger.Level.ERROR -> console.error(output)
        }
    }
}