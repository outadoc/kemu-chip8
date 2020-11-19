package fr.outadoc.kemu.chip8.controlunit

import fr.outadoc.kemu.b
import fr.outadoc.kemu.chip8.Chip8CPU
import fr.outadoc.kemu.chip8.instructionset.Chip8Instruction
import fr.outadoc.kemu.devices.ControlUnit
import fr.outadoc.kemu.get
import fr.outadoc.kemu.logging.Logger
import fr.outadoc.kemu.set

class Chip8ControlUnit(private val cpu: Chip8CPU) : ControlUnit {

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
                cpu.updateRegisters(advance = 0) {
                    copy(pc = ins.nnn)
                }
            }

            is Chip8Instruction.jsr -> {
                todo(ins)
            }

            is Chip8Instruction.skeq -> {
                cpu.updateRegisters(advance = if (cpu.registers.v[ins.x] == ins.nn) 2 else 1)
            }

            is Chip8Instruction.skne -> {
                cpu.updateRegisters(advance = if (cpu.registers.v[ins.x] != ins.nn) 2 else 1)
            }

            is Chip8Instruction.skeq2 -> {
                cpu.updateRegisters(advance = if (cpu.registers.v[ins.x] == cpu.registers.v[ins.y]) 2 else 1)
            }

            is Chip8Instruction.mov -> {
                cpu.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = nn
                        v[ins.x] = ins.nn
                    })
                }
            }

            is Chip8Instruction.add -> {
                cpu.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vx + nn
                        v[ins.x] = (v[ins.x] + ins.nn).toUByte()
                    })
                }
            }

            is Chip8Instruction.mov2 -> {
                cpu.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vy
                        v[ins.x] = v[ins.y]
                    })
                }
            }

            is Chip8Instruction.or -> {
                cpu.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vx or Vy
                        v[ins.x] = v[ins.x] or v[ins.y]
                    })
                }
            }

            is Chip8Instruction.and -> {
                cpu.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vx and Vy
                        v[ins.x] = v[ins.x] and v[ins.y]
                    })
                }
            }

            is Chip8Instruction.xor -> {
                cpu.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vx xor Vy
                        v[ins.x] = v[ins.x] xor v[ins.y]
                    })
                }
            }

            is Chip8Instruction.add2 -> {
                cpu.updateRegisters {
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
                cpu.updateRegisters {
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
                cpu.updateRegisters {
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
                cpu.updateRegisters(advance = if (cpu.registers.v[ins.x] != cpu.registers.v[ins.y]) 2 else 1)
            }

            is Chip8Instruction.mvi -> {
                cpu.updateRegisters {
                    copy(i = ins.nnn)
                }
            }

            is Chip8Instruction.jmi -> {
                cpu.updateRegisters {
                    copy(pc = (ins.nnn + v[0x0]).toUShort())
                }
            }

            is Chip8Instruction.rand -> {
                cpu.updateRegisters {
                    copy(v = v.copyOf().apply {
                        // Vx = random and nn
                        v[ins.x] = cpu.randomGenerator.nextByte() and ins.nn
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
                cpu.updateRegisters {
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
                cpu.updateRegisters {
                    // DT = Vx
                    copy(dt = v[ins.x])
                }
            }

            is Chip8Instruction.ssound -> {
                cpu.updateRegisters {
                    // DT = Vx
                    copy(st = v[ins.x])
                }
            }

            is Chip8Instruction.adi -> {
                cpu.updateRegisters {
                    copy(i = (i + v[ins.x]).toUShort())
                }
            }

            is Chip8Instruction.font -> {
                todo(ins)
            }

            is Chip8Instruction.bcd -> {
                todo(ins)
            }

            is Chip8Instruction.str -> {
                todo(ins)
            }

            is Chip8Instruction.ldr -> {
                todo(ins)
            }
        }
    }

    private fun todo(instruction: Chip8Instruction) {
        Logger.w { "instruction was ignored: $instruction" }
        cpu.updateRegisters(advance = 1)
    }
}