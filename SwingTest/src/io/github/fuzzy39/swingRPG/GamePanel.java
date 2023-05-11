package io.github.fuzzy39.swingRPG;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import io.github.fuzzy39.swingRPG.world.Screen;
import io.github.fuzzy39.swingRPG.world.World;
import io.github.fuzzy39.swingRPG.world.tiles.Tile;
import io.github.fuzzy39.swingRPG.world.tiles.TileType;

/**
 * The game panel manages the game world for us. How nice.
 * I'm sure I'm not doing all of the things I should when I inherit from a swing component, since
 * I started learning swing the day that I wrote this. I wouldn't try doing any funky stuff with it.
 * @author Fuzzy39
 *
 */
public class GamePanel extends JPanel
{

   
   
    private World world;
    
    
    
    /**
     * 
     * @param w The world this panel manages
     */
    public GamePanel() 
    {
       

      
       
    }
    
    public void setWorld(World world)
    {
    	this.world = world;
    }
    
    public World getWorld()
    {
    	return world;
    }

    /**
     * Updates the world and repaints this component.
     */
    public void update()
    {
    	 	
    
    	world.update();
    	this.repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {
    	
        super.paintComponent(g);
        //g.drawImage(bg, 0, 0, this);   
        if(world!=null)
        {
        	world.draw(g, this);
        }
        
    }

}