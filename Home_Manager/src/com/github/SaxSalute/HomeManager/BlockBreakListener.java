package com.github.SaxSalute.HomeManager;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockBreakListener implements Listener
{
	//A variable to store the plugin
	HomeManager plugin;
	
	public BlockBreakListener(HomeManager plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void breakingCheck(BlockBreakEvent event)
	{
		for (Region r : plugin.regions)
		{
			if (r.containsLocation(event.getBlock().getLocation()))
			{
				if (r.getOwner() != event.getPlayer())
				{
					event.setCancelled(true);
					event.getPlayer().sendMessage(ChatColor.RED + "This is part of " + r.getOwner().getName() + "'s home.");
				}
			}
		}
	}
	
	@EventHandler
	public void placingCheck(BlockPlaceEvent event)
	{
		for (Region r : plugin.regions)
		{
			if (r.containsLocation(event.getBlock().getLocation()))
			{
				if (r.getOwner() != event.getPlayer())
				{
					event.setCancelled(true);
					event.getPlayer().sendMessage(ChatColor.RED + "This is part of " + event.getPlayer().getName() + "'s home.");
				}
			}
		}
	}
}
