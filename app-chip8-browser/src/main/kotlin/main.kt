import fr.outadoc.kemu.Speed
import fr.outadoc.kemu.array.UByteArray2
import fr.outadoc.kemu.chip8.browser.display.CanvasChip8DisplayDriver
import kotlinx.browser.document
import fr.outadoc.kemu.chip8.Chip8Runner
import fr.outadoc.kemu.chip8.browser.display.createCanvasContext
import fr.outadoc.kemu.chip8.display.Chip8Keypad
import fr.outadoc.kemu.theme.Theme
import kotlinx.browser.window
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Uint8Array
import org.khronos.webgl.get
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement
import org.w3c.files.File
import org.w3c.files.FileReader
import org.w3c.files.get

private lateinit var displayDriver: CanvasChip8DisplayDriver
private lateinit var keypad: Chip8Keypad
private lateinit var runner: Chip8Runner

fun main() {
    window.onload = {
        val context = createCanvasContext()
        displayDriver = CanvasChip8DisplayDriver(context)
        keypad = Chip8Keypad()

        runner = Chip8Runner(keypad, displayDriver).apply {
            speed = Speed.SUPER_SLOW
        }

        document.body?.appendChild(
            form(
                onThemeSelected = { theme ->
                    displayDriver.theme = theme
                }, onProgramSelected = { program ->
                    runner.execute(program)
                }
            )
        )

        document.body?.appendChild(context.canvas)
    }
}

fun form(onThemeSelected: (Theme) -> Unit, onProgramSelected: (UByteArray2) -> Unit): HTMLFormElement {
    val programSelect = (document.createElement("input") as HTMLInputElement).apply {
        type = "file"
        oninput = {
            files?.get(0)?.let { file: File ->
                FileReader().apply {
                    onloadend = {
                        val res = Uint8Array(result as ArrayBuffer)
                        val program = UByteArray2(res.length)
                        for (i in 0 until res.length) {
                            program[i] = res[i].toUByte()
                        }
                        onProgramSelected(program)
                    }
                    readAsArrayBuffer(file)
                }
            }
        }
    }

    return (document.createElement("form") as HTMLFormElement).apply {
        id = "settings"
        append(programSelect)
    }
}