# Project Structure

This repo is easier to work with once you treat each top-level folder as having a specific job.

## Top-Level Overview

- `game/`
  Main client code, launch path, interfaces, rendering, definitions, and game-side systems.

- `runelite/`
  RuneLite support code, plugins, overlays, and integration pieces used by the client.

- `data/`
  Client data and custom data assets. Treat this as project content, not a temp folder.

- `custom/`
  Extra assets such as custom models, maps, and sprites.

- `tools/`
  Standalone helper tools. These are useful for asset or interface work but are not the main client runtime.

- `scripts/`
  Small utility scripts for one-off tasks.

- `gradle/`, `gradlew`, `gradlew.bat`
  Gradle wrapper files used to build and run the project.

## Where To Look First

- Launching the client:
  `game/build.gradle.kts`

- Root build logic:
  `build.gradle.kts`

- Included modules:
  `settings.gradle.kts`

- RuneLite plugins:
  `runelite/src/main/java/net/runelite/client/plugins/`

- Game definitions and interface code:
  `game/src/main/java/com/client/`

## Mental Model

Use this rough rule:

- If it feels like game/client behavior, look in `game/`.
- If it feels like RuneLite plugin behavior, look in `runelite/`.
- If it is content, assets, or cache-backed data, look in `data/` or `custom/`.
- If it helps edit or inspect project content, it probably belongs in `tools/` or `scripts/`.

## Generated vs Source Files

Generated or local-only files should stay out of source control. Examples:

- build output
- Gradle cache folders
- extracted native libraries
- screenshots and temp PNGs
- obfuscation and mapping outputs

Source files that matter long-term are mainly in:

- `game/`
- `runelite/`
- `data/`
- `custom/`
- selected build/config files in the repo root

## Good Habits

- Keep repo cleanup separate from feature work.
- Avoid adding debug images or local caches to Git.
- Document any new top-level folder before using it.
- Prefer obvious names over shorthand when adding new tasks or folders.
