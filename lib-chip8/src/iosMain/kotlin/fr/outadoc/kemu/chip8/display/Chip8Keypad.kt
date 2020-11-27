package fr.outadoc.kemu.chip8.display

import fr.outadoc.kemu.display.Keypad

actual class Chip8Keypad : Keypad {

    override fun isKeyPressed(key: UByte): Boolean {
        TODO("Not yet implemented")
    }

    override fun waitForKeyPress(): UByte {
        TODO("Not yet implemented")
    }
}