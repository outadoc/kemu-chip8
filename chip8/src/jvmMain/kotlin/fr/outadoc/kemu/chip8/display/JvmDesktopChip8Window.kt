package fr.outadoc.kemu.chip8.display

import java.awt.BorderLayout
import java.awt.KeyboardFocusManager
import javax.swing.JFrame

class JvmDesktopChip8Window {

    companion object {
        const val APP_TITLE = "kemu-chip8"
    }

    private val display = Chip8Display()
    private val keypad = Chip8Keypad()

    private val displayDriver = Chip8DisplayMonitor(display)

    private val window = JFrame(APP_TITLE).apply {
        preferredSize = displayDriver.size
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isResizable = false
        contentPane.apply {
            add(displayDriver, BorderLayout.CENTER)
        }
        pack()
    }

    fun start() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keypad)
        window.isVisible = true
    }
}