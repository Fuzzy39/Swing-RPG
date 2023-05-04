package io.github.fuzzy39.swingRPG.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

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
	public final static int tileSize = 50;
	
	private BufferedImage backgroundTexture;
	private Tile[][] level;
	//private Screen[] connections;
	
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
		level = new Tile[800/tileSize][400/tileSize];
		
		for(int x = 0; x<800/tileSize; x++)
		{
			for(int y=0; y<400/tileSize; y++)
			{
				
				createTile(levelMap, x, y);
			}
		}
		
		
	}
	
	
	private void createTile(TileType[][] levelMap, int x, int y)
	{
		if(levelMap[x][y] == null)
		{
			level[x][y] = null;
			return;
		}
		
		
		level[x][y] = new Tile(levelMap[x][y], tileSize*x, tileSize*y);
		
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
	 * Returns whether a given rectangle of spaced is occupied by any non-passable (solid) tiles.
	 * @param rect
	 * @return
	 */
	public boolean isValidPosition(Rectangle rect)
	{
		Rectangle screenBounds = new Rectangle(0,0,800,400);
		if(!screenBounds.contains(rect))
		{
			//System.out.println("pushing the edge");
			return false;
		}
		
		Point topLeft = rect.location;
		int minX = topLeft.x/tileSize;
		int minY = topLeft.y/tileSize;
		int maxX = (rect.getRight()-1)/tileSize;
		int maxY = (rect.getBottom()-1)/tileSize;
		
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
	 * updates all tiles
	 * @param p
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
