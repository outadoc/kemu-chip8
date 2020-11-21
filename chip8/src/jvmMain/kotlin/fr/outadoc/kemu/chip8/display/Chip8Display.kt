package fr.outadoc.kemu.chip8.display

import fr.outadoc.kemu.b
import fr.outadoc.kemu.chip8.Chip8Constants
import fr.outadoc.kemu.display.Display
import fr.outadoc.kemu.display.Point
import fr.outadoc.kemu.shr
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import javax.swing.JComponent

actual class Chip8Display : Display, JComponent() {

    private val scaleFactor = 100.0

    private val frameBufferWidth = Chip8Constants.DISPLAY_WIDTH / 8
    private val frameBufferHeight = Chip8Constants.DISPLAY_HEIGHT / 8
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
    }

    override fun paintComponent(g: Graphics?) {
        g ?: return
        frameBuffer.forEachIndexed { i, byte ->
            // Byte i in buffer is at screen position (i mod width, i / width)
            val startX = i.rem(frameBufferWidth)
            val y = i / frameBufferWidth

            // Display each bit of the byte
            for (bit in 0 until 8) {
                // get the nth bit of the byte
                // e.g for bit = 2: byte & 0b00000100 >> 2
                val pixel = (byte and ((1 shl bit).toUByte())) shr bit

                // Draw foreground color if bit is set, background otherwise
                g.color = if (pixel == 0x0.b) background else foreground
                g.drawRect(startX + bit, y, 1, 1)
            }
        }
    }

    override fun paint(g: Graphics?) {
        if (g !is Graphics2D) return
        g.transform = scaleTransform
        super.paint(g)
    }

    override fun clear() {
        (0..frameBuffer.size).forEach { i ->
            frameBuffer[i] = 0x0.b
        }
    }

    override fun displaySprite(position: Point<UByte>, sprite: UByteArray): Boolean {
        TODO("Not yet implemented")
    }
}