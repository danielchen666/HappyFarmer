package me.happyfarmer;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class VeinMinerCompatibilityListener implements Listener {

    private final JavaPlugin plugin;

    private VeinMinerCompatibilityListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("unchecked")
    public static boolean register(JavaPlugin plugin) {
        try {
            Class<? extends Event> eventClass = (Class<? extends Event>) Class.forName(
                    "wtf.choco.veinminer.api.event.player.PlayerVeinMineEvent"
            );
            VeinMinerCompatibilityListener listener = new VeinMinerCompatibilityListener(plugin);
            plugin.getServer().getPluginManager().registerEvent(
                    eventClass,
                    listener,
                    EventPriority.MONITOR,
                    (registeredListener, event) -> listener.onPlayerVeinMine(event),
                    plugin,
                    true
            );
            return true;
        } catch (ClassNotFoundException exception) {
            plugin.getLogger().warning("VeinMiner is installed, but PlayerVeinMineEvent was not found.");
            return false;
        }
    }

    private void onPlayerVeinMine(Event event) {
        Object category = invoke(event, "getCategory", Object.class);
        if (category == null) return;

        String categoryId = invoke(category, "getId", String.class);
        if (!"Hoe".equalsIgnoreCase(categoryId)) return;

        ItemStack item = invoke(event, "getItem", ItemStack.class);
        if (item == null || !CropHarvestListener.isHoe(item.getType())) return;

        Player player = invoke(event, "getPlayer", Player.class);
        if (player == null) return;

        List<CropReplant> replants = new ArrayList<>();

        for (Block block : getBlocks(event)) {
            Material cropType = block.getType();
            Material seedType = CropHarvestListener.getSeedType(cropType);
            if (seedType == null || !CropHarvestListener.isMatureCrop(block)) continue;

            CropHarvestListener.markVeinMinerReplant(block);
            replants.add(new CropReplant(block, cropType, seedType));
        }

        if (replants.isEmpty()) return;

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            for (CropReplant replant : replants) {
                CropHarvestListener.replantCrop(player, replant.block(), replant.cropType(), replant.seedType());
            }
        }, 2L);

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            for (CropReplant replant : replants) {
                CropHarvestListener.clearVeinMinerReplant(replant.block());
            }
        }, 20L);
    }

    private List<Block> getBlocks(Event event) {
        Object blocks = invoke(event, "getBlocks", Object.class);
        if (!(blocks instanceof Iterable<?> iterable)) return List.of();

        List<Block> result = new ArrayList<>();
        for (Object block : iterable) {
            if (block instanceof Block bukkitBlock) {
                result.add(bukkitBlock);
            }
        }
        return result;
    }

    private static <T> T invoke(Object target, String methodName, Class<T> expectedType) {
        try {
            Object result = target.getClass().getMethod(methodName).invoke(target);
            return expectedType.isInstance(result) ? expectedType.cast(result) : null;
        } catch (ReflectiveOperationException exception) {
            return null;
        }
    }

    private record CropReplant(Block block, Material cropType, Material seedType) {
    }
}
