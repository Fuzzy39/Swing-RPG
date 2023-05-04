package fullSample;

import io.github.fuzzy39.swingRPG.PrimaryWindow;
import io.github.fuzzy39.swingRPG.world.tiles.Tile;
import io.github.fuzzy39.swingRPG.world.tiles.TileType;


public class Sign extends TileType
{
	/*
	 * This Sign class shows that you can also create a tile type by extending the TileType class.
	 * This can be more organized and also more powerful than configuring one in another class.
	 * 
	 * The sign tile just edits the status text when the player walks up to it.
	 */
	
	private PrimaryWindow UI;
	private String text; // the text that will display when you walk up to the sign.
	
	
	public Sign(PrimaryWindow UI)
	{
		this.UI=UI;
		passable = true;
		texture = Main.sign;
		text = "[sign text not set]";
		
		// you can write enter and exit functions inline like when configuring (like so), but you can also write them as actual methods
		// you can do this without extending as well, but it gets a bit messy.
		//onEnter = (Tile t)->{UI.setText(text);};
		
		// it's probably worth noting that if multiple tiles with onEnter/Exit are in close proximity,
		// the most recently entered/exited will take precedence, meaning some strange seeming things can happen,
		// like not being able to pick up a coin right next to you.
		onEnter = this::onEnterTileConsumer;
		
		// The exit function here is writen as a method.
		onExit = this::onExitTileConsumer;
	}
	

	public Sign(PrimaryWindow UI, String s)
	{
		this(UI);
		setSignText(s);
	}
	
	public void setSignText(String s)
	{
		text = s;
	}
	
	
	// ignore the silly name. You can't name it onExit without things being annoying, since we have a variable with the same name.
	private void onExitTileConsumer(Tile t)
	{
		UI.resetText();
	}
	
	private void onEnterTileConsumer(Tile t)
	{
		UI.setText(text);
	}
	
}
