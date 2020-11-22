package fr.outadoc.kemu.timer

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class FrequencyTimer(frequency: Float) : Timer {

    private val delayMillis = (frequency * 1000L).toLong()

    override fun start() {
        GlobalScope.launch {
            runTimer()
        }
    }

    private suspend fun runTimer() {
        onTick()
        delay(delayMillis)
    }

    abstract fun onTick()
}