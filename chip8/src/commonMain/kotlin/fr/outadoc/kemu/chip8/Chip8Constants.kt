package fr.outadoc.kemu.chip8

import fr.outadoc.kemu.b

object Chip8Constants {
    const val RAM_SIZE = 4096
    const val RAM_SECTION_PROGRAM = 0x200

    val INITIAL_SP = 0x00.b
    val MAX_SP = 0xF.b

    const val TIMER_FREQ_HZ = 1f / 60f
}