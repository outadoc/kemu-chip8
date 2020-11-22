package fr.outadoc.kemu.display

interface Keypad {
    fun isKeyPressed(key: UByte): Boolean
    fun waitForKeyPress(): UByte
}