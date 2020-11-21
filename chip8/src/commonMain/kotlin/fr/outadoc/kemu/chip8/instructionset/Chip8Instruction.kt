package fr.outadoc.kemu.chip8.instructionset

import fr.outadoc.kemu.chip8.processor.isValidVRegister
import fr.outadoc.kemu.instructionset.Instruction

/**
 * @see [CHIP‐8 Instruction Set](https://github.com/mattmikolay/chip-8/wiki/CHIP%E2%80%908-Instruction-Set)
 * @see [CHIP‐8 Instruction Set](http://devernay.free.fr/hacks/chip8/chip8def.htm)
 */
@Suppress("ClassName")
sealed class Chip8Instruction : Instruction {

    /**
     * Execute machine language subroutine at address nnn.
     */
    data class sys(val nnn: Short) : Chip8Instruction()

    /**
     * Clear the screen
     */
    object cls : Chip8Instruction()

    /**
     * Return from a subroutine
     */
    object rts : Chip8Instruction()

    /**
     * Jump to address nnn
     */
    data class jmp(val nnn: Short) : Chip8Instruction()

    /**
     * Execute subroutine starting at address nnn
     */
    data class jsr(val nnn: Short) : Chip8Instruction()

    /**
     * Skip the following instruction if the value of register Vx equals nn
     */
    data class skeq(val x: Byte, val nn: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
        }
    }

    /**
     * Skip the following instruction if the value of register Vx is not equal to nn
     */
    data class skne(val x: Byte, val nn: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
        }
    }

    /**
     * Skip the following instruction if the value of register Vx is equal to the value of register Vy
     */
    data class skeq2(val x: Byte, val y: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
            check(y.isValidVRegister)
        }
    }

    /**
     * Store number nn in register Vx
     */
    data class mov(val x: Byte, val nn: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
        }
    }

    /**
     * Add the value nn to register Vx
     */
    data class add(val x: Byte, val nn: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
        }
    }

    /**
     * Store the value of register Vy in register Vx
     */
    data class mov2(val x: Byte, val y: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
            check(y.isValidVRegister)
        }
    }

    /**
     * Set Vx to Vx OR Vy
     */
    data class or(val x: Byte, val y: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
            check(y.isValidVRegister)
        }
    }

    /**
     * Set Vx to Vx AND Vy
     */
    data class and(val x: Byte, val y: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
            check(y.isValidVRegister)
        }
    }

    /**
     * Set Vx to Vx XOR Vy
     */
    data class xor(val x: Byte, val y: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
            check(y.isValidVRegister)
        }
    }

    /**
     * Add the value of register Vy to register Vx; Set VF to 01 if a carry occurs
     * Set VF to 00 if a carry does not occur
     */
    data class add2(val x: Byte, val y: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
            check(y.isValidVRegister)
        }
    }

    /**
     * Subtract the value of register Vy from register Vx
     * Set VF to 00 if a borrow occurs
     * Set VF to 01 if a borrow does not occur
     */
    data class sub(val x: Byte, val y: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
            check(y.isValidVRegister)
        }
    }

    /**
     * Store the value of register Vy shifted right one bit in register Vx¹
     * Set register VF to the least significant bit prior to the shift
     * Vy is unchanged
     */
    data class shr(val x: Byte, val y: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
            check(y.isValidVRegister)
        }
    }

    /**
     * Set register Vx to the value of Vy minus Vx
     * Set VF to 00 if a borrow occurs
     * Set VF to 01 if a borrow does not occur
     */
    data class rsb(val x: Byte, val y: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
            check(y.isValidVRegister)
        }
    }

    /**
     * Store the value of register Vy shifted left one bit in register Vx¹
     * Set register VF to the most significant bit prior to the shift
     * Vy is unchanged
     */
    data class shl(val x: Byte, val y: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
            check(y.isValidVRegister)
        }
    }

    /**
     * Skip the following instruction if the value of register Vx is not equal to the value of register Vy
     */
    data class skne2(val x: Byte, val y: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
            check(y.isValidVRegister)
        }
    }

    /**
     * Store memory address nnn in register I
     */
    data class mvi(val nnn: Short) : Chip8Instruction()

    /**
     * Jump to address nnn + V0
     */
    data class jmi(val nnn: Short) : Chip8Instruction()

    /**
     * Set Vx to a random number with a mask of nn
     */
    data class rand(val x: Byte, val nn: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
        }
    }

    /**
     * Draw a sprite at position Vx, Vy with n Bytes of sprite data starting at the address stored in I
     * Set VF to 01 if any set pixels are changed to unset, and 00 otherwise
     */
    data class sprite(val x: Byte, val y: Byte, val n: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
            check(y.isValidVRegister)
        }
    }

    /**
     * Skip the following instruction if the key corresponding to the hex value currently stored in register Vx is pressed
     */
    data class skpr(val x: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
        }
    }

    /**
     * Skip the following instruction if the key corresponding to the hex value currently stored in register Vx is not pressed
     */
    data class skup(val x: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
        }
    }

    /**
     * Store the current value of the delay timer in register Vx
     */
    data class gdelay(val x: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
        }
    }

    /**
     * Wait for a keypress and store the result in register Vx
     */
    data class key(val x: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
        }
    }

    /**
     * Set the delay timer to the value of register Vx
     */
    data class sdelay(val x: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
        }
    }

    /**
     * Set the sound timer to the value of register Vx
     */
    data class ssound(val x: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
        }
    }

    /**
     * Add the value stored in register Vx to register I
     */
    data class adi(val x: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
        }
    }

    /**
     * Set I to the memory address of the sprite data corresponding to the hexadecimal digit stored in register Vx
     */
    data class font(val x: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
        }
    }

    /**
     * Store the binary-coded decimal equivalent of the value stored in register Vx at addresses I, I + 1, and I + 2
     */
    data class bcd(val x: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
        }
    }

    /**
     * Store the values of registers V0 to Vx inclusive in memory starting at address I
     * I is set to I + x + 1 after operation
     *
     * NB: Erik Bryntse’s S-CHIP documentation incorrectly implies this instruction does not modify
     * the I register. Certain S-CHIP-compatible emulators may implement this instruction in this manner.
     */
    data class str(val x: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
        }
    }

    /**
     * Fill registers V0 to Vx inclusive with the values stored in memory starting at address I
     * I is set to I + x + 1 after operation
     *
     * NB: Erik Bryntse’s S-CHIP documentation incorrectly implies this instruction does not modify
     * the I register. Certain S-CHIP-compatible emulators may implement this instruction in this manner.
     */
    data class ldr(val x: Byte) : Chip8Instruction() {
        init {
            check(x.isValidVRegister)
        }
    }
}