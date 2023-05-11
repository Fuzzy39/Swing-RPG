package io.github.fuzzy39.swingRPG.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import io.github.fuzzy39.swingRPG.util.Direction;
import io.github.fuzzy39.swingRPG.util.Drawable;
import io.github.fuzzy39.swingRPG.util.Rectangle;
import io.github.fuzzy39.swingRPG.world.entities.Entity;
import io.github.fuzzy39.swingRPG.world.tiles.Tile;
import io.github.fuzzy39.swingRPG.world.tiles.TileType;

/**
 * A screen represents a full 16 by 8 grid of tiles, displayed all at once.
 * @author Fuzzy39
 *
 */
public class Screen implements Drawable
{

	// screen size is 800 x 400
	// tiles are 50
	// 16 by 8
	
	/**
	 * The size of a tile, in pixels. Can't change it now, sorry.
	 */
	public final static int TILE_SIZE = 50;
	
	/**
	 * The size, in pixels, of a screen. Sorry. for the time being, it's hard baked.
	 */
	public final static Point SIZE = new Point(16*TILE_SIZE, 8*TILE_SIZE); 
	
	private World world;
	
	private BufferedImage backgroundTexture;
	private Tile[][] level;
	private Point coords; // the screen's location on the world map. if null, this screen is disconnected to everything else.

	private Screen[] connections; // does the screen have connections up, down, left, and right?
	
	
	/**
	 * 
	 * @param levelMap a 16 by 8 TileType array.
	 */
	public Screen(TileType[][] levelMap, World w)
	{
	
		world = w;
		
		if(levelMap.length!=16)
		{
			System.out.println("X map should be 16, not"+levelMap.length);
		}
		
		if(levelMap[0].length!=8)
		{
			System.out.println("Y map should be 8, not "+levelMap[0].length); 
		}
		level = new Tile[800/TILE_SIZE][400/TILE_SIZE];
		
		for(int x = 0; x<800/TILE_SIZE; x++)
		{
			for(int y=0; y<400/TILE_SIZE; y++)
			{
				
				createTile(levelMap, x, y);
			}
		}
		
		connections = new Screen[4];
		
		
	}
	
	/**
	 * 
	 * @param x the x coordinate on the world grid.
	 * @param y the y coordinate on the world grid.
	 * @param levelMap a 16 by 8 TileType Array that describes the contents of the screen.
	 */
	public Screen(int x, int y, TileType[][] levelMap, World w)
	{
		this(levelMap, w);
		coords = new Point(x,y);
	}
	
	

	
	/**
	 * Set the backgound of this screen, rendered behind any tiles.
	 * @param texture
	 */
	public void setBackground(BufferedImage texture)
	{
		backgroundTexture = texture;
	}
	
	
	/**
	 * Returns the cooridates on the world map of this screen.
	 * If the screen has not been added to the map, this may not be valid.
	 * @return
	 */
	public Point getWorldCoord()
	{
		if(coords == null)
		{
			return null;
		}
		
		return new Point(coords);	
	}
	
	
	/**
	 * 
	 * @return
	 */
	public Rectangle getGlobalBounds()
	{
		if(getWorldCoord() == null)
		{
			return null;
		}
		
		Point loc = new Point(coords.x*SIZE.x, coords.y*SIZE.y);
		
		return new Rectangle(loc, SIZE);
		
	}
	
	/**
	 * 
	 * @return Whether this screen has global coordinates.
	 */
	public boolean ConnectedToWorld()
	{
		return getWorldCoord()!=null;
	}
	
	
	
	
	
	/**
	 * Returns whether this screen has another screen connected to it in the specified direction.
	 * @param d the direction out of this screen that the supposed connection would be.
	 * @return true if such a connection exists, false otherwise.
	 */
	public boolean hasConnection(Direction d)
	{
		return connections[d.ordinal()]!=null;
	}
	


	
	
