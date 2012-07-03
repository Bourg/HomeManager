package com.github.SaxSalute.HomeManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
		
		if (command.getName().equalsIgnoreCase("addbuilder"))
		{		
			if (args.length == 2)
			{
				Player tempPlayer = null;
				for (Player p : plugin.getServer().getOnlinePlayers())
					if (p.getName().equalsIgnoreCase(args[1]))
						tempPlayer = p;
				
				if (tempPlayer != null)
				{
					Region tempRegion = null;
					int x = 0;
					for (; x < plugin.regions.size(); x++)
						if (plugin.regions.get(x).getName().equalsIgnoreCase(args[0]))
						{
							tempRegion = plugin.regions.get(x);
							break;
						}
					
					if (tempRegion != null)
					{		
						if (player == null || plugin.regions.get(x).getOwner().getName().equalsIgnoreCase(player.getName()))
						{
							
							
							plugin.regions.get(x).addBuilder(plugin.getServer().getPlayer(args[1]));
							sender.sendMessage(ChatColor.GREEN + args[1] + " is now allowed to build in home " + args[0]);
							return true;
						}
						else
						{
							sender.sendMessage(ChatColor.RED + "You must be the home owner to add a builder.");
							return true;
						}
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "Home " + args[0] + " was not found.");
						return true;
					}
				}
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