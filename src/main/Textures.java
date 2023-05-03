package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Textures 
{

	public static BufferedImage casino;
	public static BufferedImage greeter;
	public static BufferedImage wall;
	public static BufferedImage sign;
	
	static void init()
	{

		try 
	    {                
			casino = ImageIO.read(new File("casino.jpg"));
		    greeter = ImageIO.read(new File("greeter.png"));
		    wall = ImageIO.read(new File("wall.jpg"));
		    sign = ImageIO.read(new File("sign.jpg"));
		    
	    } catch (IOException ex)
	    {
	        // handle exception...
	    	System.out.println("Couldn't find image(s): "+ex.getMessage());
	    }
		
	}
}
