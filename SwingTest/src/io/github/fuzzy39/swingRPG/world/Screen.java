package io.github.fuzzy39.swingRPG.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import io.github.fuzzy39.swingRPG.util.Direction;
import io.github.fuzzy39.swingRPG.util.Drawable;
import io.github.fuzzy39.swingRPG.util.Rectangle;
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
	
	
	private BufferedImage backgroundTexture;
	private Tile[][] level;
	private Point coords; // the screen's location on the world map. if null, this screen is disconnected to everything else.

	private Screen[] connections; // does the screen have connections up, down, left, and right?
	
	
	/**
	 * 
	 * @param levelMap a 16 by 8 TileType array.
	 */
	public Screen(TileType[][] levelMap)
	{
	
		
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
	
	public Screen(int x, int y, TileType[][] levelMap)
	{
		this(levelMap);
		coords = new Point(x,y);
	}
	
	
	private void createTile(TileType[][] levelMap, int x, int y)
	{
		if(levelMap[x][y] == null)
		{
			level[x][y] = null;
			return;
		}
		
		
		level[x][y] = new Tile(levelMap[x][y], TILE_SIZE*x, TILE_SIZE*y);
		
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
	 * Returns the screen that should be transitioned to should an entity exist with the given bounds.
	 * @param rect
	 * @return Returns null if no transition should occur.
	 */
	public Direction shouldTransitionTo(Rectangle rect)
	{
		
		if(rect.getBottom()<0)
		{
			return Direction.UP;
		}
		
		if(rect.getTop()>SIZE.y)
		{
			return Direction.DOWN;
		}
		
		if(rect.getRight()<0)
		{
			return Direction.LEFT;
		}
		
		if(rect.getLeft()>SIZE.x)
		{
			return Direction.RIGHT;
		}
		
		return null;
		
	}
	
	
	public boolean hasConnection(Direction d)
	{
		return connections[d.ordinal()]!=null;
	}
	
	
	public static Rectangle boundsOnOtherScreen(Direction d, Rectangle prev)
	{
		
		
		Point toAdd = d.toPoint();
		toAdd.x *= -SIZE.x;
		toAdd.y *= -SIZE.y;
		
		int x = prev.location.x+toAdd.x;
		int y = prev.location.y+toAdd.y;
		
		return new Rectangle(x, y, prev.size.x, prev.size.y);		
	
	}

	
	
	/**
	 * Returns whether a given rectangle of spaced is occupied by any non-passable (solid) tiles.
	 * @param rect
	 * @return
	 */
	public boolean isValidPosition(Rectangle rect)
	{
		Rectangle screenBounds = new Rectangle(0,0, SIZE.x,SIZE.y);
		
		if(!screenBounds.contains(rect))
		{	
			Direction d = Direction.UP;
			if(hasConnection(d) && rect.getTop()<0)
			{
				//return getConnection(d)
				//		.isValidPosition(boundsOnOtherScreen(d, rect));
				
				return true;
			}
			
			d = Direction.DOWN;
			if(hasConnection(d) && rect.getBottom()>SIZE.y)
			{
				//return getConnection(d)
				//		.isValidPosition(boundsOnOtherScreen(d, rect));
				return true;
			}
			
			d = Direction.LEFT;
			if(hasConnection(Direction.LEFT) && rect.getLeft()<0)
			{
				//return getConnection(d)
				//		.isValidPosition(boundsOnOtherScreen(d, rect));
				return true;
			}
			
			d = Direction.RIGHT;
			if(hasConnection(d) && rect.getRight()>SIZE.x)
			{
				//return getConnection(d)
				//		.isValidPosition(boundsOnOtherScreen(d, rect));
				return true;
			}
			
			return false;
		}
		
		Point topLeft = rect.location;
		int minX = topLeft.x/TILE_SIZE;
		int minY = topLeft.y/TILE_SIZE;
		int maxX = (rect.getRight()-1)/TILE_SIZE;
		int maxY = (rect.getBottom()-1)/TILE_SIZE;
		
		//System.out.println("from: "+ topLeft + "to:" +new Point(maxX, maxY));
		
		for(int x = minX; x<=maxX; x++)
		{
			for(int y = minY; y<=maxY; y++)
			{
				
				if(!isPassable(x,y))
				{
					return false;
				}
			}
		}
		
		return true;
		
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
	
	
	public Tile getTile(int x, int y) {return level[x][y];}
	
	/**
	 * Draws the screen. Who could've guessed?
	 */
	@Override
	public void draw(Graphics g, ImageObserver o) 
	{
		if(backgroundTexture != null)
		{
			g.drawImage(backgroundTexture, 0, 0, 800, 400, Color.white, o);
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
	
	
	/**
	 * Gets the connection
	 * @param d
	 */
	public Screen getConnection(Direction d)
	{
		return connections[d.ordinal()];
	}
	
	protected void setConnection(Screen s, Direction d)
	{
		connections[d.ordinal()] = s;
	}
	
	/**
	 * updates all tiles
	 * @param p
	 * @param connections the connections to this screen on 4 cardinal points up down left right
	 */
	protected void update(Player p)
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