	/**
	 * Returns whether a given rectangle of spaced is occupied by any non-passable (solid) tiles.
	 * @param rect
	 * @return
	 */
	public boolean collidesWithLevel(Rectangle rect)
	{
		
		//rect = rect.Intersection(new Rectangle(0,0, SIZE.x, SIZE.y));
		if (rect == null)
		{
			return false;
		}
		
		Point topLeft = rect.location;
		int minX = topLeft.x/TILE_SIZE;
		int minY = topLeft.y/TILE_SIZE;
		int maxX = (rect.getRight()-1)/TILE_SIZE;
		int maxY = (rect.getBottom()-1)/TILE_SIZE;
		
		minX = minX<0?0:minX;
		minY = minY<0?0:minY;
		maxX = maxX>15?15:maxX;
		maxY = maxY>7?7:maxY;
		
		for(int x = minX; x<=maxX; x++)
		{
			for(int y = minY; y<=maxY; y++)
			{
				
				if(!isPassable(x,y))
				{
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	
	/**
	 * Convert rectangles from a coordinate system where 0,0 is the top left of this screen
	 * to one where 0,0 is the top left of the screen with world grid coordinates of 0,0.
	 * May throw IllegalStateException if this screen is not connected to the world.
	 * @param rect
	 * @return
	 */
	public Rectangle convertToAbsolute(Rectangle rect)
	{
		if(!ConnectedToWorld())
		{
			throw new IllegalStateException(
					"Screen cannot convert coordinates when it is detached from the world grid.");
			
		}
		
		Point loc = new Point(rect.location);
		
		loc.x+=coords.x*SIZE.x;
		loc.y+=coords.y*SIZE.y;
		
		return new Rectangle(loc, rect.size);
	}
	
	
	/**
	 * Does the inverse of convertToAbsolute.
	 * May throw IllegalStateException if this screen is not connected to the world.
	 * @param rect
	 * @return
	 * @see convertToAbsolute
	 */
	public Rectangle convertToRelative(Rectangle rect)
	{
		if(!ConnectedToWorld())
		{
			throw new IllegalStateException(
					"Screen cannot convert coordinates when it is detached from the world grid.");
			
		}
		
		Point loc = new Point(rect.location);
		
		loc.x-=coords.x*SIZE.x;
		loc.y-=coords.y*SIZE.y;
		
		return new Rectangle(loc, rect.size);
	}
	
	
	/**
	 * Get the tile with particular coordinates on this screen.
	 * @param x
	 * @param y
	 * @return
	 */
	public Tile getTile(int x, int y) {return level[x][y];}
	
	/**
	 * Draws the screen. Who could've guessed?
	 * User code probably shouldn't call this method.
	 */
	@Override
	public void draw(Graphics g, ImageObserver o) 
	{
		if(backgroundTexture != null)
		{
			g.drawImage(backgroundTexture, 0, 0, 800, 400, o);
		}
		
		for(Tile[] tiles : level)
		{
			for(Tile t : tiles)
			{
				if(t== null)
				{
					continue;
				}
				
				t.draw(g, o);
			}
		}
		
	}
	
	public World getWorld()
	{
		return world;
	}

	/**
	 * Gets the connection
	 * @param d
	 */
	public Screen getConnection(Direction d)
	{
		return connections[d.ordinal()];
	}
	
	

	
	// Private/protected methods
	
	private void createTile(TileType[][] levelMap, int x, int y)
	{
		if(levelMap[x][y] == null)
		{
			level[x][y] = null;
			return;
		}
		
		
		level[x][y] = new Tile(this, levelMap[x][y], TILE_SIZE*x, TILE_SIZE*y);
		
	}
	
	private boolean isPassable(int x, int y)
	{
		Tile t = level[x][y];
		if(t==null)
		{
			return true;
		}
		
		return t.config.isPassable();
	}
	
	
	
	
	protected void setConnection(Screen s, Direction d)
	{
		connections[d.ordinal()] = s;
	}
	
	/**
	 * updates all tiles
	 * @param p the player
	 * @param connections the connections to this screen on 4 cardinal points up down left right
	 */
	protected void update(Entity p)
	{
	
		for(Tile[] tiles : level)
		{
			for(Tile t : tiles)
			{
				if(t==null) {continue;}
				
				t.update(p);
			}
		}
		
		
		
		
	}
	
}
