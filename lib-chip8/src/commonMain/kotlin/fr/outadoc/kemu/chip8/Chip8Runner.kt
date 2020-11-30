package fr.outadoc.kemu.chip8

import fr.outadoc.kemu.Speed
import fr.outadoc.kemu.array.UByteArray2
import fr.outadoc.kemu.chip8.display.Chip8Display
import fr.outadoc.kemu.display.FrameDispatcher
import fr.outadoc.kemu.display.Keypad
import kotlinx.coroutines.*

class Chip8Runner(
    private val keypad: Keypad,
    private val frameDispatcher: FrameDispatcher
) {
    /**
     * Delay between each CPU tick, in milliseconds.
     */
    var speed = Speed.REALTIME

    private var programJob: Job? = null
    private var runningProgram: UByteArray2? = null

    fun execute(program: UByteArray2) {
        runningProgram = program
        stop()
        programJob = GlobalScope.launch(Dispatchers.Default) {
            runProgram(program)
        }
    }

    private suspend fun runProgram(program: UByteArray2) = coroutineScope {
        Chip8CPU(keypad).apply {
            frameDispatcher.attach(display)
            loadProgram(program)

            launch {
                initializeTimers()
            }

            while (coroutineContext.isActive && loop()) {
                delay(speed.delay)
            }
        }
    }

    fun reset() {
        runningProgram?.let { program ->
            stop()
            execute(program)
        }
    }

    fun stop() {
        frameDispatcher.detach()
        programJob?.cancel()
        programJob = null
    }
}