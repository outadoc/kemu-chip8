package fr.outadoc.kemu.chip8.display

import fr.outadoc.kemu.array.UByteArray2
import fr.outadoc.kemu.array.toUByteArray2
import fr.outadoc.kemu.chip8.Chip8CPU
import kotlinx.coroutines.*
import java.awt.BorderLayout
import java.awt.KeyboardFocusManager
import java.io.File
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter
import kotlin.coroutines.coroutineContext

class JvmDesktopChip8Window {

    companion object {
        const val APP_TITLE = "kemu-chip8"
    }

    private val keypad = Chip8Keypad()
    private val displayDriver = Chip8DisplayMonitor()

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

    private val menuBar = JMenuBar().apply {
        val file = JMenu("File")
        val open = JMenuItem("Open").apply {
            setMnemonic('O')
            addActionListener {
                when (fileChooser.showDialog(window, "Run")) {
                    JFileChooser.APPROVE_OPTION -> {
                        execute(fileChooser.selectedFile)
                    }
                }
            }
        }
        file.add(open)
        add(file)
    }

    init {
        window.jMenuBar = menuBar
    }

    fun show() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keypad)
        window.isVisible = true
    }

    private var job: Job? = null

    private fun execute(programFile: File) {
        val program = programFile.inputStream().readBytes().toUByteArray2()
        job?.cancel()
        job = GlobalScope.launch(Dispatchers.Default) {
            runProgram(program)
        }
    }

    private suspend fun runProgram(program: UByteArray2) {
        Chip8CPU(keypad).apply {
            displayDriver.attachToDisplay(display)
            loadProgram(program)
            start()
            while (coroutineContext.isActive && loop()) {
                delay(200)
            }
        }
    }
}