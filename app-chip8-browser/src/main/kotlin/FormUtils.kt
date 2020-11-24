
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
import org.w3c.dom.*
import org.w3c.files.File
import org.w3c.files.FileReader
import org.w3c.files.get


fun form(
    initialTheme: Theme,
    initialSpeed: Speed,
    onThemeSelected: (Theme) -> Unit,
    onSpeedSelected: (Speed) -> Unit,
    onProgramSelected: (UByteArray2) -> Unit,
    onReset: () -> Unit,
    onStop: () -> Unit
): HTMLFormElement {
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

    val themeSelect = (document.createElement("select") as HTMLSelectElement).apply {
        Theme.values().forEach { theme ->
            appendChild(
                (document.createElement("option") as HTMLOptionElement).apply {
                    value = theme.name
                    textContent = theme.label
                }
            )
        }

        value = initialTheme.name

        oninput = {
            onThemeSelected(Theme.valueOf(value))
        }
    }

    val speedSelect = (document.createElement("select") as HTMLSelectElement).apply {
        Speed.values().forEach { speed ->
            appendChild(
                (document.createElement("option") as HTMLOptionElement).apply {
                    value = speed.name
                    textContent = speed.label
                }
            )
        }

        value = initialSpeed.name

        oninput = {
            onSpeedSelected(Speed.valueOf(value))
        }
    }

    val resetButton = (document.createElement("button") as HTMLButtonElement).apply {
        textContent = "Reset"
        onclick = {
            it.preventDefault()
            onReset()
        }
    }

    val stopButton = (document.createElement("button") as HTMLButtonElement).apply {
        textContent = "Stop"
        onclick = {
            it.preventDefault()
            onStop()
        }
    }

    return (document.createElement("form") as HTMLFormElement).apply {
        append(programSelect, themeSelect, speedSelect, resetButton, stopButton)
    }
}

private val Speed.label
    get() = when (this) {
        Speed.SUPER_SLOW -> "Super slow"
        Speed.SLOW -> "Slow"
        Speed.NORMAL -> "Normal"
        Speed.FAST -> "Fast"
        Speed.REALTIME -> "Real-time"
    }

private val Theme.label
    get() = when (this) {
        Theme.WHITE_ON_BLACK -> "White on black"
        Theme.BLACK_ON_WHITE -> "Black on white"
        Theme.MATRIX -> "Matrix"
        Theme.SOLARIZED_DARK -> "Solarized Dark"
        Theme.SOLARIZED_LIGHT -> "Solarized Light"
    }