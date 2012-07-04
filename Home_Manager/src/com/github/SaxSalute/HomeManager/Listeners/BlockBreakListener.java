package com.github.SaxSalute.HomeManager.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.github.SaxSalute.HomeManager.HomeManager;
import com.github.SaxSalute.HomeManager.Region.Region;

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
		boolean isBuilder = false;
		for (Region r : plugin.getRegions())
		{
			if (r.containsLocation(event.getBlock().getLocation()))
			{
				if (event.getPlayer().getName().equalsIgnoreCase(r.getOwner().getName()))
					isBuilder = true;
				
				for (int x = 0; x < r.getBuilders().size() && !isBuilder; x++)
				{
					isBuilder = r.getBuilders().get(x).getName().equalsIgnoreCase(event.getPlayer().getName());
				}
				
				if (!isBuilder)
				{
					event.setCancelled(true);
					event.getPlayer().sendMessage(ChatColor.RED + "This is part of " + r.getOwner().getName() + "'s home.");
					break;
				}
			}
		}
	}
	
	@EventHandler
	public void placingCheck(BlockPlaceEvent event)
	{
		boolean isBuilder = false;
		for (Region r : plugin.getRegions())
		{
			if (r.containsLocation(event.getBlock().getLocation()))
			{
				if (event.getPlayer().getName().equalsIgnoreCase(r.getOwner().getName()))
					isBuilder = true;
				
				for (int x = 0; x < r.getBuilders().size() && !isBuilder; x++)
				{
					isBuilder = r.getBuilders().get(x).getName().equalsIgnoreCase(event.getPlayer().getName());
				}
				
				if (!isBuilder)
				{
					event.setCancelled(true);
					event.getPlayer().sendMessage(ChatColor.RED + "This is part of " + r.getOwner().getName() + "'s home.");
					break;
				}
			}
		}
	}
}
