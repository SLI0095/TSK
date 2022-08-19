package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public abstract class Sprite {
	
	private Texture sprite_texture;
	protected float x, y, vector_x, vector_y, sprite_width, sprite_height;
	protected final float def_vector_x = 20;
	protected final float def_vector_y = 250;
	public Sprite(String filename, float X, float Y)
	{
		sprite_texture = new Texture(filename);
		x = X;
		y = Y;
		vector_x = 0;
		vector_y = def_vector_y;
		sprite_width = sprite_texture.getWidth();
		sprite_height = sprite_texture.getHeight();
	}
	public Sprite(float X, float Y)
	{
		sprite_texture = null;
		x = X;
		y = Y;
		vector_x = 0;
		vector_y = def_vector_y;
		sprite_width = 0;
		sprite_height = 0;
	}
	public float Get_X()
	{
		return this.x;
	}
	public float Get_Y()
	{
		return this.y;
	}
	public float Get_Width()
	{
		return this.sprite_width;
	}
	public float Get_Height()
	{
		return this.sprite_height;
	}
	public Texture Get_Texture()
	{
		return this.sprite_texture;
	}

}
