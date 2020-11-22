package fr.outadoc.kemu.chip8.display

import fr.outadoc.kemu.array.UByteArray2
import fr.outadoc.kemu.b
import fr.outadoc.kemu.chip8.Chip8Constants.DISPLAY_HEIGHT
import fr.outadoc.kemu.chip8.Chip8Constants.DISPLAY_WIDTH
import fr.outadoc.kemu.display.DisplayDriver
import fr.outadoc.kemu.theme.Theme
import fr.outadoc.kemu.theme.toColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JComponent

class SwingChip8DisplayDriver : JComponent(), DisplayDriver<Chip8Display> {

    private val scaleFactor = 10

    private var currentFrame: UByteArray2? = null
    private var job: Job? = null

    var theme: Theme = Theme.WHITE_ON_BLACK
        set(value) {
            field = value
            background = value.background.toColor()
            foreground = value.foreground.toColor()
            repaint()
        }

    init {
        background = theme.background.toColor()
        foreground = theme.foreground.toColor()

        preferredSize = Dimension(
            (DISPLAY_WIDTH * scaleFactor),
            (DISPLAY_HEIGHT * scaleFactor)
        )
    }

    override fun attach(display: Chip8Display) {
        job?.cancel()
        job = GlobalScope.launch(Dispatchers.Main) {
            display.frameBufferFlow.collect { fb ->
                currentFrame = fb.copyOf()
                repaint()
            }
        }
    }

    override fun detach() {
        job?.cancel()
        job = null
        currentFrame = null
        repaint()
    }

    override fun paintComponent(g: Graphics?) {
        if (g !is Graphics2D) return

        if (currentFrame == null) {
            super.paintComponent(g)
            return
        }

        currentFrame?.forEachIndexed { i, pixel ->
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
}