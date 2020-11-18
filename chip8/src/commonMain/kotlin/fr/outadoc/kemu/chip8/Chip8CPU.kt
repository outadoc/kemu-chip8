package fr.outadoc.kemu.chip8

import fr.outadoc.kemu.chip8.controlunit.Chip8ControlUnit
import fr.outadoc.kemu.chip8.instructionset.Chip8InstructionDecoder
import fr.outadoc.kemu.chip8.processor.Chip8Registers
import fr.outadoc.kemu.devices.CPU

class Chip8CPU : CPU {

    var registers = Chip8Registers()

    val controlUnit = Chip8ControlUnit(this)
    val decoder = Chip8InstructionDecoder()
}