package fr.outadoc.kemu.memory

interface BusDevice<Width> {
    fun read(addr: Width): Byte
    fun write(addr: Width, value: Byte)
}