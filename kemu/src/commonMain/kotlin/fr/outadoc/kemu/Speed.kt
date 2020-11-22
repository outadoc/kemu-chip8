package fr.outadoc.kemu

enum class Speed(val delay: Long) {
    SUPER_SLOW(200L),
    SLOW(100L),
    NORMAL(50L),
    FAST(10L),
    REALTIME(0L)
}