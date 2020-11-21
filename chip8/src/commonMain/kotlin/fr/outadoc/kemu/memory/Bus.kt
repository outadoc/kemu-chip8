package fr.outadoc.kemu.memory

interface Bus<Width> {
    fun read(addr: Width): Byte
    fun write(addr: Width, value: Byte)
}