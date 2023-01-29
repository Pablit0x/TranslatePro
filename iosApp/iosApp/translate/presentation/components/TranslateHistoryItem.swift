//
//  TranslateHistoryItem.swift
//  iosApp
//
//  Created by Paweł Szymański on 21/01/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslateHistoryItem: View {
    let historyItem: UiHistoryItem
    let onClick: () -> Void
    let onDelete: () -> Void
    
    @State private var showOptions = false
    var body: some View {
        VStack {
            if showOptions {
                HStack {
                    Spacer()
                    Button(action: {
                        withAnimation {
                            onDelete()
                            showOptions = false
                        }
                    }) {
                        Image(systemName: "trash")
                            .foregroundColor(.lightBlue)
                    }.padding(.trailing, 16)
                    Button(action: {
                        withAnimation {
                            showOptions = false
                        }
                    }) {
                        Image(systemName: "xmark")
                            .foregroundColor(.lightBlue)
                    }
                }
                .transition(.move(edge: .trailing))
                .padding(.bottom, 8)
            }
            VStack(alignment: .leading, spacing: 0) {
                HStack {
                    SmallLanguageIcon(language: historyItem.fromLanguage)
                        .padding(.trailing)
                    Text(historyItem.fromText)
                        .foregroundColor(.lightBlue)
                        .font(.body)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                Divider().padding(.bottom, 16).padding(.top, 16)
                HStack {
                    SmallLanguageIcon(language: historyItem.toLanguage)
                        .padding(.trailing)
                    Text(historyItem.toText)
                        .foregroundColor(.onSurface)
                        .font(.body.weight(.semibold))
                }
                .frame(maxWidth: .infinity, alignment: .leading)
            }
            .frame(maxWidth: .infinity)
            .padding()
            .gradientSurface()
            .cornerRadius(15)
            .shadow(radius: 4)
            .onTapGesture {onClick()}
            .onLongPressGesture(minimumDuration: 0.1) {
                withAnimation {
                    showOptions.toggle()
                }
            }
        }
    }

}

struct TranslateHistoryItem_Previews: PreviewProvider {
    static var previews: some View {
        TranslateHistoryItem(
            historyItem: UiHistoryItem(
                id: 0,
                fromText: "Hello",
                toText: "Hallo",
                fromLanguage: UiLanguage(language: .english, imageName: "english"),
                toLanguage: UiLanguage(language: .german, imageName: "german")
            ),
            onClick: {},
            onDelete: {}
        )
    }
}
