package fr.outadoc.kemu.display

import fr.outadoc.kemu.array.UByteArray2
import kotlinx.coroutines.flow.MutableStateFlow

interface Display {
    fun clear()
    val frameBufferFlow: MutableStateFlow<UByteArray2>
    fun displaySprite(position: Point<UByte>, sprite: UByteArray2): Boolean
}