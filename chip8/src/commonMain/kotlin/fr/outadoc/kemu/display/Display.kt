package fr.outadoc.kemu.display

interface Display {
    fun clear()
    fun displaySprite(position: Point<UByte>, sprite: UByteArray): Boolean
}