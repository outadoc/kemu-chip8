package fr.outadoc.kemu.display

interface Keypad {
    fun isKeyPressed(key: Byte): Boolean
    fun waitForKeyPress(): Byte
}