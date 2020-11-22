package fr.outadoc.kemu.devices

import fr.outadoc.kemu.array.UByteArray2

interface CPU {
    suspend fun initializeTimers()
    fun loop(): Boolean
    fun loadProgram(program: UByteArray2)
}