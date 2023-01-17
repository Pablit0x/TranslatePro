package com.ps.translatepro.translate.domain.history

import com.ps.translatepro.core.domain.util.CommonFlow

interface HistoryDataSource {
    fun getHistory() : CommonFlow<List<HistoryItem>>
    suspend fun insertHistoryItem(historyItem: HistoryItem)
}