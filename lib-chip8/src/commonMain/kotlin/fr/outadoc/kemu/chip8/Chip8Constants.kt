package fr.outadoc.kemu.chip8

import fr.outadoc.kemu.b
import fr.outadoc.kemu.s

object Chip8Constants {

    const val RAM_SIZE = 4096

    val RAM_SECTION_SPRITES = 0x40.s
    val RAM_SECTION_PROGRAM = 0x200.s

    val INITIAL_SP = 0x00.b
    val MAX_SP = 32.b

    const val TIMER_FREQ_HZ = 1f / 60f

    const val DISPLAY_HEIGHT = 32
    const val DISPLAY_WIDTH = 64
}