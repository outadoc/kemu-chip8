package fr.outadoc.kemu.chip8.browser.display

import fr.outadoc.kemu.array.UByteArray2
import fr.outadoc.kemu.b
import fr.outadoc.kemu.display.DisplayDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.w3c.dom.CanvasRenderingContext2D
import fr.outadoc.kemu.chip8.display.Chip8Display
import fr.outadoc.kemu.chip8.Chip8Constants.DISPLAY_HEIGHT
import fr.outadoc.kemu.chip8.Chip8Constants.DISPLAY_WIDTH
import fr.outadoc.kemu.theme.Theme
import kotlinx.coroutines.flow.collect

class CanvasChip8DisplayDriver(private val context: CanvasRenderingContext2D) : DisplayDriver<Chip8Display> {

    private val scaleFactor = 10.0

    private var currentFrame: UByteArray2? = null
    private var job: Job? = null

    private val offScreenContext: CanvasRenderingContext2D = createCanvasContext()

    var theme = Theme.WHITE_ON_BLACK

    init {
        with(context.canvas) {
            width = DISPLAY_WIDTH
            height = DISPLAY_HEIGHT

            with(style) {
                transformOrigin = "0 0"
                transform = "scale($scaleFactor)"
                imageRendering = "crisp-edges"
            }
        }

        with(offScreenContext.canvas) {
            width = DISPLAY_WIDTH
            height = DISPLAY_HEIGHT
        }
    }

    override fun attach(display: Chip8Display) {
        job?.cancel()
        job = GlobalScope.launch(Dispatchers.Main) {
            display.frameBufferFlow.collect { fb ->
                currentFrame = fb.copyOf()
                drawFrame()
            }
        }
    }

    override fun detach() {
        job?.cancel()
        job = null
        currentFrame = null
        drawFrame()
    }

    private fun drawFrame() {
        if (currentFrame == null) {
            offScreenContext.clear()
            swap()
            return
        }

        currentFrame?.forEachIndexed { i, pixel ->
            // Byte i in buffer is at screen position (i mod width, i / width)
            val x = i % DISPLAY_WIDTH
            val y = i / DISPLAY_WIDTH

            // Draw foreground color if bit is set, background otherwise
            offScreenContext.fillStyle = if (pixel == 0x0.b) theme.background.toString() else theme.foreground.toString()
            offScreenContext.fillRect(
                x.toDouble(), y.toDouble(), 1.0, 1.0
            )
        }

        swap()
    }

    private fun swap() {
        context.drawImage(offScreenContext.canvas, .0, .0)
        offScreenContext.clear()
    }

    private fun CanvasRenderingContext2D.clear() {
        clearRect(.0, .0, canvas.width.toDouble(), canvas.height.toDouble())
    }
}