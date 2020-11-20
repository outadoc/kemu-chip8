package fr.outadoc.kemu.chip8

import fr.outadoc.kemu.chip8.controlunit.Chip8ControlUnit
import fr.outadoc.kemu.chip8.instructionset.Chip8InstructionDecoder
import fr.outadoc.kemu.chip8.memory.Chip8Bus
import fr.outadoc.kemu.chip8.memory.Chip8RAM
import fr.outadoc.kemu.chip8.processor.Chip8Registers
import fr.outadoc.kemu.chip8.processor.RegisterAccessor
import fr.outadoc.kemu.chip8.timers.Chip8DelayTimer
import fr.outadoc.kemu.devices.CPU
import fr.outadoc.kemu.devices.DefaultRandomGenerator
import fr.outadoc.kemu.s
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class Chip8CPU : CPU, RegisterAccessor<Chip8Registers> {

    private val _registers: MutableStateFlow<Chip8Registers> = MutableStateFlow(Chip8Registers())
    override val flow: Flow<Chip8Registers>
        get() = _registers

    override val read: Chip8Registers
        get() = _registers.value

    val decoder = Chip8InstructionDecoder()
    val random = DefaultRandomGenerator()
    val memoryBus = Chip8Bus(listOf(Chip8RAM()))
    val timers = listOf(Chip8DelayTimer(this))
    val controlUnit = Chip8ControlUnit(this, random, memoryBus)

    override fun update(advance: Int, block: (Chip8Registers.() -> Chip8Registers)?) {
        val updatedRegisters = (if (block != null) read.block() else read)
        _registers.value = updatedRegisters.let { r ->
            if (advance > 0) {
                r.copy(pc = (r.pc + advance.s).toUShort())
            } else r
        }
    }
}