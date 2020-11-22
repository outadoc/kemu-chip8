package fr.outadoc.kemu.display

import fr.outadoc.kemu.array.UByteArray2

interface Display {
    fun clear()
    fun displaySprite(position: Point<UByte>, sprite: UByteArray2): Boolean
}