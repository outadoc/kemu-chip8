package fr.outadoc.kemu.chip8

import fr.outadoc.kemu.array.UByteArray2
import fr.outadoc.kemu.chip8.controlunit.Chip8ControlUnit
import fr.outadoc.kemu.chip8.display.Chip8Display
import fr.outadoc.kemu.chip8.instructionset.Chip8Instruction
import fr.outadoc.kemu.chip8.instructionset.Chip8InstructionDecoder
import fr.outadoc.kemu.chip8.memory.Chip8Bus
import fr.outadoc.kemu.chip8.memory.Chip8RAM
import fr.outadoc.kemu.chip8.memory.Chip8Sprites
import fr.outadoc.kemu.chip8.processor.Chip8RegisterHolder
import fr.outadoc.kemu.chip8.timers.Chip8DelayTimer
import fr.outadoc.kemu.chip8.timers.Chip8SoundTimer
import fr.outadoc.kemu.controlunit.ControlUnit
import fr.outadoc.kemu.devices.CPU
import fr.outadoc.kemu.display.Keypad
import fr.outadoc.kemu.logging.Logger
import fr.outadoc.kemu.memory.Bus
import fr.outadoc.kemu.random.DefaultRandomGenerator
import fr.outadoc.kemu.random.RandomGenerator
import fr.outadoc.kemu.s
import fr.outadoc.kemu.shl
import fr.outadoc.kemu.timer.Timer

class Chip8CPU(keypad: Keypad) : CPU {

    private val registerHolder = Chip8RegisterHolder()

    val display = Chip8Display()
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

    private val decoder = Chip8InstructionDecoder()
    private val controlUnit: ControlUnit<Chip8Instruction> =
        Chip8ControlUnit(registerHolder, random, memoryBus, display, keypad)

    override fun start() {
        timers.forEach { timer -> timer.start() }
    }

    override fun loop(): Boolean {
        val pc = registerHolder.read.pc

        val msb = memoryBus.read(pc).toUShort()
        val lsb = memoryBus.read((pc + 1.s).toUShort()).toUShort()
        val opCode = ((msb shl 8) or lsb)

        Logger.d { "--- tick ------------------------------" }

        // Show register state
        Logger.d { registerHolder.read.toString() }

        if (opCode == 0x0000.s) {
            Logger.w { "reached opcode 0x0000, terminating" }
            return false
        }

        Logger.d { "decoding 0x${opCode.toString(16)}" }

        val ins = decoder.parse(opCode)

        Logger.d { "executing $ins" }
        controlUnit.exec(ins)

        return true
    }

    override fun loadProgram(program: UByteArray2) {
        val start = Chip8Constants.RAM_SECTION_PROGRAM
        val end = (start + program.size.toUShort()).toUShort()

        // Copy program into memory
        program.zip(start until end).forEach { (byte, addr) ->
            memoryBus.write(addr.toUShort(), byte)
        }
    }
}
