# WhatsApp Clone - Android (Jetpack Compose & Firebase)

A modern Android real-time messaging application replicating the core user experience and visual aesthetic of WhatsApp. Built with **Kotlin**, **Jetpack Compose**, **Firebase Realtime Database / Authentication**, and **Material 3**.

---

## ✨ Features

- **Phone Number Authentication**: Secure sign-in with phone number verification and OTP handling via Firebase Authentication.
- **Profile Setup**: User profile setup and management with image selection and display support.
- **Real-Time 1-on-1 Messaging**: Instant peer-to-peer message synchronization using Firebase Realtime Database.
- **Dynamic Bottom Navigation**:
  - **Chats**: Conversations overview, latest message snippet, unread states, and timestamping.
  - **Updates / Status**: WhatsApp-style status updates and channels layout.
  - **Communities**: Group community feed and organization view.
  - **Calls**: Call logs, missed call indicators, and favorite contacts.
- **Top Bar Actions**: Search, camera shortcut, and popup settings menu.
- **User Discovery / Add Chat**: Quick modal popup to start chats by entering a phone number.
- **Settings & Logout**: Profile preferences, notification controls, storage data toggles, and secure session logout.

---

## 🛠 Tech Stack & Architecture

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3 design)
- **Architecture**: MVVM (Model-View-ViewModel) + Clean Presentation Layer
- **State Management**: Kotlin Coroutines & `StateFlow` / Compose `State`
- **Dependency Injection**: [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- **Backend as a Service (BaaS)**:
  - **Firebase Authentication**: Phone OTP Auth
  - **Firebase Realtime Database**: Synchronous messaging & user presence
- **Image Loading**: [Coil Compose](https://coil-kt.github.io/coil/compose/)
- **Navigation**: Jetpack Navigation Compose with strongly typed routes

---

## 📱 Screenshots

| Welcome Screen | Phone Registration | OTP Verification |
|:---:|:---:|:---:|
| <img width="310" height="698" alt="Screenshot 2026-11-02 164946" src="https://github.com/user-attachments/assets/2e715153-576e-47da-8576-e9ab946a9ae4" /> | <img width="315" height="702" alt="Screenshot 2026-11-02 164327" src="https://github.com/user-attachments/assets/b24d556f-6c2f-42cd-96ca-1fd35f25dc70" /> | <img width="310" height="697" alt="Screenshot 2026-11-02 164717" src="https://github.com/user-attachments/assets/a6f09ffb-291d-4960-9e98-154854f7a186" />|

| Chats Screen | 1-on-1 Chat Screen | Settings Screen |
|:---:|:---:|:---:|
|<img width="311" height="697" alt="Screenshot 2026-11-02 164151" src="https://github.com/user-attachments/assets/2db690ab-f3fa-4cd8-a142-74b5fc60c7d3" /> | <img width="312" height="697" alt="Screenshot 2026-11-02 164630" src="https://github.com/user-attachments/assets/0f09e6be-e670-4498-87a3-d66de40aca91" /> | <img width="315" height="696" alt="Screenshot 2026-11-02 164310" src="https://github.com/user-attachments/assets/dae16797-621f-43c5-8d00-1f4314853072" /> |


---

## 📂 Project Structure

```text
com.example.whatsappclone/
├── di/                     # Dependency Injection modules (AppModule)
├── model/                  # Data classes (Message, ChatListModel, PhoneAuthUser)
├── presentation/
│   ├── bottomnavigation/  # Bottom navigation bar & tab items
│   ├── callscreen/        # Call log, favorites, and call item components
│   ├── chat/              # Real-time message thread screen & bubbles
│   ├── common/            # Reusable UI widgets (AppTopBar, UiHelpers)
│   ├── communityscreen/   # Communities tab UI
│   ├── home/              # HomeScreen, ChatListBox, AddUserPopup
│   ├── navigation/        # NavHost, Routes, WhatsAppNavigationSystem
│   ├── profile/           # UserProfileSetScreen
│   ├── settings/          # SettingsScreen
│   ├── splash/            # SplashScreen
│   ├── updatescreen/      # Status and Channels list items
│   ├── userregistration/  # Phone entry & OTP verification screens
│   ├── viewmodels/        # BaseViewModel, PhoneAuthViewModel
│   └── welcome/           # Welcome / Terms onboarding screen
├── ui/theme/              # Material 3 Color, Type, Shape & Theme styling
├── util/                  # Preferences (AppPrefs) & ImageUtils
├── MainActivity.kt        # Entry-point Activity hosting Compose NavHost
└── WhatsAppCloneApplication.kt # Application class for Hilt initialization
