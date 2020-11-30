package fr.outadoc.kemu.display

import fr.outadoc.kemu.array.UByteArray2

interface FrameConsumer {
    fun displayFrame(frame: UByteArray2?)
}