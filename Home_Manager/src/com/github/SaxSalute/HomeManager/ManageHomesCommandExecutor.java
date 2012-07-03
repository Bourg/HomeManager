package com.github.SaxSalute.HomeManager;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ManageHomesCommandExecutor implements CommandExecutor
{
	private HomeManager plugin;
	
	public ManageHomesCommandExecutor(HomeManager plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		Player player = null;
		if (sender instanceof Player)
			player = (Player) sender;
		
		if (command.getName().equalsIgnoreCase("managehomes"))
		{
			if (args.length == 2)
			{
				if (args[0].equalsIgnoreCase("create"))
				{
					//If the command sender is a player, not the console
					if (player != null)
					{
						//If the player has already selected two locations
						if (plugin.selectedLocations.get(player) != null && plugin.selectedLocations.get(player)[0] != null && plugin.selectedLocations.get(player)[1] != null)
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
							//If the home name is unique
							if (position == -1)
							{
								Region tempRegion = new Region(plugin.selectedLocations.get(player)[0], plugin.selectedLocations.get(player)[1], args[1], player);
								//If regions is null
								if (plugin.regions == null)
								{
									plugin.regions = new ArrayList<Region>();
								}
								//If the home doesn't overlap another home or there are no other homes
								if (plugin.regions.size() == 0 || !(tempRegion.overlap(plugin.regions)))
								{
									plugin.regions.add(tempRegion);
									sender.sendMessage(ChatColor.GREEN + "Region " + args[1] + " has been created successfully!");
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
								sender.sendMessage(ChatColor.RED + "The home name " + args[1] + " is taken.");
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
				
				else if (args[0].equalsIgnoreCase("delete"))
				{
					int position = -1;
					
					//Find the index of the region name in the regions ArrayList
					for (int x = 0; x < plugin.regions.size() && position == -1; x++)
					{
						//If the region name is the same as the one being looked for
						if (plugin.regions.get(x).getName().equalsIgnoreCase(args[1]))
						{
							position = x;
						}
					}
					
					//If the home exists
					if (position != -1)
					{
						//Delete it and notify the player
						plugin.regions.remove(position);
						sender.sendMessage(ChatColor.GREEN + "Home " + args[1] + " has been deleted.");
						return true;
					}
					//If the home does not exist
					else
					{
						//Notify the player
						sender.sendMessage(ChatColor.RED + "Home" + args[1] + " was not found.");
						return true;
					}
				}
				//If an invalid args[0] is supplied
				else
				{
					sender.sendMessage(ChatColor.RED + args[0] + " is an invalid argument. Valid arguments include:\ncreate [name] - Create a new home.\ndelete [name] - Delete an existing home.");
					return true;
				}
			}
			//If a number of arguments other than 2 is supplied
			else
			{
				sender.sendMessage(ChatColor.RED + "/managehomes takes at least two arguments.");
			}			
		}
		
		return false;
	}
	
}
