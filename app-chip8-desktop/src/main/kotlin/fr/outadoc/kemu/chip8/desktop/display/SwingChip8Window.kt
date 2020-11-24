package fr.outadoc.kemu.chip8.desktop.display

import fr.outadoc.kemu.Speed
import fr.outadoc.kemu.array.toUByteArray2
import fr.outadoc.kemu.chip8.Chip8Runner
import fr.outadoc.kemu.chip8.display.Chip8Keypad
import fr.outadoc.kemu.theme.Theme
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

    private val keypad = Chip8Keypad()
    private val displayDriver = SwingChip8DisplayDriver()

    private val runner = Chip8Runner(keypad, displayDriver)

    private val fileChooser = JFileChooser().apply {
        fileFilter = FileNameExtensionFilter("CHIP-8 Program", "ch8")
    }

    private val window = JFrame(APP_TITLE).apply {
        setLocationRelativeTo(null)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isResizable = false
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
                                runner.stop()
                            }
                        }
                    )
                    add(
                        JMenuItem("Reset").apply {
                            accelerator = KeyStroke.getKeyStroke(
                                KeyEvent.VK_R,
                                Toolkit.getDefaultToolkit().menuShortcutKeyMask
                            )

                            addActionListener {
                                runner.reset()
                            }
                        }
                    )
                    add(
                        JMenu("Speed").apply {
                            val group = ButtonGroup()
                            Speed.values().forEach { speed ->
                                add(
                                    JRadioButtonMenuItem(speed.label).apply {
                                        group.add(this)
                                        isSelected = runner.speed == speed
                                        addActionListener {
                                            runner.speed = speed
                                        }
                                    }
                                )
                            }
                        }
                    )
                }
            )
            add(
                JMenu("Display").apply {
                    add(
                        JMenu("Theme").apply {
                            val group = ButtonGroup()
                            Theme.values().forEach { theme ->
                                add(
                                    JRadioButtonMenuItem(theme.label).apply {
                                        group.add(this)
                                        isSelected = (displayDriver.theme == theme)
                                        addActionListener {
                                            displayDriver.theme = theme
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
        KeyboardFocusManager
            .getCurrentKeyboardFocusManager()
            .addKeyEventDispatcher(keypad)

        window.jMenuBar = menuBar
        window.isVisible = true
    }

    private fun execute(programFile: File) {
        val program = programFile.inputStream().readBytes().toUByteArray2()
        runner.execute(program)
    }

    private val Speed.label
        get() = when (this) {
            Speed.SUPER_SLOW -> "Super slow"
            Speed.SLOW -> "Slow"
            Speed.NORMAL -> "Normal"
            Speed.FAST -> "Fast"
            Speed.REALTIME -> "Real-time"
        }

    private val Theme.label
        get() = when (this) {
            Theme.WHITE_ON_BLACK -> "White on black"
            Theme.BLACK_ON_WHITE -> "Black on white"
            Theme.MATRIX -> "Matrix"
            Theme.SOLARIZED_DARK -> "Solarized Dark"
            Theme.SOLARIZED_LIGHT -> "Solarized Light"
        }
}