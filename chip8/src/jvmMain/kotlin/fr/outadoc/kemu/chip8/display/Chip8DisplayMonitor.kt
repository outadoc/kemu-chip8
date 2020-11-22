package fr.outadoc.kemu.chip8.display

import fr.outadoc.kemu.array.UByteArray2
import fr.outadoc.kemu.b
import fr.outadoc.kemu.chip8.Chip8Constants.DISPLAY_HEIGHT
import fr.outadoc.kemu.chip8.Chip8Constants.DISPLAY_WIDTH
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JComponent

class Chip8DisplayMonitor : JComponent() {

    private val scaleFactor = 10

    private var latestFrame: UByteArray2? = null

    private var job: Job? = null

    init {
        background = Color.BLACK
        foreground = Color.WHITE
        preferredSize = Dimension(
            (DISPLAY_WIDTH * scaleFactor),
            (DISPLAY_HEIGHT * scaleFactor)
        )
    }

    fun attachToDisplay(display: Chip8Display) {
        job?.cancel()
        job = GlobalScope.launch(Dispatchers.Main) {
            display.frameBufferFlow.collect { fb ->
                latestFrame = fb.copyOf()
                repaint()
            }
        }
    }

    override fun paintComponent(g: Graphics?) {
        if (g !is Graphics2D) return

        latestFrame?.forEachIndexed { i, pixel ->
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
        val frameBuffer = latestFrame ?: return
        frameBuffer.indices.forEach { i ->
            val x = i % DISPLAY_WIDTH
            val y = i / DISPLAY_WIDTH
            frameBuffer[i] =
                if ((x % 2 == 0 && y % 2 == 1) || (x % 2 == 1 && y % 2 == 0)) 0x0.b else 0x1.b
        }
    }

}