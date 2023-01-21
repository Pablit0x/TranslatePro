//
//  LanguageDropDownItem.swift
//  iosApp
//
//  Created by Paweł Szymański on 21/01/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDropDownItem: View {
    var language : UiLanguage
    var onClick: () -> Void
    var body: some View {
        Button(action: onClick){
            HStack{
                if let image = UIImage(named: language.imageName.lowercased()){
                    Image(uiImage: image)
                        .resizable()
                        .frame(width: 40, height: 40)
                        .padding(.trailing, 5)
                    Text(language.language.langName)
                        .foregroundColor(.textBlack)
                }
            }
        }
    }
}

struct LanguageDropDownItem_Previews: PreviewProvider {
    static var previews: some View {
        LanguageDropDownItem(
            language: UiLanguage(language: .german, imageName: "german"), onClick: {})
    }
}
