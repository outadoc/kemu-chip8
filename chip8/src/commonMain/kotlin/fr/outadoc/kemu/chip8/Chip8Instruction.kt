package fr.outadoc.kemu.chip8

sealed class Chip8Instruction {

    /**
     * Execute machine language subroutine at address nnn
     */
    data class _0nnn(val nnn: UShort) : Chip8Instruction()

    /**
     * Clear the screen
     */
    object _00E0 : Chip8Instruction()

    /**
     * Return from a subroutine
     */
    object _00EE : Chip8Instruction()

    /**
     * Jump to address nnn
     */
    data class _1nnn(val nnn: UShort) : Chip8Instruction()

    /**
     * Execute subroutine starting at address nnn
     */
    data class _2nnn(val nnn: UShort) : Chip8Instruction()

    /**
     * Skip the following instruction if the value of register Vx equals nn
     */
    data class _3xnn(val x: UByte, val nn: UByte) : Chip8Instruction()

    /**
     * Skip the following instruction if the value of register Vx is not equal to nn
     */
    data class _4xnn(val x: UByte, val nn: UByte) : Chip8Instruction()

    /**
     * Skip the following instruction if the value of register Vx is equal to the value of register Vy
     */
    data class _5xy0(val x: UByte, val y: UByte) : Chip8Instruction()

    /**
     * Store number nn in register Vx
     */
    data class _6xnn(val x: UByte, val nn: UByte) : Chip8Instruction()

    /**
     * Add the value nn to register Vx
     */
    data class _7xnn(val x: UByte, val nn: UByte) : Chip8Instruction()

    /**
     * Store the value of register Vy in register Vx
     */
    data class _8xy0(val x: UByte, val y: UByte) : Chip8Instruction()

    /**
     * Set Vx to Vx OR Vy
     */
    data class _8xy1(val x: UByte, val y: UByte) : Chip8Instruction()

    /**
     * Set Vx to Vx AnD Vy
     */
    data class _8xy2(val x: UByte, val y: UByte) : Chip8Instruction()

    /**
     * Set Vx to Vx XOR Vy
     */
    data class _8xy3(val x: UByte, val y: UByte) : Chip8Instruction()

    /**
     * Add the value of register Vy to register Vx; Set VF to 01 if a carry occurs
     * Set VF to 00 if a carry does not occur
     */
    data class _8xy4(val x: UByte, val y: UByte) : Chip8Instruction()

    /**
     * Subtract the value of register Vy from register Vx
     * Set VF to 00 if a borrow occurs
     * Set VF to 01 if a borrow does not occur
     */
    data class _8xy5(val x: UByte, val y: UByte) : Chip8Instruction()

    /**
     * Store the value of register Vy shifted right one bit in register Vx¹
     * Set register VF to the least significant bit prior to the shift
     * Vy is unchanged
     */
    data class _8xy6(val x: UByte, val y: UByte) : Chip8Instruction()

    /**
     * Set register Vx to the value of Vy minus Vx
     * Set VF to 00 if a borrow occurs
     * Set VF to 01 if a borrow does not occur
     */
    data class _8xy7(val x: UByte, val y: UByte) : Chip8Instruction()

    /**
     * Store the value of register Vy shifted left one bit in register Vx¹
     * Set register VF to the most significant bit prior to the shift
     * Vy is unchanged
     */
    data class _8xyE(val x: UByte, val y: UByte) : Chip8Instruction()

    /**
     * Skip the following instruction if the value of register Vx is not equal to the value of register Vy
     */
    data class _9xy0(val x: UByte, val y: UByte) : Chip8Instruction()

    /**
     * Store memory address nnn in register I
     */
    data class _Annn(val nnn: UShort) : Chip8Instruction()

    /**
     * Jump to address nnn + V0
     */
    data class _Bnnn(val nnn: UShort) : Chip8Instruction()

    /**
     * Set Vx to a random number with a mask of nn
     */
    data class _Cxnn(val x: UByte, val nn: UByte) : Chip8Instruction()

    /**
     * Draw a sprite at position Vx, Vy with n UBytes of sprite data starting at the address stored in I
     * Set VF to 01 if any set pixels are changed to unset, and 00 otherwise
     */
    data class _Dxyn(val x: UByte, val y: UByte, val n: UByte) : Chip8Instruction()

    /**
     * Skip the following instruction if the key corresponding to the hex value currently stored in register Vx is pressed
     */
    data class _Ex9E(val x: UByte) : Chip8Instruction()

    /**
     * Skip the following instruction if the key corresponding to the hex value currently stored in register Vx is not pressed
     */
    data class _ExA1(val x: UByte) : Chip8Instruction()

    /**
     * Store the current value of the delay timer in register Vx
     */
    data class _Fx07(val x: UByte) : Chip8Instruction()

    /**
     * Wait for a keypress and store the result in register Vx
     */
    data class _Fx0A(val x: UByte) : Chip8Instruction()

    /**
     * Set the delay timer to the value of register Vx
     */
    data class _Fx15(val x: UByte) : Chip8Instruction()

    /**
     * Set the sound timer to the value of register Vx
     */
    data class _Fx18(val x: UByte) : Chip8Instruction()

    /**
     * Add the value stored in register Vx to register I
     */
    data class _Fx1E(val x: UByte) : Chip8Instruction()

    /**
     * Set I to the memory address of the sprite data corresponding to the hexadecimal digit stored in register Vx
     */
    data class _Fx29(val x: UByte) : Chip8Instruction()

    /**
     * Store the binary-coded decimal equivalent of the value stored in register Vx at addresses I, I + 1, and I + 2
     */
    data class _Fx33(val x: UByte) : Chip8Instruction()

    /**
     * Store the values of registers V0 to Vx inclusive in memory starting at address I
     * I is set to I + x + 1 after operation²
     */
    data class _Fx55(val x: UByte) : Chip8Instruction()

    /**
     * Fill registers V0 to Vx inclusive with the values stored in memory starting at address I
     * I is set to I + x + 1 after operation²
     */
    data class _Fx65(val x: UByte) : Chip8Instruction()
}