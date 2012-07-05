package com.github.SaxSalute.HomeManager.CommandExecutors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.SaxSalute.HomeManager.HomeManager;
import com.github.SaxSalute.HomeManager.Region.Region;

public class TransferOwnerCommandExecutor implements CommandExecutor
{
	private HomeManager plugin;
	
	public TransferOwnerCommandExecutor(HomeManager plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		Player player = null;
		if (sender instanceof Player)
			player = (Player) sender;
		
		//If the command name is transferowner
		if (command.getName().equalsIgnoreCase("transferowner"))
		{
			//If two arguments are supplied
			if (args.length == 2)
			{
				Region tempRegion = null;
				int regionNumber = 0;
				for (; regionNumber < plugin.getRegions().size(); regionNumber++)
					if (plugin.getRegions().get(regionNumber).getName().equalsIgnoreCase(args[0]))
						tempRegion = plugin.getRegions().get(regionNumber);
				
				//If the region exists
				if (tempRegion != null)
				{
					Player tempPlayer = null;
					for (Player p : plugin.getServer().getOnlinePlayers())
						if (p.getName().equalsIgnoreCase(args[1]))
							tempPlayer = p;
					
					//If the player exists
					if (tempPlayer != null)
					{
						//If the command sender is the home owner or the console
						if (player == null || tempRegion.getOwner().getName().equalsIgnoreCase(sender.getName()))
						{
							//If the target owner is not already the owner
							if (!tempRegion.getOwner().getName().equalsIgnoreCase(args[0]))
							{
								plugin.setRegion(regionNumber, plugin.getRegions().get(regionNumber).setOwner(tempPlayer).removeBuilder(args[1]));
								sender.sendMessage(ChatColor.GREEN + "The owner of home " + args[0] + " is now " + args[1]);
								tempPlayer.sendMessage(ChatColor.GREEN + "You are now the owner of home " + args[0]);
								return true;
							}
							
							//If the target owner is already the owner
							else
							{
								sender.sendMessage(ChatColor.RED + "You can not transfer ownership to the owner");
								return true;
							}
						}
						
						//If the command sender is not the home owner or the console
						else
						{
							sender.sendMessage(ChatColor.RED + "You must be the home owner to transfer ownership");
							return true;
						}
					}
					
					//If the player doesn't exist
					else
					{
						sender.sendMessage(ChatColor.RED + "Player " + args[1] + " is not online");
						return true;
					}
				}
				
				//If the region doesn't exist
				else
				{
					sender.sendMessage(ChatColor.RED + "Home " + args[0] + " does not exist");
					return true;
				}
			}
		}
		
		return false;
	}
	
}
