package fr.outadoc.kemu.chip8.display

import fr.outadoc.kemu.array.UByteArray2
import fr.outadoc.kemu.array.get
import fr.outadoc.kemu.array.set
import fr.outadoc.kemu.b
import fr.outadoc.kemu.chip8.Chip8Constants.DISPLAY_HEIGHT
import fr.outadoc.kemu.chip8.Chip8Constants.DISPLAY_WIDTH
import fr.outadoc.kemu.display.Display
import fr.outadoc.kemu.display.Point
import fr.outadoc.kemu.shr
import kotlinx.coroutines.flow.MutableStateFlow

class Chip8Display : Display {

    override val frameBufferFlow: MutableStateFlow<UByteArray2> =
        MutableStateFlow(UByteArray2(DISPLAY_WIDTH * DISPLAY_HEIGHT))

    private fun useFrameBuffer(block: (UByteArray2) -> Unit) {
        val fbCopy = frameBufferFlow.value.copyOf()
        block(fbCopy)
        frameBufferFlow.value = fbCopy
    }

    override fun clear() {
        useFrameBuffer { frameBuffer ->
            frameBuffer.indices.forEach { i ->
                frameBuffer[i] = 0x0.b
            }
        }
    }

    override fun displaySprite(position: Point<UByte>, sprite: UByteArray2): Boolean {
        val (x, y) = position
        var hasAPixelBeenErased = false

        useFrameBuffer { frameBuffer ->
            sprite.forEachIndexed { iy, row ->
                (7 downTo 0).map { bit ->
                    // Sprite is laid out from left (msb) to right (lsb)
                    (row and ((1 shl bit).toUByte())) shr bit
                }.forEachIndexed { ix, pixel ->
                    // Calculate screen coordinates for this pixel,
                    // possibly wrapping around to the opposite side of the screen
                    val targetX = (x + ix.toUShort()) % DISPLAY_WIDTH.toUShort()
                    val targetY = (y + iy.toUShort()) % DISPLAY_HEIGHT.toUShort()
                    val frameBufferIndex =
                        (targetX + targetY * DISPLAY_WIDTH.toUShort()).toUShort()

                    // xor the pixel onto the screen and check if we're erasing anything
                    val res = frameBuffer[frameBufferIndex] xor pixel
                    if (frameBuffer[frameBufferIndex] > res) {
                        hasAPixelBeenErased = true
                    }
                    frameBuffer[frameBufferIndex] = res
                }
            }
        }

        return hasAPixelBeenErased
    }
}