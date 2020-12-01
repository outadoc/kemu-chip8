package fr.outadoc.kemu.display

import fr.outadoc.kemu.array.UByteArray2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainThreadFrameDispatcher(frameConsumer: FrameConsumer? = null) : FrameDispatcher {

    private var currentFrame: UByteArray2? = null
    private var job: Job? = null

    var frameConsumer: FrameConsumer? = frameConsumer
        set(value) {
            field = value
            repaint()
        }

    override fun attach(display: Display) {
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

    private fun repaint() {
        frameConsumer?.displayFrame(currentFrame)
    }
}