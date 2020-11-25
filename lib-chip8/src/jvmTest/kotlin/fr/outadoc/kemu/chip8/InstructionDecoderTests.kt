package fr.outadoc.kemu.chip8

import fr.outadoc.kemu.chip8.instructionset.Chip8InstructionDecoder
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import java.util.stream.Stream
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertFails

class InstructionDecoderTests {

    lateinit var decoder: Chip8InstructionDecoder

    @BeforeTest
    fun setup() {
        decoder = Chip8InstructionDecoder()
    }

    @TestFactory
    fun testInstructionDecoder(): Stream<DynamicTest> {
        return instructionDecoderTestSuite.map { (input, expected) ->
            dynamicTest("Decode $input correctly") {
                val actual = decoder.parse(input.toUShort())
                assertEquals(expected, actual)
            }
        }.stream()
    }

    @TestFactory
    fun testIllegalInstructions(): Stream<DynamicTest> {
        return instructionDecoderIllegalTestSuite.map { input ->
            dynamicTest("Decode $input correctly") {
                assertFails {
                    decoder.parse(input.toUShort())
                }
            }
        }.stream()
    }
}