package fr.outadoc.kemu.chip8.controlunit

import fr.outadoc.kemu.chip8.Chip8CPU
import fr.outadoc.kemu.chip8.instructionset.Chip8Instruction
import fr.outadoc.kemu.devices.ControlUnit

class Chip8ControlUnit(private val cpu: Chip8CPU) : ControlUnit {

    fun run(instruction: Chip8Instruction) {
        when (instruction) {
            Chip8Instruction._00E0 -> {
            }

            Chip8Instruction._00EE -> {
            }

            is Chip8Instruction._0nnn -> {
            }

            is Chip8Instruction._1nnn -> {
            }

            is Chip8Instruction._2nnn -> {
            }

            is Chip8Instruction._3xnn -> {
            }

            is Chip8Instruction._4xnn -> {
            }

            is Chip8Instruction._5xy0 -> {
            }

            is Chip8Instruction._6xnn -> {
            }

            is Chip8Instruction._7xnn -> {
            }

            is Chip8Instruction._8xy0 -> {
            }

            is Chip8Instruction._8xy1 -> {
            }

            is Chip8Instruction._8xy2 -> {
            }

            is Chip8Instruction._8xy3 -> {
            }

            is Chip8Instruction._8xy4 -> {
            }

            is Chip8Instruction._8xy5 -> {
            }

            is Chip8Instruction._8xy6 -> {
            }

            is Chip8Instruction._8xy7 -> {
            }

            is Chip8Instruction._8xyE -> {
            }

            is Chip8Instruction._9xy0 -> {
            }

            is Chip8Instruction._Annn -> {
            }

            is Chip8Instruction._Bnnn -> {
            }

            is Chip8Instruction._Cxnn -> {
            }

            is Chip8Instruction._Dxyn -> {
            }

            is Chip8Instruction._Ex9E -> {
            }

            is Chip8Instruction._ExA1 -> {
            }

            is Chip8Instruction._Fx07 -> {
            }

            is Chip8Instruction._Fx0A -> {
            }

            is Chip8Instruction._Fx15 -> {
            }

            is Chip8Instruction._Fx18 -> {
            }

            is Chip8Instruction._Fx1E -> {
            }

            is Chip8Instruction._Fx29 -> {
            }

            is Chip8Instruction._Fx33 -> {
            }

            is Chip8Instruction._Fx55 -> {
            }

            is Chip8Instruction._Fx65 -> {
            }
        }
    }
}