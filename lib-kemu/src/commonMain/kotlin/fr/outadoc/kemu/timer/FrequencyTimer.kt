package fr.outadoc.kemu.timer

import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.coroutines.coroutineContext

abstract class FrequencyTimer(frequency: Float) : Timer {

    private val delayMillis = (frequency * 1000L).toLong()

    override suspend fun start() {
        while (coroutineContext.isActive) {
            onTick()
            delay(delayMillis)
        }
    }

    abstract fun onTick()
}