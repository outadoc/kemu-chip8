package fr.outadoc.kemu.chip8.memory

import fr.outadoc.kemu.b
import fr.outadoc.kemu.chip8.Chip8Constants

class Chip8Sprites : ROM(start = Chip8Constants.RAM_SECTION_SPRITES) {

    override val rom = ubyteArrayOf(
        0xF0.b, 0x90.b, 0x90.b, 0x90.b, 0xF0.b,
        0x20.b, 0x60.b, 0x20.b, 0x20.b, 0x70.b,
        0xF0.b, 0x10.b, 0xF0.b, 0x80.b, 0xF0.b,
        0xF0.b, 0x10.b, 0xF0.b, 0x10.b, 0xF0.b,
        0x90.b, 0x90.b, 0xF0.b, 0x10.b, 0x10.b,
        0xF0.b, 0x80.b, 0xF0.b, 0x10.b, 0xF0.b,
        0xF0.b, 0x80.b, 0xF0.b, 0x90.b, 0xF0.b,
        0xF0.b, 0x10.b, 0x20.b, 0x40.b, 0x40.b,
        0xF0.b, 0x90.b, 0xF0.b, 0x90.b, 0xF0.b,
        0xF0.b, 0x90.b, 0xF0.b, 0x10.b, 0xF0.b,
        0xF0.b, 0x90.b, 0xF0.b, 0x90.b, 0x90.b,
        0xE0.b, 0x90.b, 0xE0.b, 0x90.b, 0xE0.b,
        0xF0.b, 0x80.b, 0x80.b, 0x80.b, 0xF0.b,
        0xE0.b, 0x90.b, 0x90.b, 0x90.b, 0xE0.b,
        0xF0.b, 0x80.b, 0xF0.b, 0x80.b, 0xF0.b,
        0xF0.b, 0x80.b, 0xF0.b, 0x80.b, 0x80.b
    )
}