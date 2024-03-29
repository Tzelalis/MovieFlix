# MovieFlix

## Setup
To build the project you need to add TMDB API key property to your local.properties

`api.key={your_tmdb_api_key}`

In case you haven't TMDB key get one [here](https://www.themoviedb.org/settings/api)

## Install app
You can download and install APK from [Releases](https://github.com/Tzelalis/MovieFlix/releases)

## Requirements
### Mandatory
1. Home Screen:
    - Popular Movies (with pagination)
    - All info: title, backdrop path (fallback strategy poster path), release date, ~~rating~~(rating feature is implemented only on the Movie Details Screen for UI purposes)
      
      > :warning: WARNING: Favorite status is implemented only on the Movie Details Screen due to the complexity of pagination and local flow combination :hourglass::shrug:
2. Movie Details Screen:
     - All info: image, title, genres, release date, runtime, description, cast, reviews (first page), similar movies (with pagination), star rating
     - Share movie with native implementation (show only url exist)
     - Back button to return to previous screen
     - Favorite status (set, auto update)
3. Generic:
     - Pull to refresh implementation
     
### Optional
  1. Skeleton loader with placeholder (only to Home Screen, Movie Details Screen use generic loading UI)
      - Placeholder is fork from accompanist with some changes to avoid recompositions
  3. Offline Mode (Partial Implementation): Implementing offline mode with multiple genres, popular, and favorites lists necessitates a complex database structure. Only a small part for popular movies has been implemented so far.

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
  - Clean Architecture Layers:
    [UI](https://github.com/Tzelalis/MovieFlix/tree/master/app/src/main/java/com/tzel/movieflix/ui),
    [UseCase](https://github.com/Tzelalis/MovieFlix/tree/master/app/src/main/java/com/tzel/movieflix/usecase),
    [Domain](https://github.com/Tzelalis/MovieFlix/tree/master/app/src/main/java/com/tzel/movieflix/domain),
    [Data](https://github.com/Tzelalis/MovieFlix/tree/master/app/src/main/java/com/tzel/movieflix/data),
    [Framework](https://github.com/Tzelalis/MovieFlix/tree/master/app/src/main/java/com/tzel/movieflix/framework)
- UI: Compose
- DI: [Hilt](https://github.com/Tzelalis/MovieFlix/tree/master/app/src/main/java/com/tzel/movieflix/di)
- REST APIs requests: Retrofit + Moshi + okHTTP
- Local storage: Room DB and DataStore
- Project setup: Gradle and [Version Catalogs](https://github.com/Tzelalis/MovieFlix/blob/master/gradle/libs.versions.toml), flavors (dev, prod), buildTypes (debug, release), proguard, etc
- Logs: Timber (and okhttp interceptor for http logs)
