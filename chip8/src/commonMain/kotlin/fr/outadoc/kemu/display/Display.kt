package fr.outadoc.kemu.display

interface Display {
    fun clear()
    fun displaySprite(x: UByte, y: UByte, sprite: UByteArray): Boolean
}