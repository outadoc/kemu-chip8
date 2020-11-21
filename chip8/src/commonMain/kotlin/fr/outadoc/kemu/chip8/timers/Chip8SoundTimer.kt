package fr.outadoc.kemu.chip8.timers

import fr.outadoc.kemu.b
import fr.outadoc.kemu.chip8.Chip8Constants
import fr.outadoc.kemu.chip8.processor.Chip8Registers
import fr.outadoc.kemu.registers.RegisterAccessor
import fr.outadoc.kemu.timer.FrequencyTimer

class Chip8SoundTimer(private val registers: RegisterAccessor<Chip8Registers>) :
    FrequencyTimer(Chip8Constants.TIMER_FREQ_HZ) {

    override fun onTick() {
        // Decrement ST until it reaches zero
        if (registers.read.st > 0x0.b) {
            registers.update(advance = 0) {
                copy(st = (st - 0x1.b).toUByte())
            }
        }
    }
}