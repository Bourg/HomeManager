package com.github.SaxSalute.HomeManager;

import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.util.ArrayList;

public class SaveLoad
{
	private HomeManager plugin;
	private Yaml yaml;
	
	public SaveLoad(HomeManager plugin)
	{
		this.plugin = plugin;
		yaml = new Yaml();
		
		File file = new File("regions.yml");
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
				plugin.getLogger().info("regions.yml not found. regions.yml has been created.");
			}
			catch (IOException e) {
				plugin.getLogger().info("An error has occured in the SaveLoad constructor.");
				plugin.getLogger().info(e.getMessage());
			}
		}
	}
	
	public void saveData()
	{
		try 
		{
			BufferedWriter out = new BufferedWriter(new FileWriter("regions.yml"));
			out.write(yaml.dump(plugin.regions));
			out.close();
		}
		catch (IOException e)
		{
			plugin.getLogger().info("An error has occured in the loadData method.");
			plugin.getLogger().info(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadData()
	{
		try
		{
			plugin.regions = (ArrayList<Region>) yaml.load(new FileInputStream(new File("regions.yml")));
		}
		catch (IOException e)
		{
			plugin.getLogger().info("An error has occured in the loadData method.");
			plugin.getLogger().info(e.getMessage());
		}
	}
}
