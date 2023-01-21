//
//  LanguageDropDown.swift
//  iosApp
//
//  Created by Paweł Szymański on 21/01/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDropDown: View {
    var language: UiLanguage
    var isOpen: Bool
    var selectLanguage: (UiLanguage) -> Void
    var body: some View {
        Menu{
            VStack{
                ForEach(UiLanguage.Companion().allLanguages, id: \.self.language.langCode){ language in
                    LanguageDropDownItem(language: language, onClick: {selectLanguage(language)})
                }
            }
        } label: {
            HStack {
                Text(language.language.langName)
                    .foregroundColor(Color.onSecondary)
                    .multilineTextAlignment(.center)
            }
            
            .frame(width: 140, height: 50)
            .background(Color.secondaryColor)
            .clipShape(RoundedRectangle(cornerRadius: 20))
        }
        
    }
}

struct LanguageDropDown_Previews: PreviewProvider {
    static var previews: some View {
        LanguageDropDown(
            language: UiLanguage(language: .polish, imageName: "polish"), isOpen: true, selectLanguage: {language in }
        )
    }
}
