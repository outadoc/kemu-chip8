package fr.outadoc.kemu.chip8.display

import fr.outadoc.kemu.b
import fr.outadoc.kemu.chip8.Chip8Constants
import fr.outadoc.kemu.display.Display
import fr.outadoc.kemu.display.Point
import fr.outadoc.kemu.get
import fr.outadoc.kemu.set
import fr.outadoc.kemu.shr
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
        val (x, y) = position
        var hasAPixelBeenErased = false

        sprite.forEachIndexed { iy, row ->
            (0 until 8).map { bit ->
                (row and ((1 shl bit).toUByte())) shr bit
            }.forEachIndexed { ix, pixel ->
                // Calculate screen coordinates for this pixel,
                // possibly wrapping around to the opposite side of the screen
                val targetX = (x + ix.toUShort()) % frameBufferWidth.toUShort()
                val targetY = (y + iy.toUShort()) % frameBufferHeight.toUShort()
                val frameBufferIndex = (targetX + targetY * frameBufferWidth.toUShort()).toUShort()

                // xor the pixel onto the screen and check if we're erasing anything
                val res = frameBuffer[frameBufferIndex] xor pixel
                if (frameBuffer[frameBufferIndex] > res) {
                    hasAPixelBeenErased = true
                }
                frameBuffer[frameBufferIndex] = res
            }
        }

        return hasAPixelBeenErased
    }
}