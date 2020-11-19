package fr.outadoc.kemu.chip8.memory

import fr.outadoc.kemu.b
import fr.outadoc.kemu.devices.Bus
import fr.outadoc.kemu.devices.BusDevice

class Chip8Bus(private val devices: List<BusDevice<UShort>>) : Bus<UShort> {

    override fun read(addr: UShort): UByte {
        return devices.fold(0x00.b) { acc, memory ->
            // Every device is asked for a value. If they don't return 0 we or it with the others.
            acc or memory.read(addr)
        }
    }

    override fun write(addr: UShort, value: UByte) {
        devices.forEach { memory ->
            memory.write(addr, value)
        }
    }
}