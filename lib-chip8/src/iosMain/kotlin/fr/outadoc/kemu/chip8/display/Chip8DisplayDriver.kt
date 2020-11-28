package fr.outadoc.kemu.chip8.display

import fr.outadoc.kemu.display.DisplayDriver
import fr.outadoc.kemu.chip8.display.Chip8Display

actual class Chip8DisplayDriver : DisplayDriver<Chip8Display> {

    override fun attach(display: Chip8Display) {
    }

    override fun detach() {
    }
}