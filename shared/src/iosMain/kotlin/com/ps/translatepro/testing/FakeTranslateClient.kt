package com.ps.translatepro.testing

import com.ps.translatepro.core.domain.language.Language
import com.ps.translatepro.translate.domain.translate.TranslateClient

class FakeTranslateClient : TranslateClient {

    var translatedText = "Fake Translation"

    override suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String {
        return translatedText
    }
}