package com.ps.translatepro.android.translate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ps.translatepro.translate.domain.history.HistoryDataSource
import com.ps.translatepro.translate.domain.translate.TranslateUseCase
import com.ps.translatepro.translate.presentation.TranslateEvent
import com.ps.translatepro.translate.presentation.TranslateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidTranslateViewModel @Inject constructor(
    private val translateUseCase: TranslateUseCase,
    private val historyDataSource: HistoryDataSource
) : ViewModel() {
    private val viewModel by lazy {
        TranslateViewModel(
            translateUseCase = translateUseCase,
            historyDataSource = historyDataSource,
            coroutineScope = viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: TranslateEvent) {
        viewModel.onEvent(event = event)
    }
}