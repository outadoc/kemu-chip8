package fr.outadoc.kemu

inline val Int.s
    get() = this.toUShort()

inline val Int.b
    get() = this.toUByte()

inline infix fun UShort.shr(bitCount: Int): UShort = (toUInt() shr bitCount).toUShort()
inline infix fun UByte.shr(bitCount: Int): UByte = (toUInt() shr bitCount).toUByte()

inline infix fun UShort.shl(bitCount: Int): UShort = (toUInt() shl bitCount).toUShort()
inline infix fun UByte.shl(bitCount: Int): UByte = (toUInt() shl bitCount).toUByte()
