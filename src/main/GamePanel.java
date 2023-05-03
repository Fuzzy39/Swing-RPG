package main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game.Player;
import game.Screen;
import game.World;
import game.tiles.Sign;
import game.tiles.TileType;

public class GamePanel extends JPanel{

    private BufferedImage bg;
   
    private World world;
    private Controls controls;
    double i;
    
    public GamePanel() 
    {
      
       
       TileType o = TileType.None;
       TileType w = TileType.Wall;
       TileType s = TileType.Sign;
       
       world = new World(new Screen( 
    		   new TileType[][] {
    			   
    			   // inc y >
    			   // in x V
    			   {w,w,w,w,w,w,w,w},
    			   {w,o,o,o,w,o,o,w},
    			   {w,o,o,o,w,o,o,w},
    			   {w,o,o,o,w,o,o,w},
    			   {w,o,o,o,w,o,o,w},
    			   {w,o,o,o,o,o,o,w},
    			   {w,o,o,o,o,o,o,w},
    			   {w,o,o,o,w,o,o,w},
    			   {w,o,o,o,w,o,o,w},
    			   {w,w,w,w,w,o,o,w},
    			   {w,o,o,o,o,o,o,w},
    			   {w,o,o,o,o,o,o,w},
    			   {w,o,o,o,w,o,o,w},
    			   {w,o,s,o,w,o,o,w},
    			   {w,o,o,o,w,o,o,w},
    			   {w,w,w,w,w,s,s,w}
    		   }));
       
       world.getCurrentScreen().getTile(13, 2).config = new Sign("The sign seems blank.");
       		
       Sign sign = new Sign("Casino Currently Under Construction. Pardon Our Dust!");
       world.getCurrentScreen().getTile(15, 5).config = sign;
       world.getCurrentScreen().getTile(15, 6).config = sign;
  		
    
       
       
       controls = new Controls(world.getPlayer());
       
    }

    public void update()
    {
    	 	
    	controls.update();
    	world.update();
    	this.repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {
    	
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, this); // see javadoc for more info on the parameters       
        world.draw(g, this);
    }

}