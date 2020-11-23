import kotlinx.browser.document
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import fr.outadoc.kemu.chip8.Chip8Constants

fun main() {
    val context = initCanvas()
}

fun initCanvas(): CanvasRenderingContext2D {
    val canvas = document.createElement("canvas") as HTMLCanvasElement
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    document.body?.appendChild(canvas)
    return context
}