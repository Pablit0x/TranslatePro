//
//  ProgressButton.swift
//  iosApp
//
//  Created by Paweł Szymański on 21/01/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ProgressButton: View {
    var text: String
    var isLoading: Bool
    var onClick: () -> Void
    var body: some View {
        Button(action: {
            if !isLoading {
                onClick()
            }
        }){
            if isLoading{
                ProgressView()
                    .animation(.easeInOut, value: isLoading)
                    .padding()
                    .background(Color.primaryColor)
                    .cornerRadius(100)
                    .progressViewStyle(CircularProgressViewStyle(tint: .white))
            } else {
                Text(text.uppercased())
                    .animation(.easeInOut, value: isLoading)
                    .padding(.horizontal, 16)
                    .padding(.vertical, 12)
                    .font(.body.weight(.bold))
                    .background(Color.primaryColor)
                    .foregroundColor(Color.onPrimary)
                    .cornerRadius(100)
            }
        }
    }
}

struct ProgressButton_Previews: PreviewProvider {
    static var previews: some View {
        ProgressButton(text: "Translate", isLoading: false, onClick : {})
    }
}


