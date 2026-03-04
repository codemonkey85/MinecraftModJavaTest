# Testing the Size Mod

## Prerequisites

- **JDK 21** — [Download from Adoptium](https://adoptium.net/) (select Java 21 LTS)
- **IntelliJ IDEA** (recommended) or another IDE with Gradle support

## First-Time Setup

1. Clone or open this project in IntelliJ IDEA (**File > Open**, select the project folder)
2. IntelliJ will detect the Gradle build and begin importing — this may take several minutes the first time as it downloads NeoForge, Minecraft assets, and decompiled sources
3. Once synced, you should see the Gradle tool window on the right with run configurations available

## Running the Game

### From IntelliJ
Open the **Gradle** tool window and run:
- `Tasks > neoforge > runClient` — launches a Minecraft client with the mod loaded
- `Tasks > neoforge > runServer` — launches a headless server (useful for multiplayer testing)

### From the terminal
```bash
./gradlew runClient
./gradlew runServer
```

## Testing the Items

1. Launch `runClient` and create or load a **Creative Mode** world
2. Open your inventory and go to the **Tools** tab
3. You should see two new items:
   - **Grow Crystal** — right-click to grow by 0.25x
   - **Shrink Crystal** — right-click to shrink by 0.25x
4. Hold a crystal and right-click to change your size
5. A chat message will confirm your new size (e.g. `You have grown! Size: 1.25x`)

## Testing the Commands

All commands are available to all players (no operator permissions required).

| Command | Expected result |
|---|---|
| `/size get` | Prints your current size (default `1.00x`) |
| `/size grow` | Increases size by 0.25x, confirms in chat |
| `/size shrink` | Decreases size by 0.25x, confirms in chat |
| `/size set 0.5` | Sets size to exactly 0.5x (half size) |
| `/size set 3.0` | Sets size to 3x (giant) |
| `/size reset` | Returns size to 1.0x (normal) |

### Boundary cases to verify
- `/size set 0.1` — minimum allowed size, player should become very small
- `/size set 10.0` — maximum allowed size, player should become very large
- Spam right-clicking **Shrink Crystal** — size should stop at `0.10x` and not go below
- Spam right-clicking **Grow Crystal** — size should stop at `10.00x` and not go above

## Building a distributable JAR

```bash
./gradlew build
```

The output JAR will be at `build/libs/sizemod-1.0.0.jar`. To install it manually, drop it into the `mods/` folder of a NeoForge 21.11 Minecraft installation.
