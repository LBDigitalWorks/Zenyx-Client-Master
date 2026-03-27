# Dev Workflow

This repo now has a simple format-and-check workflow.

## Most Common Commands

Format everything:

```powershell
.\gradlew.bat spotlessApply
```

Check formatting and compile the client:

```powershell
.\gradlew.bat verifyClient
```

Compile only:

```powershell
.\gradlew.bat :game:compileJava :game:compileKotlin
```

Run the client:

```powershell
.\gradlew.bat :game:Run-Normal
```

Run the client in developer mode:

```powershell
.\gradlew.bat :game:Run-Development
```

## Recommended Habit

Before pushing:

```powershell
.\gradlew.bat spotlessApply
.\gradlew.bat verifyClient
```

If `spotlessCheck` fails, run `spotlessApply` and try again.

## What These Commands Do

- `spotlessApply`: fixes formatting automatically.
- `verifyClient`: checks formatting and makes sure the `game` module still compiles.
- `:game:compileJava :game:compileKotlin`: fastest compile-only safety check.

## Notes

- Formatting is controlled by `.editorconfig` and Spotless.
- Java uses `google-java-format`.
- Kotlin and Gradle Kotlin files use `ktlint`.
