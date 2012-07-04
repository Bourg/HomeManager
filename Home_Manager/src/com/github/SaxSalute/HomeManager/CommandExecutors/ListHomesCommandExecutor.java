package com.github.SaxSalute.HomeManager.CommandExecutors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.SaxSalute.HomeManager.HomeManager;
import com.github.SaxSalute.HomeManager.Region.Region;

public class ListHomesCommandExecutor implements CommandExecutor
{
	private HomeManager plugin;
	
	public ListHomesCommandExecutor(HomeManager plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		/*
		 * homeinfo
		 */
		if (command.getName().equalsIgnoreCase("listhomes"))
		{
			//If there are no homes
			if (plugin.getRegions() == null || plugin.getRegions().size() == 0)
			{
				sender.sendMessage(ChatColor.YELLOW + "No homes have been created");
				return true;
			}
			
			//List All
			if (args.length == 0 || args[0].equalsIgnoreCase("all"))
			{
				sender.sendMessage(ChatColor.BLUE + "Existing Homes: " + plugin.getRegions().size());
	
				//If there are homes
				for (Region r : plugin.getRegions())
				{
					sender.sendMessage(ChatColor.YELLOW + r.getName());
				}
				return true;
			}
			
			//List with details
			else if (args.length == 2 && args[0].equalsIgnoreCase("details"))
			{
				int position = -1;
				
				//Find the index of the region name in the regions ArrayList
				for (int x = 0; x < plugin.getRegions().size() && position == -1; x++)
				{
					if (plugin.getRegions().get(x).getName().equalsIgnoreCase(args[1]))
					{
						position = x;
					}
				}
				
				if (position != -1)
				{
					sender.sendMessage(ChatColor.BLUE + "Home Name: " + args[1]);
					sender.sendMessage(ChatColor.YELLOW + "Owner: " +  plugin.getRegions().get(position).getOwner().getName());
					sender.sendMessage(ChatColor.YELLOW + "Builders: ");
					for (int x = 0; x < plugin.getRegions().get(position).getBuilders().size(); x++)
						sender.sendMessage(ChatColor.YELLOW + plugin.getRegions().get(position).getBuilders().get(x).getName() + " ");
					return true;
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "No home named " + args[1] + " was found.");
					return true;
				}
			}
		}
		return false;
	}
}
