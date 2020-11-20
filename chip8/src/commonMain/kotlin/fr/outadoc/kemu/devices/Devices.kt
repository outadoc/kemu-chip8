package fr.outadoc.kemu.devices

interface CPU {
    fun start()
    fun loop()
}

interface Timer

interface Registers

interface Bus<Width> {
    fun read(addr: Width): UByte
    fun write(addr: Width, value: UByte)
}

interface BusDevice<Width> {
    fun read(addr: Width): UByte
    fun write(addr: Width, value: UByte)
}

interface ControlUnit {

}