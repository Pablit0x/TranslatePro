package com.ps.translatepro.di

import com.ps.translatepro.translate.data.local.FakeHistoryDataSource
import com.ps.translatepro.translate.data.remote.FakeTranslateClient
import com.ps.translatepro.translate.domain.history.HistoryDataSource
import com.ps.translatepro.translate.domain.translate.TranslateClient
import com.ps.translatepro.translate.domain.translate.TranslateUseCase
import com.ps.translatepro.voice_to_text.data.FakeVoiceToTextParser
import com.ps.translatepro.voice_to_text.domain.VoiceToTextParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideFakeTranslateClient() : TranslateClient {
        return FakeTranslateClient()
    }

    @Provides
    @Singleton
    fun provideFakeHistoryDataSource() : HistoryDataSource {
        return FakeHistoryDataSource()
    }

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        client: TranslateClient,
        dataSource: HistoryDataSource
    ) : TranslateUseCase {
        return TranslateUseCase(client = client, historyDataSource = dataSource)
    }

    @Provides
    @Singleton
    fun provideFakeVoiceToTextParser() : VoiceToTextParser{
        return FakeVoiceToTextParser()
    }
}