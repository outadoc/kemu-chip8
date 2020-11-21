package fr.outadoc.kemu.chip8.memory

import fr.outadoc.kemu.chip8.Chip8Constants
import fr.outadoc.kemu.memory.BusDevice
import fr.outadoc.kemu.get
import fr.outadoc.kemu.set

class Chip8RAM : BusDevice<Short> {

    private val ram = ByteArray(Chip8Constants.RAM_SIZE)

    override fun read(addr: Short): Byte {
        return ram[addr]
    }

    override fun write(addr: Short, value: Byte) {
        ram[addr] = value
    }
}