package com.ps.translatepro.testing

import com.ps.translatepro.core.domain.util.CommonFlow
import com.ps.translatepro.core.domain.util.toCommonFlow
import com.ps.translatepro.translate.domain.history.HistoryDataSource
import com.ps.translatepro.translate.domain.history.HistoryItem
import kotlinx.coroutines.flow.MutableStateFlow

class FakeHistoryDataSource : HistoryDataSource {

    private val _data = MutableStateFlow<List<HistoryItem>>(emptyList())

    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return _data.toCommonFlow()
    }

    override suspend fun insertHistoryItem(historyItem: HistoryItem) {
        _data.value += historyItem
    }

    override fun deleteHistoryItemById(historyItemId: Int) {
        _data.value = _data.value.filter { it.id != historyItemId.toLong() }
    }

    override fun clearHistory() {
        _data.value = emptyList()
    }
}