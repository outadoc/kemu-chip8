package fr.outadoc.kemu

expect val REALTIME_DELAY_MS: Long

enum class Speed(val delay: Long) {
    SUPER_SLOW(200L),
    SLOW(100L),
    NORMAL(50L),
    FAST(10L),
    REALTIME(REALTIME_DELAY_MS)
}