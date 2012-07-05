package com.github.SaxSalute.HomeManager.CommandExecutors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.SaxSalute.HomeManager.HomeManager;
import com.github.SaxSalute.HomeManager.Region.Region;

public class AddBuilderCommandExecutor implements CommandExecutor
{
	private HomeManager plugin;
	
	public AddBuilderCommandExecutor(HomeManager plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		Player player = null;
		if (sender instanceof Player)
			player = (Player) sender;
		
		//If the command name is addbuilder
		if (command.getName().equalsIgnoreCase("addbuilder"))
		{		
			//If two arguments are supplied
			if (args.length == 2)
			{
				Player tempPlayer = null;
				for (Player p : plugin.getServer().getOnlinePlayers())
					if (p.getName().equalsIgnoreCase(args[1]))
						tempPlayer = p;
				
				//If the target player is online
				if (tempPlayer != null)
				{
					Region tempRegion = null;
					int x = 0;
					for (; x < plugin.getRegions().size(); x++)
						if (plugin.getRegions().get(x).getName().equalsIgnoreCase(args[0]))
						{
							tempRegion = plugin.getRegions().get(x);
							break;
						}
					
					//If the target region exists
					if (tempRegion != null)
					{
						//If the command sender is the console or the home owner
						if (player == null || plugin.getRegions().get(x).getOwner().getName().equalsIgnoreCase(sender.getName()))
						{
							for (Player p : plugin.getRegions().get(x).getBuilders())
							{
								//If the player is already a builder
								if (p.getName().equalsIgnoreCase(args[1]))
								{
									sender.sendMessage(ChatColor.RED + args[1] + " is already a builder in home " + args[0]);
									return true;
								}
								//If the player is the home owner
								else if (p.getName().equalsIgnoreCase(plugin.getRegions().get(x).getOwner().getName()))
								{
									sender.sendMessage(ChatColor.RED + "You cannot add the home owner as a builder");
									return true;
								}
							}
							
							//If all above condidion are true
							plugin.setRegion(x, plugin.getRegions().get(x).addBuilder(plugin.getServer().getPlayer(args[1])));
							sender.sendMessage(ChatColor.GREEN + args[1] + " is now allowed to build in home " + args[0]);
							tempPlayer.sendMessage(ChatColor.GREEN + "You are now allowed to build in home " + args[0]);
							return true;
						}
						
						//If the command sender is not the owner or the consolse
						else
						{
							sender.sendMessage(ChatColor.RED + "You must be the home owner to add a builder.");
							return true;
						}
					}
					//If the target region does not exist
					else
					{
						sender.sendMessage(ChatColor.RED + "Home " + args[0] + " was not found.");
						return true;
					}
				}
				//If the target player is not online
				else
				{
					sender.sendMessage(ChatColor.RED + "Player " + args[1] + " is not online. Please try again when the player is online.");
					return true;
				}
			}
		}
		return false;
	}
	
}
