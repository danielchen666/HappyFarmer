# Daniel's HappyFarmer

A Minecraft Paper plugin that enhances the farming experience.

## Features

- **Crop Harvest & Replant**: Left-click a mature crop with a hoe to harvest and automatically replant (consumes 1 seed from inventory).
- **VeinMiner Compatibility**: When VeinMiner harvests mature crops with a hoe, HappyFarmer replants the harvested crop area using seeds from the player's inventory.
- **Custom Composting**: Additional items can be composted in vanilla composters (e.g. meat, cooked food, feathers, golden apples).
- **Furnace Bread**: Replaces the vanilla bread crafting recipe with a furnace recipe (1 wheat → 1 bread, 200 ticks, 0.35 XP).
- **NextGenNether Recipes**: Craft a Shulker Box with a Chest surrounded by Netherrack, and an Ender Chest with a Chest surrounded by Blackstone.
- **Netherrack Fuel**: Netherrack can be used as furnace fuel for 300 ticks.

## Requirements

- **Minecraft**: 26.2
- **Server**: Paper 26.2 build 40 (`26.2-40-dev/26.2@6d23ced`)
- **Paper API**: `26.2.build.40-alpha`
- **Java runtime**: 25+
- **Java compiler target**: 21
- **Optional integration**: VeinMiner 2.4.0

## Installation

1. Download the latest `happyfarmer-v0.5.jar` from [Releases](https://github.com/danielchen666/daniels-happyfarmer/releases).
2. Place the jar in your server's `plugins/` directory.
3. Restart the server.

## Building from Source

```bash
git clone https://github.com/danielchen666/daniels-happyfarmer.git
cd daniels-happyfarmer
mvn package
```

The compiled jar will be in `target/happyfarmer-v0.5.jar`.

## Configuration

The plugin is configured via `plugin.yml` and uses default Minecraft settings for farming.

## License

This project is licensed under the [MIT License](LICENSE).
