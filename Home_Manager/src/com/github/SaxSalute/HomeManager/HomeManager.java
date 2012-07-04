package com.github.SaxSalute.HomeManager;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;

import com.github.SaxSalute.HomeManager.CommandExecutors.AddBuilderCommandExecutor;
import com.github.SaxSalute.HomeManager.CommandExecutors.CreateHomeCommandExecutor;
import com.github.SaxSalute.HomeManager.CommandExecutors.DeleteHomeCommandExecutor;
import com.github.SaxSalute.HomeManager.CommandExecutors.ListHomesCommandExecutor;
import com.github.SaxSalute.HomeManager.Listeners.BlockBreakListener;
import com.github.SaxSalute.HomeManager.Listeners.WandClickListener;
import com.github.SaxSalute.HomeManager.Region.Region;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeManager extends JavaPlugin
{
	//The command executors
	private CreateHomeCommandExecutor createHomeExecutor;
	private DeleteHomeCommandExecutor deleteHomeExecutor;
	private AddBuilderCommandExecutor addBuilderExecutor;
	private ListHomesCommandExecutor listHomesExecutor;
	//A map of Players and the two locations that they have selected
	private Map<Player, Location[]> selectedLocations;
	//An arraylist of all regions
	private ArrayList<Region> regions;
	
	public void onEnable()
	{
		//Creates selectedLocations
		selectedLocations = new HashMap<Player, Location[]>();
		//Create regions
		regions = new ArrayList<Region>();
		
		//Creates the command executor and register the commands
		createHomeExecutor = new CreateHomeCommandExecutor(this);
		deleteHomeExecutor = new DeleteHomeCommandExecutor(this);
		addBuilderExecutor = new AddBuilderCommandExecutor(this);
		listHomesExecutor = new ListHomesCommandExecutor(this);
		
		getCommand("createhome").setExecutor(createHomeExecutor);
		getCommand("deletehome").setExecutor(deleteHomeExecutor);
		getCommand("addbuilder").setExecutor(addBuilderExecutor);
		getCommand("listhomes").setExecutor(listHomesExecutor);
		
		//Registers the wand click listener
		getServer().getPluginManager().registerEvents(new WandClickListener(this), this);
		//Registers the block break listener
		getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
		
		//saveload = new SaveLoad(this);
		//saveload.loadData();
	}
	
	public void onDisable()
	{
		getLogger().info("Home Manager has been disabled");
	}
	
	public Map<Player, Location[]> getSelectedLocations()
	{
		return selectedLocations;
	}
	
	public ArrayList<Region> getRegions()
	{
		return regions;
	}
	
	public void addSelectedLocation(Player p)
	{
		selectedLocations.put(p, new Location[2]);
	}
	
	public void setSelectedLocation(Player p, int i, Location l)
	{
		selectedLocations.get(p)[i] = l;
	}
	
	public void addRegion(Region r)
	{
		if (regions == null)
			regions = new ArrayList<Region>();
		regions.add(r);
	}
	
	public void setRegion(int i, Region r)
	{
		regions.set(i, r);
	}
	
	public void removeRegion(int i)
	{
		regions.remove(i);
	}
}
