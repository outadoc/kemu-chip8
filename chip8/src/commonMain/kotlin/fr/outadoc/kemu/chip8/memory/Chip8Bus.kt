package fr.outadoc.kemu.chip8.memory

import fr.outadoc.kemu.b
import fr.outadoc.kemu.memory.Bus
import fr.outadoc.kemu.memory.BusDevice
import kotlin.experimental.or

class Chip8Bus(private val devices: List<BusDevice<Short>>) : Bus<Short> {

    override fun read(addr: Short): Byte {
        return devices.fold(0x00.b) { acc, memory ->
            // Every device is asked for a value. If they don't return 0 we or it with the others.
            acc or memory.read(addr)
        }
    }

    override fun write(addr: Short, value: Byte) {
        devices.forEach { memory ->
            memory.write(addr, value)
        }
    }
}