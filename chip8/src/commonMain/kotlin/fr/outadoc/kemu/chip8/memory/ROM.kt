package fr.outadoc.kemu.chip8.memory

import fr.outadoc.kemu.b
import fr.outadoc.kemu.get
import fr.outadoc.kemu.memory.BusDevice

abstract class ROM(private val start: Short) : BusDevice<Short> {

    protected abstract val rom: ByteArray

    private val end: Short
        get() = (start + rom.size.toShort()).toShort()

    override fun read(addr: Short): Byte {
        if (addr !in start..end) return 0x0.b
        return rom[(addr - start).toShort()]
    }

    override fun write(addr: Short, value: Byte) {
        // This is ROM
    }
}