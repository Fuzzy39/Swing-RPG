package fullSample;

import java.awt.Point;
import java.awt.image.BufferedImage;

import io.github.fuzzy39.swingRPG.PrimaryWindow;
import io.github.fuzzy39.swingRPG.Engine;
import io.github.fuzzy39.swingRPG.world.Player;
import io.github.fuzzy39.swingRPG.world.Screen;
import io.github.fuzzy39.swingRPG.world.World;
import io.github.fuzzy39.swingRPG.world.tiles.Tile;
import io.github.fuzzy39.swingRPG.world.tiles.TileType;

public class Main 
{
	/*
	 * Calling SwingRPG a 'game engine' of any sort at this point is ridiculous
	 *  hyperbole, and will probably be generous even when the project is done. 
	 *  You can do some stuff with it, though. this code demonstrates that.
	 *  As of version 0.1, the 'engine' is very inflexible and not particularly capable.
	 *  hopefully that will improve with time.
	 */
	
	/*
	 * This code demonstrates a little coin collecting game, demonstrating most of the features the engine has to offer.
	 */
	public static BufferedImage floor;
	public static BufferedImage greeter;
	public static BufferedImage wall;
	public static BufferedImage sign;
	public static BufferedImage coin;
	
	static int coinsLeft = 8;

	public static void main(String[] args) 
	{
		//This is a sample program intended to demonstrate the capabilities of the engine, which are currently, limited.
		// using the engine is somewhat obtuse, as well.
		// Note that this does not demonstrate everything the engine can do.
		
		
		// I should note that these images are completely random things I pulled off of google image search
		// I probably should've checked I had the rights to them and stuff.
		// I did not.
		
		// first, textures have to be loaded.
		floor = Engine.loadTexture("bluefloor.jpg");
		wall = Engine.loadTexture("wall.jpg");
		greeter= Engine.loadTexture("greeter.jpg");
		sign = Engine.loadTexture("sign.jpg");
		coin = Engine.loadTexture("coin.jpg");
		
		
		// lets make the engine now. this sets up the UI.
		Engine engine = new Engine("Sample Project");
		
		
		// next, the map needs to be created. this is an involved process.
		// We need to make a screen for the map, and to make a screen,
	
	
		// now we can create the world, woo!
		// the point is where the player starts, in pixels. tiles are 50 pixels.
		// yeah, its set in stone for now. eh.
	    World world = new World(new Point(80,80), createScreen(engine));
	    
	    // we need to give the player a texture, if we want to see it.
	    world.getPlayer().setTexture(greeter);
	    // note that the player can be moved with WASD or the arrow keys. this cannot be changed, but
	    // the player speed, acceleration, friction can be:
	    //Player.MAX_SPEED = 15; // default is 10.
	    // yeah, all of this is super crude, granted. Maybe it'll get better?
	    
	    // lets initialize the engine now that the world has been made.
	    engine.initialize(world);
	   
		
		// set the default text below the game screen. this will be briefly overwritten when you walk over a sign.
		engine.getMainUI().setStatusText("A 'game' made with Swing RPG!");

	}
	
	
	private static Screen createScreen(Engine engine)
	{
		
		// in order to make the screen, we have to make all of the types of tiles that can exist.
		
		// there are two ways of making tile types. You can create one, then configure it, or extend TileType.
		// both are shown off in this sample program. I'd probably reccomend just configuring simple ones like floors and walls,
		// and extending more complex ones, like signs and coins and whatnot.
		
		// walls are very simple
		TileType w  = new TileType();
		w.setTexture(wall);
		
		
		// floors can be walked on. Don't ask why I used o.
		TileType o = new TileType();
		o.setPassable(true);
		o.setTexture(floor);
		
		
		// coins are complicated, and they deserve their own class. but to show that it is not required, this method configures
		// a coin tile type.
		// coins display text when you walk on them, and can be picked up and removed.
		// when removed, they display other text.
		TileType c = configureCoin(engine, o);
		
		// signs are defined as their own class.
		TileType s = new Sign(engine.getMainUI(), "Your goal is to pick up all 8 coins! ready?");
		
		// lets make another type of sign
		TileType q = new Sign(engine.getMainUI(), "You can read this???");
		
		// create the screen we start on.
		// yes, this is a very ugly way to do this. 
		// screens are always 16 by 8.
		
		return new Screen
		( 
		   new TileType[][] 
		   {
			   
			   // Y increases >
		       // X increases V
			   {q,w,w,w,w,w,w,w},
			   {w,o,o,w,o,o,o,w},
			   {w,o,o,o,o,s,o,w},
			   {w,w,w,w,o,o,o,w},
			   {w,w,c,w,w,o,w,w},
			   {w,o,o,o,w,o,o,w},
			   {w,c,w,o,w,w,o,w},
			   {w,w,w,o,w,c,o,w},
			   {w,c,o,o,o,o,o,w},
			   {w,w,w,w,w,w,o,w},
			   {w,o,o,o,o,o,o,w},
			   {w,o,c,o,w,w,o,w},
			   {w,o,o,o,w,c,o,w},
			   {w,o,c,o,w,w,o,w},
			   {w,o,o,o,w,c,o,w},
			   {w,w,w,w,w,w,w,w}
		   }
		 );
	}
	
	private static TileType configureCoin(Engine engine, TileType floor)
	{
		// coins are fairly complex, you probably want to make this tile type its own class.
		// for demonstration purposes, I'll define it without doing that, though.
		
		// lets make a quick reference to the GUI since coins are going to 
		// display several messages.
		PrimaryWindow UI = engine.getMainUI();
		
		// we want a coin to display a message after it's picked up. In order for that message to 
		// go away, we need the tile that replaces it to reset the status text
		// when the player walks away.
		// let's make a copy of the floor tile that will do that.
		TileType afterPickup = new TileType(floor);
		afterPickup.setOnExit((Tile t)->{UI.resetText();});
		
		
		// do basic stuff:
		TileType c = new TileType();
		c.setPassable(true);
		c.setTexture(coin);
		
		// when the player walks over a coin, we want to display some text
		// and make it so that the interact button allows it to be picked up.
		c.setOnEnter((Tile t)->
		{
			UI.setText("Ooh, a shiny coin!");
			
			// here we define the function that is called when the player picks up the coin.
			// we change the coin tile to our special floor tile,
			// change the status to indicate the coin was picked up,
			// and disable the interact button.
			UI.setOnInteract(()->
			{
				coinsLeft--;
				t.setConfig(afterPickup);
				UI.setText("Picked up a coin. Only "+coinsLeft+" to go!" );
				UI.disableInteraction();
				
				// lets make it so we update the default message when all coins are collected.
				
				if(coinsLeft == 0)
				{
					UI.setStatusText("Congrats! You got all 8 coins. You're rich, now, surely!");
				}
				
				
			}, "Pick Up");
			
		});
		
		// when we leave the coin, we should reset our status and disable interaction.
		c.setOnExit((Tile t)->
		{
			UI.resetText();
			UI.disableInteraction();
		});
		
		// this really deserves its own class. This is simply to demonstrate that it isn't required.
		return c;
	}
	


}
