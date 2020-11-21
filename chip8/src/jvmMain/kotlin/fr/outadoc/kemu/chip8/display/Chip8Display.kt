package fr.outadoc.kemu.chip8.display

import fr.outadoc.kemu.display.Display
import fr.outadoc.kemu.display.Point
import javax.swing.JComponent

actual class Chip8Display : Display, JComponent() {

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun displaySprite(position: Point<UByte>, sprite: UByteArray): Boolean {
        TODO("Not yet implemented")
    }
}