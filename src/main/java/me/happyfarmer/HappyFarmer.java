package me.happyfarmer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class HappyFarmer extends JavaPlugin {

    private NamespacedKey breadFurnaceKey;
    private NextGenRecipeListener nextGenRecipeListener;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CropHarvestListener(this), this);
        getServer().getPluginManager().registerEvents(new CompostListener(), this);

        if (getServer().getPluginManager().isPluginEnabled("VeinMiner")) {
            if (VeinMinerCompatibilityListener.register(this)) {
                getLogger().info("Enabled VeinMiner crop replant compatibility.");
            }
        }

        nextGenRecipeListener = new NextGenRecipeListener(this);
        getServer().getPluginManager().registerEvents(nextGenRecipeListener, this);

        Bukkit.removeRecipe(NamespacedKey.minecraft("bread"));

        breadFurnaceKey = new NamespacedKey(this, "bread_from_wheat");
        FurnaceRecipe breadRecipe = new FurnaceRecipe(
                breadFurnaceKey,
                new ItemStack(Material.BREAD),
                Material.WHEAT,
                0.35f,
                200
        );
        Bukkit.addRecipe(breadRecipe);
        nextGenRecipeListener.addRecipes();

        getLogger().info("HappyFarmer has been enabled!");
        getLogger().info("Removed vanilla bread crafting recipe.");
        getLogger().info("Added furnace recipe: 1 wheat -> 1 bread (200 ticks, 0.35 XP).");
        getLogger().info("Added NextGen recipes, including Rice Balls and netherrack smelting.");
    }

    @Override
    public void onDisable() {
        if (breadFurnaceKey != null) {
            Bukkit.removeRecipe(breadFurnaceKey);
        }
        if (nextGenRecipeListener != null) {
            nextGenRecipeListener.removeRecipes();
        }
        Bukkit.resetRecipes();
        getLogger().info("HappyFarmer has been disabled! Recipes reset to vanilla.");
    }
}
