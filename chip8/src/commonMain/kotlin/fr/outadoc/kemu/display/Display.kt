package fr.outadoc.kemu.display

import fr.outadoc.kemu.array.UByteArray2
import kotlinx.coroutines.flow.MutableStateFlow

interface Display {
    val frameBufferFlow: MutableStateFlow<UByteArray2>
    fun clear()
    fun displaySprite(position: Point<UByte>, sprite: UByteArray2): Boolean
}