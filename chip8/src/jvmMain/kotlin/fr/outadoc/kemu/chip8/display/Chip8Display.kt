package fr.outadoc.kemu.chip8.display

import fr.outadoc.kemu.chip8.Chip8Constants
import fr.outadoc.kemu.display.Display
import fr.outadoc.kemu.display.Point
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JComponent

actual class Chip8Display : Display, JComponent() {

    init {
        background = Color.BLACK
        foreground = Color.WHITE
        size = Dimension(Chip8Constants.DISPLAY_HEIGHT, Chip8Constants.DISPLAY_WIDTH)
    }

    private val frameBuffer = UByteArray(
        (Chip8Constants.DISPLAY_WIDTH / 8) * (Chip8Constants.DISPLAY_HEIGHT / 8)
    )

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        g ?: return
    }

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun displaySprite(position: Point<UByte>, sprite: UByteArray): Boolean {
        TODO("Not yet implemented")
    }
}