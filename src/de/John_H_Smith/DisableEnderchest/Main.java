package de.John_H_Smith.DisableEnderchest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public Logger log = this.getLogger();
	private List<String> disabledWorlds = new ArrayList<>();
	private List<String> disabledWorldsDefault = new ArrayList<>();
	private PluginDescriptionFile plugin = this.getDescription();
	
	@Override
	public void onEnable() {
		disabledWorldsDefault.add("world");
		initConfig();
		disabledWorlds = getConfig().getStringList("DisabledWorlds");
		getServer().getPluginManager().registerEvents(new EnderchestClickListener(this, disabledWorlds), this);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player player = (Player) sender;
		
		if( cmd.getName().equalsIgnoreCase("disableenderchest") ) {
			if( player.hasPermission("disableenderchest.admin")) {
				if( args.length == 1 ) {
					if( args[0].equalsIgnoreCase("version")) {
						player.sendMessage(ChatColor.GOLD + plugin.getName() + ChatColor.YELLOW + " version " + ChatColor.GOLD + plugin.getVersion() + ChatColor.YELLOW + " by " + ChatColor.GOLD + plugin.getAuthors().get(0));
						return true;
					}
					
					//	RELOAD COMMAND
					if( args[0].equalsIgnoreCase("reload") ) {
						initConfig();
						player.sendMessage(ChatColor.GOLD + plugin.getName() + ChatColor.GREEN + " has been reloaded");
						return true;
					}
					
					//  HELP COMMAND
					if( args[0].equalsIgnoreCase("help") ) {
						player.sendMessage(ChatColor.GREEN + "----- " + ChatColor.GOLD + plugin.getName() + ChatColor.GREEN + " -----");
						player.sendMessage(ChatColor.GOLD + "/de version" + ChatColor.GREEN + "     Shows the current version");
						player.sendMessage(ChatColor.GOLD + "/de reload" + ChatColor.GREEN + "     Reloads the config");
						player.sendMessage(ChatColor.GOLD + "/de add <world>" + ChatColor.GREEN + "     Adds world to the disabled worlds");
						player.sendMessage(ChatColor.GOLD + "/de remove <world>" + ChatColor.GREEN + "     Remove world from disabled worlds");
						return true;
					}
				}
				
				if( args.length == 2 ) {
					//  ADD COMMAND
					if( args[0].equalsIgnoreCase("add") ) {
						if( getServer().getWorld(args[1]) != null) {
							disabledWorlds.add(args[1]);
							getConfig().set("DisabledWorlds", disabledWorlds);
							saveConfig();
							initConfig();
							player.sendMessage(ChatColor.GREEN + "World " + ChatColor.GOLD + args[1] + ChatColor.GREEN + " has been added");
						} else player.sendMessage(ChatColor.RED + "This world doesn't exists!");
						return true;
					}
					
					//  REMOVE COMMAND
					if( args[0].equalsIgnoreCase("remove") ) {
						if( getServer().getWorld(args[1]) != null) {
							disabledWorlds.remove(args[1]);
							getConfig().set("DisabledWorlds", disabledWorlds);
							saveConfig();
							initConfig();
							player.sendMessage(ChatColor.GREEN + "World " + ChatColor.GOLD + args[1] + ChatColor.GREEN + " has been " + ChatColor.RED + "removed");
						} else player.sendMessage(ChatColor.RED + "This world doesn't exists!");
						return true;
					}
				}	
			} else {
				player.sendMessage(ChatColor.RED + "You don't have enough permissions");
				return true;
			}
		}		
		return false;
	}
	
	private void initConfig() {
		reloadConfig();
		getConfig().options().header("DisableEnderchest");
		getConfig().addDefault("DisabledWorlds", disabledWorldsDefault);
		getConfig().addDefault("ErrorMessage", "&cThe enderchest is not allowed here!");
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
}
