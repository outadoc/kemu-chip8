package fr.outadoc.kemu.chip8.processor

interface RegisterAccessor<T> {

    /**
     * Read-only access to the registers.
     */
    val read: T

    /**
     * Updates the registers.
     *
     * @param advance The PC will be incremented by the value of [advance].
     * @param block The [block] will be executed with the current value of the registers,
     * and its return value will be used as their new value.
     */
    fun update(advance: Int = 1, block: (T.() -> T)? = null)
}

