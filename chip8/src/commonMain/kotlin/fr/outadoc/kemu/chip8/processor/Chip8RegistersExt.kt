package fr.outadoc.kemu.chip8.processor

val UByte.isValidVRegister: Boolean
    get() = this < V_REGISTER_COUNT
