package fr.outadoc.kemu

inline val Int.s
    get() = this.toShort()

inline val Int.b
    get() = this.toByte()

inline infix fun Short.shr(bitCount: Int): Short = (toInt() shr bitCount).toShort()
inline infix fun Byte.shr(bitCount: Int): Byte = (toInt() shr bitCount).toByte()

inline infix fun Short.shl(bitCount: Int): Short = (toInt() shl bitCount).toShort()
inline infix fun Byte.shl(bitCount: Int): Byte = (toInt() shl bitCount).toByte()
