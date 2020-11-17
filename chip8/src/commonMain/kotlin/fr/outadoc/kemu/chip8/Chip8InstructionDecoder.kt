package fr.outadoc.kemu.chip8

import fr.outadoc.kemu.b
import fr.outadoc.kemu.chip8.Chip8Instruction.*
import fr.outadoc.kemu.s
import fr.outadoc.kemu.shr

class Chip8InstructionDecoder {

    fun parse(i: UShort): Chip8Instruction {
        // Split the instruction into 4 * 4 bits
        val a = i and 0xF000.s
        val b = i and 0x0F00.s
        val c = i and 0x00F0.s
        val d = i and 0x000F.s

        // The same values, but shifted right
        val a2 = (a shr 3).toUByte()
        val b2 = (b shr 2).toUByte()
        val c2 = (c shr 1).toUByte()
        val d2 = d.toUByte()

        return when {
            i == 0x00E0.s -> _00E0
            i == 0x00EE.s -> _00EE
            a2 == 0x0.b -> _0nnn(nnn = b or c or d)
            a2 == 0x1.b -> _1nnn(nnn = b or c or d)
            a2 == 0x2.b -> _2nnn(nnn = b or c or d)
            a2 == 0x3.b -> _3xnn(x = b2, nn = (c or d).toUByte())
            a2 == 0x4.b -> _4xnn(x = b2, nn = (c or d).toUByte())
            a2 == 0x5.b && d2 == 0x0.b -> _5xy0(x = b2, y = c2)
            a2 == 0x6.b -> _6xnn(x = b2, nn = (c or d).toUByte())
            a2 == 0x7.b -> _7xnn(x = b2, nn = (c or d).toUByte())
            a2 == 0x8.b && d2 == 0x0.b -> _8xy0(x = b2, y = c2)
            a2 == 0x8.b && d2 == 0x1.b -> _8xy1(x = b2, y = c2)
            a2 == 0x8.b && d2 == 0x2.b -> _8xy2(x = b2, y = c2)
            a2 == 0x8.b && d2 == 0x3.b -> _8xy3(x = b2, y = c2)
            a2 == 0x8.b && d2 == 0x4.b -> _8xy4(x = b2, y = c2)
            a2 == 0x8.b && d2 == 0x5.b -> _8xy5(x = b2, y = c2)
            a2 == 0x8.b && d2 == 0x6.b -> _8xy6(x = b2, y = c2)
            a2 == 0x8.b && d2 == 0x7.b -> _8xy7(x = b2, y = c2)
            a2 == 0x8.b && d2 == 0xE.b -> _8xyE(x = b2, y = c2)
            a2 == 0x9.b && d2 == 0x0.b -> _9xy0(x = b2, y = c2)
            a2 == 0xA.b -> _Annn(nnn = b or c or d)
            a2 == 0xB.b -> _Bnnn(nnn = b or c or d)
            a2 == 0xC.b -> _Cxnn(x = b2, nn = (c or d).toUByte())
            a2 == 0xD.b -> _Dxyn(x = b2, y = c2, n = d2)
            a2 == 0xE.b && c2 == 0x9.b && d2 == 0xE.b -> _Ex9E(x = b2)
            a2 == 0xE.b && c2 == 0xA.b && d2 == 0x1.b -> _ExA1(x = b2)
            a2 == 0xF.b && c2 == 0x0.b && d2 == 0x7.b -> _Fx07(x = b2)
            a2 == 0xF.b && c2 == 0x0.b && d2 == 0xA.b -> _Fx0A(x = b2)
            a2 == 0xF.b && c2 == 0x1.b && d2 == 0x5.b -> _Fx15(x = b2)
            a2 == 0xF.b && c2 == 0x1.b && d2 == 0x8.b -> _Fx18(x = b2)
            a2 == 0xF.b && c2 == 0x1.b && d2 == 0xE.b -> _Fx1E(x = b2)
            a2 == 0xF.b && c2 == 0x2.b && d2 == 0x9.b -> _Fx29(x = b2)
            a2 == 0xF.b && c2 == 0x3.b && d2 == 0x3.b -> _Fx33(x = b2)
            a2 == 0xF.b && c2 == 0x5.b && d2 == 0x5.b -> _Fx55(x = b2)
            a2 == 0xF.b && c2 == 0x6.b && d2 == 0x5.b -> _Fx65(x = b2)

            else -> throw IllegalStateException("instruction $i is unsupported")
        }
    }
}

