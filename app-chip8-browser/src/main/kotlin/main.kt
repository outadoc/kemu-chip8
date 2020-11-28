import fr.outadoc.kemu.Speed
import kotlinx.browser.document
import fr.outadoc.kemu.chip8.Chip8Runner
import fr.outadoc.kemu.chip8.display.createCanvasContext
import fr.outadoc.kemu.chip8.display.Chip8DisplayDriver
import fr.outadoc.kemu.chip8.display.Chip8Keypad
import kotlinx.browser.window
import org.w3c.dom.*

private lateinit var displayDriver: Chip8DisplayDriver
private lateinit var keypad: Chip8Keypad
private lateinit var runner: Chip8Runner

fun main() {
    window.onload = {
        val context = createCanvasContext().apply {
            canvas.id = "display"
        }

        displayDriver = Chip8DisplayDriver(context)
        keypad = Chip8Keypad()

        runner = Chip8Runner(keypad, displayDriver).apply {
            speed = Speed.SUPER_SLOW
        }

        document.getElementById("display-container")?.appendChild(context.canvas)

        document.getElementById("settings-container")?.appendChild(
            form(
                initialSpeed = runner.speed,
                initialTheme = displayDriver.theme,
                onThemeSelected = { theme ->
                    displayDriver.theme = theme
                },
                onSpeedSelected = { speed ->
                    runner.speed = speed
                },
                onProgramSelected = { program ->
                    runner.execute(program)
                },
                onReset = {
                    runner.reset()
                },
                onStop = {
                    runner.stop()
                }
            )
        )
    }
}
