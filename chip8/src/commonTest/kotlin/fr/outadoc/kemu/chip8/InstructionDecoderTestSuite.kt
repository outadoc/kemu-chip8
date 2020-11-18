package fr.outadoc.kemu.chip8

import fr.outadoc.kemu.b
import fr.outadoc.kemu.chip8.Chip8Instruction.*
import fr.outadoc.kemu.s

val instructionDecoderTestSuite = listOf(
    0x00E0 to _00E0,
    0x00EE to _00EE,
    0x1123 to _1nnn(nnn = 0x123.s),
    0x2321 to _2nnn(nnn = 0x321.s),
    0x3532 to _3xnn(x = 0x5.b, nn = 0x32.b),
    0x4026 to _4xnn(x = 0x0.b, nn = 0x26.b),
    0x5120 to _5xy0(x = 0x1.b, y = 0x2.b)
)

val instructionDecoderIllegalTestSuite = listOf(
    0x512F
)