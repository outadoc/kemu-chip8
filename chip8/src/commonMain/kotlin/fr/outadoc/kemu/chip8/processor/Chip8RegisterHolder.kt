package fr.outadoc.kemu.chip8.processor

import fr.outadoc.kemu.registers.RegisterAccessor
import fr.outadoc.kemu.s
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class Chip8RegisterHolder : RegisterAccessor<Chip8Registers> {

    private val _registers: MutableStateFlow<Chip8Registers> = MutableStateFlow(Chip8Registers())
    override val flow: Flow<Chip8Registers>
        get() = _registers

    override val read: Chip8Registers
        get() = _registers.value

    override fun update(advance: Int, block: (Chip8Registers.() -> Chip8Registers)?) {
        val updatedRegisters = (if (block != null) read.block() else read)
        _registers.value = updatedRegisters.let { r ->
            if (advance > 0) {
                r.copy(pc = (r.pc + advance.s).toUShort())
            } else r
        }
    }
}