package fr.outadoc.kemu.devices

interface CPU {
    fun start()
    fun loop()
    fun loadProgram(program: UByteArray)
}