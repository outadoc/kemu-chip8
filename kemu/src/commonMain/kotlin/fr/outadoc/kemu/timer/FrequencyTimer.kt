package fr.outadoc.kemu.timer

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

abstract class FrequencyTimer(frequency: Float) : Timer {

    private val delayMillis = (frequency * 1000L).toLong()

    override fun start() {
        GlobalScope.launch {
            runTimer()
        }
    }

    private suspend fun runTimer() {
        while (coroutineContext.isActive) {
            onTick()
            delay(delayMillis)
        }
    }

    abstract fun onTick()
}