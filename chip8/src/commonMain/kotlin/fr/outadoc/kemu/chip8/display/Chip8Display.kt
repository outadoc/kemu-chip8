package fr.outadoc.kemu.chip8.display

import fr.outadoc.kemu.b
import fr.outadoc.kemu.chip8.Chip8Constants.DISPLAY_HEIGHT
import fr.outadoc.kemu.chip8.Chip8Constants.DISPLAY_WIDTH
import fr.outadoc.kemu.display.Display
import fr.outadoc.kemu.display.Point
import fr.outadoc.kemu.get
import fr.outadoc.kemu.set
import fr.outadoc.kemu.shr
import kotlin.experimental.and
import kotlin.experimental.xor

class Chip8Display : Display {

    val frameBuffer = ByteArray(DISPLAY_WIDTH * DISPLAY_HEIGHT)

    override fun clear() {
        frameBuffer.indices.forEach { i ->
            frameBuffer[i] = 0x0.b
        }
    }

    override fun displaySprite(position: Point<Byte>, sprite: ByteArray): Boolean {
        val (x, y) = position
        var hasAPixelBeenErased = false

        sprite.forEachIndexed { iy, row ->
            (0 until 8).map { bit ->
                (row and ((1 shl bit).toByte())) shr bit
            }.forEachIndexed { ix, pixel ->
                // Calculate screen coordinates for this pixel,
                // possibly wrapping around to the opposite side of the screen
                val targetX = (x + ix.toShort()) % DISPLAY_WIDTH.toShort()
                val targetY = (y + iy.toShort()) % DISPLAY_HEIGHT.toShort()
                val frameBufferIndex =
                    (targetX + targetY * DISPLAY_WIDTH.toShort()).toShort()

                // xor the pixel onto the screen and check if we're erasing anything
                val res = frameBuffer[frameBufferIndex] xor pixel
                if (frameBuffer[frameBufferIndex] > res) {
                    hasAPixelBeenErased = true
                }
                frameBuffer[frameBufferIndex] = res
            }
        }

        return hasAPixelBeenErased
    }
}