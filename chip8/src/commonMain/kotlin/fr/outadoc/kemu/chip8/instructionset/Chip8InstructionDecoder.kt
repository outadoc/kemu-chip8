package fr.outadoc.kemu.chip8.instructionset

import fr.outadoc.kemu.b
import fr.outadoc.kemu.chip8.instructionset.Chip8Instruction.*
import fr.outadoc.kemu.s
import fr.outadoc.kemu.shr

class Chip8InstructionDecoder {

    fun parse(i: UShort): Chip8Instruction {
        // Split the instruction into 4 nibbles
        val a = i and 0xF000.s
        val b = i and 0x0F00.s
        val c = i and 0x00F0.s
        val d = i and 0x000F.s

        // The same values, but shifted right n nibbles
        val a2 = (a shr 3 * 4).toUByte()
        val b2 = (b shr 2 * 4).toUByte()
        val c2 = (c shr 1 * 4).toUByte()
        val d2 = d.toUByte()

        return when {
            i == 0x00E0.s -> cls
            i == 0x00EE.s -> rts
            a2 == 0x0.b -> sys(nnn = b or c or d)
            a2 == 0x1.b -> jmp(nnn = b or c or d)
            a2 == 0x2.b -> jsr(nnn = b or c or d)
            a2 == 0x3.b -> skeq(x = b2, nn = (c or d).toUByte())
            a2 == 0x4.b -> skne(x = b2, nn = (c or d).toUByte())
            a2 == 0x5.b && d2 == 0x0.b -> skeq2(x = b2, y = c2)
            a2 == 0x6.b -> mov(x = b2, nn = (c or d).toUByte())
            a2 == 0x7.b -> add(x = b2, nn = (c or d).toUByte())
            a2 == 0x8.b && d2 == 0x0.b -> mov2(x = b2, y = c2)
            a2 == 0x8.b && d2 == 0x1.b -> or(x = b2, y = c2)
            a2 == 0x8.b && d2 == 0x2.b -> and(x = b2, y = c2)
            a2 == 0x8.b && d2 == 0x3.b -> xor(x = b2, y = c2)
            a2 == 0x8.b && d2 == 0x4.b -> add2(x = b2, y = c2)
            a2 == 0x8.b && d2 == 0x5.b -> sub(x = b2, y = c2)
            a2 == 0x8.b && d2 == 0x6.b -> shr(x = b2, y = c2)
            a2 == 0x8.b && d2 == 0x7.b -> rsb(x = b2, y = c2)
            a2 == 0x8.b && d2 == 0xE.b -> shl(x = b2, y = c2)
            a2 == 0x9.b && d2 == 0x0.b -> skne2(x = b2, y = c2)
            a2 == 0xA.b -> mvi(nnn = b or c or d)
            a2 == 0xB.b -> jmi(nnn = b or c or d)
            a2 == 0xC.b -> rand(x = b2, nn = (c or d).toUByte())
            a2 == 0xD.b -> sprite(x = b2, y = c2, n = d2)
            a2 == 0xE.b && c2 == 0x9.b && d2 == 0xE.b -> skpr(x = b2)
            a2 == 0xE.b && c2 == 0xA.b && d2 == 0x1.b -> skup(x = b2)
            a2 == 0xF.b && c2 == 0x0.b && d2 == 0x7.b -> gdelay(x = b2)
            a2 == 0xF.b && c2 == 0x0.b && d2 == 0xA.b -> key(x = b2)
            a2 == 0xF.b && c2 == 0x1.b && d2 == 0x5.b -> sdelay(x = b2)
            a2 == 0xF.b && c2 == 0x1.b && d2 == 0x8.b -> ssound(x = b2)
            a2 == 0xF.b && c2 == 0x1.b && d2 == 0xE.b -> adi(x = b2)
            a2 == 0xF.b && c2 == 0x2.b && d2 == 0x9.b -> font(x = b2)
            a2 == 0xF.b && c2 == 0x3.b && d2 == 0x3.b -> bcd(x = b2)
            a2 == 0xF.b && c2 == 0x5.b && d2 == 0x5.b -> str(x = b2)
            a2 == 0xF.b && c2 == 0x6.b && d2 == 0x5.b -> ldr(x = b2)

            else -> throw IllegalStateException("instruction ${i.toString(16)} is unsupported")
        }
    }
}

