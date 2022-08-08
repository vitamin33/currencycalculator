# currencycalculator
Currency calculator. 
Simple application allowing to convert amount in one currency to another, using exchange rate of a given date.    

## Scenarios  
- Scenario 1: “User enters the application, chooses the date, original currency, the amount in original currency and the target currency. The amount in the target currency should be shown (and stored in the history, see Scenario 2).” 
- Scenario 2: “User enters the application, opens the history page and can see last 10 operations they performed.”  

## Technologies:
- Google [Jetpack Compose](https://developer.android.com/jetpack/compose) dev kit
- Dependency injection (with [Hilt](http://google.github.io/hilt/))
- Reactive programming with [Kotlin Flows](https://kotlinlang.org/docs/reference/coroutines/flow.html)
- Android architecture components with [ViewModels](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Coroutines](https://developer.android.com/kotlin/coroutines) for async work
- [Room](https://developer.android.com/training/data-storage/room) database
- MVI architecture (with Events, State and Effects)
- It uses [NBU API](https://bank.gov.ua/ua/open-data/api-dev) to get currency rates
- [Moshi](https://github.com/square/moshi) library for parsing JSONs

[![test](https://github.com/blocoio/android-template/workflows/test/badge.svg?branch=master)](https://github.com/blocoio/android-template/actions?query=workflow%3Atest+branch%3Amaster)

## Clean architecture with 3 layers
- Data (for database, API code)
- Domain (for business logic and models)
- Presentation (for UI logic, with MVI)
Architecture designed to be maintainable testable and easy to support for big projects and future growth of the project.

 <img src="images/AndroidTemplate-CleanArchitecture.png" alt="ArchiTecture logo"/>

## Tests examples for:
- View model events
- Use cases
- Room database

## Structure

* `build.gradle` - root gradle config file
* `settings.gradle` - root gradle settings file
* `app` - our only project in this repo
* `app/build.gradle` - project gradle config file
* `app/src` - main project source directory
* `app/src/main` - main project flavour
* `app/src/main/AndroidManifest.xml` - manifest file
* `app/src/main/java` - java source directory
* `app/src/main/res` - resources directory


### Manual build
1. Download this repository extract and open folder on Android Studio
2. Wait for indexing and libraries downloading
3. Click on 'Run' app button or you can use console command  ```./gradlew assembleDebug ```


Also you can just download .apk build in 'build' folder and install.

And you're ready to start working on currency-calculator app.
