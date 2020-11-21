package fr.outadoc.kemu.memory

interface BusDevice<Width> {
    fun read(addr: Width): UByte
    fun write(addr: Width, value: UByte)
}