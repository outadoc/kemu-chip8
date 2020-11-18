package fr.outadoc.kemu.chip8.processor

import fr.outadoc.kemu.chip8.REGISTER_COUNT

val UByte.isValidVRegister: Boolean
    get() = this < REGISTER_COUNT
