package fr.outadoc.kemu.devices

interface RandomGenerator {
    var seed: Int
    fun nextByte(): UByte
}