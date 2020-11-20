package fr.outadoc.kemu.chip8

import fr.outadoc.kemu.chip8.controlunit.Chip8ControlUnit
import fr.outadoc.kemu.chip8.instructionset.Chip8InstructionDecoder
import fr.outadoc.kemu.chip8.memory.Chip8Bus
import fr.outadoc.kemu.chip8.memory.Chip8RAM
import fr.outadoc.kemu.chip8.processor.Chip8Registers
import fr.outadoc.kemu.chip8.processor.RegisterAccessor
import fr.outadoc.kemu.devices.CPU
import fr.outadoc.kemu.devices.DefaultRandomGenerator
import fr.outadoc.kemu.s

class Chip8CPU : CPU, RegisterAccessor<Chip8Registers> {

    private var _registers = Chip8Registers()
    override val read: Chip8Registers
        get() = _registers

    val decoder = Chip8InstructionDecoder()
    val random = DefaultRandomGenerator()
    val memoryBus = Chip8Bus(listOf(Chip8RAM()))

    val controlUnit = Chip8ControlUnit(this, random, memoryBus)

    override fun update(advance: Int, block: (Chip8Registers.() -> Chip8Registers)?) {
        val updatedRegisters = (if (block != null) read.block() else read)
        _registers = updatedRegisters.let { r ->
            if (advance > 0) {
                r.copy(pc = (r.pc + advance.s).toUShort())
            } else r
        }
    }
}