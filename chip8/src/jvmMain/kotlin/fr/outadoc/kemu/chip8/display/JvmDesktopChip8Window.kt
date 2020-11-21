package fr.outadoc.kemu.chip8.display

import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JFrame


class JvmDesktopChip8Window {

    private val display = Chip8Display()
    private val keypad = Chip8Keypad()

    private val window = JFrame("kemu-chip8").apply {
        preferredSize = Dimension(1024, 512)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isResizable = false
        contentPane.apply {
            add(display, BorderLayout.CENTER)
            add(keypad, BorderLayout.EAST)

        }
        pack()
    }

    fun show() {
        window.isVisible = true
    }
}