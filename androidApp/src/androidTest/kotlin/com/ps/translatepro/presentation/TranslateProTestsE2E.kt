package com.ps.translatepro.presentation


import android.Manifest
import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.GrantPermissionRule
import com.ps.translatepro.android.MainActivity
import com.ps.translatepro.translate.data.remote.FakeTranslateClient
import com.ps.translatepro.translate.domain.translate.TranslateClient
import com.ps.translatepro.voice_to_text.data.FakeVoiceToTextParser
import com.ps.translatepro.voice_to_text.domain.VoiceToTextParser
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import com.ps.translatepro.android.R
import com.ps.translatepro.android.di.AppModule
import com.ps.translatepro.android.translate.presentation.FROM_LANGUAGE_DROP_DOWN_TAG
import com.ps.translatepro.android.translate.presentation.TO_LANGUAGE_DROP_DOWN_TAG
import com.ps.translatepro.android.translate.presentation.components.IDLE_TRANSLATE_TEXT_FIELD_TAG
import com.ps.translatepro.android.translate.presentation.components.SWAP_LANGUAGES_BUTTON_TAG
import com.ps.translatepro.android.voice_to_text.di.VoiceToTextModule
import com.ps.translatepro.core.domain.language.Language
import com.ps.translatepro.translate.domain.history.HistoryDataSource
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class, VoiceToTextModule::class)
class TranslateProTestsE2E {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.RECORD_AUDIO
    )

    @Inject
    lateinit var fakeVoiceParser: VoiceToTextParser

    @Inject
    lateinit var fakeClient: TranslateClient

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun recordAndTranslate() : Unit = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val parser = fakeVoiceParser as FakeVoiceToTextParser
        val client = fakeClient as FakeTranslateClient

        composeRule
            .onNodeWithContentDescription(context.getString(R.string.record_audio))
            .performClick()

        composeRule
            .onNodeWithContentDescription(context.getString(R.string.record_audio))
            .performClick()

        composeRule
            .onNodeWithContentDescription(context.getString(R.string.stop_recording))
            .performClick()

        composeRule
            .onNodeWithText(parser.voiceResult)
            .assertIsDisplayed()

        composeRule
            .onNodeWithContentDescription(context.getString(R.string.apply))
            .performClick()

        composeRule
            .onNodeWithText(parser.voiceResult)
            .assertIsDisplayed()

        composeRule
            .onNodeWithText(context.getString(R.string.translate), ignoreCase = true)
            .performClick()

        composeRule
            .onNodeWithText(parser.voiceResult)
            .assertIsDisplayed()

        composeRule
            .onNodeWithText(client.translatedText)
            .assertIsDisplayed()
    }

    @Test
    fun changeToLanguage() : Unit = runBlocking {

        composeRule
            .onNodeWithTag(TO_LANGUAGE_DROP_DOWN_TAG)
            .assertTextContains(Language.POLISH.langName)

        composeRule
            .onNodeWithTag(TO_LANGUAGE_DROP_DOWN_TAG)
            .performClick()

        composeRule
            .onNodeWithText(Language.AZERBAIJANI.langName)
            .performClick()

        composeRule
            .onNodeWithTag(FROM_LANGUAGE_DROP_DOWN_TAG)
            .assertTextContains(Language.AZERBAIJANI.langName)
    }

    @Test
    fun changeFromLanguage() : Unit = runBlocking {

        composeRule
            .onNodeWithTag(FROM_LANGUAGE_DROP_DOWN_TAG)
            .assertTextContains(Language.ENGLISH.langName)

        composeRule
            .onNodeWithTag(FROM_LANGUAGE_DROP_DOWN_TAG)
            .performClick()

        composeRule
            .onNodeWithText(Language.ARABIC.langName)
            .performClick()

        composeRule
            .onNodeWithTag(FROM_LANGUAGE_DROP_DOWN_TAG)
            .assertTextContains(Language.ARABIC.langName)
    }

    @Test
    fun textTranslation(): Unit = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val client = fakeClient as FakeTranslateClient

        composeRule
            .onNodeWithTag(IDLE_TRANSLATE_TEXT_FIELD_TAG)
            .assertIsNotFocused()

        composeRule
            .onNodeWithTag(IDLE_TRANSLATE_TEXT_FIELD_TAG)
            .performTextInput("test")

        composeRule
            .onNodeWithTag(IDLE_TRANSLATE_TEXT_FIELD_TAG)
            .assertIsFocused()

        composeRule
            .onNodeWithText(context.getString(R.string.translate), ignoreCase = true)
            .performClick()

        composeRule
            .onNodeWithText(client.translatedText)
            .assertIsDisplayed()
    }

    @Test
    fun swapLanguages() : Unit = runBlocking{
        composeRule
            .onNodeWithTag(FROM_LANGUAGE_DROP_DOWN_TAG)
            .assertTextContains(Language.ENGLISH.langName)

        composeRule
            .onNodeWithTag(TO_LANGUAGE_DROP_DOWN_TAG)
            .assertTextContains(Language.POLISH.langName)

        composeRule
            .onNodeWithTag(SWAP_LANGUAGES_BUTTON_TAG)
            .performClick()

        composeRule
            .onNodeWithTag(FROM_LANGUAGE_DROP_DOWN_TAG)
            .assertTextContains(Language.POLISH.langName)

        composeRule
            .onNodeWithTag(TO_LANGUAGE_DROP_DOWN_TAG)
            .assertTextContains(Language.ENGLISH.langName)
    }
}