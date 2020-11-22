package fr.outadoc.kemu.devices

import fr.outadoc.kemu.array.UByteArray2

interface CPU {
    fun start()
    fun loop(): Boolean
    fun loadProgram(program: UByteArray2)
}