package fr.outadoc.kemu.chip8.controlunit

import fr.outadoc.kemu.b
import fr.outadoc.kemu.chip8.instructionset.Chip8Instruction
import fr.outadoc.kemu.chip8.processor.Chip8Registers
import fr.outadoc.kemu.chip8.processor.RegisterAccessor
import fr.outadoc.kemu.devices.Bus
import fr.outadoc.kemu.devices.ControlUnit
import fr.outadoc.kemu.devices.RandomGenerator
import fr.outadoc.kemu.get
import fr.outadoc.kemu.logging.Logger
import fr.outadoc.kemu.set
import kotlin.math.pow

class Chip8ControlUnit(
    private val registers: RegisterAccessor<Chip8Registers>,
    private val random: RandomGenerator,
    private val memoryBus: Bus<UShort>
) : ControlUnit {

    fun run(ins: Chip8Instruction) {
        when (ins) {
            Chip8Instruction.cls -> {
                todo(ins)
            }

            Chip8Instruction.rts -> {
                todo(ins)
            }

            is Chip8Instruction.sys -> {
                // This instruction is only used on the old computers on which Chip-8 was
                // originally implemented. It is ignored by modern interpreters.
                todo(ins)
            }

            is Chip8Instruction.jmp -> {
                registers.updateRegisters(advance = 0) {
                    copy(pc = ins.nnn)
                }
            }

            is Chip8Instruction.jsr -> {
                todo(ins)
            }

            is Chip8Instruction.skeq -> {
                registers.updateRegisters(advance = if (registers.read.v[ins.x] == ins.nn) 2 else 1)
            }

            is Chip8Instruction.skne -> {
                registers.updateRegisters(advance = if (registers.read.v[ins.x] != ins.nn) 2 else 1)
            }

            is Chip8Instruction.skeq2 -> {
                registers.updateRegisters(advance = if (registers.read.v[ins.x] == registers.read.v[ins.y]) 2 else 1)
            }

            is Chip8Instruction.mov -> {
                registers.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = nn
                        v[ins.x] = ins.nn
                    })
                }
            }

            is Chip8Instruction.add -> {
                registers.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vx + nn
                        v[ins.x] = (v[ins.x] + ins.nn).toUByte()
                    })
                }
            }

            is Chip8Instruction.mov2 -> {
                registers.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vy
                        v[ins.x] = v[ins.y]
                    })
                }
            }

            is Chip8Instruction.or -> {
                registers.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vx or Vy
                        v[ins.x] = v[ins.x] or v[ins.y]
                    })
                }
            }

            is Chip8Instruction.and -> {
                registers.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vx and Vy
                        v[ins.x] = v[ins.x] and v[ins.y]
                    })
                }
            }

            is Chip8Instruction.xor -> {
                registers.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vx xor Vy
                        v[ins.x] = v[ins.x] xor v[ins.y]
                    })
                }
            }

            is Chip8Instruction.add2 -> {
                registers.updateRegisters {
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
                registers.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vx - Vy
                        v[ins.x] = (v[ins.x] - v[ins.y]).toUByte()
                        // Vf is the NOT borrow bit
                        v[0xf] = if (v[ins.x] > v[ins.y]) 0x1.b else 0x0.b
                    })
                }
            }

            is Chip8Instruction.shr -> {
                todo(ins)
            }

            is Chip8Instruction.rsb -> {
                registers.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vy - Vx
                        v[ins.x] = (v[ins.y] - v[ins.x]).toUByte()
                        // Vf is the NOT borrow bit
                        v[0xf] = if (v[ins.y] > v[ins.x]) 0x1.b else 0x0.b
                    })
                }
            }

            is Chip8Instruction.shl -> {
                todo(ins)
            }

            is Chip8Instruction.skne2 -> {
                registers.updateRegisters(advance = if (registers.read.v[ins.x] != registers.read.v[ins.y]) 2 else 1)
            }

            is Chip8Instruction.mvi -> {
                registers.updateRegisters {
                    copy(i = ins.nnn)
                }
            }

            is Chip8Instruction.jmi -> {
                registers.updateRegisters {
                    copy(pc = (ins.nnn + v[0x0]).toUShort())
                }
            }

            is Chip8Instruction.rand -> {
                registers.updateRegisters {
                    copy(v = v.copyOf().apply {
                        // Vx = random and nn
                        v[ins.x] = random.nextByte() and ins.nn
                    })
                }
            }

            is Chip8Instruction.sprite -> {
                todo(ins)
            }

            is Chip8Instruction.skpr -> {
                todo(ins)
            }

            is Chip8Instruction.skup -> {
                todo(ins)
            }

            is Chip8Instruction.gdelay -> {
                registers.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = DT
                        v[ins.x] = dt
                    })
                }
            }

            is Chip8Instruction.key -> {
                todo(ins)
            }

            is Chip8Instruction.sdelay -> {
                registers.updateRegisters {
                    // DT = Vx
                    copy(dt = v[ins.x])
                }
            }

            is Chip8Instruction.ssound -> {
                registers.updateRegisters {
                    // DT = Vx
                    copy(st = v[ins.x])
                }
            }

            is Chip8Instruction.adi -> {
                registers.updateRegisters {
                    // I = I + Vx
                    copy(i = (i + v[ins.x]).toUShort())
                }
            }

            is Chip8Instruction.font -> {
                todo(ins)
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

                registers.updateRegisters(advance = 1)
            }

            is Chip8Instruction.str -> {
                // Copy registers V0 .. Vx to memory at I .. I + x
                for (iv in (0x0.b..ins.x).map { it.toUByte() }) {
                    memoryBus.write((registers.read.i + iv).toUShort(), registers.read.v[iv])
                }

                registers.updateRegisters(advance = 1) {
                    // I = I + x + 1
                    copy(i = (i + ins.x + i).toUShort())
                }
            }

            is Chip8Instruction.ldr -> {
                // Copy memory at I .. I + x to registers V0 .. Vx
                registers.updateRegisters {
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

    private fun todo(instruction: Chip8Instruction) {
        Logger.w { "instruction was ignored: $instruction" }
        registers.updateRegisters(advance = 1)
    }
}