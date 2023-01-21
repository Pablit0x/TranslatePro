package com.ps.translatepro.di

import com.ps.translatepro.database.TranslateDatabase
import com.ps.translatepro.translate.data.history.SqlDelightHistoryDataSource
import com.ps.translatepro.translate.data.local.DatabaseDriverFactory
import com.ps.translatepro.translate.data.remote.HttpClientFactory
import com.ps.translatepro.translate.data.translate.KtorTranslateClient
import com.ps.translatepro.translate.domain.history.HistoryDataSource
import com.ps.translatepro.translate.domain.translate.TranslateClient
import com.ps.translatepro.translate.domain.translate.TranslateUseCase

class AppModule {

    val historyDataSource: HistoryDataSource by lazy {
        SqlDelightHistoryDataSource(
            TranslateDatabase(
                driver = DatabaseDriverFactory().create()
            )
        )
    }

    private val translateClient: TranslateClient by lazy{
        KtorTranslateClient(
            httpClient = HttpClientFactory().create()
        )
    }

    val translateUseCase: TranslateUseCase by lazy{
        TranslateUseCase(client = translateClient, historyDataSource =  historyDataSource)
    }
}