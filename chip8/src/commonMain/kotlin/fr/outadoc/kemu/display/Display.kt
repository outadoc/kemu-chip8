package fr.outadoc.kemu.display

interface Display {
    fun clear()
    fun displaySprite(position: Point<Byte>, sprite: ByteArray): Boolean
}