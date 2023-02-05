package com.ps.translatepro.di

import com.ps.translatepro.testing.FakeHistoryDataSource
import com.ps.translatepro.testing.FakeTranslateClient
import com.ps.translatepro.testing.FakeVoiceToTextParser
import com.ps.translatepro.translate.domain.translate.TranslateUseCase

class TestAppModule : AppModule {
    override val historyDataSource = FakeHistoryDataSource()
    override val translateClient = FakeTranslateClient()
    override val translateUseCase =
        TranslateUseCase(client = translateClient, historyDataSource = historyDataSource)
    override val voiceParser = FakeVoiceToTextParser()
}
