package me.happyfarmer;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CropHarvestListener implements Listener {

    private static final Map<Material, Material> CROP_TO_SEED = new EnumMap<>(Material.class);
    private static final Set<String> VEINMINER_REPLANTS = new HashSet<>();

    private final JavaPlugin plugin;

    static {
        CROP_TO_SEED.put(Material.WHEAT, Material.WHEAT_SEEDS);
        CROP_TO_SEED.put(Material.CARROTS, Material.CARROT);
        CROP_TO_SEED.put(Material.POTATOES, Material.POTATO);
        CROP_TO_SEED.put(Material.BEETROOTS, Material.BEETROOT_SEEDS);
    }

    public CropHarvestListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        ItemStack handItem = event.getPlayer().getInventory().getItemInMainHand();
        if (handItem == null || !isHoe(handItem.getType())) return;

        Block block = event.getBlock();
        Material blockType = block.getType();
        Material seedType = CROP_TO_SEED.get(blockType);
        if (seedType == null) return;
        if (!isMatureCrop(block)) return;

        String blockKey = blockKey(block);
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            if (VEINMINER_REPLANTS.remove(blockKey)) return;
            replantCrop(event.getPlayer(), block, blockType, seedType);
        }, 2L);
    }

    public static boolean isHoe(Material material) {
        return material == Material.WOODEN_HOE
                || material == Material.STONE_HOE
                || material == Material.IRON_HOE
                || material == Material.GOLDEN_HOE
                || material == Material.DIAMOND_HOE
                || material == Material.NETHERITE_HOE;
    }

    public static Material getSeedType(Material cropType) {
        return CROP_TO_SEED.get(cropType);
    }

    public static boolean isMatureCrop(Block block) {
        if (!(block.getBlockData() instanceof Ageable ageable)) return false;
        return ageable.getAge() >= ageable.getMaximumAge();
    }

    public static String blockKey(Block block) {
        return block.getWorld().getUID() + ":" + block.getX() + ":" + block.getY() + ":" + block.getZ();
    }

    public static void markVeinMinerReplant(Block block) {
        VEINMINER_REPLANTS.add(blockKey(block));
    }

    public static void clearVeinMinerReplant(Block block) {
        VEINMINER_REPLANTS.remove(blockKey(block));
    }

    public static boolean replantCrop(Player player, Block block, Material cropType, Material seedType) {
        if (!hasSeed(player, seedType)) return false;

        consumeSeed(player, seedType);
        block.setType(cropType);
        player.playSound(block.getLocation().add(0.5, 0.5, 0.5),
                Sound.ITEM_CROP_PLANT, 1.0f, 1.2f);
        player.spawnParticle(Particle.HAPPY_VILLAGER,
                block.getLocation().add(0.5, 0.8, 0.5), 5, 0.3, 0.2, 0.3, 0);
        return true;
    }

    private static boolean hasSeed(Player player, Material seedType) {
        return player.getInventory().containsAtLeast(new ItemStack(seedType), 1);
    }

    private static void consumeSeed(Player player, Material seedType) {
        ItemStack[] contents = player.getInventory().getContents();
        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item != null && item.getType() == seedType) {
                item.setAmount(item.getAmount() - 1);
                if (item.getAmount() <= 0) {
                    contents[i] = null;
                }
                return;
            }
        }
    }
}
