package com.bruno13palhano.ui.shared

import androidx.compose.runtime.MonotonicFrameClock

class MainClock : MonotonicFrameClock {
    override suspend fun <R> withFrameNanos(onFrame: (frameTimeNanos: Long) -> R): R {
        return onFrame(System.nanoTime())
    }
}