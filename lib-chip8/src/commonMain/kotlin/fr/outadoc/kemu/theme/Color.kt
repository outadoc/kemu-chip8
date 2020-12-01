package fr.outadoc.kemu.theme

data class Color(val r: Int, val g: Int, val b: Int) {
    override fun toString(): String {
        return "#${r.toString(16).padStart(2, '0')}" +
                g.toString(16).padStart(2, '0') +
                b.toString(16).padStart(2, '0')
    }
}

fun String.toColor(): Color {
    val sanitized = replace("#", "").toLowerCase()
    return Color(
        r = sanitized.substring(0 until 2).toInt(16),
        g = sanitized.substring(2 until 4).toInt(16),
        b = sanitized.substring(4 until 6).toInt(16)
    )
}