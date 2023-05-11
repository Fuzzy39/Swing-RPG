package io.github.fuzzy39.swingRPG;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import io.github.fuzzy39.swingRPG.world.World;

/**
 * This class is the primary engine class. 
 * as for now it has miscellaneous useful things. 
 * It's a bit scatter-brained, maybe things will improve in the future?
 * @author Fuzzy39
 *
 */
public class Engine 
{

	/*
	 * Todo list:
	 * multi-screen world
	 * entity system
	 * proper keybinding
	 * player -> entity
	 * 
	 * minigames?
	 * figure out what buttons do
	 * proper ui
	 * 
	 */		
		public int playerAcceleration;
	
		private PrimaryWindow mainUI;
		private Controls controls;
		private GamePanel game;
		
		
	   
		/**
		 * Create the game engine! You probably only need one of these.
		 * @param windowTitle the title of the primary game window.
		 * @param map the world map that will be played on.
		 */
	    public Engine(String windowTitle)
	    {
	    	System.out.println("Swing RPG engine v 0.3 | See online at: https://github.com/Fuzzy39/Swing-RPG");
	    	
	    	game = new GamePanel();
	    	mainUI = new PrimaryWindow(windowTitle, game);
	    	
	    	controls = new Controls();
	        playerAcceleration = 4;
	      
	    }
	    
	   
	    
	    private void update(ActionEvent e)
	    {
	    	game.update();
	    	controls.update(game.getWorld().getPlayer(), playerAcceleration);
	    	
	    }
	    
	    /**
	     * The Engine requires a map for the game to be playable.
	     * @param map
	     */
	    public void initialize(World map)
	    {
	    	if(game.getWorld()!=null)
	    	{
	    		throw new IllegalArgumentException("already have been initialized.");
	    	}
	    	
	    	if(map.getInitialized()!= true)
	    	{
	    		throw new IllegalArgumentException("world must be initialized before engine initialization");
	    	}
	    	game.setWorld(map);
	    	
	    	Timer t = new Timer(1000/60, this::update );
	        t.start();
	    }
	    
	    
	    
	    public PrimaryWindow getMainUI()
	    {
	    	return mainUI;
	    }
	    
	    public GamePanel getGamePanel()
	    {
	    	return game;
	    }
	    
	    
	    /**
	     * Create an texture from a file name.
	     * This method really does pretty much nothing, but it saves you writing a try/catch, I guess.
	     * @param filename
	     * @return the texture in question. If loading failed, returns null.
	     */
	    public static BufferedImage loadTexture(String filename)
	    {
	    	BufferedImage toReturn = null;
	    	try 
		    {                
				 toReturn = ImageIO.read(new File(filename));
			   
			    
		    } catch (IOException ex)
		    {
		        // handle exception...
		    	System.out.println("Warning: Couldn't find image with filename: "+filename);
		    }
	    	
	    	return toReturn;
			
	    }
	 
	}
