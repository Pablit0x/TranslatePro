package com.ps.translatepro.voice_to_text.presentation

data class VoiceToTextState(
    val powerRatios: List<Float> = emptyList(),
    val spokenText: String = "",
    val recordPermission: Boolean = false,
    val recordError: String? = null,
    val displayState: DisplayState? = null
)

enum class DisplayState {
    WAITING_TO_TALK,
    SPEAKING,
    DISPLAYING_RESULTS,
    ERROR
}
