package fr.outadoc.kemu.display

interface FrameDispatcher {
    fun attach(display: Display)
    fun detach()
}