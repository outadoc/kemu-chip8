package fr.outadoc.kemu.display

interface DisplayDriver<T: Display> {
    fun attach(display: T)
    fun detach()
}