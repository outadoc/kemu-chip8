package fr.outadoc.kemu.chip8

import fr.outadoc.kemu.chip8.instructionset.Chip8InstructionDecoder
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class InstructionDecoderTests {

    lateinit var decoder: Chip8InstructionDecoder

    @BeforeTest
    fun setup() {
        decoder = Chip8InstructionDecoder()
    }

    @Test
    fun testInstructionDecoder() {
        instructionDecoderTestSuite.forEach { (input, expected) ->
            val actual = decoder.parse(input.toUShort())
            assertEquals(expected, actual, "Did not decode $input correctly")
        }
    }

    @Test
    fun testIllegalInstructions() {
        instructionDecoderIllegalTestSuite.forEach { input ->
            assertFails("Incorrectly decoded $input as valid") {
                decoder.parse(input.toUShort())
            }
        }
    }
}