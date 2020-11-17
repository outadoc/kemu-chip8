package fr.outadoc.kemu.chip8

import fr.outadoc.kemu.devices.CPU

class Chip8CPU : CPU {

    var registers = Chip8Registers()
}

class Chip8Registers {

    /**
     * General-purpose registers. v[0xf] should not be used by any programs.
     */
    val v: ByteArray = ByteArray(REGISTER_COUNT)

    /**
     * This register is generally used to store memory addresses, so only the lowest (rightmost) 12 bits are usually used.
     */
    val i: Short = 0x00

    /**
     * Program counter, points to the currently executing address.
     */
    val pc: Short = 0x00

    /**
     * Stack pointer, points to the topmost level of the stack.
     */
    val sp: Byte = 0x00

    /**
     * Delay timer register. Delay timer is enabled when value is non-zero.
     */
    val dt: Byte = 0x00

    /**
     * Sound timer register. Sound timer is enabled when value is non-zero.
     */
    val st: Byte = 0x00
}