

import java.awt.Point;
import java.awt.image.BufferedImage;

import io.github.fuzzy39.swingRPG.PrimaryWindow;
import io.github.fuzzy39.swingRPG.Engine;
import io.github.fuzzy39.swingRPG.world.Screen;
import io.github.fuzzy39.swingRPG.world.World;
import io.github.fuzzy39.swingRPG.world.entities.EntityType;
import io.github.fuzzy39.swingRPG.world.entities.UpdatePriority;
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
		greeter= Engine.loadTexture("player.png");
		sign = Engine.loadTexture("sign.jpg");
		coin = Engine.loadTexture("coin.jpg");
		
		
		// lets make the engine now. this sets up the UI.
		Engine engine = new Engine("Sample Project");
		
		
		// next, the map needs to be created. this is an involved process.
		// We need to make a screen for the map, and to make a screen,
	
	
		// now we can create the world, woo!
		// the point is where the player starts, in pixels. tiles are 50 pixels.
		// yeah, its set in stone for now. eh.
	 
	    World world = createWorld(engine);
	    	

	    
	    // lets initialize the engine now that the world has been made.
	    engine.initialize(world);
	   
		
		// set the default text below the game screen. this will be briefly overwritten when you walk over a sign.
		engine.getMainUI().setStatusText("A 'game' made with Swing RPG!");

	}
	
	
	private static World createWorld(Engine engine)
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
		
		
		// Entity types.
		EntityType player = new EntityType(greeter, new Point(50,50));
		player.setUpdatePriority(UpdatePriority.Always);
		
		// create the screen we start on.
		// yes, this is a very ugly way to do this. 
		// screens are always 16 by 8.
		World world = new World();
		 
		
		
		Screen initial =  new Screen
		(0,0,
		   new TileType[][] 
		   {
			   
			   // Y increases >
		       // X increases V
			   {w,o,o,o,o,o,o,w},
			   {o,o,o,o,o,o,o,o},
			   {o,o,o,o,o,o,o,o},
			   {o,o,o,o,o,o,o,o},
			   {o,o,o,o,o,o,o,o},
			   {o,o,o,o,o,o,o,o},
			   {o,o,o,o,o,o,o,o},
			   {o,o,o,w,w,o,o,o},
			   {o,o,o,w,w,o,o,o},
			   {o,o,o,o,o,o,o,o},
			   {o,o,o,o,o,o,o,o},
			   {o,o,o,o,o,o,o,o},
			   {o,o,o,o,o,o,o,o},
			   {o,o,o,o,o,o,o,o},
			   {o,o,o,o,o,o,o,o},
			   {w,o,o,o,o,o,o,w}
		   }, world
		 );

		world.initialize(new Point(80,80), player, initial);
		
		
		 
		 world.addScreen( new Screen
			( 0,-1,
			   new TileType[][] 
			   {
				   
				   // Y increases >
			       // X increases V
				   {w,w,w,w,w,w,w,w},
				   {w,o,o,o,o,o,o,o},
				   {w,o,o,o,o,o,o,o},
				   {w,o,o,o,o,o,o,o},
				   {w,o,o,o,o,o,o,o},
				   {w,o,o,o,o,o,o,o},
				   {w,o,o,o,w,o,o,o},
				   {w,o,o,w,o,o,o,o},
				   {w,o,o,w,o,o,o,o},
				   {w,o,o,o,w,o,o,o},
				   {w,o,o,o,o,o,o,o},
				   {w,o,o,o,o,o,o,o},
				   {w,o,o,o,o,o,o,o},
				   {w,o,o,o,o,o,o,o},
				   {w,o,o,o,o,o,o,o},
				   {w,w,w,w,w,w,w,w}
			   }, world
			 )
		);
		 
		 world.addScreen( new Screen
					( 0,1,
					   new TileType[][] 
					   {
						   
						   // Y increases >
					       // X increases V
						   {w,w,w,w,w,w,w,w},
						   {o,o,o,o,o,o,o,w},
						   {o,o,o,o,o,o,o,w},
						   {o,o,o,o,o,o,o,w},
						   {o,o,o,o,o,o,o,w},
						   {o,o,o,o,o,o,o,w},
						   {o,o,o,w,o,o,o,w},
						   {o,o,o,o,w,o,o,w},
						   {o,o,o,o,w,o,o,w},
						   {o,o,o,w,o,o,o,w},
						   {o,o,o,o,o,o,o,w},
						   {o,o,o,o,o,o,o,w},
						   {o,o,o,o,o,o,o,w},
						   {o,o,o,o,o,o,o,w},
						   {o,o,o,o,o,o,o,w},
						   {w,w,w,w,w,w,w,w}
					   } , world
					 )
				);
		 
		 
		 world.addScreen( new Screen
					( -1,0,
					   new TileType[][] 
					   {
						   
						   // Y increases >
					       // X increases V
						   {w,w,w,w,w,w,w,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,w,w,o,o,w},
						   {w,o,w,o,o,w,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,o,o,o,o,w}
					   }, world
					 )
				);
		
		 
		 world.addScreen( new Screen
					( 1,0,
					   new TileType[][] 
					   {
						   
						   // Y increases >
					       // X increases V
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,w,o,o,w,o,w},
						   {w,o,o,w,w,o,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,o,o,o,o,o,o,w},
						   {w,w,w,w,w,w,w,w}
					   }, world
					 )
				);
		return world;
			
		  
		  
	}
	
	
	


}
