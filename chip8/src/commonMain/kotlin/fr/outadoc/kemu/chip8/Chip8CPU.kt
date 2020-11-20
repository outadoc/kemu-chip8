package fr.outadoc.kemu.chip8

import fr.outadoc.kemu.chip8.controlunit.Chip8ControlUnit
import fr.outadoc.kemu.chip8.instructionset.Chip8InstructionDecoder
import fr.outadoc.kemu.chip8.memory.Chip8Bus
import fr.outadoc.kemu.chip8.memory.Chip8RAM
import fr.outadoc.kemu.chip8.processor.Chip8RegisterHolder
import fr.outadoc.kemu.chip8.processor.Chip8Registers
import fr.outadoc.kemu.chip8.processor.RegisterAccessor
import fr.outadoc.kemu.chip8.timers.Chip8DelayTimer
import fr.outadoc.kemu.chip8.timers.Chip8SoundTimer
import fr.outadoc.kemu.devices.CPU
import fr.outadoc.kemu.devices.DefaultRandomGenerator
import fr.outadoc.kemu.s
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class Chip8CPU : CPU {

    val registerHolder = Chip8RegisterHolder()

    val decoder = Chip8InstructionDecoder()
    val random = DefaultRandomGenerator()

    val memoryBus = Chip8Bus(
        listOf(
            Chip8RAM()
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
}