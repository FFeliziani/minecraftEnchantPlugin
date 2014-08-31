package com.federicofeliziani.minecraftTest;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by f3l1x on 25/08/14.
 */
public class MinecraftTest extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        //super.onEnable();
        getLogger().info("[MINECRAFT TEST] Starting to enable Test Plugin");
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("[MINECRAFT TEST] Initialising Configuration:");
        loadConfiguration();
        getLogger().info("[MINECRAFT TEST] Plugin Enabled!");
    }

    @Override
    public void onDisable() {
        //super.onDisable();
        getLogger().info("Goodbye World!");
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onPlayerUse(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if(p.getItemInHand().getType() == Material.BLAZE_POWDER){
            Fireball fire = p.getWorld().spawn(e.getPlayer().getLocation(), Fireball.class);
            fire.setShooter(p);
        }
        else if(p.getItemInHand().getType() == Material.BLAZE_ROD){
            //Do whatever
        }
    }

    public void loadConfiguration() {
        getConfig().addDefault("outputPath", "plugins" + File.separator + "Output");
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        PlayerInventory inventory = player.getInventory();

        if(e.getItem().getType() == Material.DIAMOND && (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) && player.getGameMode() == GameMode.CREATIVE)
        {
            player.setGameMode(GameMode.SURVIVAL);
            //inventory.clear();
            //LET'S CREATE SOME ARMOR
            ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
            ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
            ItemStack leggins = new ItemStack(Material.DIAMOND_LEGGINGS);
            ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);

            //NOW LET'S GET THE META
            ItemMeta helmetMeta = helmet.getItemMeta();
            ItemMeta chestMeta = chestplate.getItemMeta();
            ItemMeta legginsMeta = leggins.getItemMeta();
            ItemMeta bootsMeta = boots.getItemMeta();

            //LET'S DO ALL THE FANCY STUFF, ONE ITEM AT A TIME

            //HELMET
            helmetMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Deep Sea Crown");
            helmetMeta.addEnchant(Enchantment.OXYGEN, 3, true);
            helmetMeta.addEnchant(Enchantment.WATER_WORKER, 1, true);
            helmetMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
            helmetMeta.addEnchant(Enchantment.DURABILITY, 5, true);

            //CHESTPLATE
            chestMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Dragonscale Armor");
            chestMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
            chestMeta.addEnchant(Enchantment.DURABILITY, 5, true);

            //LEGGINS
            legginsMeta.setDisplayName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Pants of Nature");
            legginsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
            legginsMeta.addEnchant(Enchantment.DURABILITY, 5, true);

            //BOOTS
            bootsMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Boots of Speed");
            bootsMeta.addEnchant(Enchantment.PROTECTION_FALL, 5, true);
            bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
            bootsMeta.addEnchant(Enchantment.DURABILITY, 5, true);

            //SAME AS ABOVE, BUT WITH THE LORE
            List<String> helmetLore = Arrays.asList(ChatColor.DARK_BLUE + "This helmet is said", ChatColor.DARK_BLUE + "to have been property of", ChatColor.DARK_BLUE + "Neptune, the Sea God");
            List<String> chestLore = Arrays.asList(ChatColor.DARK_RED + "This armor piece was forged", ChatColor.DARK_RED + "in the deepest mines of this world.", ChatColor.DARK_RED + "It is said to be almost indestructible");
            List<String> legginsLore = Arrays.asList(ChatColor.GREEN + "These pants have been built with", ChatColor.GREEN + "the strength of a thousand ENT");
            List<String> bootsLore = Arrays.asList(ChatColor.YELLOW + "Mercury's Boots,", ChatColor.YELLOW + "Gods' messenger");

            //ADDING THE LORE BACK TO THE META
            helmetMeta.setLore(helmetLore);
            chestMeta.setLore(chestLore);
            legginsMeta.setLore(legginsLore);
            bootsMeta.setLore(bootsLore);

            //ADDING ALL META BACK
            helmet.setItemMeta(helmetMeta);
            chestplate.setItemMeta(chestMeta);
            leggins.setItemMeta(legginsMeta);
            boots.setItemMeta(bootsMeta);

            //ADD ITEMS TO THE USER'S INVENTORY
            inventory.setHelmet(helmet);
            inventory.setChestplate(chestplate);
            inventory.setLeggings(leggins);
            inventory.setBoots(boots);

            inventory.setHeldItemSlot(0);
            inventory.setItemInHand(new ItemStack(Material.EMERALD));
            inventory.setItem(1, new ItemStack(Material.BLAZE_ROD));
        }
        if(e.getItem().getType() == Material.EMERALD && (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR))
        {
            player.setGameMode(GameMode.CREATIVE);
            //inventory.clear();
            inventory.setHeldItemSlot(0);
            inventory.setItemInHand(new ItemStack(Material.DIAMOND));
        }
        if(e.getItem().getType() == Material.BLAZE_ROD && (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) && player.getGameMode() == GameMode.SURVIVAL)
        {
            Location playerLocation = player.getLocation();
            playerLocation.setY(playerLocation.getY() + 1.5f);
            Fireball fire = player.getWorld().spawn(playerLocation, Fireball.class);
            fire.setShooter(player);
        }
    }
}