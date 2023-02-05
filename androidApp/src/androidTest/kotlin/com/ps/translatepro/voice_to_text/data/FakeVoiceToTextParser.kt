package com.ps.translatepro.voice_to_text.data

import com.ps.translatepro.core.domain.util.CommonStateFlow
import com.ps.translatepro.core.domain.util.toCommonStateFlow
import com.ps.translatepro.voice_to_text.domain.VoiceToTextParser
import com.ps.translatepro.voice_to_text.domain.VoiceToTextParserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeVoiceToTextParser : VoiceToTextParser{

    private val _state = MutableStateFlow(VoiceToTextParserState())

    var voiceResult = "test output"

    override val state: CommonStateFlow<VoiceToTextParserState>
        get() = _state.toCommonStateFlow()

    override fun startListening(languageCode: String) {
        _state.update{ it.copy(
            result = "",
            isSpeaking = true
        )}
    }

    override fun stopListening() {
        _state.update{it.copy(
            result = voiceResult,
            isSpeaking = false
        )}
    }

    override fun cancel()  = Unit

    override fun reset() {
       _state.update { VoiceToTextParserState() }
    }
}