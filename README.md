# Daniel's HappyFarmer

A Minecraft Paper plugin that enhances the farming experience.

## Features

- Left-click mature crops with a hoe to harvest and replant (consumes 1 seed).
- VeinMiner integration: replants crops after VeinMiner area harvests.
- Custom composting: meat, cooked food, feathers, golden apples, etc.
- Furnace bread recipe replaces vanilla crafting (1 wheat in furnace -> 1 bread).
- Nether recipes: Shulker Box (Chest + 8 Netherrack), Ender Chest (Chest + 8 Blackstone).
- Netherrack furnace fuel (300 ticks).

## Requirements

- Minecraft: 26.2
- Server: Paper 26.2 build 40
- Paper API: 26.2.build.40-alpha
- Java runtime: 25+, compiler target: 21
- Optional: [VeinMiner 2.4.0](https://github.com/2008Choco/VeinMiner) for area replanting

## Installation

1. Download happyfarmer-v0.5.jar from [Releases](https://github.com/danielchen666/HappyFarmer/releases).
2. Place in plugins/.
3. Restart the server.

## Build

```bash
git clone https://github.com/danielchen666/HappyFarmer.git
cd HappyFarmer
mvn package
```

Compiled jar: `target/happyfarmer-v0.5.jar`.

## License

MIT — see [LICENSE](LICENSE).