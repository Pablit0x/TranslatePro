package com.ps.translatepro.translate.data.history

import com.ps.translatepro.translate.domain.history.HistoryItem
import database.HistoryEntity

fun HistoryEntity.toHistoryItem() : HistoryItem{
    return HistoryItem(
        id = id,
        fromLanguageCode = fromLanguageCode,
        fromText = fromText,
        toLanguageCode = toLanguageCode,
        toText = toText
    )
}