package fr.outadoc.kemu.chip8.display

import fr.outadoc.kemu.display.Keypad
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.awt.KeyEventDispatcher
import java.awt.event.KeyEvent

actual class Chip8Keypad : Keypad, KeyEventDispatcher {

    private val alphabet = (('0'..'9').zip(0x0..0x9) + ('a'..'f').zip(0xa..0xf))
        .map { (a, b) -> a to b.toUByte() }
        .toMap()

    private val pressedKeys = mutableSetOf<UByte>()

    override fun isKeyPressed(key: UByte): Boolean {
        return key in pressedKeys
    }

    override fun waitForKeyPress(): UByte {
        runBlocking {
            while (pressedKeys.isEmpty()) {
                delay(100)
            }
        }

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

    private val Char.asHexChar: UByte?
        get() = alphabet[toLowerCase()]
}