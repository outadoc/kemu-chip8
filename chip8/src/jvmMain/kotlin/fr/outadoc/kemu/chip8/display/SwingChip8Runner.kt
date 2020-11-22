package fr.outadoc.kemu.chip8.display

import fr.outadoc.kemu.array.UByteArray2
import fr.outadoc.kemu.array.toUByteArray2
import fr.outadoc.kemu.chip8.Chip8CPU
import kotlinx.coroutines.*
import java.awt.BorderLayout
import java.awt.KeyboardFocusManager
import java.awt.Toolkit
import java.awt.event.KeyEvent
import java.io.File
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter
import kotlin.coroutines.coroutineContext


class SwingChip8Runner {

    companion object {
        const val APP_TITLE = "kemu-chip8"
    }

    private val keypad = AwtChip8Keypad()
    private val displayDriver = SwingChip8DisplayDriver()

    private val fileChooser = JFileChooser().apply {
        fileFilter = FileNameExtensionFilter("CHIP-8 Program", "ch8")
    }

    private val window = JFrame(APP_TITLE).apply {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isResizable = false
        setLocationRelativeTo(null)
        contentPane.apply {
            add(displayDriver, BorderLayout.CENTER)
        }
        pack()
    }

    private val menuBar =
        JMenuBar().apply {
            add(
                JMenu("File").apply {
                    add(
                        JMenuItem("Open").apply {
                            accelerator = KeyStroke.getKeyStroke(
                                KeyEvent.VK_O,
                                Toolkit.getDefaultToolkit().menuShortcutKeyMask
                            )

                            addActionListener {
                                when (fileChooser.showDialog(window, "Run")) {
                                    JFileChooser.APPROVE_OPTION -> {
                                        execute(fileChooser.selectedFile)
                                    }
                                }
                            }
                        }
                    )
                }
            )
            add(
                JMenu("Execution").apply {
                    add(
                        JMenuItem("Stop").apply {
                            addActionListener {
                                stop()
                            }
                        }
                    )
                }
            )
        }

    fun show() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keypad)
        window.jMenuBar = menuBar
        window.isVisible = true
    }

    private var programJob: Job? = null

    private fun execute(programFile: File) {
        val program = programFile.inputStream().readBytes().toUByteArray2()
        programJob?.cancel()
        programJob = GlobalScope.launch(Dispatchers.Default) {
            runProgram(program)
        }
    }

    private suspend fun runProgram(program: UByteArray2) {
        Chip8CPU(keypad).apply {
            displayDriver.attach(display)
            loadProgram(program)
            start()
            while (coroutineContext.isActive && loop()) {
                delay(200)
            }
        }
    }

    private fun stop() {
        displayDriver.detach()
        programJob?.cancel()
        programJob = null
    }
}