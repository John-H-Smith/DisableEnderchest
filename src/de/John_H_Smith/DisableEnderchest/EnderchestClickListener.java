package de.John_H_Smith.DisableEnderchest;

import java.util.List;

import org.bukkit.block.EnderChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class EnderchestClickListener implements Listener {
	private Main plugin;
	private List<String> disabledWorlds;
	
	public EnderchestClickListener(Main plugin, List<String> list) {
		this.plugin = plugin;
		this.disabledWorlds = list;
	}
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		if (event.getInventory().equals(event.getPlayer().getEnderChest() ) )
	    {
	        if( disabledWorlds.contains(event.getPlayer().getWorld().getName()) ) {
	        	event.setCancelled(true);
	        	event.getPlayer().sendMessage("Die Enderchest ist hier verboten!");
	        }
	        System.out.println("Enderchest geöffnet");
	    }
	}
	
}
