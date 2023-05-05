package io.github.fuzzy39.swingRPG.world.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import io.github.fuzzy39.swingRPG.util.Drawable;
import io.github.fuzzy39.swingRPG.util.Rectangle;
import io.github.fuzzy39.swingRPG.world.Player;
import io.github.fuzzy39.swingRPG.world.Screen;


public class Tile implements Drawable
{
	
	private Rectangle bounds;
	
	
	
	private boolean triggered = false;
	public TileType config;
	
	
	public Tile (TileType conf, int x, int y)
	{
		bounds = new Rectangle(x,y, Screen.TILE_SIZE, Screen.TILE_SIZE);
	
		config = conf;
		
		
	}
	
	
	public void setConfig(TileType config)
	{
		this.config = config;
	}
	

	public void update(Player p)
	{
		
		
		if(p.getBounds().intersects(bounds))
		{
			if(!triggered)
			{
				triggered = true;
				if(config.onEnter==null) {return;}
				
				config.InvokeOnEnter(this);
			}
			return;
		}
		
		if(triggered && config.onExit!=null)
		{
			config.InvokeOnExit(this);
		}
		triggered = false;
		
	}
	
	@Override
	public void draw(Graphics g, ImageObserver o) 
	{
		
		if(config.texture == null)
		{
			return;
		}
		
		g.drawImage(config.texture, bounds.location.x, bounds.location.y, bounds.size.x, bounds.size.y, Color.white, o);
		
	}

}
