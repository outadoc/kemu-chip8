import fr.outadoc.kemu.array.UByteArray2
import fr.outadoc.kemu.display.DisplayDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.w3c.dom.CanvasRenderingContext2D

class CanvasChip8DisplayDriver(private val context: CanvasRenderingContext2D) : DisplayDriver<Chip8Display> {

    private val scaleFactor = 10

    private var currentFrame: UByteArray2? = null
    private var job: Job? = null

    init {
        context.canvas.width = Chip8Constants.DISPLAY_HEIGHT * scaleFactor
        context.canvas.height = Chip8Constants.DISPLAY_WIDTH * scaleFactor
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