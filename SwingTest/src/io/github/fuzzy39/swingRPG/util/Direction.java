package io.github.fuzzy39.swingRPG.util;

import java.awt.Point;

public enum Direction 
{
	UP ,
	DOWN ,
	LEFT,
	RIGHT;
	
	public Point toPoint()
	{
		int x = 0;
		int y = 0;
		
		switch(this)
		{
			case UP:
				y = -1;
				break;
			case DOWN:
				y = 1;
				break;
			case LEFT:
				x = -1;
				break;
			case RIGHT:
				x = 1;
				break;
		}
		
		return new Point(x,y);
	}
	
	
	public Direction opposite()
	{
		switch(this)
		{
			case UP:
				return DOWN;
			case DOWN:
				return UP;
			case LEFT:
				return RIGHT;
			case RIGHT:
				return LEFT;
		}
		
		// will never happen
		return UP;
	}
}
