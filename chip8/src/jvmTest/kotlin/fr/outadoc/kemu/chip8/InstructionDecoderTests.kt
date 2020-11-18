package fr.outadoc.kemu.chip8

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import java.util.stream.Stream
import kotlin.test.assertEquals

actual class InstructionDecoderTests {

    lateinit var decoder: Chip8InstructionDecoder

    @BeforeEach
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
}