package com.ps.translatepro.core.presentation

import com.ps.translatepro.core.domain.language.Language

expect class UiLanguage {
    val language: Language
    companion object{
        fun byCode(langCode: String): UiLanguage
        val allLanguages: List<UiLanguage>
    }
}