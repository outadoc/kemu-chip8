package fr.outadoc.kemu.chip8

import fr.outadoc.kemu.Speed
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
    private var timersJob: Job? = null

    /**
     * Delay between each CPU tick, in milliseconds.
     */
    var speed = Speed.REALTIME

    fun execute(program: UByteArray2) {
        stop()
        programJob = GlobalScope.launch(Dispatchers.Default) {
            runProgram(program)
        }
    }

    private suspend fun runProgram(program: UByteArray2) {
        Chip8CPU(keypad).apply {
            displayDriver.attach(display)
            loadProgram(program)

            timersJob = GlobalScope.launch(Dispatchers.Default) {
                initializeTimers()
            }

            while (coroutineContext.isActive && loop()) {
                delay(speed.delay)
            }
        }
    }

    fun stop() {
        displayDriver.detach()
        timersJob?.cancel()
        timersJob = null
        programJob?.cancel()
        programJob = null
    }
}