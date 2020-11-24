package fr.outadoc.kemu.chip8.browser.display

import kotlinx.browser.document
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement

fun createCanvasContext(): CanvasRenderingContext2D {
    val canvas = document.createElement("canvas") as HTMLCanvasElement
    return canvas.getContext("2d") as CanvasRenderingContext2D
}