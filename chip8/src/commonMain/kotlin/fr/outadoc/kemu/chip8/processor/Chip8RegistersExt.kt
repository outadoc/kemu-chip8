package fr.outadoc.kemu.chip8.processor

import fr.outadoc.kemu.b

val Byte.isValidVRegister: Boolean
    get() = this < 16.b