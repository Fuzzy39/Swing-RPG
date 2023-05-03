package game.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Player;
import game.Screen;
import main.Drawable;
import main.Rectangle;
import main.Textures;


public class Tile implements Drawable
{
	
	private Rectangle bounds;
	
	
	
	private boolean triggered = false;
	public TileConfiguration config;
	
	
	public Tile (TileType t, int x, int y)
	{
		bounds = new Rectangle(x,y, Screen.tileSize, Screen.tileSize);
	
		switch(t)
		{
			case None:
				throw new IllegalArgumentException("None type for tile makes no sense!!! Buttcheek.");
			case Wall:
				config = new Wall();
				break;
			case Sign:
				config = new Sign();
				break;
				
		}
		
		
	}
	

	public void update(Player p)
	{
		
		
		if(p.getBounds().intersects(bounds.inflate(20)))
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
		
		g.drawImage(config.texture, bounds.location.x, bounds.location.y, bounds.size.x, bounds.size.y, Color.white, o);
		
	}

}
