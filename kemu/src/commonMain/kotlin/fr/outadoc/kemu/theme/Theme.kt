package fr.outadoc.kemu.theme

enum class Theme(val background: Color, val foreground: Color) {
    WHITE_ON_BLACK(
        background = "#000000".toColor(),
        foreground = "#ffffff".toColor()
    ),
    BLACK_ON_WHITE(
        background = "#ffffff".toColor(),
        foreground = "#000000".toColor()
    ),
    MATRIX(
        background = "#000000".toColor(),
        foreground = "#00FF00".toColor()
    )
}