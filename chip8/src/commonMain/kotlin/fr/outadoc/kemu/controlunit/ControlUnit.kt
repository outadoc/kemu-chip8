package fr.outadoc.kemu.controlunit

import fr.outadoc.kemu.instructionset.Instruction

interface ControlUnit<T : Instruction> {
    fun exec(ins: T)
}