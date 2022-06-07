# TestApp

## Description
Budget management application.
Helps you to track your account and transactions data 

It consists of two screens.<br/>
`Account Screen` and `Transactions Screen`.<br/>
`Account Screen` displaying the list with all accounts and the total sum of founds in all accounts <br/>
`Transactions Screen` displaying listed by month transactions in chosen account<br/>

To better demonstrate the difference between orthodox `XML` and new `jetpack compose` approaches,
`Account Screen` was implemented using `XML` and `Transaction Screen` using `jetpack compose`<br/>
That decision was made to show how easy `XML` and `jetpack compose` can coexist together<br/>

Application is built using `MVVM` pattern that helps to better encapsulate business logic and UI creation<br/>

`Navigation lib` is used to simplify transitions between fragments and for better visualisation of the project structure<br/>

`kotlin coroutines` is used for async work like getting and parcing data from json and list iterations

`LiveData` and `mutableState` are used to trigger UI updates in case of value change 

`Hilt` is currently used to simplify `ViewModel` declarations, but can be easily applied for various dependency injections in the future

## How it works

1. On app launch, MainActivity starting `AccountFragment`
2. after view is created, requesting viewModel to get accounts data `viewModel.getAccounts()` and setting observer to await the value updates `viewModel.mAccounts.observe`
3. `viewModel` on `IO` thread getting string content of `accounts.json` and parsing it into a list of `Account` objects
4. after data is received, on `Main` thread triggers the setup of the `Adapter` for `RecyclerView` with the all of `Account's` data
5. on account item click navigates to `Transactions Screen`and repeats the above steps 2-4 for `Transactions`
6. in addition to the above in `setHeadersToFirstItems` in `viewModel` specify which items of the list should have a header with month, year, and balance information


## What can be done next

1. Propper test coverage of the code. (Looking forward to improving my knowledge in this area together with the Monetree team)
2. managing the data directly from json is not very comfortable, moving data to Room DB (optionally Firebase) should make tasks like getting items sharing the same month more simple
3. User-friendly Exception and empty response cases coverage (display some explanation of the problem and what to do next)


## Note

I didn't really understand this part "The list should be ordered by the account nickname and `grouped by institution`."<br/>
It's not hard to group a few accounts by the institution in one list item like on `screen_1.jpg`, but then how can we check the Transactions information only for one account from the group? Or that was a list containing child lists inside?


## Troubleshooting of running project on Android Studio

if your Android Studio uses JDK 1.8 you can run into the following error when trying to run the project.<br/>
The following link will direct you on how to resolve it https://stackoverflow.com/a/67002271/6737006

```Kotlin
An exception occurred applying plugin request [id: 'com.android.application']
> Failed to apply plugin 'com.android.internal.application'.
   > Android Gradle plugin requires Java 11 to run. You are currently using Java 1.8.
     You can try some of the following options:
       - changing the IDE settings.
       - changing the JAVA_HOME environment variable.
       - changing `org.gradle.java.home` in `gradle.properties`. 
