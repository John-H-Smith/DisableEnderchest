package de.John_H_Smith.DisableEnderchest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public Logger log = this.getLogger();
	List<String> disabledWorlds = new ArrayList<>();
	List<String> disabledWorldsDefault = new ArrayList<>();
	
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
		
		return false;
	}
	
	private void initConfig() {
		reloadConfig();
		getConfig().options().header("DisableEnderchest");
		getConfig().addDefault("DisabledWorlds", disabledWorldsDefault);
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
}
