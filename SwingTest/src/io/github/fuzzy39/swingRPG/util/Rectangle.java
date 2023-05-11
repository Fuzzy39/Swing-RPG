package io.github.fuzzy39.swingRPG.util;
import java.awt.Point;

/**
 * A rectangle represents, uh, a rectangle. positive x is right, positive y is down.
 * @author Fuzzy39
 *
 */
public class Rectangle 
{

	public Point location;
	public Point size;
	
	public Rectangle(int x, int y, int width, int height)
	{
		this(new Point(x,y), new Point(width, height));
	}
	
	public Rectangle(Point loc, Point size)
	{
		location = loc;
		this.size = size;
	}
	
	public Rectangle(Rectangle from)
	{
		location = from.location;
		size = from.size;
	}

	
	public int getLeft() { return location.x; }
	public int getRight() {return location.x + size.x;}
	
	public int getTop() { return location.y; }
	public int getBottom() {return location.y + size.y;}
	
	
	public boolean intersects(Rectangle other)
	{


		return  getLeft()<other.getRight() 
				&& getRight() > other.getLeft()
				&& getTop() < other.getBottom() 
				&& getBottom() > other.getTop();
	}
	
	
	public boolean contains(Rectangle other)
	{
		return getLeft() <= other.getLeft() 
			&& getRight()>= other.getRight()
			&& getTop() <= other.getTop()
			&& getBottom() >= other.getBottom();
	}
	
	
	public Rectangle inflate(int by)
	{
		Point newPos = new Point(location.x-by, location.y-by);
		Point newSize = new Point(size.x+by+by, size.y+by+by);
		return new Rectangle(newPos, newSize);
	}
	
		
	
	public Rectangle Intersection(Rectangle other)
	{
		if(!intersects(other))
		{
			return null;
		}
		
		
		int left = getLeft()>other.getLeft()? getLeft() : other.getLeft();
		int top = getTop()>other.getTop() ? getTop() : other.getTop();
		
		int right = getRight()<other.getRight()? getRight() : other.getRight();
		int bottom =  getRight()<other.getRight()? getRight() : other.getRight();	
		
		Point size = new Point(right-left, bottom-top);
		Point location = new Point(left, top);
		
		return new Rectangle(location, size);
		
		
	}
	
	@Override
	public String toString()
	{
		return "[loc:"+location+", size:"+size+"]";
	}
	
	
	
}
