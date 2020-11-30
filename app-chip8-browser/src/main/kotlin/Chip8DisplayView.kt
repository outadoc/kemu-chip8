import fr.outadoc.kemu.array.UByteArray2
import fr.outadoc.kemu.b
import fr.outadoc.kemu.chip8.Chip8Constants.DISPLAY_HEIGHT
import fr.outadoc.kemu.chip8.Chip8Constants.DISPLAY_WIDTH
import fr.outadoc.kemu.chip8.display.createCanvasContext
import fr.outadoc.kemu.display.FrameConsumer
import fr.outadoc.kemu.theme.Theme
import org.w3c.dom.CanvasRenderingContext2D

class Chip8DisplayView(private val context: CanvasRenderingContext2D) : FrameConsumer {

    private var currentFrame: UByteArray2? = null
    private val offScreenContext: CanvasRenderingContext2D = createCanvasContext()

    var theme = Theme.values().first()
        set(value) {
            field = value
            drawFrame()
        }

    init {
        with(context.canvas) {
            width = DISPLAY_WIDTH
            height = DISPLAY_HEIGHT
        }

        with(offScreenContext.canvas) {
            width = DISPLAY_WIDTH
            height = DISPLAY_HEIGHT
        }
    }

    override fun displayFrame(frame: UByteArray2?) {
        currentFrame = frame
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
            offScreenContext.fillStyle =
                if (pixel == 0x0.b) theme.background.toString() else theme.foreground.toString()
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