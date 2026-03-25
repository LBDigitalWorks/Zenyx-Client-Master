# Contributing

This repo is easier to maintain if setup, formatting, and commits stay predictable.

## Requirements

- Java 11
- Git
- IntelliJ IDEA recommended

## Before You Start

1. Pull the latest changes.
2. Create a branch for your work.
3. Make sure the project imports cleanly as a Gradle project.
4. Run the client once before editing code.

## Run Commands

Normal run:

```powershell
.\gradlew.bat :game:Run-Normal
```

Development run:

```powershell
.\gradlew.bat :game:Run-Development
```

Build:

```powershell
.\gradlew.bat build
```

## Workflow Rules

- Keep cleanup commits separate from feature commits.
- Keep documentation commits separate from gameplay or UI changes when practical.
- Avoid mixing unrelated fixes into one commit.
- Prefer small commits with clear intent.
- Do not use `runelite:run`; use the `game` module tasks.

## Files You Should Not Commit

Do not commit generated or local-only content such as:

- Gradle cache folders
- IDE folders
- build output
- extracted native libraries
- screenshots and temp PNGs
- obfuscation and mapping outputs

If you generate a useful reference asset that should stay in the repo, put it in a deliberate location and document why it belongs there.

## Editing Rules

- Follow `.editorconfig`.
- Use LF for source files unless the file type is explicitly Windows-oriented, such as `.bat` or `.ps1`.
- Preserve existing project structure unless there is a clear reason to reorganize it.
- Avoid introducing new top-level folders without documenting them.

## Before Commit

Run through this checklist:

1. The client still launches.
2. The changed files belong to the correct module.
3. No generated files were added by accident.
4. The commit contains one coherent change.
5. Any new folder or workflow is documented.

## Before Push

1. Run `.\gradlew.bat build` if your changes affect compile-time behavior.
2. Review `git status` and `git diff --stat`.
3. Make sure commit messages describe the actual change.

## Commit Style

Prefer direct commit messages such as:

- `Clean generated artifacts from repo`
- `Add onboarding and project structure docs`
- `Rename Elvarg plugin and update client branding`

## If You Are Unsure

- Read [README.md](README.md)
- Read [docs/GETTING_STARTED.md](docs/GETTING_STARTED.md)
- Read [docs/PROJECT_STRUCTURE.md](docs/PROJECT_STRUCTURE.md)

If the repo feels unclear, improve the docs first instead of guessing.
