package com.ps.translatepro.voice_to_text.presentation

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.ps.translatepro.voice_to_text.domain.FakeVoiceToTextParser
import com.ps.translatepro.voice_to_text.domain.VoiceToTextParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

class VoiceToTextViewModelTest {

    private lateinit var viewModel: VoiceToTextViewModel
    private lateinit var voiceToTextParser: VoiceToTextParser

    @BeforeTest
    fun setup() {
        voiceToTextParser = FakeVoiceToTextParser()
        viewModel = VoiceToTextViewModel(
            parser = voiceToTextParser,
            coroutineScope = CoroutineScope(Dispatchers.Default)
        )
    }

    @Test
    fun `Record permission granted`() = runBlocking {
        viewModel.state.test {
            awaitItem()

            viewModel.onEvent(VoiceToTextEvent.PermissionResult(isGranted = true, isPermanentlyDeclined = false))

            val resultState = awaitItem()
            assertThat(resultState.recordPermission).isTrue()

        }
    }

    @Test
    fun `Record permission declined`() = runBlocking {
        viewModel.state.test {
            awaitItem()

            viewModel.onEvent(VoiceToTextEvent.PermissionResult(isGranted = false, isPermanentlyDeclined = false))

            val resultState = awaitItem()
            assertThat(resultState.recordPermission).isFalse()

        }
    }

    @Test
    fun `Record permission permanently declined`() = runBlocking {
        viewModel.state.test {
            awaitItem()

            viewModel.onEvent(VoiceToTextEvent.PermissionResult(isGranted = false, isPermanentlyDeclined = true))

            val resultState = awaitItem()
            assertThat(resultState.recordPermission).isFalse()

        }
    }

    @Test
    fun `Recording with permissions`() = runBlocking {
        viewModel.state.test {
            awaitItem()

            viewModel.onEvent(VoiceToTextEvent.PermissionResult(isGranted = true, isPermanentlyDeclined = false))

            awaitItem()

            val languageCode = "pl"

            viewModel.onEvent(VoiceToTextEvent.ToggleRecording(languageCode = languageCode))

            val state = awaitItem()

            assertThat(state.displayState).isEqualTo(DisplayState.SPEAKING)

            viewModel.onEvent(VoiceToTextEvent.ToggleRecording(languageCode = languageCode))

            val resultState = awaitItem()

            assertThat(resultState.displayState).isEqualTo(DisplayState.WAITING_TO_TALK)
        }
    }

    @Test
    fun `Recording without permissions`() = runBlocking {
        viewModel.state.test {
            awaitItem()

            val languageCode = "pl"

            viewModel.onEvent(VoiceToTextEvent.ToggleRecording(languageCode = languageCode))

            val state = awaitItem()

            assertThat(state.displayState).isEqualTo(DisplayState.ERROR)
        }
    }
}