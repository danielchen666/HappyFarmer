# Daniel's HappyFarmer

A Minecraft Paper plugin that enhances the farming experience.

## Features

- **Crop Harvest & Replant**: Right-click a mature crop with a hoe to harvest and automatically replant (consumes 1 seed from inventory).
- **Sapling Growth Control**: Only player-placed saplings grow into trees naturally. Bonemeal still works on any sapling.
- **Custom Composting**: Additional items can be composted in vanilla composters (e.g. meat, cooked food, feathers, golden apples).
- **Furnace Bread**: Replaces the vanilla bread crafting recipe with a furnace recipe (1 wheat → 1 bread, 200 ticks, 0.35 XP).

## Requirements

- **Minecraft**: 1.21+
- **Server**: Paper (or Spigot) with API version 1.21+
- **Java**: 21+

## Installation

1. Download the latest `happyfarmer-v0.3.jar` from [Releases](https://github.com/danielchen666/daniels-happyfarmer/releases).
2. Place the jar in your server's `plugins/` directory.
3. Restart the server.

## Building from Source

```bash
git clone https://github.com/danielchen666/daniels-happyfarmer.git
cd daniels-happyfarmer
mvn package
```

The compiled jar will be in `target/happyfarmer-v0.3.jar`.

## Configuration

Player-placed sapling data is stored in `plugins/HappyFarmer/player_placed_saplings.yml`. This file is managed automatically — do not edit it while the server is running.

## License

This project is licensed under the [MIT License](LICENSE).
