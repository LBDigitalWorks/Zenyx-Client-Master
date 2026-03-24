# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Elvarg Rebirth Client is a RuneScape Private Server (RSPS) client based on revision 235. It integrates RuneLite features with a custom game client engine, loading OSRS cache data.

## Build Commands

```bash
# Build the project
./gradlew build

# Run in normal mode
./gradlew :game:Run-Normal

# Run in development mode (with assertions and developer features)
./gradlew :game:Run-Development

# Build fat JAR
./gradlew fatJar

# Clean build
./gradlew clean build
```

## Project Architecture

### Module Structure
- **game/** - Core game client engine
  - Entry point: `Application.java` → delegates to `net.runelite.client.RuneLite`
  - Main client class: `com.client.Client` - contains game loop, rendering, networking
  - Configuration: `com.client.Configuration` - server addresses, ports, cache settings
- **runelite/** - RuneLite integration layer
  - Entry point: `net.runelite.client.RuneLite`
  - Plugin system: `net.runelite.client.plugins.*`
  - API interfaces: `net.runelite.api.*`

### Key Packages in game/
- `com.client.audio/` - Sound system (PCM, MIDI, Vorbis)
- `com.client.definitions/` - Game data definitions (items, NPCs, objects, animations)
- `com.client.graphics/` - Rendering, interfaces, sprites
- `com.client.scene/` - 3D scene graph, map regions, tiles
- `com.client.js5/` - Cache system (JS5 protocol)
- `com.client.engine/` - Input handling (keyboard, mouse)

### Key Packages in runelite/
- `net.runelite.client.plugins/` - Plugin implementations (GPU, HD, ground items, etc.)
- `net.runelite.client.ui/` - Swing UI components
- `net.runelite.client.config/` - Configuration management
- `net.runelite.api/` - Client API interfaces

### Rendering Plugins
- `GpuPlugin` - Standard GPU-accelerated rendering via OpenGL/JOGL
- `HdPlugin` - High-definition graphics with enhanced shaders and lighting

## Configuration

Server connection settings in `com.client.Configuration`:
- `DEDICATED_SERVER_ADDRESS` - Production server IP
- `TEST_SERVER_ADDRESS` - Test server IP
- `PORT` / `TEST_PORT` - Connection ports
- `CLIENT_VERSION` - Must match server version for login
- `CACHE_VERSION` - Triggers cache updates when changed

## Technology Stack
- Java 11
- Kotlin 1.8.22
- Gradle with Kotlin DSL
- LWJGL 3.3.x / JOGL for OpenGL
- Guice for dependency injection
- Lombok for boilerplate reduction
- Netty for networking

---

## Refactoring Guide

This section defines the priorities, rules, and patterns for the ongoing client cleanup effort.

### Refactoring Priorities (In Order)

1. **Architecture Refactoring** — Break up god classes, extract subsystems
2. **Rename Obfuscated Identifiers** — Replace decompiled names with meaningful ones
3. **Add Documentation** — Javadoc and inline comments
4. **Remove Dead Code** — Eliminate provably unreachable code

### Priority 1: Architecture Refactoring

The primary goal is extracting cohesive subsystems from `com.client.Client` and other large classes into dedicated manager/handler classes using **delegation patterns**.

**Rules:**
- Extract functionality into helper/manager classes — do NOT use inheritance for decomposition
- The `Client` class should delegate to extracted managers, not lose access to them
- Preserve all existing functionality — nothing should break
- Each extracted class should have a single clear responsibility
- Use existing package structure where it makes sense (e.g., networking code → `com.client.net/`)
- Note: some subsystems already have their own packages (`com.client.audio/`, `com.client.engine/`, etc.) — build on what exists rather than duplicating structure

**Subsystems likely still embedded in Client.java:**
- `PacketHandler` — incoming packet processing (the giant switch/case or if-else chain)
- `GameRenderer` — main render loop orchestration, minimap, HUD drawing
- `InterfaceManager` — widget/interface opening, closing, interaction
- `MenuHandler` — right-click menu building and action processing
- `ChatManager` — chat message handling, filtering, history
- `SceneManager` — wraps scene graph interaction, object placement

Note: Input handling (`com.client.engine/`) and audio (`com.client.audio/`) may already be partially extracted. Check before creating redundant classes.

**Delegation pattern to follow:**
```java
// BEFORE: Everything in Client.java
public class Client extends GameShell {
    // Hundreds of lines of packet handling mixed in with everything else
    public void processPacket(int opcode, PacketBuffer buffer) { ... }
}

// AFTER: Extracted to dedicated class
public class PacketHandler {
    private final Client client;

    public PacketHandler(Client client) {
        this.client = client;
    }

    public void processPacket(int opcode, PacketBuffer buffer) { ... }
}

// Client.java delegates:
public class Client extends GameShell {
    private final PacketHandler packetHandler;
    // ...
}
```

**Critical warnings:**
- Many methods in Client.java reference other fields/methods in Client — extracted classes will need a reference back to Client or to specific interfaces
- Watch for circular dependencies; prefer passing specific values over the whole Client reference where feasible
- Some methods may be called via reflection (especially RuneLite API bridge methods) — identify these before moving
- The game loop tick order matters — don't change the order that systems are updated/rendered
- RuneLite plugins interact with the client through `net.runelite.api.*` interfaces — make sure extracted code doesn't break these API contracts

### Priority 2: Rename Obfuscated Identifiers

After architecture changes are stable, rename obfuscated names to meaningful ones.

**Rules:**
- Only rename things you can confidently identify the purpose of
- If unsure, add a `// TODO: identify purpose` comment instead of guessing
- Naming conventions:
  - Classes: `PascalCase` (e.g., `PlayerEntity`, `PacketBuilder`)
  - Methods: `camelCase` (e.g., `decodePosition`, `renderModel`)
  - Fields: `camelCase` (e.g., `currentHealth`, `animationFrame`)
  - Constants: `UPPER_SNAKE_CASE` (e.g., `MAX_PLAYERS`, `TILE_SIZE`)
- Rename across the entire project (all references) — don't leave stale references
- Preserve any names that are part of custom features (likely already named well)
- Preserve RuneLite API interface method names — these have external contracts

**Common patterns in decompiled RSPS clients:**
- `anInt1045`, `aString32`, `aBoolean7` — need context-based renaming
- `method128` — trace callers/callees to determine purpose
- `Class30_Sub2_Sub1` — often cache/archive related entries

**Strategy:**
- Start with the most-used classes and their public APIs
- Work outward from the classes refactored in Priority 1
- Use IDE-safe refactoring (rename symbol, not find-replace text)

### Priority 3: Documentation

**Rules:**
- Add Javadoc to every public class explaining its responsibility
- Add Javadoc to public methods with `@param`, `@return`, and behavior descriptions
- Add inline comments for complex algorithms (rendering math, protocol encoding, bit manipulation)
- Document the packet protocol: for each opcode, document the packet structure and what game action it represents
- Document cache format: for each JS5 index/archive, what data it contains
- Distinguish original decompiled code from custom additions where possible

**Focus areas:**
- Networking protocol (packet opcodes, encoding formats)
- JS5 cache loading (file formats, index structures)
- Rendering pipeline (draw order, coordinate systems, GPU/HD plugin integration)
- RuneLite API bridge layer
- Any custom features added by the team

### Priority 4: Dead Code Removal

**Rules:**
- Only remove code that is provably unreachable
- Be cautious — code may be called via reflection, referenced by opcode, invoked by RuneLite plugins, or conditionally compiled
- Do NOT remove commented-out code blocks with `// TODO` or developer notes
- Safe to remove: unused imports, methods with zero callers (after thorough search), write-only fields, duplicate utilities
- When in doubt, mark with `// POSSIBLY DEAD:` instead of deleting

### General Safety Rules

**Always:**
- Compile and test after each extraction/rename — do not batch large changes
- One subsystem extraction per commit with descriptive messages
- Verify the client launches and connects after changes

**Never modify without explicit permission:**
- RSA key pairs or ISAAC seed handling
- Login/networking protocol details (opcodes, encoding)
- JS5 cache format parsing
- `com.client.Configuration` values
- RuneLite API interfaces (`net.runelite.api.*`)
- Build configuration (Gradle files, build scripts)
- Any anti-cheat or security-related code
- GPU/HD plugin shader code

### Code Style
- Java conventions (Oracle style guide)
- Max line length: 120 characters
- Use `this.` prefix only when disambiguating from parameters
- Prefer `final` on fields that don't change after construction
- No wildcard imports — use specific imports
- Respect existing Lombok annotations (`@Getter`, `@Setter`, etc.)
- Respect existing Guice injection patterns (`@Inject`, `@Singleton`, etc.)