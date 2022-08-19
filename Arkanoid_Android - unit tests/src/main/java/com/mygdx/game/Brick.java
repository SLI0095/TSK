package com.mygdx.game;

public class Brick extends Sprite {
	
	private boolean ingame;
	
	public Brick(String filename, float x, float y)
	{
		super(filename, x, y);
		ingame = true;
	}
	
	public float top_y()
	{
		return this.y + this.sprite_height;
	}
	public float right_x()
	{
		return this.x + this.sprite_width;
	}
	public void destroy()
	{
		ingame = false;
	}
	public boolean ingame()
	{
		return ingame;
	}

}
