package io.github.fuzzy39.swingRPG.world.tiles;

import java.awt.image.BufferedImage;



/**
 * Defines a tile's configuration. Highly configurable!
 * @author Fuzzy39
 *
 */
public class TileType 
{

	protected boolean passable;
	
	protected BufferedImage texture;
	
	protected TileConsumer onEnter; // when the player 
	protected TileConsumer onExit;
	
	public TileType()
	{
		passable = false;
		texture = null;
		onEnter = (Tile t)->{};
		onExit = (Tile t)->{};
	}
	
	public TileType(TileType from)
	{
		passable = from.passable;
		texture = from.texture;
		onEnter = from.onEnter;
		onExit = from.onExit;
	}
	
	public boolean isPassable() {return passable;}
	
	public void setPassable(boolean passable) {this.passable = passable;}
	
	
	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}
	
	/**
	 * What happens when the player walks on (or next to) this tile?
	 * @param onEnter
	 */
	public void setOnEnter(TileConsumer onEnter) {
		this.onEnter = onEnter;
	}
	
	/**
	 * what happens when the player leaves the tile?
	 * @param onExit
	 */
	public void setOnExit(TileConsumer onExit) {
		this.onExit = onExit;
	}
	
	protected void InvokeOnEnter(Tile t)
	{
		onEnter.Invoke(t);
	}
	
	protected void InvokeOnExit(Tile t)
	{
		onExit.Invoke(t);
	}
	
	
	
}
