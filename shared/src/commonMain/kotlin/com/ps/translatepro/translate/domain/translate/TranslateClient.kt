package com.ps.translatepro.translate.domain.translate

import com.ps.translatepro.core.domain.language.Language

interface TranslateClient {
    suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ) : String
}