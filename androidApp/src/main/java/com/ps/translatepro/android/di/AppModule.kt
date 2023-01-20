package com.ps.translatepro.android.di

import android.app.Application
import com.ps.translatepro.database.TranslateDatabase
import com.ps.translatepro.translate.data.history.SqlDelightHistoryDataSource
import com.ps.translatepro.translate.data.local.DatabaseDriverFactory
import com.ps.translatepro.translate.data.remote.HttpClientFactory
import com.ps.translatepro.translate.data.translate.KtorTranslateClient
import com.ps.translatepro.translate.domain.history.HistoryDataSource
import com.ps.translatepro.translate.domain.translate.TranslateClient
import com.ps.translatepro.translate.domain.translate.TranslateUseCase
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient{
        return HttpClientFactory().create()
    }

    @Provides
    @Singleton
    fun provideTranslateClient(httpClient: HttpClient) : TranslateClient{
        return KtorTranslateClient(httpClient = httpClient)
    }

    @Provides
    @Singleton
    fun provideDatabaseDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(context = app).create()
    }

    @Provides
    @Singleton
    fun provideHistoryDataSource(driver: SqlDriver) : HistoryDataSource{
        return SqlDelightHistoryDataSource(TranslateDatabase(driver = driver))
    }

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        client: TranslateClient,
        dataSource: HistoryDataSource
    ) : TranslateUseCase {
        return TranslateUseCase(client = client, historyDataSource = dataSource)
    }
}