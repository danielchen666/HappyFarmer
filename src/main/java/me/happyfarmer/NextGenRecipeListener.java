package me.happyfarmer;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class NextGenRecipeListener implements Listener {

    private final JavaPlugin plugin;
    private final NamespacedKey shulkerBoxKey;
    private final NamespacedKey enderChestKey;
    private final NamespacedKey nigoStoneKey;
    private final NamespacedKey nigoStoneBlastKey;
    private final NamespacedKey superRiceBallKey;
    private final List<NamespacedKey> riceBallKeys = new ArrayList<>();

    public NextGenRecipeListener(JavaPlugin plugin) {
        this.plugin = plugin;
        shulkerBoxKey = new NamespacedKey(plugin, "shulker_box_from_netherrack");
        enderChestKey = new NamespacedKey(plugin, "ender_chest_from_blackstone");
        nigoStoneKey = new NamespacedKey(plugin, "nigo_stone_from_netherrack");
        nigoStoneBlastKey = new NamespacedKey(plugin, "nigo_stone_blasting_from_netherrack");
        superRiceBallKey = new NamespacedKey(plugin, "super_rice_ball");
    }

    public void addRecipes() {
        ShapedRecipe shulkerRecipe = new ShapedRecipe(shulkerBoxKey, new ItemStack(Material.SHULKER_BOX));
        shulkerRecipe.shape("NNN", "NCN", "NNN");
        shulkerRecipe.setIngredient('N', Material.NETHERRACK);
        shulkerRecipe.setIngredient('C', Material.CHEST);
        Bukkit.addRecipe(shulkerRecipe);

        ShapedRecipe enderChestRecipe = new ShapedRecipe(enderChestKey, new ItemStack(Material.ENDER_CHEST));
        enderChestRecipe.shape("BBB", "BCB", "BBB");
        enderChestRecipe.setIngredient('B', Material.BLACKSTONE);
        enderChestRecipe.setIngredient('C', Material.CHEST);
        Bukkit.addRecipe(enderChestRecipe);

        FurnaceRecipe nigoStoneRecipe = new FurnaceRecipe(
                nigoStoneKey,
                createNigoStone(),
                Material.NETHERRACK,
                0.15f,
                400
        );
        Bukkit.addRecipe(nigoStoneRecipe);

        BlastingRecipe nigoStoneBlastRecipe = new BlastingRecipe(
                nigoStoneBlastKey,
                createNigoStone(),
                Material.NETHERRACK,
                0.15f,
                200
        );
        Bukkit.addRecipe(nigoStoneBlastRecipe);

        addRiceBallRecipes();
        addSuperRiceBallRecipe();
    }

    private void addRiceBallRecipes() {
        Material[] meats = {
                Material.COOKED_BEEF, Material.COOKED_CHICKEN, Material.COOKED_MUTTON,
                Material.COOKED_PORKCHOP, Material.COOKED_COD, Material.COOKED_SALMON, Material.COOKED_RABBIT
        };

        ItemStack riceBall = createRiceBall();

        for (Material meat : meats) {
            NamespacedKey key = new NamespacedKey(plugin, "rice_ball_" + meat.name().toLowerCase());
            ShapedRecipe recipe = new ShapedRecipe(key, riceBall);
            recipe.shape("WWW", "WMW", "WWW");
            recipe.setIngredient('W', Material.WHEAT);
            recipe.setIngredient('M', meat);
            Bukkit.addRecipe(recipe);
            riceBallKeys.add(key);
        }
    }

    private void addSuperRiceBallRecipe() {
        ItemStack superRiceBall = createSuperRiceBall();
        ShapedRecipe recipe = new ShapedRecipe(superRiceBallKey, superRiceBall);
        recipe.shape("RRR", "   ", "   ");
        recipe.setIngredient('R', createRiceBall());
        Bukkit.addRecipe(recipe);
    }

    public void removeRecipes() {
        Bukkit.removeRecipe(shulkerBoxKey);
        Bukkit.removeRecipe(enderChestKey);
        Bukkit.removeRecipe(nigoStoneKey);
        Bukkit.removeRecipe(nigoStoneBlastKey);
        Bukkit.removeRecipe(superRiceBallKey);
        for (NamespacedKey key : riceBallKeys) {
            Bukkit.removeRecipe(key);
        }
        riceBallKeys.clear();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().removePotionEffect(PotionEffectType.NAUSEA);
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        if (item.getType() == Material.BAKED_POTATO && item.hasItemMeta()) {
            var meta = item.getItemMeta();
            if (meta == null) return;

            String name = meta.itemName().toString();
            org.bukkit.entity.Player player = event.getPlayer();

            if (name.contains("超級大飯糰") || name.contains("超")) {
                player.setFoodLevel(20);
                Bukkit.broadcastMessage(player.getName() + " 吃了超級大飯糰");
                
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 400, 0));
                    
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        Bukkit.broadcastMessage(player.getName() + " 吃太飽要睡著了");
                    }, 100L); // 5s after nausea starts (total 10s)
                    
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        player.kickPlayer("喔愛 什麼是愛\n你看我的眼神怎麼這麼可愛\n若是講你的心親像大海\n我也會甘願 為了你暈船\n喔喔喔愛 什麼是愛\n你看我的眼神甘嘸一絲絲愛\n真想欲在這個花花世界\n帶你去一個 溫暖的所在\nNow I just want hold you tight, oh baby don’t cry.\nI miss you in this rainy night.\nI’ll show you the best in my mind.\nLet’s dance in the night.\nI will sing a song for you, the love in my eyes.\n喔喔喔愛 有你的將來\n我對你的感情我講不出來\n在這個風風雨雨的世界\n你敢會嫌棄 我騎摩托車\n喔喔喔愛 有我的未來\n我對你的感情我要講出來\n在這個風風雨雨的世界\n我永遠在這 等你瞭解");
                    }, 160L); // 8s after nausea starts (total 13s)
                }, 100L); // 5 seconds later
            } else if (name.contains("飯糰")) {
                int currentFood = player.getFoodLevel();
                player.setFoodLevel(Math.min(20, currentFood + 5));
            }
        }
    }

    private ItemStack createRiceBall() {
        ItemStack item = new ItemStack(Material.BAKED_POTATO);
        var meta = item.getItemMeta();
        meta.itemName(Component.text("飯糰"));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createSuperRiceBall() {
        ItemStack item = new ItemStack(Material.BAKED_POTATO);
        var itemMeta = item.getItemMeta();
        
        // 彩虹大波浪 (Rainbow Wave) - 使用多個彩色片段組成
        Component rainbowName = Component.text()
                .append(Component.text("超").color(NamedTextColor.RED))
                .append(Component.text("級").color(NamedTextColor.GOLD))
                .append(Component.text("大").color(NamedTextColor.YELLOW))
                .append(Component.text("飯").color(NamedTextColor.GREEN))
                .append(Component.text("糰").color(NamedTextColor.BLUE))
                .build();
        
        itemMeta.itemName(rainbowName);
        itemMeta.lore(List.of(Component.text("非常新鮮非常美味").color(NamedTextColor.GRAY)));
        item.setItemMeta(itemMeta);
        return item;
    }

    private ItemStack createNigoStone() {
        ItemStack item = new ItemStack(Material.CHARCOAL);
        var meta = item.getItemMeta();
        meta.itemName(Component.text("尼哥石", TextColor.color(0x5C4033)));
        item.setItemMeta(meta);
        return item;
    }
}
