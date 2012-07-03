package com.github.SaxSalute.HomeManager;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeManager extends JavaPlugin
{
	//The command executors
	private CreateHomeCommandExecutor createHomeExecutor;
	private DeleteHomeCommandExecutor deleteHomeExecutor;
	private AddBuilderCommandExecutor addBuilderExecutor;
	//A map of Players and the two locations that they have selected
	protected Map<Player, Location[]> selectedLocations;
	protected SaveLoad saveload;
	//An arraylist of all regions
	protected ArrayList<Region> regions;
	
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
		
		getCommand("createhome").setExecutor(createHomeExecutor);
		getCommand("deletehome").setExecutor(deleteHomeExecutor);
		getCommand("addbuilder").setExecutor(addBuilderExecutor);
		
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
}
