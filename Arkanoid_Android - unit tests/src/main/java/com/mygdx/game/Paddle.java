package com.mygdx.game;

public class Paddle extends Sprite {
	
	
	public Paddle(String filename, float x, float y)
	{
		super(filename, x, y);
	}
	
	public void move(float x)
	{
		this.x = x - this.sprite_width/2;
	}

}
