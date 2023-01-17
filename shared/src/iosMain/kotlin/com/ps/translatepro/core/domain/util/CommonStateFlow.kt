package com.ps.translatepro.core.domain.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

actual open class CommonStateFlow<T> actual constructor(
    private val flow: StateFlow<T>
) : CommonFlow<T>(flow), StateFlow<T> {

    override val replayCache: List<T>
        get() = flow.replayCache

    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        flow.collect(collector)
    }

    override val value: T
        get() = flow.value
}