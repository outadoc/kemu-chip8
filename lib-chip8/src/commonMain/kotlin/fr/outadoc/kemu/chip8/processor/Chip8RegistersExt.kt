package fr.outadoc.kemu.chip8.processor

import fr.outadoc.kemu.b

val UByte.isValidVRegister: Boolean
    get() = this < 16.b