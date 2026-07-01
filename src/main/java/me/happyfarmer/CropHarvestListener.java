package me.happyfarmer;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.EnumMap;
import java.util.Map;

public class CropHarvestListener implements Listener {

    private final Map<Material, Material> cropToSeed = new EnumMap<>(Material.class);

    public CropHarvestListener() {
        cropToSeed.put(Material.WHEAT, Material.WHEAT_SEEDS);
        cropToSeed.put(Material.CARROTS, Material.CARROT);
        cropToSeed.put(Material.POTATOES, Material.POTATO);
        cropToSeed.put(Material.BEETROOTS, Material.BEETROOT_SEEDS);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block block = event.getClickedBlock();
        if (block == null) return;

        ItemStack handItem = event.getItem();
        if (handItem == null || !isHoe(handItem.getType())) return;

        Material blockType = block.getType();
        Material seedType = cropToSeed.get(blockType);
        if (seedType == null) return;

        if (!(block.getBlockData() instanceof Ageable ageable)) return;
        if (ageable.getAge() < ageable.getMaximumAge()) return;

        Player player = event.getPlayer();
        boolean hasSeed = hasSeed(player, seedType);

        block.breakNaturally();

        if (hasSeed) {
            consumeSeed(player, seedType);
            block.setType(blockType);
            player.playSound(block.getLocation().add(0.5, 0.5, 0.5),
                    Sound.ITEM_CROP_PLANT, 1.0f, 1.2f);
            player.spawnParticle(Particle.HAPPY_VILLAGER,
                    block.getLocation().add(0.5, 0.8, 0.5), 5, 0.3, 0.2, 0.3, 0);
        }

        player.spawnParticle(Particle.BLOCK,
                block.getLocation().add(0.5, 0.5, 0.5), 10, 0.3, 0.3, 0.3, 0,
                block.getBlockData());

        event.setCancelled(true);
    }

    private boolean isHoe(Material material) {
        return material == Material.WOODEN_HOE
                || material == Material.STONE_HOE
                || material == Material.IRON_HOE
                || material == Material.GOLDEN_HOE
                || material == Material.DIAMOND_HOE
                || material == Material.NETHERITE_HOE;
    }

    private boolean hasSeed(Player player, Material seedType) {
        return player.getInventory().containsAtLeast(new ItemStack(seedType), 1);
    }

    private void consumeSeed(Player player, Material seedType) {
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
