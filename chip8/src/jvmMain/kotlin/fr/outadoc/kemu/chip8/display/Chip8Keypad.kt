package fr.outadoc.kemu.chip8.display

import fr.outadoc.kemu.display.Keypad
import java.awt.KeyEventDispatcher
import java.awt.event.KeyEvent

actual class Chip8Keypad : Keypad, KeyEventDispatcher {

    private val alphabet = (('0'..'9').zip(0x0..0x9) + ('a'..'f').zip(0xa..0xf))
        .map { (a, b) -> a to b.toByte() }
        .toMap()

    private val pressedKeys = mutableSetOf<Byte>()

    override fun isKeyPressed(key: Byte): Boolean {
        return key in pressedKeys
    }

    override fun waitForKeyPress(): Byte {
        while (pressedKeys.isEmpty()) {
            Thread.sleep(100)
        }

        // Janky, we should do that stuff on another thread :(
        return pressedKeys.first()
    }

    override fun dispatchKeyEvent(ke: KeyEvent?): Boolean {
        return when (ke?.id) {
            KeyEvent.KEY_PRESSED -> {
                ke.keyChar.asHexChar?.let { hex ->
                    pressedKeys.add(hex)
                    true
                } ?: false
            }
            KeyEvent.KEY_RELEASED -> {
                ke.keyChar.asHexChar?.let { hex ->
                    pressedKeys.remove(hex)
                    true
                } ?: false
            }
            else -> false
        }
    }

    private val Char.asHexChar: Byte?
        get() = alphabet[toLowerCase()]
}