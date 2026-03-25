# Zenyx Client

Custom RuneLite-based client project with the main game code in `game/` and RuneLite integration in `runelite/`.

## What This Repo Contains

- `game/`: main client entry point and game-specific code
- `runelite/`: RuneLite framework code and client plugins
- `data/`: cache data and custom data assets used by the client
- `custom/`: custom models, maps, and sprites
- `tools/`: helper tools and editors
- `scripts/`: one-off helper scripts

More detail is in [docs/PROJECT_STRUCTURE.md](docs/PROJECT_STRUCTURE.md).

## Requirements

- Java 11
- Gradle wrapper included in the repo
- IntelliJ IDEA recommended for first-time setup

## First-Time Setup

1. Install Java 11 and make sure `java -version` reports version 11.
2. Open the repo as a Gradle project in IntelliJ IDEA.
3. Let Gradle finish indexing and downloading dependencies.
4. Run the client with one of the tasks below.

If you are brand new to the project, read [docs/GETTING_STARTED.md](docs/GETTING_STARTED.md) first.

## How To Run

From the repo root:

```powershell
.\gradlew.bat :game:Run-Normal
```

Development mode:

```powershell
.\gradlew.bat :game:Run-Development
```

Build the project:

```powershell
.\gradlew.bat build
```

Create the obfuscated jar:

```powershell
.\gradlew.bat obfuscate
```

## Important Notes

- Do not run `runelite:run` directly. The project is set up to run through the `game` module.
- Build output, local caches, screenshots, temp PNGs, and mapping files are generated content and should not be committed.
- The repo currently has active local code changes. If you are experimenting, use a separate branch or make small commits often.

## Common Starting Points

- Main application entry: `game/`
- RuneLite plugin work: `runelite/src/main/java/net/runelite/client/plugins/`
- Client assets and custom data: `data/` and `custom/`
- One-off tooling: `tools/`

## Suggested Workflow

1. Pull or branch from a clean baseline.
2. Run `:game:Run-Normal` and confirm the client starts.
3. Make one focused change at a time.
4. Re-run the client after each meaningful change.
5. Keep cleanup commits separate from feature commits.

## Troubleshooting

- Gradle import issues: reimport the Gradle project and confirm Java 11 is selected.
- Missing dependencies on first run: let Gradle finish fully before trying to launch.
- Wrong run target: use `:game:Run-Normal` or `:game:Run-Development`, not `runelite:run`.
- Strange local state: generated files can be cleared safely if they are ignored by Git.

## Extra Docs

- [docs/GETTING_STARTED.md](docs/GETTING_STARTED.md)
- [docs/PROJECT_STRUCTURE.md](docs/PROJECT_STRUCTURE.md)
