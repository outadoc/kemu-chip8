package fr.outadoc.kemu.chip8

import fr.outadoc.kemu.chip8.Chip8Instruction.*
import kotlin.test.Test
import kotlin.test.assertEquals

class InstructionDecoderTests {

    private val testSuite = listOf(
        0x00E0 to _00E0,
        0x00EE to _00EE,
        0x1123 to _1nnn(nnn = 0x123.toUShort()),
        0x2321 to _2nnn(nnn = 0x321.toUShort())
    )

    @Test
    fun testInstructionDecoder() {
        val decoder = Chip8InstructionDecoder()
        testSuite.forEach { (input, expected) ->
            val actual = decoder.parse(input.toUShort())
            assertEquals(expected, actual)
        }
    }
}