<a href="https://github.com/danielchen666/HappyFarmer/releases/latest" alt="Latest release">
    <img src="https://img.shields.io/github/v/release/danielchen666/HappyFarmer?include_prereleases" alt="Latest release">
</a>
<a href="LICENSE" alt="License">
    <img src="https://img.shields.io/github/license/danielchen666/HappyFarmer" alt="License">
</a>

# HappyFarmer

A Minecraft Paper plugin that enhances farming, adds nether recipes, and integrates with VeinMiner for mass crop replanting.

🪴 Left-click mature crops with a hoe → harvest & replant (consumes 1 seed)  
⛏️ VeinMiner mass harvest → replants the whole area in one pass  
🧪 Expanded composting (meat, feathers, golden apples, and more)  
🍞 Furnace bread: 1 wheat → 1 bread (replaces vanilla craft)  
🔥 Netherrack furnace fuel (300 ticks)  
📦 Nether recipes: Shulker Box (Netherrack + Chest) · Ender Chest (Blackstone + Chest)

For details on requirements and configuration, see [`plugin.yml`](src/main/resources/plugin.yml).

## VeinMiner Integration

VeinMiner is optional but highly recommended for mass crop replanting. When VeinMiner's Hoe category harvests mature crops, HappyFarmer replants every affected block using seeds from the player's inventory.

[VeinMiner on GitHub](https://github.com/2008Choco/VeinMiner)

## Build & Install

```bash
git clone https://github.com/danielchen666/HappyFarmer.git && cd HappyFarmer && mvn package
```

Place `target/happyfarmer-v0.5.jar` in `plugins/` and restart the server.

## License

MIT – see [LICENSE](LICENSE).