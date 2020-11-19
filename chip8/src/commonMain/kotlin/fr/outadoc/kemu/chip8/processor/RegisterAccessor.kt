package fr.outadoc.kemu.chip8.processor

interface RegisterAccessor<T> {
    val read: T
    fun updateRegisters(advance: Int = 1, block: (T.() -> T)? = null)
}

