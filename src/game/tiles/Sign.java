package game.tiles;

import main.Main;
import main.Textures;

public class Sign extends TileConfiguration 
{
	
	public Sign()
	{
		passable = true;
		texture = Textures.sign;
		onEnter = (Tile t)->{Main.setText("[sign text not set]");};
		onExit = (Tile t)->{Main.resetText();};
	}
	
	public Sign(String s)
	{
		this();
		setSignText(s);
	}
	
	public void setSignText(String s)
	{
		onEnter = (Tile t)->{Main.setText(s);};
	}
}
