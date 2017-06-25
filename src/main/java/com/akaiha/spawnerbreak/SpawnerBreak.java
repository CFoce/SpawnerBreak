package com.akaiha.spawnerbreak;

import org.bukkit.plugin.java.JavaPlugin;

import com.akaiha.spawnerbreak.command.CommandManager;
import com.akaiha.spawnerbreak.listener.BreakListener;

public class SpawnerBreak extends JavaPlugin {

	public void onEnable() {
		getCommand("spawnerbreak").setExecutor(new CommandManager());
		getServer().getPluginManager().registerEvents(new BreakListener(), this);
	}
	
	public void onDisable() {}
}
