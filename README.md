# HappyFarmer

![Version](https://img.shields.io/github/v/release/danielchen666/HappyFarmer?label=version)
![License](https://img.shields.io/github/license/danielchen666/HappyFarmer)
![Java](https://img.shields.io/badge/Java-21%2B-orange)
![Minecraft](https://img.shields.io/badge/Minecraft-26.2-blue)

A modern Minecraft Paper plugin designed to enhance the farming experience with automated replanting, custom composting, and nether-themed utilities.

## ✨ Features

- 🌾 **Smart Harvest**: Left-click mature crops with a hoe to harvest and instantly replant.
- 🔗 **VeinMiner Integration**: Support for bulk replanting when using [VeinMiner](https://github.com/2008Choco/VeinMiner).
- ♻️ **Enhanced Composting**: Add meat, cooked foods, and rare items to composters.
- 🍞 **Furnace Bread**: New furnace recipe: `1 Wheat` $\rightarrow$ `1 Bread`.
- 🔥 **Nether Utilities**:
  - **Shulker Box**: Craftable with a Chest surrounded by Netherrack.
  - **Ender Chest**: Craftable with a Chest surrounded by Blackstone.
  - **Netherrack Fuel**: Netherrack now burns in furnaces (300 ticks).

## ⚙️ Requirements

| Component | Version |
| :--- | :--- |
| **Minecraft** | `26.2` |
| **Server** | Paper `26.2 build 40` |
| **Java Runtime** | `25+` |
| **API** | `26.2.build.40-alpha` |
| **VeinMiner** | `2.4.0` (Optional) |

## 🚀 Installation

1. Download the latest `.jar` from the [Releases](https://github.com/danielchen666/HappyFarmer/releases) page.
2. Drop the file into your server's `plugins/` folder.
3. Restart your server.

## 🛠️ Building from Source

```bash
git clone https://github.com/danielchen666/HappyFarmer.git
cd HappyFarmer
mvn package
```
The compiled jar will be available at `target/happyfarmer-v0.5.jar`.

## 📄 License

Distributed under the [MIT License](LICENSE).
