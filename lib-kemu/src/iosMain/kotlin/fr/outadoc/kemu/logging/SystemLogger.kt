package fr.outadoc.kemu.logging

import platform.Foundation.NSLog

actual class SystemLogger actual constructor() : LoggingMethod {

    override fun log(level: Logger.Level, message: () -> String) {
        val output = "[${level.name}] ${message()}"
        NSLog(output)
    }
}