package fr.outadoc.kemu

inline operator fun UByteArray.get(index: UByte): UByte = this[index.toInt()]
inline operator fun UByteArray.set(index: UByte, value: UByte) {
    this[index.toInt()] = value
}

inline operator fun UByteArray.get(index: UShort): UByte = this[index.toInt()]
inline operator fun UByteArray.set(index: UShort, value: UByte) {
    this[index.toInt()] = value
}
