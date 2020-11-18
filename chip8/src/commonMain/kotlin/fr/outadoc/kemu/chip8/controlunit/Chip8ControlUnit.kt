package fr.outadoc.kemu.chip8.controlunit

import fr.outadoc.kemu.chip8.Chip8CPU
import fr.outadoc.kemu.chip8.instructionset.Chip8Instruction
import fr.outadoc.kemu.devices.ControlUnit
import fr.outadoc.kemu.logging.Logger

class Chip8ControlUnit(private val cpu: Chip8CPU) : ControlUnit {

    fun run(instruction: Chip8Instruction) {
        when (instruction) {
            Chip8Instruction.cls -> {
            }

            Chip8Instruction.rts -> {
            }

            is Chip8Instruction.sys -> {
                // This instruction is only used on the old computers on which Chip-8 was
                // originally implemented. It is ignored by modern interpreters.
                Logger.w { "instruction was ignored: $instruction" }
            }

            is Chip8Instruction.jmp -> {
            }

            is Chip8Instruction.jsr -> {
            }

            is Chip8Instruction.skeq -> {
            }

            is Chip8Instruction.skne -> {
            }

            is Chip8Instruction.skeq2 -> {
            }

            is Chip8Instruction.mov -> {
            }

            is Chip8Instruction.add -> {
            }

            is Chip8Instruction.mov2 -> {
            }

            is Chip8Instruction.or -> {
            }

            is Chip8Instruction.and -> {
            }

            is Chip8Instruction.xor -> {
            }

            is Chip8Instruction.add2 -> {
            }

            is Chip8Instruction.sub -> {
            }

            is Chip8Instruction.shr -> {
            }

            is Chip8Instruction.rsb -> {
            }

            is Chip8Instruction.shl -> {
            }

            is Chip8Instruction.skne2 -> {
            }

            is Chip8Instruction.mvi -> {
            }

            is Chip8Instruction.jmi -> {
            }

            is Chip8Instruction.rand -> {
            }

            is Chip8Instruction.sprite -> {
            }

            is Chip8Instruction.skpr -> {
            }

            is Chip8Instruction.skup -> {
            }

            is Chip8Instruction.gdelay -> {
            }

            is Chip8Instruction.key -> {
            }

            is Chip8Instruction.sdelay -> {
            }

            is Chip8Instruction.ssound -> {
            }

            is Chip8Instruction.adi -> {
            }

            is Chip8Instruction.font -> {
            }

            is Chip8Instruction.bcd -> {
            }

            is Chip8Instruction.str -> {
            }

            is Chip8Instruction.ldr -> {
            }
        }
    }
}