package com.ps.translatepro.translate.data.remote

import io.ktor.client.*

expect class HttpClientFactory {
    fun create() : HttpClient
}