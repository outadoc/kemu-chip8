package fr.outadoc.kemu

operator fun UByteArray.get(index: UByte): UByte = this[index.toInt()]
operator fun UByteArray.set(index: UByte, value: UByte) {
    this[index.toInt()] = value
}

operator fun UByteArray.get(index: UShort): UByte = this[index.toInt()]
operator fun UByteArray.set(index: UShort, value: UByte) {
    this[index.toInt()] = value
}
