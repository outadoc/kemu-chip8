package fr.outadoc.kemu.memory

interface Bus<Width> {
    fun read(addr: Width): UByte
    fun write(addr: Width, value: UByte)
}