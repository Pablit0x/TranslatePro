# TranslatePro - Kotlin Multiplatform Translation App <img src="logo.png" alt="Logo" width="30" height="30">

The TranslatePro is a mobile application that allows users to effortlessly translate text between 28 different languages on both Android and iOS devices. It leverages the power of Kotlin Multiplatform development to provide a seamless user experience across platforms with as little as possible code duplication.

https://github.com/Pablit0x/TranslatePro/assets/76017191/1c4ec70f-0b33-429b-a2d2-d264464efab8

https://github.com/Pablit0x/TranslatePro/assets/76017191/afcdabfa-9ff4-40cf-9cb5-abaa84f012f3


## Features

- **Language Translation**: Translate text from one language to another with ease. The app supports 28 different languages, ensuring that users can communicate effectively regardless of the language barrier.

- **Text-to-Speech**: Hear the translated text pronounced accurately using the built-in text-to-speech functionality. This feature helps users improve their pronunciation and makes it easier to communicate in foreign languages.

- **Speech-to-Text**: Speak into your device's microphone, and the app will convert your speech into text for translation. This feature is incredibly useful when you're unable to type or prefer the convenience of voice input. Please note that speech-to-text functionality may not work on Android emulators.

- **Light and Dark Mode**: Enjoy a personalized visual experience with the app's light and dark mode support. Choose your preferred theme to suit your style and enhance readability in different lighting conditions.

- **Local Database**: The app utilizes an SQLite database to store translations locally. This allows users to access their past translations even when offline, providing a convenient way to revisit and reuse previously translated content.

## Technical Stack

The TranslatePro employs a robust technical stack to deliver its features and functionality:

- **Kotlin Multiplatform**: The app utilizes Kotlin Multiplatform development, enabling code sharing between Android and iOS platforms while maintaining native performance.

- **Dagger Hilt**: Dependency injection in the Android side of the app is handled by Dagger Hilt, a powerful dependency injection framework that simplifies the management of dependencies and promotes modular application design.

- **Coroutines and Flows**: The app leverages Kotlin Coroutines and Flows to handle asynchronous operations and reactive streams. This ensures smooth execution of background tasks and seamless data flow between components.

- **Unit and End-to-End Testing**: Both the Android and iOS sides of the app are tested using unit testing and end-to-end testing methodologies. This ensures the app's stability, reliability, and adherence to functional requirements.
