package me.happyfarmer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class HappyFarmer extends JavaPlugin {

    private NamespacedKey breadFurnaceKey;
    private NetherRecipeListener netherRecipeListener;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CropHarvestListener(this), this);
        getServer().getPluginManager().registerEvents(new CompostListener(), this);

        if (getServer().getPluginManager().isPluginEnabled("VeinMiner")) {
            if (VeinMinerCompatibilityListener.register(this)) {
                getLogger().info("Enabled VeinMiner crop replant compatibility.");
            }
        }

        netherRecipeListener = new NetherRecipeListener(this);
        getServer().getPluginManager().registerEvents(netherRecipeListener, this);

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
        netherRecipeListener.addRecipes();

        getLogger().info("HappyFarmer has been enabled!");
        getLogger().info("Removed vanilla bread crafting recipe.");
        getLogger().info("Added furnace recipe: 1 wheat -> 1 bread (200 ticks, 0.35 XP).");
        getLogger().info("Added nether recipes and netherrack furnace fuel.");
    }

    @Override
    public void onDisable() {
        if (breadFurnaceKey != null) {
            Bukkit.removeRecipe(breadFurnaceKey);
        }
        if (netherRecipeListener != null) {
            netherRecipeListener.removeRecipes();
        }
        Bukkit.resetRecipes();
        getLogger().info("HappyFarmer has been disabled! Recipes reset to vanilla.");
    }
}
