package fr.outadoc.kemu.chip8.display

import fr.outadoc.kemu.b
import fr.outadoc.kemu.chip8.Chip8Constants.DISPLAY_HEIGHT
import fr.outadoc.kemu.chip8.Chip8Constants.DISPLAY_WIDTH
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JComponent

class Chip8DisplayMonitor(private val display: Chip8Display) : JComponent() {

    private val scaleFactor = 10

    init {
        background = Color.BLACK
        foreground = Color.WHITE
        size = Dimension(
            (DISPLAY_HEIGHT * scaleFactor),
            (DISPLAY_WIDTH * scaleFactor)
        )

        displayCheckerBoard()
    }

    override fun paintComponent(g: Graphics?) {
        if (g !is Graphics2D) return

        display.frameBuffer.forEachIndexed { i, pixel ->
            // Byte i in buffer is at screen position (i mod width, i / width)
            val x = i % DISPLAY_WIDTH
            val y = i / DISPLAY_WIDTH

            // Draw foreground color if bit is set, background otherwise
            g.color = if (pixel == 0x0.b) background else foreground
            g.fillRect(
                (x * scaleFactor), (y * scaleFactor), scaleFactor, scaleFactor
            )
        }
    }

    private fun displayCheckerBoard() {
        display.frameBuffer.indices.forEach { i ->
            val x = i % DISPLAY_WIDTH
            val y = i / DISPLAY_WIDTH
            display.frameBuffer[i] =
                if ((x % 2 == 0 && y % 2 == 1) || (x % 2 == 1 && y % 2 == 0)) 0x0.b else 0x1.b
        }
    }

}