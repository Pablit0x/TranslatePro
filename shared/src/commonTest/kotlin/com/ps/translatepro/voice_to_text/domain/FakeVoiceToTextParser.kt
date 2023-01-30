package com.ps.translatepro.voice_to_text.domain

import com.ps.translatepro.core.domain.util.CommonStateFlow
import com.ps.translatepro.core.domain.util.toCommonStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeVoiceToTextParser : VoiceToTextParser {

    private val _state = MutableStateFlow(VoiceToTextParserState())
    override val state: CommonStateFlow<VoiceToTextParserState> =
        _state.toCommonStateFlow()


    override fun startListening(languageCode: String) {
        _state.update {
            VoiceToTextParserState()
            it.copy(
                isSpeaking = true
            )
        }
    }

    override fun stopListening() {
        _state.update { VoiceToTextParserState() }
    }

    override fun cancel() {}

    override fun reset() {
        _state.value =  VoiceToTextParserState()
    }
}