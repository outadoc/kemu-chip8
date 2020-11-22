package fr.outadoc.kemu.chip8.display

import fr.outadoc.kemu.array.toUByteArray2
import fr.outadoc.kemu.chip8.Chip8Runner
import java.awt.BorderLayout
import java.awt.KeyboardFocusManager
import java.awt.Toolkit
import java.awt.event.KeyEvent
import java.io.File
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter

class SwingChip8Window {

    companion object {
        const val APP_TITLE = "kemu-chip8"
    }

    private val keypad = AwtChip8Keypad()
    private val displayDriver = SwingChip8DisplayDriver()

    private val runner = Chip8Runner(keypad, displayDriver)

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

    private val speeds = listOf(
        "Super slow" to 200L,
        "Slow" to 100L,
        "Normal" to 50L,
        "Fast" to 10L,
        "Real-time" to 0L
    )

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
                                runner.stop()
                            }
                        }
                    )
                    add(
                        JMenu("Speed").apply {
                            val group = ButtonGroup()
                            speeds.forEach { (speedLabel, delay) ->
                                add(
                                    JRadioButtonMenuItem(speedLabel).apply {
                                        group.add(this)
                                        isSelected = runner.delay == delay
                                        addActionListener {
                                            runner.delay = delay
                                        }
                                    }
                                )
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

    private fun execute(programFile: File) {
        val program = programFile.inputStream().readBytes().toUByteArray2()
        runner.execute(program)
    }
}