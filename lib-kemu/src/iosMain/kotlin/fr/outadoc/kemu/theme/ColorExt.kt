package fr.outadoc.kemu.theme

import platform.CoreGraphics.CGColorCreateSRGB
import platform.CoreGraphics.CGColorRef

fun Color.toColor(): CGColorRef {
    return CGColorCreateSRGB(
        red = r.toDouble(),
        green = g.toDouble(),
        blue = b.toDouble(),
        alpha = 1.0
    )!!
}