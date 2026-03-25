# Getting Started

This file is for someone opening the client for the first time.

## 1. What You Need

- Java 11 installed
- IntelliJ IDEA recommended
- Git

## 2. Open The Project

1. Clone the repo.
2. Open the repo folder in IntelliJ IDEA.
3. Choose to import it as a Gradle project if IntelliJ asks.
4. Wait for dependency download and indexing to finish.

## 3. Run The Client

Use one of these commands from the repo root:

```powershell
.\gradlew.bat :game:Run-Normal
```

```powershell
.\gradlew.bat :game:Run-Development
```

In IntelliJ, you can also open the Gradle tool window and run:

- `game > Tasks > Runelite > Run-Normal`
- `game > Tasks > Runelite > Run-Development`

## 4. Understand The Modules

- `game/` is the actual client/game side entry point.
- `runelite/` contains RuneLite-side support code and plugins.

If you are unsure where to change something, start by identifying whether it is:

- game behavior or interface logic: check `game/`
- RuneLite plugin or overlay behavior: check `runelite/`
- asset or cache-backed content: check `data/` or `custom/`

## 5. Safe Cleanup

These are usually generated and safe to remove when ignored by Git:

- `.gradle/`
- `.idea/`
- `build/`
- `game/build/`
- `runelite/build/`
- temp screenshots and dump folders

Do not randomly delete files from `data/`, `custom/`, `game/`, or `runelite/`.

## 6. Before You Change Code

- Make sure the client runs once before editing anything.
- Create a branch for your work.
- Keep cleanup changes separate from gameplay or UI changes.
- Commit small, testable steps.

## 7. If Something Breaks

- Reimport the Gradle project.
- Confirm Java 11 is selected in IntelliJ.
- Run `.\gradlew.bat build`.
- Check whether you edited the correct module.

If the issue is project layout confusion, read [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md).
