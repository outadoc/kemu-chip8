package fr.outadoc.kemu.chip8.timers

import fr.outadoc.kemu.b
import fr.outadoc.kemu.chip8.processor.Chip8Registers
import fr.outadoc.kemu.chip8.processor.RegisterAccessor
import fr.outadoc.kemu.devices.Timer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Chip8DelayTimer(private val registers: RegisterAccessor<Chip8Registers>) : Timer {

    private val frequency = 1 / 60
    private val delayMillis = frequency * 1000L

    init {
        GlobalScope.launch {
            runTimer()
        }
    }

    private suspend fun runTimer() {
        onTick()
        delay(delayMillis)
    }

    private fun onTick() {
        // Decrement DT until it reaches zero
        if (registers.read.dt > 0x0.b) {
            registers.update(advance = 0) {
                copy(dt = (dt - 0x1.b).toUByte())
            }
        }
    }
}