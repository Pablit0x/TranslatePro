//
//  TranslateScreen.swift
//  iosApp
//
//  Created by Paweł Szymański on 21/01/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslateScreen: View {
    private var historyDataSource: HistoryDataSource
    private var translateUseCase: TranslateUseCase
    @ObservedObject var viewModel: IOSTranslateViewModel
    private let parser: any VoiceToTextParser
    
    init(historyDataSource: HistoryDataSource, translateUseCase: TranslateUseCase, parser: VoiceToTextParser) {
        self.historyDataSource = historyDataSource
        self.translateUseCase = translateUseCase
        self.parser = parser
        self.viewModel = IOSTranslateViewModel(historyDataSource: historyDataSource, translateUseCase: translateUseCase)
    }
    
    @State var showingAlert : Bool = false
    @State private var showOptions = false
    var body: some View {
        ZStack {
            List{
                HStack(alignment: .center) {
                    LanguageDropDown(
                        language: viewModel.state.fromLanguage,
                        isOpen: viewModel.state.isChoosingFromLanguage,
                        selectLanguage: { language in
                            viewModel.onEvent(event: TranslateEvent.ChooseFromLanguage(language: language))
                        }
                    ).accessibilityIdentifier("from language dropdown")
                    Spacer()
                    SwapLanguageButton(onClick: {
                        viewModel.onEvent(event: TranslateEvent.SwapLanguages())
                    })
                    Spacer()
                    LanguageDropDown(
                        language: viewModel.state.toLanguage,
                        isOpen: viewModel.state.isChoosingToLanguage,
                        selectLanguage: { language in
                            viewModel.onEvent(event: TranslateEvent.ChooseToLanguage(language: language))
                        }
                    ).accessibilityIdentifier("to language dropdown")
                }
                .listRowSeparator(.hidden)
                .listRowBackground(Color.background)
                
                TranslateTextField(
                    fromText: Binding(get: { viewModel.state.fromText }, set: { value in
                        viewModel.onEvent(event: TranslateEvent.ChangeTranslationText(text: value))
                    }),
                    toText: viewModel.state.toText,
                    isTranslating: viewModel.state.isTranslating,
                    fromLanguage: viewModel.state.fromLanguage,
                    toLanguage: viewModel.state.toLanguage,
                    onTranslateEvent: { viewModel.onEvent(event: $0) }
                )
                .listRowSeparator(.hidden)
                .listRowBackground(Color.background)
                
                if !viewModel.state.history.isEmpty {
                    HStack{
                        Text("History")
                            .font(.title)
                            .bold()
                        Spacer()
                        if showOptions {
                            Button(action: {
                                showingAlert = true
                            }) {
                                Image(systemName: "trash")
                                    .foregroundColor(.lightBlue)
                            }
                            .padding(.trailing, 16)
                            .alert(isPresented: $showingAlert){
                                Alert(
                                    title: Text("Clear Translation History"),
                                    message: Text("Are you sure you want to clear entire translation history?"),
                                    primaryButton: .destructive(Text("Clear")){
                                        showOptions = false
                                        viewModel.onEvent(event: TranslateEvent.ClearHistory())
                                    },
                                    secondaryButton: .cancel(){showOptions = false}
                                )
                            }
                        }
                        Button(action: {
                            showOptions.toggle()
                        }) {
                            Image(systemName: "ellipsis")
                                .foregroundColor(.lightBlue)
                        }
                    }
                    .padding(.vertical, 16)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color.background)
                }
                
                ForEach(viewModel.state.history, id: \.self.id) { historyItem in
                    TranslateHistoryItem(
                        historyItem: historyItem,
                        onClick: {
                            viewModel.onEvent(event: TranslateEvent.SelectHistoryItem(selectedHistoryItem: historyItem))
                        },
                        onDelete: {viewModel.onEvent(event: TranslateEvent.DeleteHistoryItem(historyItemId: Int32(historyItem.id)))}
                    )
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color.background)
                }
            }
            .listStyle(.plain)
            .buttonStyle(.plain)
            
            VStack {
                Spacer()
                NavigationLink(
                    destination: VoiceToTextScreen(
                        onResult: { spokenText in
                            viewModel.onEvent(event: TranslateEvent.SubmitVoiceResult(result: spokenText))
                        },
                        parser: parser,
                        languageCode: viewModel.state.fromLanguage.language.langCode)) {
                            ZStack {
                                Circle()
                                    .foregroundColor(.primaryColor)
                                    .padding()
                                Image(uiImage: UIImage(named: "mic")!)
                                    .foregroundColor(.onPrimary)
                                    .accessibilityIdentifier("Record audio")
                            }
                            .frame(maxWidth: 100, maxHeight: 100)
                        }
            }
        }
        .onAppear {
            viewModel.startObserving()
        }
        .onDisappear {
            viewModel.dispose()
        }
    }
}
