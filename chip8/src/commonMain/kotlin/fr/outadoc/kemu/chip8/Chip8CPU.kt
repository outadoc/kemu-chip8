package fr.outadoc.kemu.chip8

import fr.outadoc.kemu.chip8.controlunit.Chip8ControlUnit
import fr.outadoc.kemu.chip8.display.Chip8Display
import fr.outadoc.kemu.chip8.display.Chip8Keypad
import fr.outadoc.kemu.chip8.memory.Chip8Sprites
import fr.outadoc.kemu.chip8.instructionset.Chip8InstructionDecoder
import fr.outadoc.kemu.chip8.memory.Chip8Bus
import fr.outadoc.kemu.chip8.memory.Chip8RAM
import fr.outadoc.kemu.chip8.processor.Chip8RegisterHolder
import fr.outadoc.kemu.chip8.timers.Chip8DelayTimer
import fr.outadoc.kemu.chip8.timers.Chip8SoundTimer
import fr.outadoc.kemu.controlunit.ControlUnit
import fr.outadoc.kemu.devices.CPU
import fr.outadoc.kemu.display.Display
import fr.outadoc.kemu.display.Keypad
import fr.outadoc.kemu.get
import fr.outadoc.kemu.memory.Bus
import fr.outadoc.kemu.random.DefaultRandomGenerator
import fr.outadoc.kemu.random.RandomGenerator
import fr.outadoc.kemu.timer.Timer

class Chip8CPU : CPU {

    private val registerHolder = Chip8RegisterHolder()

    private val random: RandomGenerator = DefaultRandomGenerator()

    private val memoryBus: Bus<UShort> = Chip8Bus(
        listOf(
            Chip8RAM(),
            Chip8Sprites()
        )
    )

    private val timers = listOf<Timer>(
        Chip8DelayTimer(registerHolder),
        Chip8SoundTimer(registerHolder)
    )

    private val display: Display = Chip8Display()
    private val keypad: Keypad = Chip8Keypad()

    private val decoder = Chip8InstructionDecoder()
    private val controlUnit: ControlUnit =
        Chip8ControlUnit(registerHolder, random, memoryBus, display, keypad)

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