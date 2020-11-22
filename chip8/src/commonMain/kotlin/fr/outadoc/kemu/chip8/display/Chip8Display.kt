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

class Chip8Display : Display {

    val frameBuffer = UByteArray2(DISPLAY_WIDTH * DISPLAY_HEIGHT)

    override fun clear() {
        frameBuffer.indices.forEach { i ->
            frameBuffer[i] = 0x0.b
        }
    }

    override fun displaySprite(position: Point<UByte>, sprite: UByteArray2): Boolean {
        val (x, y) = position
        var hasAPixelBeenErased = false

        sprite.forEachIndexed { iy, row ->
            (0 until 8).map { bit ->
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

        return hasAPixelBeenErased
    }
}