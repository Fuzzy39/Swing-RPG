package main;
import java.awt.Point;

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


		return  getLeft()<=other.getRight() 
				&& getRight() >= other.getLeft()
				&& getTop() <= other.getBottom() 
				&& getBottom() >= other.getTop();
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
	
	
	@Override
	public String toString()
	{
		return "[loc:"+location+", size:"+size+"]";
	}
	
	
	
}
