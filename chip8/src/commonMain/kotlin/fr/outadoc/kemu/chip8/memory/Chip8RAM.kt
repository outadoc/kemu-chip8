package fr.outadoc.kemu.chip8.memory

import fr.outadoc.kemu.chip8.Chip8Constants
import fr.outadoc.kemu.devices.BusDevice
import fr.outadoc.kemu.get
import fr.outadoc.kemu.set

class Chip8RAM : BusDevice<UShort> {

    private val ram = UByteArray(Chip8Constants.RAM_SIZE)

    override fun read(addr: UShort): UByte {
        return ram[addr]
    }

    override fun write(addr: UShort, value: UByte) {
        ram[addr] = value
    }
}