package me.happyfarmer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class NetherRecipeListener implements Listener {

    private final NamespacedKey shulkerBoxKey;
    private final NamespacedKey enderChestKey;

    public NetherRecipeListener(JavaPlugin plugin) {
        shulkerBoxKey = new NamespacedKey(plugin, "shulker_box_from_netherrack");
        enderChestKey = new NamespacedKey(plugin, "ender_chest_from_blackstone");
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
    }

    public void removeRecipes() {
        Bukkit.removeRecipe(shulkerBoxKey);
        Bukkit.removeRecipe(enderChestKey);
    }

    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent event) {
        if (event.getFuel().getType() == Material.NETHERRACK) {
            event.setBurnTime(300);
            event.setBurning(true);
        }
    }
}
