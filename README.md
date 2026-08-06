# WoltCompose

> A Jetpack Compose learning project focused on building a modern Android application using Clean Architecture, Material 3, and best practices.

| City Selection | City Search | Restaurant List | Restaurant Search |
|:--------------:|:-----------:|:---------------:|:-----------------:|
| <img src="screenshots/cities.png" width="220"/> | <img src="screenshots/city-search.png" width="220"/> | <img src="screenshots/restaurants.png" width="220"/> | <img src="screenshots/restaurant-search.png" width="220"/> |

---

## Overview

WoltCompose is a learning project created to practice modern Android development with **Jetpack Compose** while following a production-inspired architecture. Instead of building a simple demo app, the goal was to structure the project in a way that resembles a real application, making it easier to extend with new features in the future.

The application loads a list of available cities, allows the user to search for a city, and then displays restaurants available in the selected location using the city's latitude and longitude.

Although the application is intentionally small, the project emphasizes maintainability, separation of concerns, reusable UI components, and scalable navigation patterns.

---

## Goals

The primary goals of this project are:

- Learn Jetpack Compose
- Learn modern Android architecture
- Practice Clean Architecture principles
- Build reusable Compose components
- Use dependency injection with Hilt
- Consume REST APIs with Retrofit
- Apply proper state management
- Keep the project scalable for future features

---

## Features

### City Selection

The application starts by loading all supported cities from the API.

Users can:

- Browse available cities
- Search cities instantly
- Select a city to continue

Cities are sorted alphabetically to improve usability.

---

### Restaurant Listing

After selecting a city, the application's navigation passes the selected city object to the next screen.

Using the city's:

- Latitude
- Longitude

the application requests restaurants available in that specific location.

Each restaurant card displays:

- Restaurant image
- Name
- Description
- Delivery estimate
- Address
- Categories

---

### Restaurant Search

Restaurants can be filtered instantly using a local search.

Searching currently works against:

- Restaurant name
- Restaurant categories

This provides an instant experience without making additional network requests.

---

## Architecture

The project follows a simplified version of **Clean Architecture**.

```
Presentation
│
├── Screens
├── Components
├── ViewModels
└── UiState

Domain
│
├── Models
├── Repository Interfaces
└── Use Cases

Data
│
├── API
├── DTOs
├── Mappers
├── Repository Implementations
└── Network
```

### Why Clean Architecture?

Although this application is relatively small, following Clean Architecture helps keep responsibilities separated.

Benefits include:

- Easier testing
- Better maintainability
- Easier feature additions
- Clear separation between UI and business logic
- Reduced coupling between layers

---

## Project Structure

```
app
├── data
│   ├── remote
│   ├── mapper
│   ├── dto
│   └── repository
│
├── domain
│   ├── model
│   ├── repository
│   └── usecase
│
├── presentation
│   ├── cities
│   ├── restaurants
│   ├── components
│   └── navigation
│
└── di
```

---

## Navigation

The project uses **Jetpack Navigation Compose**.

Current navigation flow:

```
Choose City
      │
      ▼
Restaurants
```

Instead of passing primitive values between destinations, the selected `City` object is passed through the navigation back stack using `SavedStateHandle`.

This approach was chosen because:

- It keeps navigation simple
- No unnecessary route arguments
- Type-safe access to the selected city
- Easy to extend as the application grows

---

## State Management

Each screen owns its own ViewModel.

The UI observes immutable state objects using `StateFlow`.

```
ViewModel
      │
      ▼
UiState
      │
      ▼
Composable UI
```

This creates a single source of truth and keeps the UI reactive.

---

## Reusable Components

To avoid duplicated UI code, reusable Compose components were created.

Examples include:

- SearchTextField

The search component is shared between:

- City search
- Restaurant search

This keeps styling and behaviour consistent throughout the application.

---

## Networking

The project uses:

- Retrofit
- Kotlin Serialization
- OkHttp
- Hilt

Network models are mapped into domain models before reaching the presentation layer.

```
API

↓

DTO

↓

Mapper

↓

Domain Model

↓

UI
```

This prevents the UI from depending directly on API responses.

---

## UI

The project uses Material 3 components throughout.

Current UI features include:

- Material 3 Top App Bars
- Scaffold layouts
- Search fields
- Restaurant cards
- Loading states
- Error states
- Empty states

The UI intentionally focuses on clean layouts while remaining easy to extend later.

---

## Technologies

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- Hilt
- Retrofit
- Kotlin Serialization
- Coil
- StateFlow
- Coroutines

---

## Future Improvements

Some ideas planned for future iterations include:

- Restaurant details screen
- Favorites
- Better animations
- Pull-to-refresh
- Offline caching
- Pagination
- Dark theme improvements
- Unit tests
- UI tests
- CI/CD pipeline
- Detekt
- Spotless / ktlint

---

## Why This Project?

The purpose of WoltCompose is not to recreate the Wolt application, but to build a realistic Android project while learning modern Android development.

The project intentionally prioritizes:

- Clean architecture
- Readable code
- Reusable components
- Modern Android practices
- Maintainable project structure

As the project evolves, additional features will continue to be implemented while preserving these architectural principles.
