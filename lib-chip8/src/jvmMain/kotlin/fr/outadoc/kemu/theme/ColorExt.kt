package fr.outadoc.kemu.theme

fun Color.toColor(): java.awt.Color {
    return java.awt.Color(r, g, b)
}