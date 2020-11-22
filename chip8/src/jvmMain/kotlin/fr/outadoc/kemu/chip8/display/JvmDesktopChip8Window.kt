package fr.outadoc.kemu.chip8.display

import fr.outadoc.kemu.array.toUByteArray2
import fr.outadoc.kemu.chip8.Chip8CPU
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

        val program = javaClass.classLoader.getResourceAsStream("test_opcode.ch8")?.use { stream ->
            stream.readBytes().toUByteArray2()
        } ?: throw IllegalArgumentException("Could not open resource")

        GlobalScope.launch(Dispatchers.Default) {
            Chip8CPU(display, keypad).apply {
                loadProgram(program)
                start()
                while (true) {
                    loop()
                    delay(200)
                }
            }
        }
    }
}