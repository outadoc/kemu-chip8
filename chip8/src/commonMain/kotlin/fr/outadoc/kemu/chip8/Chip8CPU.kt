package fr.outadoc.kemu.chip8

import fr.outadoc.kemu.chip8.controlunit.Chip8ControlUnit
import fr.outadoc.kemu.chip8.memory.Chip8Sprites
import fr.outadoc.kemu.chip8.instructionset.Chip8InstructionDecoder
import fr.outadoc.kemu.chip8.memory.Chip8Bus
import fr.outadoc.kemu.chip8.memory.Chip8RAM
import fr.outadoc.kemu.chip8.processor.Chip8RegisterHolder
import fr.outadoc.kemu.chip8.timers.Chip8DelayTimer
import fr.outadoc.kemu.chip8.timers.Chip8SoundTimer
import fr.outadoc.kemu.devices.CPU
import fr.outadoc.kemu.get
import fr.outadoc.kemu.random.DefaultRandomGenerator

class Chip8CPU : CPU {

    val registerHolder = Chip8RegisterHolder()

    val decoder = Chip8InstructionDecoder()
    val random = DefaultRandomGenerator()

    val memoryBus = Chip8Bus(
        listOf(
            Chip8RAM(),
            Chip8Sprites()
        )
    )

    val timers = listOf(
        Chip8DelayTimer(registerHolder),
        Chip8SoundTimer(registerHolder)
    )

    val controlUnit = Chip8ControlUnit(registerHolder, random, memoryBus)

    override fun start() {
        timers.forEach { timer -> timer.start() }
    }

    override fun loop() {
    }

    override fun loadProgram(program: UByteArray) {
        val start = Chip8Constants.RAM_SECTION_PROGRAM
        val end = (start + program.size.toUShort()).toUShort()

        // Copy program into memory
        for (addr in (start..end).map { it.toUShort() }) {
            memoryBus.write(addr, program[addr])
        }
    }
}