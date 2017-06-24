package com.akaiha.spawnerbreak.utilities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpawnerItem {
	
	public static ItemStack getSpawnerItem(EntityType entity, int amount) {
		ItemStack spawner = new ItemStack(Material.MOB_SPAWNER, amount);
        ItemMeta meta = spawner.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add(entity.toString());
        meta.setLore(lore);
        meta.setDisplayName(StringUtils.capitalize(entity.toString().toLowerCase()) + " Spawner");
        spawner.setItemMeta(meta);
		return spawner;
	}
}
