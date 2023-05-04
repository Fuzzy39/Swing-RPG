package io.github.fuzzy39.swingRPG.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import io.github.fuzzy39.swingRPG.util.Drawable;

public class World implements Drawable 
{

	//private ArrayList<Screen> map;
	private Screen currentScreen;
	private Player player;
	
	public World(Point playerStart, Screen startingScreen)
	{
		currentScreen = startingScreen;
		player = new Player(playerStart.x, playerStart.y);
	}
	
	@Override
	public void draw(Graphics g, ImageObserver o) 
	{
		
		currentScreen.draw(g,  o);
		player.draw(g, o);

	}
	

	
	public void update()
	{
		player.update(currentScreen);
		currentScreen.update(player);
		
	}
	
	public Screen getCurrentScreen()
	{
		return currentScreen;
	}
	
	public Player getPlayer()
	{
		return player;
	}

}
