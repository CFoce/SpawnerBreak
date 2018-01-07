package com.akaiha.spawnerbreak.listener;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import com.akaiha.spawnerbreak.utilities.SpawnerItem;

public class BreakListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
    public void onSpawnerBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInHand();
        if (block.getType() == Material.MOB_SPAWNER
        	&& !event.isCancelled()
            && player.hasPermission("spawnerbreak.mine")
            && item != null
            && item.getType() == Material.DIAMOND_PICKAXE
            && item.getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {
                CreatureSpawner spawnBlock = (CreatureSpawner) block.getState();
                event.setExpToDrop(0);
                block.getWorld().dropItem(block.getLocation(), SpawnerItem.getSpawnerItem(spawnBlock.getSpawnedType(),1));
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSpawnerPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInHand();
        List<String> lore = item.getItemMeta().getLore();
        if (block.getType() == Material.MOB_SPAWNER 
        	&& !event.isCancelled()
            && item != null
            && lore != null) {
        	    if (!player.hasPermission("spawnerbreak.place")) {
        	    	event.setCancelled(true);
        	    } else {
        	    	CreatureSpawner spawner = (CreatureSpawner) block.getState();
        	    	try {
        	    		spawner.setSpawnedType(EntityType.valueOf(lore.get(0)));
        	    	} catch (IllegalArgumentException e) {
        	    		event.setCancelled(true);
        	    		player.sendMessage(ChatColor.RED + "You have a bad spawner, contact your Admin.");
        	    	}
        	    }
        }
    }
}
