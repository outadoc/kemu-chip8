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
        foreground = "#00ff00".toColor()
    ),
    SOLARIZED_DARK(
        background = "#002833".toColor(),
        foreground = "#839495".toColor()
    ),
    SOLARIZED_LIGHT(
        background = "#fdf6e3".toColor(),
        foreground = "#657b83".toColor()
    )
}