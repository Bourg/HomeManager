package com.github.SaxSalute.HomeManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteHomeCommandExecutor implements CommandExecutor
{
	private HomeManager plugin;
	
	public DeleteHomeCommandExecutor(HomeManager plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		Player player = null;
		if (sender instanceof Player)
			player = (Player) sender;
		
		if (command.getName().equalsIgnoreCase("deletehome"))
		{
			if (args.length == 1)
			{
				int position = -1;
				
				//Find the index of the region name in the regions ArrayList
				for (int x = 0; x < plugin.regions.size() && position == -1; x++)
				{
					//If the region name is the same as the one being looked for
					if (plugin.regions.get(x).getName().equalsIgnoreCase(args[0]))
					{
						position = x;
					}
				}
				
				//If the home exists
				if (position != -1)
				{
					if (!plugin.regions.get(position).getOwner().isOp() || (plugin.regions.get(position).getOwner().isOp() && (player == null || plugin.regions.get(position).getOwner().equals(player))))
					{
						//Delete it and notify the player
						plugin.regions.remove(position);
						sender.sendMessage(ChatColor.GREEN + "Home " + args[0] + " has been deleted.");
						return true;
					}
					
					else
					{
						sender.sendMessage(ChatColor.RED + "This home is owned by an Op. This home may only be deleted by the owner or by the server console.");
						return true;
					}
				}
				//If the home does not exist
				else
				{
					//Notify the player
					sender.sendMessage(ChatColor.RED + "Home " + args[0] + " was not found.");
					return true;
				}
			}
		}	
		return false;
	}
}
