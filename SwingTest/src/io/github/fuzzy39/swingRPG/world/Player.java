package io.github.fuzzy39.swingRPG.world;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import io.github.fuzzy39.swingRPG.util.Drawable;
import io.github.fuzzy39.swingRPG.util.Rectangle;

public class Player implements Drawable
{

	private BufferedImage texture;
	private Rectangle bounds;
	private Point velocity;
	
	public static int ACCELERATION = 5;
	public static int MAX_SPEED = 10;
	public static int FRICTION = 2;
	
	/**
	 * Create a new player at a given position. (probably shouldn't make multiple...)
	 * @param x
	 * @param y
	 */
	protected Player(int x, int y)
	{
		
		int size = 40;
		bounds = new Rectangle(x,y,size, size);
		velocity = new Point();
		texture = null;
		
	}
	
	
	public void setTexture(BufferedImage texture)
	{
		this.texture = texture;
	}
	
	@Override
	public void draw(Graphics g, ImageObserver o) 
	{
	
		if(texture == null)
		{
			//TODO call what?
			System.out.println("Warning: the player has no texture. if you want to see it, consider calling player.setTexture.");
		}
		g.drawImage(texture, bounds.location.x, bounds.location.y, bounds.size.x, bounds.size.y, Color.white, o);
		
	}
	
	protected void update(Screen level)
	{
		
		// move to our new position!
		
		int oldy = bounds.location.y;
		
		int oldx = bounds.location.x;
		
		for(int x = 1; x<=Math.abs(velocity.x); x++)
		{
			Point newPos = new Point(oldx+(int)Math.signum(velocity.x)*x,oldy);
			
			Rectangle newBounds = new Rectangle(newPos, bounds.size);
			
			if(!level.isValidPosition(newBounds) || x == Math.abs(velocity.x))
			{
				bounds.location.x += (int)Math.signum(velocity.x)*(x-1);
				break;
			}
			
		}
		
		oldx = bounds.location.x;
	
		for(int y = 1; y<=Math.abs(velocity.y); y++)
		{
			Point newPos = new Point(oldx,oldy+(int)Math.signum(velocity.y)*y);
			
			Rectangle newBounds = new Rectangle(newPos, bounds.size);
			
			if(!level.isValidPosition(newBounds) || y == Math.abs(velocity.y))
			{
				bounds.location.y += (int)Math.signum(velocity.y)*(y-1);
				break;
			}
			
		}
		
	
		
		velocity.x = reduce(velocity.x, FRICTION);
		velocity.y = reduce(velocity.y, FRICTION);
	}
	
	/**
	 * add velocity to the player.
	 * @param x
	 * @param y
	 */
	public void addVelocity(int x, int y)
	{
		velocity.x = cap(velocity.x+x, MAX_SPEED);
		velocity.y = cap(velocity.y+y, MAX_SPEED);
		
	}
	
	
	public Point getVelocity()
	{
		return new Point(velocity);
	}
	
	public Point getPosition()
	{
		return new Point(bounds.location);
	}
	
	
	public Rectangle getBounds() {return new Rectangle(bounds);};
	
	private int cap(int tocap, int cap)
	{
		if(Math.abs(tocap)>cap)
		{
			int i = (int)Math.signum(tocap);
			tocap = cap*i;
		}
		return tocap;
	}
	
	
	
	private int reduce(int a, int b)
	{
		if(Math.abs(b)>Math.abs(a))
		{
			return 0;
		}
		int i = (int)Math.signum(a);
		return i*(Math.abs(a)-b);
	}

	
}
