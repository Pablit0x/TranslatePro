//
//  TextToSpeech.swift
//  iosApp
//
//  Created by Paweł Szymański on 21/01/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import AVFoundation

struct TextToSpeech {
    private let synthesizer = AVSpeechSynthesizer()
    
    func speak(text: String, language: String){
        let utterance = AVSpeechUtterance(string: text)
        utterance.voice = AVSpeechSynthesisVoice(language: language)
        utterance.volume = 1
        synthesizer.speak(utterance)
    }
}
