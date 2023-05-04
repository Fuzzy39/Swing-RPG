package io.github.fuzzy39.swingRPG.world.tiles;

@FunctionalInterface
public interface TileConsumer 
{
	public void Invoke(Tile t);
}
