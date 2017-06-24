package com.akaiha.spawnerbreak.command;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.akaiha.spawnerbreak.utilities.SpawnerItem;

public class CommandManager implements CommandExecutor {

	private final String COMMAND_GIVE = "/spawnerbreak give <player> <entity> [amount]";
	private final String COMMAND_INFO = "/spawnerbreak info";
	private final String COMMAND_HELP = "/spawnerbreak help";
	private final String COMMAND_CHANGE = "/spawnerbreak change <setting> <value>";
	private final String COMMAND_PERM_ERROR = ChatColor.RED + "You do not have permission for this command.";
	private final String COMMAND_NOT_SPAWNER = ChatColor.RED + "That is not a spawner.";
	private final String COMMAND_INV_FULL = ChatColor.RED + "Players inventory is full.";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			try {
				switch(Commands.valueOf(args[0].toUpperCase())) {
				case GIVE:
					if (!sender.hasPermission("spawnerbreak.give")) {
						sender.sendMessage(COMMAND_PERM_ERROR);
						return false;
					}
					Inventory inv = Bukkit.getPlayer(args[1]).getInventory();
					if(args.length > 3) {
						giveSpawner(sender,inv,args[2],Integer.parseInt(args[3]));
					} else {
						giveSpawner(sender,inv,args[2],1);
					}
					break;
				case INFO:
					try {
						if (!sender.hasPermission("spawnerbreak.info")) {
							sender.sendMessage(COMMAND_PERM_ERROR);
							return false;
						}
						CreatureSpawner spawner = (CreatureSpawner) ((Player) sender).getTargetBlock((Set<Material>) null, 3).getState();
						sender.sendMessage("Entity: " + spawner.getSpawnedType().toString() + ", Delay: " + spawner.getDelay());
					} catch (IllegalArgumentException | ClassCastException e) {
						sender.sendMessage(COMMAND_NOT_SPAWNER);
					}
					break;
				case CHANGE:
					try {
						CreatureSpawner spawner = (CreatureSpawner) ((Player) sender).getTargetBlock((Set<Material>) null, 3).getState();
						switch(CommandsChange.valueOf(args[1].toUpperCase())) {
							case ENTITY:
								if (!sender.hasPermission("spawnerbreak.change.entity")) {
									sender.sendMessage(COMMAND_PERM_ERROR);
									return false;
								}
								spawner.setSpawnedType(EntityType.valueOf(args[2].toUpperCase()));
								spawner.update();
								break;
							case DELAY:
								if (!sender.hasPermission("spawnerbreak.change.delay")) {
									sender.sendMessage(COMMAND_PERM_ERROR);
									return false;
								}
								spawner.setDelay(Integer.parseInt(args[2]));
								break;
						}
					} catch (IllegalArgumentException | ArrayIndexOutOfBoundsException | ClassCastException | NullPointerException  e) {
						sender.sendMessage(COMMAND_CHANGE);
					}
					break;
				default:
					senderHelpTwo(sender);
			}
			}catch (IllegalArgumentException | ArrayIndexOutOfBoundsException | NullPointerException e) {
				senderHelpThree(sender);
			}
		} else {
			try {
				if (Commands.valueOf(args[0].toUpperCase()) == Commands.GIVE) {
					Inventory inv = Bukkit.getPlayer(args[1]).getInventory();
					if(args.length > 3) {
						giveSpawner(sender,inv,args[2],Integer.parseInt(args[3]));
					} else {
						giveSpawner(sender,inv,args[2],1);
					}
				} else {
					senderHelpOne(sender);
				}
			}catch (IllegalArgumentException | ArrayIndexOutOfBoundsException | NullPointerException e) {
				senderHelpOne(sender);
			}
		}
		return false;
	}
	
	private void giveSpawner(CommandSender sender, Inventory inv, String entity, int amount) {
		if (inv.firstEmpty() != -1) {
			inv.addItem(SpawnerItem.getSpawnerItem(EntityType.valueOf(entity.toUpperCase()), amount));
		} else {
			sender.sendMessage(COMMAND_INV_FULL);
		}
	}
	
	private void senderHelpOne(CommandSender sender) {
		sender.sendMessage(COMMAND_HELP);
		sender.sendMessage(COMMAND_GIVE);
	}
	
	private void senderHelpTwo(CommandSender sender) {
		senderHelpOne(sender);
		sender.sendMessage(COMMAND_INFO);
	}
	
	private void senderHelpThree(CommandSender sender) {
		senderHelpTwo(sender);
		sender.sendMessage(COMMAND_CHANGE);
	}
}
