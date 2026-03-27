# Client Refactor Roadmap

This roadmap is for making the client easier to change without mixing cleanup work into gameplay changes.

## Current Risks

- `game/src/main/java/com/client/Client.java` is the main maintenance bottleneck at roughly 12k lines.
- Client-owned settings are split across multiple systems:
  - `com.client.features.settings.Preferences`
  - `com.client.settings.SettingManager`
  - `com.client.utilities.settings.SettingsManager`
  - `com.client.features.settings.InformationFile`
- Startup code mixes core boot, asset loading, UI setup, and persistence concerns.
- Interface logic still relies on scattered ids and direct cache access in many places.
- There is little automated protection around settings, loaders, and packet-side utility code.

## Phase 1: Stabilize Persistence And Startup

Goal: reduce ambiguity before deeper refactors.

- Centralize file locations used for client-managed settings and account data.
- Keep legacy formats working, but make their ownership explicit.
- Fix obvious persistence bugs before moving logic.
- Document which settings system owns which data.
- Avoid adding more `Client` responsibilities during new feature work.

Completed in this phase:

- Added `com.client.settings.ClientStoragePaths` to define settings file locations in one place.
- Updated settings-related classes to use the shared path definitions.
- Fixed `SettingsManager.loadSettings()` to read the same file path it checks for existence.

## Phase 2: Reduce `Client` Surface Area

Goal: move responsibility out of `Client.java` without changing behavior.

Suggested extraction order:

1. bootstrap and startup
2. preference sync and profile loading
3. login flow orchestration
4. menu handling glue
5. packet/update dispatch helpers

Rules for this phase:

- Extract one responsibility at a time.
- Prefer thin coordinator classes over deep inheritance.
- Keep field ownership obvious. If a new class only proxies `Client`, it is probably not the right cut yet.

## Phase 3: Normalize UI And Interface Code

Goal: make interfaces easier to edit safely.

- Prefer named constants or typed definitions for interface ids, button ids, and sprite ids.
- Expand the Kotlin widget builder/loader approach where it already exists.
- Keep raw `RSInterface.interfaceCache[...]` writes behind small helper APIs when possible.
- Remove duplicated interface setup paths once replacement APIs exist.

## Phase 4: Add Regression Guardrails

Goal: protect the easiest-to-break code paths first.

Add tests around:

- settings serialization and migrations
- packet decoding helpers
- JSON/YAML/resource loaders
- widget/interface builders
- isolated math or utility classes with game-side impact

Do not start with rendering-heavy tests unless a subsystem has already been isolated enough to test cleanly.

## Ongoing Rules

- Keep cleanup commits separate from gameplay changes.
- When a subsystem is touched repeatedly, document its ownership in `docs/` before it grows further.
- Avoid introducing new persistence files unless an owner and migration story are clear.
- Prefer one serialization library per subsystem, and converge over time instead of mixing styles in new code.
