import fr.outadoc.kemu.Speed
import kotlinx.browser.document
import fr.outadoc.kemu.chip8.Chip8Runner
import fr.outadoc.kemu.chip8.display.createCanvasContext
import fr.outadoc.kemu.chip8.display.Chip8Keypad
import fr.outadoc.kemu.display.MainThreadFrameDispatcher
import kotlinx.browser.window

private lateinit var displayView: Chip8DisplayView
private lateinit var keypad: Chip8Keypad
private lateinit var runner: Chip8Runner

fun main() {
    window.onload = {
        val context = createCanvasContext().apply {
            canvas.id = "display"
        }

        displayView = Chip8DisplayView(context)
        keypad = Chip8Keypad()

        val frameDispatcher = MainThreadFrameDispatcher(displayView)
        runner = Chip8Runner(keypad, frameDispatcher).apply {
            speed = Speed.SUPER_SLOW
        }

        document.getElementById("display-container")?.appendChild(context.canvas)

        document.getElementById("settings-container")?.appendChild(
            form(
                initialSpeed = runner.speed,
                initialTheme = displayView.theme,
                onThemeSelected = { theme ->
                    displayView.theme = theme
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
