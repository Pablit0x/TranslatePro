package com.ps.translatepro.translate.presentation

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.ps.translatepro.core.presentation.UiLanguage
import com.ps.translatepro.translate.data.local.FakeHistoryDataSource
import com.ps.translatepro.translate.data.remote.FakeTranslateClient
import com.ps.translatepro.translate.domain.history.HistoryItem
import com.ps.translatepro.translate.domain.translate.TranslateUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

class TranslateViewModelTest {

    private lateinit var viewModel: TranslateViewModel
    private lateinit var client: FakeTranslateClient
    private lateinit var dataSource: FakeHistoryDataSource

    @BeforeTest
    fun setup() {
        client = FakeTranslateClient()
        dataSource = FakeHistoryDataSource()
        val translate = TranslateUseCase(
            client = client, historyDataSource = dataSource
        )
        viewModel = TranslateViewModel(
            translateUseCase = translate,
            historyDataSource = dataSource,
            coroutineScope = CoroutineScope(Dispatchers.Default)
        )
    }

    @Test
    fun `Combined history items and states`() = runBlocking {
        viewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(TranslateState())

            val item = HistoryItem(
                id = 0,
                fromLanguageCode = "pl",
                fromText = "from",
                toLanguageCode = "en",
                toText = "to"
            )
            dataSource.insertHistoryItem(item)

            val state = awaitItem()

            val expected = UiHistoryItem(
                id = item.id!!,
                fromText = item.fromText,
                toText = item.toText,
                fromLanguage = UiLanguage.byCode(item.fromLanguageCode),
                toLanguage = UiLanguage.byCode(item.toLanguageCode)
            )

            assertThat(state.history.first()).isEqualTo(expected)
        }
    }

    @Test
    fun `Successful translation`() = runBlocking {
        viewModel.state.test {

            awaitItem()

            viewModel.onEvent(TranslateEvent.ChangeTranslationText("Test"))

            awaitItem()

            viewModel.onEvent(TranslateEvent.Translate)

            val loadingState = awaitItem()
            assertThat(loadingState.isTranslating).isTrue()

            val resultState = awaitItem()
            assertThat(resultState.isTranslating).isFalse()
            assertThat(resultState.toText).isEqualTo(client.translatedText)

        }
    }

    @Test
    fun `Choosing from language`() = runBlocking {
        viewModel.state.test {

            awaitItem()

            viewModel.onEvent(TranslateEvent.OpenFromLanguageDropDown)

            val openFromLanguageDropDownState = awaitItem()

            assertThat(openFromLanguageDropDownState.isChoosingFromLanguage).isEqualTo(true)

            val uiLanguage = UiLanguage.byCode("pl")

            viewModel.onEvent(TranslateEvent.ChooseFromLanguage(uiLanguage))


            val resultState = awaitItem()

            assertThat(resultState.isChoosingFromLanguage).isEqualTo(false)
            assertThat(resultState.toLanguage).isEqualTo(uiLanguage)
        }
    }

    @Test
    fun `Choosing to language`() = runBlocking {
        viewModel.state.test {

            awaitItem()

            viewModel.onEvent(TranslateEvent.ChangeTranslationText("Test"))

            awaitItem()

            viewModel.onEvent(TranslateEvent.OpenToLanguageDropDown)

            val openToLanguageDropDownState = awaitItem()

            assertThat(openToLanguageDropDownState.isChoosingToLanguage).isEqualTo(true)

            val uiLanguage = UiLanguage.byCode("pl")

            viewModel.onEvent(TranslateEvent.ChooseToLanguage(uiLanguage))

            val closingToLanguage = awaitItem()

            assertThat(closingToLanguage.isChoosingToLanguage).isEqualTo(false)
            assertThat(closingToLanguage.toLanguage).isEqualTo(uiLanguage)

            awaitItem()


            val resultState = awaitItem()
            assertThat(resultState.toText).isEqualTo(client.translatedText)
            assertThat(resultState.isChoosingToLanguage).isEqualTo(false)
            assertThat(resultState.toLanguage).isEqualTo(uiLanguage)
        }
    }

    @Test
    fun `Opening and closing DropDownMenu`() = runBlocking {
        viewModel.state.test {

            awaitItem()

            viewModel.onEvent(TranslateEvent.OpenToLanguageDropDown)

            val openToLanguageDropDownState = awaitItem()

            assertThat(openToLanguageDropDownState.isChoosingToLanguage).isEqualTo(true)

            viewModel.onEvent(TranslateEvent.OpenFromLanguageDropDown)

            val openFromLanguageDropDownState = awaitItem()

            assertThat(openFromLanguageDropDownState.isChoosingFromLanguage).isEqualTo(true)

            viewModel.onEvent(TranslateEvent.StopChoosingLanguage)

            val resultState = awaitItem()

            assertThat(resultState.isChoosingFromLanguage).isEqualTo(false)
            assertThat(resultState.isChoosingToLanguage).isEqualTo(false)
        }
    }

    @Test
    fun `Swapping languages`() = runBlocking {
        viewModel.state.test {

            val initialState = awaitItem()

            assertThat(initialState).isEqualTo(TranslateState())

            viewModel.onEvent(TranslateEvent.ChooseFromLanguage(UiLanguage.byCode("pl")))

            awaitItem()

            viewModel.onEvent(TranslateEvent.ChooseToLanguage(UiLanguage.byCode("en")))

            awaitItem()

            viewModel.onEvent(TranslateEvent.SwapLanguages)

            val resultState = awaitItem()

            assertThat(resultState.fromLanguage).isEqualTo(UiLanguage.byCode("en"))
            assertThat(resultState.toLanguage).isEqualTo(UiLanguage.byCode("pl"))
        }
    }

    @Test
    fun `Deleting single translation history item`() = runBlocking {
        viewModel.state.test {

            val item1 = HistoryItem(
                id = 0,
                fromLanguageCode = "pl",
                fromText = "from",
                toLanguageCode = "en",
                toText = "to"
            )
            dataSource.insertHistoryItem(historyItem = item1)

            awaitItem()

            val item2 = HistoryItem(
                id = 1,
                fromLanguageCode = "en",
                fromText = "from",
                toLanguageCode = "pl",
                toText = "to"
            )

            dataSource.insertHistoryItem(historyItem = item2)

            awaitItem()

            dataSource.deleteHistoryItemById(historyItemId = item2.id!!.toInt())

            val expected = UiHistoryItem(
                id = item1.id!!,
                fromText = item1.fromText,
                toText = item1.toText,
                fromLanguage = UiLanguage.byCode(item1.fromLanguageCode),
                toLanguage = UiLanguage.byCode(item1.toLanguageCode)
            )

            val resultState = awaitItem()
            assertThat(resultState.history.size).isEqualTo(1)
            assertThat(resultState.history.first()).isEqualTo(expected)
        }
    }

    @Test
    fun `Clearing entire translation history`() = runBlocking {
        viewModel.state.test {

            val item1 = HistoryItem(
                id = 0,
                fromLanguageCode = "pl",
                fromText = "from",
                toLanguageCode = "en",
                toText = "to"
            )
            dataSource.insertHistoryItem(historyItem = item1)

            awaitItem()

            val item2 = HistoryItem(
                id = 1,
                fromLanguageCode = "en",
                fromText = "from",
                toLanguageCode = "pl",
                toText = "to"
            )

            dataSource.insertHistoryItem(historyItem = item2)

            awaitItem()

            dataSource.clearHistory()


            val resultState = awaitItem()
            assertThat(resultState.history.isEmpty()).isTrue()
        }
    }

    @Test
    fun `Closing translation`() = runBlocking {
        viewModel.state.test {
            awaitItem()

            viewModel.onEvent(TranslateEvent.ChangeTranslationText("Hello"))

            val textInputState = awaitItem()

            assertThat(textInputState.fromText).isEqualTo("Hello")

            viewModel.onEvent(TranslateEvent.CloseTranslation)

            val resultState = awaitItem()

            assertThat(resultState.fromText).isEmpty()
        }
    }

}