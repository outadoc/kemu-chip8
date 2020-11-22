package fr.outadoc.kemu.chip8.memory

import fr.outadoc.kemu.array.UByteArray2
import fr.outadoc.kemu.array.get
import fr.outadoc.kemu.array.set
import fr.outadoc.kemu.chip8.Chip8Constants
import fr.outadoc.kemu.memory.BusDevice

class Chip8RAM : BusDevice<UShort> {

    private val ram = UByteArray2(Chip8Constants.RAM_SIZE)

    override fun read(addr: UShort): UByte {
        return ram[addr]
    }

    override fun write(addr: UShort, value: UByte) {
        ram[addr] = value
    }
}