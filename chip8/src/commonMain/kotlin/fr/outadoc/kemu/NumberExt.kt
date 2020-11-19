package fr.outadoc.kemu

inline val Int.s
    get() = this.toUShort()

inline val Int.b
    get() = this.toUByte()

inline infix fun UShort.shr(bitCount: Int): UShort = (toUInt() shr bitCount).toUShort()
