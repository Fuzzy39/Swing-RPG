package io.github.fuzzy39.swingRPG.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import io.github.fuzzy39.swingRPG.util.Direction;
import io.github.fuzzy39.swingRPG.util.Drawable;
import io.github.fuzzy39.swingRPG.util.Rectangle;
import io.github.fuzzy39.swingRPG.world.entities.Entity;
import io.github.fuzzy39.swingRPG.world.entities.EntityType;

public class World implements Drawable 
{

	private ArrayList<Screen> map;
	
	private Entity player;
	private ArrayList<Entity> entities;
	
	private Screen currentScreen;
	
	private boolean initialized = false;
	
	
	public World()
	{
		map = new ArrayList<Screen>();
		entities = new ArrayList<Entity>();
		
		currentScreen = null;
	
		player = null;
		

		
	}
	
	public ArrayList<Entity> getEntities()
	{
		return entities;
	}
	
	public boolean getInitialized()
	{
		return initialized;
	}
	
	public void initialize(Point playerStart, EntityType playerType, Screen initial)
	{
		if(initialized)
		{
			throw new IllegalStateException("World alread initialized!");
		}
		addScreen(initial);
		currentScreen = initial;
		
		
		if(collidesWithWorld(playerType.CollidesWithWalls(), new Rectangle(playerStart, playerType.getSize()), currentScreen))
		{
			//throw new IllegalArgumentException("Player is colliding with world on initialization.");
		}
		player = new Entity(currentScreen, playerStart, playerType);
		
		initialized = true;
		
	}
	
	
	@Override
	public void draw(Graphics g, ImageObserver o) 
	{
		
		currentScreen.draw(g,  o);
		
		for(Entity e: entities)
		{
			e.draw(g, o);
		}
		player.draw(g, o);

	}
	
	/**
	 * Adds a screen to the world map.
	 * @param toAdd
	 */
	public void addScreen(Screen toAdd)
	{
		if(toAdd == null)
		{
			throw new IllegalArgumentException();
		}
		
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
		
		if(!toAdd.ConnectedToWorld())
		{
			return;
		}
		
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
	
	
	/**
	 * User code shouldn't need to call this.
	 * Updates entities, tiles, players, etc.
	 */
	public void update()
	{
		player.update();
		
		if(!player.isInScreen(currentScreen))
		{
			for(Screen screen : map)
			{
				if(player.isInScreen(screen))
				{
					currentScreen = screen;
				}
			}
		}
		
		
		for(Entity e : entities)
		{
			e.update();
			
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
	
	public Entity getPlayer()
	{
		return player;
	}
	
	
	/**
	 * returns the screen with the particular world coordinates, if it exists.
	 * @param gridX
	 * @param gridY
	 * @return
	 */
	public Screen getScreen(int gridX, int gridY)
	{
		Point p = new Point(gridX, gridY);
		
		for(Screen check : map)
		{
			if(check.getWorldCoord().equals(p))
			{
				return check;
			}
		}
		
		return null;
	}
	

	public void addEntity(Entity e)
	{
		entities.add(e);
	}
	
	/**
	 * Returns whether a rectangle would collide with something on the world.
	 * if part of the rectangle intersects with space outside of any screen, this method will return true.
	 * if you are trying to check for collision on a screen not connected to the world grid, use the relative overload
	 * of this method.
	 * 
	 * @param absoluteBounds the absolute bounds being checked against.
	 * @return whether this rectangle contains anything that has collision (world borders and walls, but not entities.)
	 */
	public boolean collidesWithWorld(boolean collideWithWalls, Rectangle absoluteBounds)
	{
		// figure out the screen coords that this rectangle takes up.
		int minX = Math.floorDiv(absoluteBounds.getLeft(),Screen.SIZE.x);
		int maxX = absoluteBounds.getRight()/Screen.SIZE.x;

		int minY = Math.floorDiv(absoluteBounds.getTop(), Screen.SIZE.y);
		int maxY = absoluteBounds.getBottom()/Screen.SIZE.y;
		

		for(int x = minX; x<=maxX; x++ )
		{
			for(int y = minY; y<=maxY; y++)
			{
				Screen s = getScreen(x,y);
				if(s== null)
				{
					return true;
				}
				
				Rectangle relative = s.convertToRelative(absoluteBounds);
				if(collideWithWalls &&s.collidesWithLevel(relative))
				{
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	
	
	/**
	 * Returns whether a rectangle would collide with something on the world.
	 * if part of the rectangle intersects with space outside of any screen, this method will return true.
	 * unlike the absolute overload of this method, this method is able to check collision for screens that are not
	 * connected to the world grid.
	 * 
	 * @param relativeBounds The bounds of the rectangle in relation to relativeTo
	 * @param relativeTo The screen that the bounds are relative to. the top left corner of the screen is 0,0.
	 * @return whether this rectangle contains anything that has collision (world borders and walls, but not entities.)
	 */
	public boolean collidesWithWorld(boolean collideWithWalls, Rectangle relativeBounds, Screen relativeTo)
	{
		if(relativeTo.ConnectedToWorld())
		{
			Rectangle absBounds = relativeTo.convertToAbsolute(relativeBounds);
			return collidesWithWorld(collideWithWalls, absBounds);
		}		
		
		if(!new Rectangle(new Point(0,0), Screen.SIZE).contains(relativeBounds))
		{
			return true;
		}
		
		if(!collideWithWalls)
		{
			return false;
		}
		
		return relativeTo.collidesWithLevel(relativeBounds);
		
		
	}

}
