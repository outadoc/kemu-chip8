package fr.outadoc.kemu.chip8.processor

import fr.outadoc.kemu.b
import fr.outadoc.kemu.s

val V_REGISTER_COUNT = 16.toUInt()

data class Chip8Registers(

    /**
     * General-purpose registers. v[0xf] should not be used by any programs.
     */
    val v: UByteArray = UByteArray(V_REGISTER_COUNT.toInt()),

    /**
     * This register is generally used to store memory addresses, so only the lowest (rightmost) 12 bits are usually used.
     */
    val i: UShort = 0x00.s,

    /**
     * Program counter, points to the currently executing address.
     */
    val pc: UShort = 0x00.s,

    /**
     * Stack pointer, points to the topmost level of the stack.
     */
    val sp: UByte = 0x00.b,

    /**
     * Delay timer register. Delay timer is enabled when value is non-zero.
     */
    val dt: UByte = 0x00.b,

    /**
     * Sound timer register. Sound timer is enabled when value is non-zero.
     */
    val st: UByte = 0x00.b
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Chip8Registers

        if (!v.contentEquals(other.v)) return false
        if (i != other.i) return false
        if (pc != other.pc) return false
        if (sp != other.sp) return false
        if (dt != other.dt) return false
        if (st != other.st) return false

        return true
    }

    override fun hashCode(): Int {
        var result = v.contentHashCode()
        result = 31 * result + i.hashCode()
        result = 31 * result + pc.hashCode()
        result = 31 * result + sp.hashCode()
        result = 31 * result + dt.hashCode()
        result = 31 * result + st.hashCode()
        return result
    }
}