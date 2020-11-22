package fr.outadoc.kemu.array

inline operator fun UByteArray2.get(index: UByte): UByte = this[index.toInt()]
inline operator fun UByteArray2.set(index: UByte, value: UByte) {
    this[index.toInt()] = value
}

inline operator fun UByteArray2.get(index: UShort): UByte = this[index.toInt()]
inline operator fun UByteArray2.set(index: UShort, value: UByte) {
    this[index.toInt()] = value
}

inline fun ByteArray.toUByteArray2(): UByteArray2 = UByteArray2(this)
inline fun Collection<UByte>.toUByteArray2(): UByteArray2 = ubyteArray2Of(*this.toUByteArray())