package com.ps.translatepro.android.translate.presentation

import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import com.ps.translatepro.android.R
import com.ps.translatepro.android.core.theme.LightBlue
import com.ps.translatepro.android.translate.presentation.components.*
import com.ps.translatepro.translate.domain.translate.TranslateError
import com.ps.translatepro.translate.presentation.TranslateEvent
import com.ps.translatepro.translate.presentation.TranslateState
import kotlinx.coroutines.launch
import java.util.*

const val FROM_LANGUAGE_DROP_DOWN_TAG = "FromLanguageDropDown"
const val TO_LANGUAGE_DROP_DOWN_TAG = "ToLanguageDropDown"


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TranslateScreen(
    state: TranslateState,
    onEvent: (TranslateEvent) -> Unit,
) {
    var showOptions by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val firstItemVisible by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0
        }
    }

    LaunchedEffect(key1 = state.error) {
        val message = when (state.error) {
            TranslateError.SERVICE_UNAVAILABLE -> context.getString(R.string.error_service_unavailable)
            TranslateError.CLIENT_ERROR -> context.getString(R.string.error_client)
            TranslateError.SERVER_ERROR -> context.getString(R.string.error_server)
            TranslateError.UNKNOWN_ERROR -> context.getString(R.string.error_unknown)
            else -> null
        }
        message?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            onEvent(TranslateEvent.OnErrorSeen)
        }
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(TranslateEvent.RecordAudio) },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                modifier = Modifier.size(65.dp)
            ) {

                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.mic),
                    contentDescription = stringResource(id = R.string.record_audio),
                    modifier = Modifier.size(35.dp)
                )
            }
        }, floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LanguageDropDown(
                        language = state.fromLanguage,
                        isOpen = state.isChoosingFromLanguage,
                        onClick = {
                            keyboardController?.hide()
                            onEvent(TranslateEvent.OpenFromLanguageDropDown)
                        },
                        onDismiss = {
                            onEvent(TranslateEvent.StopChoosingLanguage)
                        },
                        onSelectLanguage = {
                            onEvent(TranslateEvent.ChooseFromLanguage(it))
                        },
                        modifier = Modifier.testTag(FROM_LANGUAGE_DROP_DOWN_TAG)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    SwapLanguagesButton(
                        onClick = {
                            onEvent(TranslateEvent.SwapLanguages)
                        })
                    Spacer(modifier = Modifier.weight(1f))
                    LanguageDropDown(
                        language = state.toLanguage,
                        isOpen = state.isChoosingToLanguage,
                        onClick = {
                            keyboardController?.hide()
                            onEvent(TranslateEvent.OpenToLanguageDropDown)
                        },
                        onDismiss = {
                            onEvent(TranslateEvent.StopChoosingLanguage)
                        },
                        onSelectLanguage = {
                            onEvent(TranslateEvent.ChooseToLanguage(it))
                        },
                        modifier = Modifier.testTag(TO_LANGUAGE_DROP_DOWN_TAG)
                    )
                }
            }
            item {
                val clipboardManager = LocalClipboardManager.current
                val tts = rememberTextToSpeech()
                TranslateTextField(
                    fromText = state.fromText,
                    toText = state.toText,
                    isTranslating = state.isTranslating,
                    fromLanguage = state.fromLanguage,
                    toLanguage = state.toLanguage,
                    onTranslateClick = {
                        keyboardController?.hide()
                        onEvent(TranslateEvent.Translate)
                    },
                    onTextChange = { onEvent(TranslateEvent.ChangeTranslationText(it)) },
                    onCopyClick = { text ->
                        clipboardManager.setText(buildAnnotatedString { append(text) })
                        Toast.makeText(
                            context,
                            context.getString(com.ps.translatepro.android.R.string.copied_to_clipboard),
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    onCloseClick = { onEvent(TranslateEvent.CloseTranslation) },
                    onSpeakerClick = {
                        tts.language = state.toLanguage.toLocale() ?: Locale.ENGLISH
                        tts.speak(
                            state.toText, TextToSpeech.QUEUE_FLUSH, null, null
                        )
                    },
                    onTextFieldClick = { onEvent(TranslateEvent.EditTranslation) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                if (state.history.isNotEmpty()) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = stringResource(id = R.string.history),
                            style = MaterialTheme.typography.h2
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        AnimatedVisibility(visible = showOptions) {
                            IconButton(onClick = { onEvent(TranslateEvent.OpenClearHistoryDialog) }) {
                                Icon(
                                    imageVector = Icons.Rounded.DeleteForever,
                                    contentDescription = stringResource(id = R.string.clear_history),
                                    tint = LightBlue
                                )

                            }
                        }
                        IconButton(onClick = { showOptions = !showOptions }) {
                            Icon(
                                imageVector = Icons.Rounded.MoreHoriz,
                                contentDescription = stringResource(id = R.string.close),
                                tint = LightBlue
                            )

                        }
                    }
                }
            }
            items(state.history) { historyItem ->
                TranslateHistoryItem(historyItem = historyItem, onClick = {
                    coroutineScope.launch {
                        if (!firstItemVisible) listState.animateScrollToItem(1)
                    }
                    onEvent(TranslateEvent.SelectHistoryItem(historyItem))
                }, onDelete = {
                    onEvent(TranslateEvent.DeleteHistoryItem(historyItemId = historyItem.id.toInt()))

                }, modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    if (state.isClearHistoryDialogOpen) {
        ClearHistoryDialog(closeDialog = {
            onEvent(TranslateEvent.CloseClearHistoryDialog)
            showOptions = false
        }, clearHistory = {
            onEvent(TranslateEvent.ClearHistory)
            showOptions = false
        })
    }
}