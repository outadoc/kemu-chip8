package fr.outadoc.kemu

inline operator fun ByteArray.get(index: Byte): Byte = this[index.toInt()]
inline operator fun ByteArray.set(index: Byte, value: Byte) {
    this[index.toInt()] = value
}

inline operator fun ByteArray.get(index: Short): Byte = this[index.toInt()]
inline operator fun ByteArray.set(index: Short, value: Byte) {
    this[index.toInt()] = value
}
