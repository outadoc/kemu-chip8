package fr.outadoc.kemu.random

import kotlin.random.Random

class DefaultRandomGenerator : RandomGenerator {

    override var seed: Int = 0
        set(value) {
            field = value
            random = Random(seed)
        }

    private var random = Random(seed)

    override fun nextByte(): Byte {
        return random.nextBits(8).toByte()
    }
}