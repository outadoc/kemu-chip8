package fr.outadoc.kemu.registers

import fr.outadoc.kemu.chip8.processor.Chip8Registers
import kotlinx.coroutines.flow.Flow

interface RegisterAccessor<T : Registers> {

    /**
     * Read-only access to the registers.
     */
    val read: T

    /**
     * Flow of the registers, updated every time they change.
     */
    val flow: Flow<Chip8Registers>

    /**
     * Updates the registers.
     *
     * @param advance The PC will be incremented by the value of [advance].
     * @param block The [block] will be executed with the current value of the registers,
     * and its return value will be used as their new value.
     */
    fun update(advance: Int = 2, block: (T.() -> T)? = null)
}