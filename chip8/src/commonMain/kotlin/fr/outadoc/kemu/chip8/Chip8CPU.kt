package fr.outadoc.kemu.chip8

import fr.outadoc.kemu.chip8.controlunit.Chip8ControlUnit
import fr.outadoc.kemu.chip8.instructionset.Chip8InstructionDecoder
import fr.outadoc.kemu.chip8.memory.Chip8Bus
import fr.outadoc.kemu.chip8.memory.Chip8RAM
import fr.outadoc.kemu.chip8.processor.Chip8Registers
import fr.outadoc.kemu.devices.CPU
import fr.outadoc.kemu.devices.DefaultRandomGenerator
import fr.outadoc.kemu.s

class Chip8CPU : CPU {

    private var _registers = Chip8Registers()
    val registers: Chip8Registers
        get() = _registers

    val controlUnit = Chip8ControlUnit(this)
    val decoder = Chip8InstructionDecoder()
    val random = DefaultRandomGenerator()
    val memoryBus = Chip8Bus(listOf(Chip8RAM()))

    fun updateRegisters(advance: Int = 1, block: (Chip8Registers.() -> Chip8Registers)? = null) {
        val updatedRegisters = (if (block != null) registers.block() else registers)
        _registers = updatedRegisters.let { r ->
            if (advance > 0) {
                r.copy(pc = (r.pc + advance.s).toUShort())
            } else r
        }
    }
}