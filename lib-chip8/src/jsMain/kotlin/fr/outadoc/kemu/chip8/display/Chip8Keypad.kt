package fr.outadoc.kemu.chip8.display

import fr.outadoc.kemu.display.Keypad
import kotlinx.browser.window

actual class Chip8Keypad : Keypad {

    private val alphabet = (('0'..'9').zip(0x0..0x9) + ('a'..'f').zip(0xa..0xf))
        .map { (a, b) -> a to b.toUByte() }
        .toMap()

    private val pressedKeys = mutableSetOf<UByte>()

    init {
        window.onkeydown = { e ->
            e.key[0].asHexChar?.let { key ->
                pressedKeys.add(key)
            }
        }

        window.onkeyup = { e ->
            e.key[0].asHexChar?.let { key ->
                pressedKeys.remove(key)
            }
        }
    }

    override fun isKeyPressed(key: UByte): Boolean {
        return key in pressedKeys
    }

    override fun waitForKeyPress(): UByte {
        while (pressedKeys.isEmpty()) {
        }
        return pressedKeys.first()
    }

    private val Char.asHexChar: UByte?
        get() = alphabet[toLowerCase()]
}