package io.github.fuzzy39.swingRPG.world.entities;

import java.awt.Point;
import java.awt.image.BufferedImage;

import io.github.fuzzy39.swingRPG.util.Direction;

public class EntityType
{
	
	// physical characteristics
	private int friction;
	private int maxSpeed;
	private Point size;
	
	// stuff
	private BufferedImage texture;
	private UpdatePriority updatePriority;
	private boolean collidesWithWalls;
	//private boolean collidesWithEntities; //collides with all entities, all entities collide with it.
	
	// events
	private EntityConsumer onUpdate;
	private CollisionEvent onCollide; // with a tile.
	
	public EntityType(BufferedImage texture, Point size )
	{
		this.size = size;
		this.texture = texture;
		collidesWithWalls = true;
		//collidesWithEntities = false;
		friction = 1;
		maxSpeed = 6;
		onUpdate = null;
		
		updatePriority = UpdatePriority.ThisScreenOnly;
	}
	
	// auto generated getters and setters go brrrr
	
	public int getMaxSpeed() {
		return maxSpeed;
	}


	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}


	public int getFriction() {
		return friction;
	}

	public void setFriction(int friction) {
		this.friction = friction;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public Point getSize() {
		return size;
	}

	public void setSize(Point size) {
		this.size = size;
	}

	public UpdatePriority getUpdatePriority() {
		return updatePriority;
	}

	public void setUpdatePriority(UpdatePriority updatePriority) {
		this.updatePriority = updatePriority;
	}

	public void invokeOnUpdate(Entity entity) 
	{
		if(onUpdate==null)
		{
			return;
		}
		
		onUpdate.Invoke(entity);
	}

	public void setOnCollide(CollisionEvent onCollision) {
		this.onCollide = onCollision;
	}
	
	public void invokeOnCollision(Entity entity, Direction d) 
	{
		if(onCollide==null)
		{
			return;
		}
		
		onCollide.Invoke(entity, d);
	}

	public void setOnUpdate(EntityConsumer onUpdate) {
		this.onUpdate = onUpdate;
	}
	
	
	public boolean CollidesWithWalls() {
		return collidesWithWalls;
	}

	public void setCollidesWithWalls(boolean collidesWithWalls) {
		this.collidesWithWalls = collidesWithWalls;
	}

	/*public boolean CollidesWithEntities() {
		return collidesWithEntities;
	}
	
	public void setCollidesWithEntities(boolean collidesWithEntities) {
		this.collidesWithEntities = collidesWithEntities;
	}*/
	
	
}
