package fr.outadoc.kemu.chip8

import fr.outadoc.kemu.array.UByteArray2
import fr.outadoc.kemu.chip8.display.Chip8Display
import fr.outadoc.kemu.display.DisplayDriver
import fr.outadoc.kemu.display.Keypad
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

class Chip8Runner(
    private val keypad: Keypad,
    private val displayDriver: DisplayDriver<Chip8Display>
) {
    private var programJob: Job? = null

    fun execute(program: UByteArray2) {
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

    fun stop() {
        displayDriver.detach()
        programJob?.cancel()
        programJob = null
    }
}