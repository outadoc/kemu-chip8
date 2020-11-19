package fr.outadoc.kemu.devices

interface CPU {

}

interface Timer {

}

interface Bus<Width> {
    fun read(addr: Width): UByte
    fun write(addr: Width, value: UByte)
}

interface BusDevice<Width> {
    fun read(addr: Width): UByte
    fun write(addr: Width, value: UByte)
}

interface Clock {

}

interface ControlUnit {

}