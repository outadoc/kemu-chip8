package fr.outadoc.kemu.chip8.display

import fr.outadoc.kemu.b
import fr.outadoc.kemu.chip8.Chip8Constants
import fr.outadoc.kemu.display.Display
import fr.outadoc.kemu.display.Point
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import javax.swing.JComponent

actual class Chip8Display : Display, JComponent() {

    private val scaleFactor = 20.0

    private val frameBufferWidth = Chip8Constants.DISPLAY_WIDTH
    private val frameBufferHeight = Chip8Constants.DISPLAY_HEIGHT
    private val frameBuffer = UByteArray(frameBufferWidth * frameBufferHeight)

    private val scaleTransform = AffineTransform().apply {
        scale(scaleFactor, scaleFactor)
    }

    init {
        background = Color.BLACK
        foreground = Color.WHITE
        size = Dimension(
            (Chip8Constants.DISPLAY_HEIGHT * scaleFactor).toInt(),
            (Chip8Constants.DISPLAY_WIDTH * scaleFactor).toInt()
        )

        displayCheckerBoard()
    }

    override fun paintComponent(g: Graphics?) {
        if (g !is Graphics2D) return

        frameBuffer.forEachIndexed { i, pixel ->
            // Byte i in buffer is at screen position (i mod width, i / width)
            val x = i % frameBufferWidth
            val y = i / frameBufferWidth

            // Draw foreground color if bit is set, background otherwise
            g.color = if (pixel == 0x0.b) background else foreground
            g.drawRect(x, y, 1, 1)
        }
    }

    override fun paint(g: Graphics?) {
        if (g !is Graphics2D) return
        g.transform = scaleTransform
        super.paint(g)
    }

    override fun clear() {
        frameBuffer.indices.forEach { i ->
            frameBuffer[i] = 0x0.b
        }
    }

    private fun displayCheckerBoard() {
        frameBuffer.indices.forEach { i ->
            val x = i % frameBufferWidth
            val y = i / frameBufferWidth
            frameBuffer[i] =
                if ((x % 2 == 0 && y % 2 == 1) || (x % 2 == 1 && y % 2 == 0)) 0x0.b else 0x1.b
        }
    }

    override fun displaySprite(position: Point<UByte>, sprite: UByteArray): Boolean {
        TODO("Not yet implemented")
    }
}