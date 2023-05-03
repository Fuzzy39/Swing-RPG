package game.tiles;

import java.awt.image.BufferedImage;

import main.Textures;


/**
 * Defines a tile's configuration. Typically better to use a more specific subclass.
 * @author Fuzzy39
 *
 */
public class TileConfiguration 
{

	protected boolean passable;
	
	protected BufferedImage texture;
	
	protected TileConsumer onEnter; // when the player 
	protected TileConsumer onExit;
	
	public TileConfiguration()
	{
		passable = false;
		texture = Textures.wall;
		onEnter = (Tile t)->{};
		onExit = (Tile t)->{};
	}
	
	public boolean isPassable() {return passable;}
	
	public void setPassable(boolean passable) {this.passable = passable;}
	
	
	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}
	
	
	public void setOnEnter(TileConsumer onEnter) {
		this.onEnter = onEnter;
	}
	
	public void setOnExit(TileConsumer onExit) {
		this.onExit = onExit;
	}
	
	public void InvokeOnEnter(Tile t)
	{
		onEnter.Invoke(t);
	}
	
	public void InvokeOnExit(Tile t)
	{
		onExit.Invoke(t);
	}
	
	
	
}
