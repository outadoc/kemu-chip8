package fr.outadoc.kemu.random

interface RandomGenerator {
    var seed: Int
    fun nextByte(): UByte
}