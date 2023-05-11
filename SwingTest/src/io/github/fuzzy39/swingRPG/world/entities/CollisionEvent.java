package io.github.fuzzy39.swingRPG.world.entities;

import io.github.fuzzy39.swingRPG.util.Direction;
import io.github.fuzzy39.swingRPG.world.tiles.Tile;

// At this point I should just learn how the java event api works
public interface CollisionEvent 
{
	public void Invoke(Entity e, Direction from);
}
