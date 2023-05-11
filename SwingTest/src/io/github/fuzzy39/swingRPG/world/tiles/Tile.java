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
import io.github.fuzzy39.swingRPG.world.Screen;
import io.github.fuzzy39.swingRPG.world.World;
import io.github.fuzzy39.swingRPG.world.entities.Entity;


public class Tile implements Drawable
{
	
	private Rectangle bounds;
	private World world;
	
	
	private boolean triggered = false;
	public TileType config;
	
	
	public Tile (Screen s, TileType conf, int x, int y)
	{
		world = s.getWorld();
		bounds = new Rectangle(x,y, Screen.TILE_SIZE, Screen.TILE_SIZE);
	
		config = conf;
		
		
	}
	
	
	public void setConfig(TileType config)
	{
		this.config = config;
	}
	

	public void update(Entity player)
	{
		
		Screen s = world.getCurrentScreen();
		Rectangle pbounds = new Rectangle(player.getBounds());
		if(s.ConnectedToWorld())
		{
			pbounds =s.convertToRelative(pbounds);
		}
		
		if(pbounds.intersects(bounds.inflate(1)))
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
		
		g.drawImage(config.texture, bounds.location.x, bounds.location.y, bounds.size.x, bounds.size.y, o);
		
	}

}
