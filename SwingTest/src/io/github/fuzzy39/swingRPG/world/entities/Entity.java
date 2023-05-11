package io.github.fuzzy39.swingRPG.world.entities;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import io.github.fuzzy39.swingRPG.util.Direction;
import io.github.fuzzy39.swingRPG.util.Drawable;
import io.github.fuzzy39.swingRPG.util.Rectangle;
import io.github.fuzzy39.swingRPG.world.Screen;
import io.github.fuzzy39.swingRPG.world.World;

/**
 * A BasicEnity represents an actor on the world. They are capable of moving on and between screens.
 * Their behavior and other aspects are managed by their EntityType.
 * @author Fuzzy39
 *
 */
public class Entity implements Drawable
{
	
	/*
	 * Here's the crux of my problem
	 * I have to make a coordinate system that works across screens despite some screens not having global coordinates.
	 * Spelling that out was helpful.
	 * so
	 * I have to make global coords.
	 * but optionally, non global ones.
	 */
	
	
	
	
	/**
	 * The screen this entity is tied to. if null, this entity has global, rather than local coordinates.
	 */
	private Screen tiedTo;
	
	/**
	 * The bounds of this entity. in global coordinates if tiedTo is null, local otherwise. 
	 */
	private Rectangle bounds;
	
	private Point velocity;
	
	
	protected World world; 
	
	protected EntityType type;
	
	
	boolean velocityModified = false;
	
	
	public Entity(Screen s, Point position, EntityType type)
	{
		this.type = type;
		bounds = new Rectangle(position, type.getSize());
		velocity = new Point(0,0);
		teleportLocal(bounds.location.x, bounds.location.y, s);
		
		world = s.getWorld();
		world.addEntity(this);
	}
	
	
	/**
	 * Moves an entity from its position by a specified amount, if possible.
	 * @param byX
	 * @param byY
	 * @return Returns whether the entity was able to move to the specified location.
	 */
	public Direction nudge(int byX, int byY) 
	{
		
		// this is very ugly.
		
		int oldy = bounds.location.y;
		
		int oldx = bounds.location.x;
		
		Direction toReturn = null;
		
		// Nudge x
		for(int x = 1; x<=Math.abs(velocity.x); x++)
		{
			Point newPos = new Point(oldx+(int)Math.signum(velocity.x)*x,oldy);
			
			Rectangle newBounds = new Rectangle(newPos, bounds.size);
			
			boolean collides = collides(newBounds);
			
			if(collides)
			{
				toReturn =  velocity.x>0?Direction.RIGHT:Direction.LEFT;
			}
			
			if(collides || x == Math.abs(velocity.x))
			{
				bounds.location.x += (int)Math.signum(velocity.x)*(x-1);
				break;
			}
			
		}
		
		oldx = bounds.location.x;
	
		// Nudge y
		for(int y = 1; y<=Math.abs(velocity.y); y++)
		{
			Point newPos = new Point(oldx,oldy+(int)Math.signum(velocity.y)*y);
			
			Rectangle newBounds = new Rectangle(newPos, bounds.size);
			
			boolean collides = collides(newBounds);
			
			if(collides)
			{
				toReturn = velocity.y>0?Direction.DOWN:Direction.UP;
			}
			
			if(collides || y == Math.abs(velocity.y))
			{
				bounds.location.y += (int)Math.signum(velocity.y)*(y-1);
				break;
			}
			
		}
		
		return toReturn;
		
	}
	
	private boolean collides(Rectangle newBounds)
	{
		if(tiedTo==null)
		{
			return world.collidesWithWorld(type.CollidesWithWalls(),newBounds);
		}
		
		return world.collidesWithWorld(type.CollidesWithWalls(), newBounds, tiedTo);
	}
	
	
	public boolean collidesWith(Entity other)
	{
		
		if(this.tiedTo!=null)
		{
			if(tiedTo!=other.tiedTo)
			{
				return false;
			}
			
			return bounds.intersects(other.bounds);
		}
		
		if(other.tiedTo!=null)
		{
			return false;
		}
		
		return bounds.intersects(other.bounds);
	}
	
	
	/**
	 * Teleport this entity to a place given its global coordinates
	 * @param x
	 * @param y
	 */
	public void teleportGlobal(int x, int y) 
	{
		// no muss, no fuss.
		bounds.location.x = x;
		bounds.location.y = y;
		tiedTo = null;

	}
	
	/**
	 * Teleport this entity to a place on a particular screen. Required in cases where a screen does not have 
	 * global coordinates.
	 * @param x
	 * @param y
	 * @param s
	 */
	public void teleportLocal(int x, int y, Screen s) 
	{
		if(s.ConnectedToWorld())
		{
			Point size = Screen.SIZE;
			Point worldCoord = s.getWorldCoord();
			x += size.x * worldCoord.x;
			y += size.y * worldCoord.y;
			tiedTo = null;
			
		}
		else
		{
			tiedTo = s;
		}
		
		bounds.location.x = x;
		bounds.location.y = y;
		

	}
	
	
	public void update()
	{
		if(type.getUpdatePriority() == UpdatePriority.ThisScreenOnly)
		{
			if(!isInScreen(world.getCurrentScreen()))
			{
				return;
			}
		}
		
		Direction d = nudge(velocity.x, velocity.y);
		if(d!= null)
		{
			type.invokeOnCollision(this, d);
		}
	
		if(! velocityModified)
		{
			velocity.x = reduce(velocity.x, type.getFriction());
			velocity.y = reduce(velocity.y, type.getFriction());
		}
		velocityModified = false;
		
		type.invokeOnUpdate(this);
		
	}
	
	


	/**
	 * Returns the screen the top left corner of this entity is currently on.
	 * May return null if no such screen exists.
	 * @return
	 */

	public Screen getScreen() 
	{
		
		if(tiedTo!=null)
		{
			return tiedTo;
		}
		
		return world.getScreen(bounds.location.x/Screen.SIZE.x, bounds.location.y/Screen.SIZE.y);
	}


	/**
	 * Returns whether this entity's bounds intersect a particular screen's bounds.
	 * @param s
	 * @return
	 */
	public boolean isInScreen(Screen s) 
	{
		if(tiedTo!=null)
		{
			return s == tiedTo;
		}
		
		if(!s.ConnectedToWorld())
		{
			return false;
		}
		
		return s.getGlobalBounds().intersects(bounds);
	}



	@Override
	public void draw(Graphics g, ImageObserver o) 
	{
		// b is not a value type, dummy!
		Rectangle b;
		if(tiedTo==null)
		{
			b=world.getCurrentScreen().convertToRelative(bounds);
		}
		else
		{
			b=bounds;
		}
		
		g.drawImage(type.getTexture(), b.location.x, b.location.y, b.size.x, b.size.y, o);
	}
	
	

	/**
	 * add velocity to the player.
	 * @param x
	 * @param y
	 */
	public void addVelocity(int x, int y)
	{
		velocity.x = cap(velocity.x+x, type.getMaxSpeed());
		velocity.y = cap(velocity.y+y, type.getMaxSpeed());
		
		velocityModified = true;
		
	}
	
	
	public Point getVelocity()
	{
		return new Point(velocity);
	}
	
	public void setVelocity(Point vel)
	{
		velocity = vel;
	}
	
	public Point getPosition()
	{
		return new Point(bounds.location);
	}

	
	public Rectangle getBounds() {return new Rectangle(bounds);};
	
	
	// private mmethods
	
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
