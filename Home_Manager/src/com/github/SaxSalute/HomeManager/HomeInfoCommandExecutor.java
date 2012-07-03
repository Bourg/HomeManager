package com.github.SaxSalute.HomeManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HomeInfoCommandExecutor implements CommandExecutor
{
	private HomeManager plugin;
	
	public HomeInfoCommandExecutor(HomeManager plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		/*
		 * homeinfo
		 */
		if (command.getName().equalsIgnoreCase("homeinfo"))
		{
			//If there are no homes
			if (plugin.regions == null || plugin.regions.size() == 0)
			{
				sender.sendMessage(ChatColor.YELLOW + "No homes have been created");
				return true;
			}
			
			//If either one or zero arguments have been supplied
			if (args.length <= 2)
			{
				//If no argument has been supplied or the argument supplied was "all"
				if (args.length == 0 || args[0].equalsIgnoreCase("all"))
				{
					sender.sendMessage(ChatColor.BLUE + "Existing Homes: " + plugin.regions.size());
		
					//If there are homes
					for (Region r : plugin.regions)
					{
						sender.sendMessage(ChatColor.YELLOW + r.getName());
					}
					return true;
				}
				if (args.length == 2 && args[0].equalsIgnoreCase("details"))
				{
					int position = -1;
					
					//Find the index of the region name in the regions ArrayList
					for (int x = 0; x < plugin.regions.size() && position == -1; x++)
					{
						if (plugin.regions.get(x).getName().equalsIgnoreCase(args[1]))
						{
							position = x;
						}
					}
					
					if (position != -1)
					{
						sender.sendMessage(ChatColor.BLUE + "Home Name: " + args[1]);
						sender.sendMessage(ChatColor.YELLOW + "Owner: " +  plugin.regions.get(position).getOwner().getName());
						return true;
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "No home named " + args[1] + " was found.");
						return true;
					}
				}
			}
			
			//If more than two arguments have been supplied
			else
			{
				sender.sendMessage(ChatColor.RED + "/homeinfo takes one of the following modifiers:\nall - List all existing homes");
			}
		}
		return false;
	}
	
}
