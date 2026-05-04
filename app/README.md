     # W-MVI

# 📱 Searching App

A scalable Android application built using **Clean Architecture**, **MVI**, **Jetpack Compose**, **Retrofit**, and **Koin**.

This project demonstrates enterprise-level Android architecture with strong separation of concerns, modular structure, and testable business logic.

---

## 🚀 Tech Stack

- Kotlin
- Jetpack Compose
- Clean Architecture
- MVI (Intent → State → Effect)
- Retrofit
- Koin (Dependency Injection)
- Coroutines + Flow
- Multi-module architecture

---

## 🏗 Architecture Overview

This project follows **Clean Architecture principles** with strict dependency direction:

```
feature → domain
data → domain
app → feature + data + domain + core
domain → (no dependency)
```

The **domain layer is pure Kotlin** and does not depend on Android framework or external libraries.

---

## 📦 Module Structure

```
:app                  → Composition root
:core                 → Shared infrastructure
:domain               → Business logic layer
:data                 → Data layer implementation
:feature-userprofile  → Presentation layer (MVI)
```

---

### 🧠 Domain Layer

Responsible for business logic and core models.

```
domain/
 ├── model/
 ├── repository/
 └── usecase/
```

- Business models
- Enums representing domain states
- Repository interfaces
- Use cases

> Framework-independent and fully testable.

---

### 🌐 Data Layer

Implements repository contracts and handles external data sources.

```
data/
 ├── remote/
 │   ├── api/
 │   └── dto/
 ├── mapper/
 └── repository/
```

Flow:

```
API → DTO → Mapper → Domain Model
```

- Retrofit APIs
- DTO models
- Mapper layer
- Repository implementation

> Protects domain from backend changes.

---

### 🎨 Feature Layer (MVI)

Each feature is isolated and follows unidirectional data flow.

```
feature-userprofile/
 ├── UserProfileScreen.kt
 ├── UserProfileViewModel.kt
 ├── UserProfileIntent.kt
 └── UserProfileEffect.kt
```

MVI Flow:

```
User Action → Intent → ViewModel → UseCase → State → UI
```

> Predictable state management and easier debugging.

---

### 🛠 Core Module

Reusable infrastructure components:

```
core/
 ├── network/
 ├── common/
 └── ui/theme/
```

- Retrofit configuration
- Interceptors
- Shared utilities
- App theme

---

## 🔄 Application Data Flow

```
User Action
    ↓
Intent
    ↓
ViewModel
    ↓
UseCase
    ↓
Repository (interface)
    ↓
RepositoryImpl (data)
    ↓
API
    ↓
DTO
    ↓
Mapper
    ↓
Domain Model
    ↓
UI State
```

---

## 🔐 Strong Typing Strategy

The domain layer avoids raw `String` usage for business states.

Example:

```kotlin
enum class OnGoingStatus
```

DTO values are safely converted using mapper:

```kotlin
OnGoingStatus.fromRaw(dto.status)
```

Benefits:

- Compile-time safety
- Clear business meaning
- Backend changes isolated to data layer

---

## 🧪 Testing Strategy

- Unit testing for use cases
- Mapper testing
- Repository testing with mocked API
- ViewModel testing
- Compose UI testing

---

## 📈 Scalability

This architecture supports:

- Adding new feature modules
- Offline-first architecture
- Paging integration
- Large team collaboration
- CI/CD integration
- Easy refactoring

---

## ▶️ How to Run

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle
4. Run the `app` module

---

## 🎯 Goals of This Project

- Demonstrate scalable Android architecture
- Showcase Clean Architecture in practice
- Provide production-ready project structure
- Apply strong domain modeling
- Maintain separation of concerns

---

## 👨‍💻 Author

Built with scalability, maintainability, and enterprise architecture principles in mind.

---

## 📌 Future Improvements

- Paging 3 integration
- Room caching (offline support)
- Result wrapper for unified error handling
- Multi-feature navigation system
- CI architecture validation rules

---

# ⭐ If you find this project helpful, consider giving it a star!
