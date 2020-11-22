package fr.outadoc.kemu.chip8.controlunit

import fr.outadoc.kemu.array.get
import fr.outadoc.kemu.array.set
import fr.outadoc.kemu.array.toUByteArray2
import fr.outadoc.kemu.b
import fr.outadoc.kemu.chip8.Chip8Constants
import fr.outadoc.kemu.chip8.instructionset.Chip8Instruction
import fr.outadoc.kemu.chip8.processor.Chip8Registers
import fr.outadoc.kemu.controlunit.ControlUnit
import fr.outadoc.kemu.display.Display
import fr.outadoc.kemu.display.Keypad
import fr.outadoc.kemu.display.Point
import fr.outadoc.kemu.exceptions.StackOverflowException
import fr.outadoc.kemu.exceptions.StackUnderflowException
import fr.outadoc.kemu.logging.Logger
import fr.outadoc.kemu.memory.Bus
import fr.outadoc.kemu.random.RandomGenerator
import fr.outadoc.kemu.registers.RegisterAccessor
import fr.outadoc.kemu.s
import fr.outadoc.kemu.shl
import fr.outadoc.kemu.shr
import kotlin.math.pow

class Chip8ControlUnit(
    private val registers: RegisterAccessor<Chip8Registers>,
    private val random: RandomGenerator,
    private val memoryBus: Bus<UShort>,
    private val display: Display,
    private val keypad: Keypad
) : ControlUnit<Chip8Instruction> {

    override fun exec(ins: Chip8Instruction) {
        when (ins) {
            Chip8Instruction.cls -> {
                display.clear()
                registers.update(advance = 2)
            }

            Chip8Instruction.rts -> {
                registers.update {
                    if (sp == 0.b) {
                        Logger.e { "FATAL: stack underflow" }
                        throw StackUnderflowException()
                    }

                    // Read LSB of PC from stack
                    val lsb = memoryBus.read((sp - 0x1.b).toUShort()).toUShort()

                    // Read MSB of PC from stack
                    val msb = memoryBus.read((sp - 0x2.b).toUShort()).toUShort()

                    copy(
                        sp = (sp - 0x2.b).toUByte(),
                        pc = (msb shl 8) or lsb
                    )
                }
            }

            is Chip8Instruction.sys -> {
                // This instruction is only used on the old computers on which Chip-8 was
                // originally implemented. It is ignored by modern interpreters.
                Logger.w { "instruction was ignored: $ins" }
                registers.update(advance = 2)
            }

            is Chip8Instruction.jmp -> {
                registers.update(advance = 0) {
                    copy(pc = ins.nnn)
                }
            }

            is Chip8Instruction.jsr -> {
                registers.update(advance = 0) {
                    if (sp == Chip8Constants.MAX_SP) {
                        Logger.e { "FATAL: stack overflow" }
                        throw StackOverflowException()
                    }

                    // Add MSB of PC to stack
                    memoryBus.write(sp.toUShort(), (pc shr 8).toUByte())

                    // Add LSB of PC to stack
                    memoryBus.write((sp + 0x1.b).toUShort(), pc.toUByte())

                    // PC = nnn, SP = SP + 1
                    copy(
                        sp = (sp + 0x2.b).toUByte(),
                        pc = ins.nnn
                    )
                }
            }

            is Chip8Instruction.skeq -> {
                registers.update(advance = if (registers.read.v[ins.x] == ins.nn) 4 else 2)
            }

            is Chip8Instruction.skne -> {
                registers.update(advance = if (registers.read.v[ins.x] != ins.nn) 4 else 2)
            }

            is Chip8Instruction.skeq2 -> {
                registers.update(advance = if (registers.read.v[ins.x] == registers.read.v[ins.y]) 4 else 2)
            }

            is Chip8Instruction.mov -> {
                registers.update {
                    copy(v = v.copyOf().also { v ->
                        // Vx = nn
                        v[ins.x] = ins.nn
                    })
                }
            }

            is Chip8Instruction.add -> {
                registers.update {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vx + nn
                        v[ins.x] = (v[ins.x] + ins.nn).toUByte()
                    })
                }
            }

            is Chip8Instruction.mov2 -> {
                registers.update {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vy
                        v[ins.x] = v[ins.y]
                    })
                }
            }

            is Chip8Instruction.or -> {
                registers.update {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vx or Vy
                        v[ins.x] = v[ins.x] or v[ins.y]
                    })
                }
            }

            is Chip8Instruction.and -> {
                registers.update {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vx and Vy
                        v[ins.x] = v[ins.x] and v[ins.y]
                    })
                }
            }

            is Chip8Instruction.xor -> {
                registers.update {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vx xor Vy
                        v[ins.x] = v[ins.x] xor v[ins.y]
                    })
                }
            }

            is Chip8Instruction.add2 -> {
                registers.update {
                    copy(v = v.copyOf().also { v ->
                        val res: UInt = v[ins.x] + v[ins.y]
                        // Vx = Vx + Vy
                        v[ins.x] = res.toUByte()
                        // Vf is the carry bit
                        v[0xf] = if (res > 0xff.toUByte()) 0x1.b else 0x0.b
                    })
                }
            }

            is Chip8Instruction.sub -> {
                registers.update {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vx - Vy
                        v[ins.x] = (v[ins.x] - v[ins.y]).toUByte()
                        // Vf is the NOT borrow bit
                        v[0xf] = if (v[ins.x] > v[ins.y]) 0x1.b else 0x0.b
                    })
                }
            }

            is Chip8Instruction.shr -> {
                registers.update {
                    copy(v = v.copyOf().also { v ->
                        // Set Vf to 1 if the LSB of Vx is 1
                        v[0xf] = if (v[ins.x] and 0x01.b == 0x01.b) 0x1.b else 0x0.b
                        v[ins.x] = v[ins.x] shr 1
                    })
                }
            }

            is Chip8Instruction.rsb -> {
                registers.update {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vy - Vx
                        v[ins.x] = (v[ins.y] - v[ins.x]).toUByte()
                        // Vf is the NOT borrow bit
                        v[0xf] = if (v[ins.y] > v[ins.x]) 0x1.b else 0x0.b
                    })
                }
            }

            is Chip8Instruction.shl -> {
                registers.update {
                    copy(v = v.copyOf().also { v ->
                        // Set Vf to 1 if the MSB of Vx is 1
                        v[0xf] = if (v[ins.x] and 0x80.b == 0x80.b) 0x1.b else 0x0.b
                        v[ins.x] = v[ins.x] shl 1
                    })
                }
            }

            is Chip8Instruction.skne2 -> {
                registers.update(advance = if (registers.read.v[ins.x] != registers.read.v[ins.y]) 4 else 2)
            }

            is Chip8Instruction.mvi -> {
                registers.update {
                    copy(i = ins.nnn)
                }
            }

            is Chip8Instruction.jmi -> {
                registers.update {
                    copy(pc = (ins.nnn + v[0x0]).toUShort())
                }
            }

            is Chip8Instruction.rand -> {
                registers.update {
                    copy(v = v.copyOf().apply {
                        // Vx = random and nn
                        v[ins.x] = random.nextByte() and ins.nn
                    })
                }
            }

            is Chip8Instruction.sprite -> {
                registers.update {
                    // Copy n bytes from the sprite at address I
                    val sprite = (i until (i + ins.n).toUShort()).map { addr ->
                        memoryBus.read(addr.toUShort())
                    }.toUByteArray2()

                    // Display sprite at address (Vx, Vy)
                    val collision = display.displaySprite(
                        position = Point(
                            x = v[ins.x],
                            y = v[ins.y]
                        ),
                        sprite = sprite
                    )

                    // Set Vf if there was a collision
                    copy(v = v.copyOf().also { v ->
                        v[0xf] = if (collision) 0x1.b else 0x0.b
                    })
                }
            }

            is Chip8Instruction.skpr -> {
                registers.update(advance = if (keypad.isKeyPressed(registers.read.v[ins.x])) 4 else 2)
            }

            is Chip8Instruction.skup -> {
                registers.update(advance = if (!keypad.isKeyPressed(registers.read.v[ins.x])) 4 else 2)
            }

            is Chip8Instruction.gdelay -> {
                registers.update {
                    copy(v = v.copyOf().also { v ->
                        // Vx = DT
                        v[ins.x] = dt
                    })
                }
            }

            is Chip8Instruction.key -> {
                registers.update {
                    copy(v = v.copyOf().also { v ->
                        v[ins.x] = keypad.waitForKeyPress()
                    })
                }
            }

            is Chip8Instruction.sdelay -> {
                registers.update {
                    // DT = Vx
                    copy(dt = v[ins.x])
                }
            }

            is Chip8Instruction.ssound -> {
                registers.update {
                    // DT = Vx
                    copy(st = v[ins.x])
                }
            }

            is Chip8Instruction.adi -> {
                registers.update {
                    // I = I + Vx
                    copy(i = (i + v[ins.x]).toUShort())
                }
            }

            is Chip8Instruction.font -> {
                registers.update {
                    copy(i = (Chip8Constants.RAM_SECTION_SPRITES + v[ins.x]).toUShort())
                }
            }

            is Chip8Instruction.bcd -> {
                fun UByte.nthBcdDigit(n: UByte): UByte {
                    // I don't see what you mean. Working with numerical types in Kotlin is fiiiine.
                    return ((this / (10f.pow((n - 1.toUInt()).toInt())).toUInt()).rem(10.toUInt())).toUByte()
                }

                // Copy three base-10 digits of Vx to memory at I .. I + 2
                val vx = registers.read.v[ins.x]
                for (n in (0 until 3).map { it.toUByte() }) {
                    memoryBus.write((registers.read.i + n).toUShort(), vx.nthBcdDigit(n))
                }

                registers.update(advance = 2)
            }

            is Chip8Instruction.str -> {
                // Copy registers V0 .. Vx to memory at I .. I + x
                for (iv in (0x0.b..ins.x).map { it.toUByte() }) {
                    memoryBus.write((registers.read.i + iv).toUShort(), registers.read.v[iv])
                }

                registers.update {
                    // I = I + x + 1
                    copy(i = (i + ins.x + i).toUShort())
                }
            }

            is Chip8Instruction.ldr -> {
                // Copy memory at I .. I + x to registers V0 .. Vx
                registers.update {
                    val v = v.copyOf()
                    for (iv in (0x0.b..ins.x).map { it.toUByte() }) {
                        v[iv] = memoryBus.read((registers.read.i + iv).toUShort())
                    }

                    // Additionally, I = I + x + 1
                    copy(
                        v = v,
                        i = (i + ins.x + i).toUShort()
                    )
                }
            }
        }
    }
}