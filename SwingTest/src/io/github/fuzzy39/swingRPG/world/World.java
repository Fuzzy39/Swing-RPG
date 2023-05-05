package io.github.fuzzy39.swingRPG.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import io.github.fuzzy39.swingRPG.util.Direction;
import io.github.fuzzy39.swingRPG.util.Drawable;

public class World implements Drawable 
{

	private ArrayList<Screen> map;
	private Screen currentScreen;
	private Player player;
	
	public World(Point playerStart, Screen startingScreen)
	{
		map = new ArrayList<Screen>();
		currentScreen = startingScreen;
		map.add(currentScreen);
		
		player = new Player(playerStart.x, playerStart.y);
	}
	
	@Override
	public void draw(Graphics g, ImageObserver o) 
	{
		
		currentScreen.draw(g,  o);
		player.draw(g, o);

	}
	
	/**
	 * Adds a screen to the world map.
	 * @param toAdd
	 */
	public void addScreen(Screen toAdd)
	{
		Point coord = toAdd.getWorldCoord();
		if(coord!= null)
		{
			for(Screen s : map)
			{
				if(coord.equals(s.getWorldCoord()))
				{
					throw new IllegalArgumentException("World already has screen with existing coordinates at: "+coord);
				}
				
				
			}
		}
		
		map.add(toAdd);
		
		// find the neighbors and hook everything up
		for(Direction d : Direction.values())
		{
			Point dPoint = d.toPoint();
			Point lookingFor = new Point(coord);
			lookingFor.x+=dPoint.x;
			lookingFor.y+=dPoint.y;
			
			for(Screen s: map)
			{
				if(lookingFor.equals(s.getWorldCoord()))
				{
					toAdd.setConnection(s, d);
					s.setConnection(toAdd, d.opposite());
				}
			}
		}
			
	}
	
	
	/**
	 * Returns whether a given screen has a connection on a particular face.
	 * @param s
	 * @param d
	 * @return
	 */
	public boolean hasScreenConnection(Screen s, Direction d)
	{
		Point coord = s.getWorldCoord();
		if(coord == null)
		{
			return false;
		}
		
		Point toAdd = d.toPoint();
		coord.x += toAdd.x;
		coord.y += toAdd.y;
		
		for(Screen screen : map)
		{
			if(coord.equals(screen.getWorldCoord()))
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	public void update()
	{
		Screen s = player.update(currentScreen);
		if(s!= null)
		{
			setCurrentScreen(s);
		}
		
		currentScreen.update(player);
		
	}
	
	public Screen getCurrentScreen()
	{
		return currentScreen;
	}
	
	
	/**
	 * Sets the current screen. the player is not repositioned.
	 * @param s
	 */
	public void setCurrentScreen(Screen s)
	{
		boolean has = false;
		for(Screen check : map)
		{
			if(check==s)
			{
				has = true;
			}
		}
		
		if(!has)
		{
			throw new IllegalArgumentException("Screen must be attached to map");
			
		}
		
		currentScreen = s;
	}
	
	public Player getPlayer()
	{
		return player;
	}

}
