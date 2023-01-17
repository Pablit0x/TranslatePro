package com.ps.translatepro.translate.data.history

import com.ps.translatepro.core.domain.util.CommonFlow
import com.ps.translatepro.core.domain.util.toCommonFlow
import com.ps.translatepro.database.TranslateDatabase
import com.ps.translatepro.translate.domain.history.HistoryDataSource
import com.ps.translatepro.translate.domain.history.HistoryItem
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightHistoryDataSource(
    database : TranslateDatabase
) : HistoryDataSource {

    private val queries = database.translateProQueries

    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return queries
            .getHistory()
            .asFlow()
            .mapToList()
            .map{ history ->
                history.map { it.toHistoryItem()}
            }
            .toCommonFlow()
    }

    override suspend fun insertHistoryItem(historyItem: HistoryItem) {
        queries.insertHistoryEntity(
            id = historyItem.id,
            fromLanguageCode = historyItem.fromLanguageCode,
            fromText = historyItem.fromText,
            toLanguageCode = historyItem.toLanguageCode,
            toText = historyItem.toText,
            timestamp = Clock.System.now().toEpochMilliseconds()
        )
    }
}