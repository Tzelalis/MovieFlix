# MovieFlix

## Setup
To build the project you need to add TMDB API key property to your local.properties
`api.key={your_tmdb_api_key}`

## Install app
You can download and install APK from [Releases](https://github.com/Tzelalis/MovieFlix/releases)

## Requirements
### Mandatory
1. Home Screen:
    - Popular Movies (with pagination)
    - All info: title, backdrop path (fallback strategy poster path), release date, rating
      
      > :warning: WARNING: Favorite status is implemented only on the Movie Details Screen due to the complexity of pagination and local flow combination
2. Movie Details Screen:
     - All info: image, title, genres, release date, runtime, description, cast, reviews (first page), similar movies (with pagination), star rating
     - Share movie with native implementation (show only url exist)
     - Back button to return to previous screen
     - Favorite status (set, auto update)
3. Generic:
     - Pull to refresh implementation
     
### Optional
  1. Skeleton loader with placeholder (only from Home Screen, Movie Details Screen use generic loading UI)
  2. Offline Mode (Partial Implementation): Implementing offline mode with multiple genres, popular, and favorites lists necessitates a complex database structure. Only a small part for popular movies has been implemented so far.

### Additional
  1. Splash screen
  2. Movies categoriezed by genres (with pagination)
  3. Open external urls like cast profiles and review to Custom Tab
  4. Import Helvetica custom font family
  5. Generic loading and error state
  6. Status and navigation bar override with custom implementation for color behaviour
  7. Network interceptor to throw custom exception for no available network 
  8. Modern UI base on Netflix

## Let's talk about project
- Pattern: Clean Architecture with MVVM 
  - Clean Arch. Layers: UI, UseCase, Domain, Data, Framework
- UI: Compose
- DI: Hilt
- REST APIs requests: Retrofit + Moshi + okHTTP
- Local storage: Room DB and DataStore
- Project setup: Gradle and Version Catalogs, flavors (dev, prod), buildTypes (debug, release), proguard, etc
- Logs: Timber (and okhttp interceptor for http logs)
