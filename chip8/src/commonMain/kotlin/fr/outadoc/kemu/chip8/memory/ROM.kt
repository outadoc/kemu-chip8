package fr.outadoc.kemu.chip8.memory

import fr.outadoc.kemu.b
import fr.outadoc.kemu.get
import fr.outadoc.kemu.memory.BusDevice

abstract class ROM(private val start: UShort) : BusDevice<UShort> {

    protected abstract val rom: UByteArray

    private val end: UShort
        get() = (start + rom.size.toUShort()).toUShort()

    override fun read(addr: UShort): UByte {
        if (addr !in start..end) return 0x0.b
        return rom[(addr - start).toUShort()]
    }

    override fun write(addr: UShort, value: UByte) {
        // This is ROM
    }
}