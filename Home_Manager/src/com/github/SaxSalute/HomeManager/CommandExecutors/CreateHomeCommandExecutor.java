package com.github.SaxSalute.HomeManager.CommandExecutors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.SaxSalute.HomeManager.HomeManager;
import com.github.SaxSalute.HomeManager.Region.Region;

public class CreateHomeCommandExecutor implements CommandExecutor
{
	private HomeManager plugin;
	
	public CreateHomeCommandExecutor(HomeManager plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		Player player = null;
		if (sender instanceof Player)
			player = (Player) sender;
		
		if (command.getName().equalsIgnoreCase("createhome"))
		{
			if (args.length == 1)
			{
				
				if (player != null)
				{
					//If the player has already selected two locations
					if (plugin.getSelectedLocations().get(player) != null && plugin.getSelectedLocations().get(player)[0] != null && plugin.getSelectedLocations().get(player)[1] != null)
					{
						int position = -1;
						
						//Find the index of the region name in the regions ArrayList
						for (int x = 0; x < plugin.getRegions().size() && position == -1; x++)
						{
							if (plugin.getRegions().get(x).getName().equalsIgnoreCase(args[0]))
							{
								position = x;
							}
						}
						//If the home name is unique
						if (position == -1)
						{
							Region tempRegion = new Region(plugin.getSelectedLocations().get(player)[0], plugin.getSelectedLocations().get(player)[1], args[0], player);
							//If the home doesn't overlap another home or there are no other homes
							if (plugin.getRegions().size() == 0 || !(tempRegion.overlap(plugin.getRegions())))
							{
								plugin.addRegion(tempRegion);
								sender.sendMessage(ChatColor.GREEN + "Region " + args[0] + " has been created successfully!");
								return true;
							}
							//If the home overlaps another home
							else
							{
								sender.sendMessage("Two homes can not overlap.");
								return true;
							}
						}
						//If the home name is taken
						else
						{
							sender.sendMessage(ChatColor.RED + "The home name " + args[0] + " is taken.");
							return true;
						}
					}
					//If the player has not selected two locations using the feather
					else
					{
						sender.sendMessage(ChatColor.RED + "You must select two locations using the feather before creating a home.");
						return true;
					}
				}
				//If the command sender is the console
				else
				{
					sender.sendMessage(ChatColor.RED + "Homes can only be created by players in game.");
					return true;
				}
			}
		}
		return false;
	}
}
