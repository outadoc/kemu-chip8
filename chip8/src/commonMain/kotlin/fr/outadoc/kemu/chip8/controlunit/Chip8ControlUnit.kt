package fr.outadoc.kemu.chip8.controlunit

import fr.outadoc.kemu.b
import fr.outadoc.kemu.chip8.Chip8CPU
import fr.outadoc.kemu.chip8.instructionset.Chip8Instruction
import fr.outadoc.kemu.devices.ControlUnit
import fr.outadoc.kemu.get
import fr.outadoc.kemu.logging.Logger
import fr.outadoc.kemu.set

class Chip8ControlUnit(private val cpu: Chip8CPU) : ControlUnit {

    fun run(i: Chip8Instruction) {
        when (i) {
            Chip8Instruction.cls -> {
                todo(i)
            }

            Chip8Instruction.rts -> {
                todo(i)
            }

            is Chip8Instruction.sys -> {
                // This instruction is only used on the old computers on which Chip-8 was
                // originally implemented. It is ignored by modern interpreters.
                todo(i)
            }

            is Chip8Instruction.jmp -> {
                cpu.updateRegisters(advance = 0) {
                    copy(pc = i.nnn)
                }
            }

            is Chip8Instruction.jsr -> {
                todo(i)
            }

            is Chip8Instruction.skeq -> {
                cpu.updateRegisters(advance = if (cpu.registers.v[i.x] == i.nn) 2 else 1)
            }

            is Chip8Instruction.skne -> {
                cpu.updateRegisters(advance = if (cpu.registers.v[i.x] != i.nn) 2 else 1)
            }

            is Chip8Instruction.skeq2 -> {
                cpu.updateRegisters(advance = if (cpu.registers.v[i.x] == cpu.registers.v[i.y]) 2 else 1)
            }

            is Chip8Instruction.mov -> {
                cpu.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = nn
                        v[i.x] = i.nn
                    })
                }
            }

            is Chip8Instruction.add -> {
                cpu.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vx + nn
                        v[i.x] = (v[i.x] + i.nn).toUByte()
                    })
                }
            }

            is Chip8Instruction.mov2 -> {
                cpu.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vy
                        v[i.x] = v[i.y]
                    })
                }
            }

            is Chip8Instruction.or -> {
                cpu.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vx or Vy
                        v[i.x] = v[i.x] or v[i.y]
                    })
                }
            }

            is Chip8Instruction.and -> {
                cpu.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vx and Vy
                        v[i.x] = v[i.x] and v[i.y]
                    })
                }
            }

            is Chip8Instruction.xor -> {
                cpu.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vx xor Vy
                        v[i.x] = v[i.x] xor v[i.y]
                    })
                }
            }

            is Chip8Instruction.add2 -> {
                cpu.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        val res: UInt = v[i.x] + v[i.y]
                        // Vx = Vx + Vy
                        v[i.x] = res.toUByte()
                        // Vf is the carry bit
                        v[0xf] = if (res > 0xff.toUByte()) 0x1.b else 0x0.b
                    })
                }
            }

            is Chip8Instruction.sub -> {
                cpu.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vx - Vy
                        v[i.x] = (v[i.x] - v[i.y]).toUByte()
                        // Vf is the NOT borrow bit
                        v[0xf] = if (v[i.x] > v[i.y]) 0x1.b else 0x0.b
                    })
                }
            }

            is Chip8Instruction.shr -> {
                todo(i)
            }

            is Chip8Instruction.rsb -> {
                cpu.updateRegisters {
                    copy(v = v.copyOf().also { v ->
                        // Vx = Vy - Vx
                        v[i.x] = (v[i.y] - v[i.x]).toUByte()
                        // Vf is the NOT borrow bit
                        v[0xf] = if (v[i.y] > v[i.x]) 0x1.b else 0x0.b
                    })
                }
            }

            is Chip8Instruction.shl -> {
                todo(i)
            }

            is Chip8Instruction.skne2 -> {
                cpu.updateRegisters(advance = if (cpu.registers.v[i.x] != cpu.registers.v[i.y]) 2 else 1)
            }

            is Chip8Instruction.mvi -> {
                cpu.updateRegisters {
                    copy(i = i.nnn)
                }
            }

            is Chip8Instruction.jmi -> {
                cpu.updateRegisters {
                    copy(pc = (i.nnn + v[0x0]).toUShort())
                }
            }

            is Chip8Instruction.rand -> {
                cpu.updateRegisters {
                    copy(v = v.copyOf().apply {
                        // Vx = random and nn
                        v[i.x] = cpu.randomGenerator.nextByte() and i.nn
                    })
                }
            }

            is Chip8Instruction.sprite -> {
                todo(i)
            }

            is Chip8Instruction.skpr -> {
                todo(i)
            }

            is Chip8Instruction.skup -> {
                todo(i)
            }

            is Chip8Instruction.gdelay -> {
                todo(i)
            }

            is Chip8Instruction.key -> {
                todo(i)
            }

            is Chip8Instruction.sdelay -> {
                todo(i)
            }

            is Chip8Instruction.ssound -> {
                todo(i)
            }

            is Chip8Instruction.adi -> {
                todo(i)
            }

            is Chip8Instruction.font -> {
                todo(i)
            }

            is Chip8Instruction.bcd -> {
                todo(i)
            }

            is Chip8Instruction.str -> {
                todo(i)
            }

            is Chip8Instruction.ldr -> {
                todo(i)
            }
        }
    }

    private fun todo(instruction: Chip8Instruction) {
        Logger.w { "instruction was ignored: $instruction" }
        cpu.updateRegisters(advance = 1)
    }
}