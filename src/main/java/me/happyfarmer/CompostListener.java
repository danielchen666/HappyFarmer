package me.happyfarmer;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

public class CompostListener implements Listener {

    private static final int COMPOSTER_MAX_LEVEL = 8;
    private final Random random = new Random();

    private final Map<Material, Float> customCompostChances = new EnumMap<>(Material.class);

    public CompostListener() {
        customCompostChances.put(Material.FEATHER, 0.30f);
        customCompostChances.put(Material.POISONOUS_POTATO, 0.30f);
        customCompostChances.put(Material.PUFFERFISH, 0.30f);
        customCompostChances.put(Material.ROTTEN_FLESH, 0.30f);
        customCompostChances.put(Material.SPIDER_EYE, 0.30f);

        customCompostChances.put(Material.BEEF, 0.65f);
        customCompostChances.put(Material.PORKCHOP, 0.65f);
        customCompostChances.put(Material.CHICKEN, 0.65f);
        customCompostChances.put(Material.MUTTON, 0.65f);
        customCompostChances.put(Material.RABBIT, 0.65f);
        customCompostChances.put(Material.COD, 0.65f);
        customCompostChances.put(Material.SALMON, 0.65f);
        customCompostChances.put(Material.SUGAR, 0.65f);

        customCompostChances.put(Material.COOKED_BEEF, 0.85f);
        customCompostChances.put(Material.COOKED_PORKCHOP, 0.85f);
        customCompostChances.put(Material.COOKED_CHICKEN, 0.85f);
        customCompostChances.put(Material.COOKED_MUTTON, 0.85f);
        customCompostChances.put(Material.COOKED_RABBIT, 0.85f);
        customCompostChances.put(Material.COOKED_COD, 0.85f);
        customCompostChances.put(Material.COOKED_SALMON, 0.85f);
        customCompostChances.put(Material.MUSHROOM_STEW, 0.85f);
        customCompostChances.put(Material.RABBIT_STEW, 0.85f);
        customCompostChances.put(Material.BEETROOT_SOUP, 0.85f);
        customCompostChances.put(Material.SUSPICIOUS_STEW, 0.85f);
        customCompostChances.put(Material.GOLDEN_APPLE, 0.85f);
        customCompostChances.put(Material.ENCHANTED_GOLDEN_APPLE, 0.85f);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block block = event.getClickedBlock();
        if (block == null || block.getType() != Material.COMPOSTER) return;

        ItemStack handItem = event.getItem();
        if (handItem == null) return;

        Material handType = handItem.getType();
        if (handType.isCompostable()) return;

        Float chance = customCompostChances.get(handType);
        if (chance == null) return;

        event.setCancelled(true);

        if (!processCompost(block, chance)) return;
        handItem.setAmount(handItem.getAmount() - 1);
    }

    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent event) {
        Inventory destination = event.getDestination();
        if (destination.getType() != InventoryType.COMPOSTER || destination.getLocation() == null) return;

        Material itemType = event.getItem().getType();
        if (itemType.isCompostable()) return;

        Float chance = customCompostChances.get(itemType);
        if (chance == null) return;

        Block block = destination.getLocation().getBlock();
        if (block.getType() != Material.COMPOSTER) return;

        event.setCancelled(true);
        if (!processCompost(block, chance)) return;

        event.getSource().removeItem(new ItemStack(itemType, 1));
    }

    private boolean processCompost(Block block, float chance) {
        if (!(block.getBlockData() instanceof Levelled levelled)) return false;
        if (levelled.getLevel() >= COMPOSTER_MAX_LEVEL) return false;

        boolean success = random.nextFloat() < chance;

        if (success) {
            int newLevel = levelled.getLevel() + 1;
            levelled.setLevel(newLevel);
            block.setBlockData(levelled);
            block.getWorld().playSound(block.getLocation().add(0.5, 0.5, 0.5),
                    Sound.BLOCK_COMPOSTER_FILL_SUCCESS, 1.0f, 1.0f);
            if (newLevel >= COMPOSTER_MAX_LEVEL) {
                block.getWorld().spawnParticle(Particle.HAPPY_VILLAGER,
                        block.getLocation().add(0.5, 0.8, 0.5), 8, 0.3, 0.3, 0.3, 0);
            }
        } else {
            block.getWorld().playSound(block.getLocation().add(0.5, 0.5, 0.5),
                    Sound.BLOCK_COMPOSTER_FILL, 1.0f, 1.0f);
        }

        block.getWorld().spawnParticle(Particle.COMPOSTER,
                block.getLocation().add(0.5, 0.7, 0.5), 4, 0.2, 0.2, 0.2, 0);

        return true;
    }
}
