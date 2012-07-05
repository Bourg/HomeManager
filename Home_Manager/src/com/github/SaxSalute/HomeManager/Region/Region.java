package com.github.SaxSalute.HomeManager.Region;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Region
{
	private int[] pointA, pointB;
	String regionName;
	Player regionOwner;
	Location locationA, locationB;
	ArrayList<Player> builders;
	
	//WARNING! DO NOT USE TO SAVE LOCATIONS, ONLY FOR ONE TIME USES!
	public Region(Location a, Location b)
	{
		locationA = a;
		locationB = b;
		
		pointA = new int[3];
		pointB = new int[3];
		
		pointA[0] = a.getBlockX();
		pointA[1] = a.getBlockY();
		pointA[2] = a.getBlockZ();
		
		pointB[0] = b.getBlockX();
		pointB[1] = b.getBlockY();
		pointB[2] = b.getBlockZ();
	}
	
	public Region(Location a, Location b, String name, Player owner)
	{
		locationA = a;
		locationB = b;
		
		pointA = new int[3];
		pointB = new int[3];
		
		pointA[0] = a.getBlockX();
		pointA[1] = a.getBlockY();
		pointA[2] = a.getBlockZ();
		
		pointB[0] = b.getBlockX();
		pointB[1] = b.getBlockY();
		pointB[2] = b.getBlockZ();
		
		regionOwner = owner;
		regionName = name;
		
		builders = new ArrayList<Player>();
	}
	
	public String getName()
	{
		return regionName;
	}
	
	public Location getLocationA()
	{
		return locationA;
	}
	
	public Location getLocationB()
	{
		return locationB;
	}
	
	
	public int getMinX()
	{
		return (pointA[0] < pointB[0] ? pointA[0] : pointB[0]);
	}
	
	public int getMinY()
	{
		return (pointA[1] < pointB[1] ? pointA[1] : pointB[1]);
	}
	
	public int getMinZ()
	{
		return (pointA[2] < pointB[2] ? pointA[2] : pointB[2]);
	}
	
	public int getMaxX()
	{
		return (pointA[0] > pointB[0] ? pointA[0] : pointB[0]);
	}
	
	public int getMaxY()
	{
		return (pointA[1] > pointB[1] ? pointA[1] : pointB[1]);
	}
	
	public int getMaxZ()
	{
		return (pointA[2] > pointB[2] ? pointA[2] : pointB[2]);
	}
	
	public Player getOwner()
	{
		return regionOwner;
	}
	
	public boolean overlap(Region r)
	{
		return !(this.getMinX() > r.getMaxX() || this.getMaxX() < r.getMinX() || this.getMinY() > r.getMaxY() || this.getMaxY() < r.getMinY() || this.getMinZ() > r.getMaxZ() || this.getMaxZ() < r.getMinZ());
	}
	
	public boolean overlap(ArrayList<Region> regions)
	{
		if (regions == null || regions.size() == 0)
			return false;
		for (Region r : regions)
			if (this.overlap(r))
				return true;
		return false;
	}
	
	public boolean containsLocation(Location loc)
	{		
		return this.overlap(new Region(loc, loc));
	}
	
	public Region addBuilder(Player b)
	{
		builders.add(b);
		return this;
	}
	
	public Region removeBuilder(String s)
	{
		for (int x = 0; x < builders.size(); x++)
			if (builders.get(x).getName().equalsIgnoreCase(s))
			{
				builders.remove(x);
				break;
			}
		return this;
	}
	
	public Region removeBuilder(Player p)
	{
		for (int x = 0; x < builders.size(); x++)
			if (builders.get(x).getName().equalsIgnoreCase(p.getName()))
			{
				builders.remove(x);
				break;
			}
		return this;
	}
	
	public ArrayList<Player> getBuilders()
	{
		return builders;
	}
	
	public Region setOwner(Player p)
	{
		regionOwner = p;
		return this;
	}
}
