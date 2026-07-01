package me.happyfarmer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SaplingGrowListener implements Listener {

    private final JavaPlugin plugin;
    private final Set<String> playerPlacedSaplings = new HashSet<>();
    private final File dataFile;

    public SaplingGrowListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "player_placed_saplings.yml");
        loadData();
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!isSapling(event.getBlock().getType())) return;
        playerPlacedSaplings.add(toKey(event.getBlock()));
        saveData();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!isSapling(event.getBlock().getType())) return;
        if (playerPlacedSaplings.remove(toKey(event.getBlock()))) {
            saveData();
        }
    }

    @EventHandler
    public void onStructureGrow(StructureGrowEvent event) {
        if (event.isFromBonemeal()) return;

        Block block = event.getLocation().getBlock();
        if (!isSapling(block.getType())) return;

        if (!playerPlacedSaplings.contains(toKey(block))) {
            event.setCancelled(true);
        } else {
            playerPlacedSaplings.remove(toKey(block));
            saveData();
        }
    }

    private String toKey(Block block) {
        return block.getWorld().getName() + ":" + block.getX() + ":" + block.getY() + ":" + block.getZ();
    }

    private boolean isSapling(Material material) {
        try {
            return Tag.SAPLINGS.isTagged(material);
        } catch (Exception e) {
            return material == Material.OAK_SAPLING
                    || material == Material.SPRUCE_SAPLING
                    || material == Material.BIRCH_SAPLING
                    || material == Material.JUNGLE_SAPLING
                    || material == Material.ACACIA_SAPLING
                    || material == Material.DARK_OAK_SAPLING
                    || material == Material.CHERRY_SAPLING
                    || material == Material.PALE_OAK_SAPLING
                    || material == Material.MANGROVE_PROPAGULE;
        }
    }

    private void loadData() {
        if (!dataFile.exists()) return;
        try {
            var config = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(dataFile);
            playerPlacedSaplings.addAll(config.getStringList("saplings"));
            plugin.getLogger().info("Loaded " + playerPlacedSaplings.size() + " player-placed sapling records.");
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to load player-placed sapling data: " + e.getMessage());
        }
    }

    private void saveData() {
        try {
            var config = new org.bukkit.configuration.file.YamlConfiguration();
            config.set("saplings", new java.util.ArrayList<>(playerPlacedSaplings));
            config.save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to save player-placed sapling data: " + e.getMessage());
        }
    }
}
