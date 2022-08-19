package com.mygdx.game;

public class Ball extends Sprite {
	
	private boolean ingame = false;
	
	public Ball(String filename,float x,float y)
	{
		super(filename,x,y);
		
	}
	public Ball(float x,float y)
	{
		super(x,y);
		
	}
	public void move(float paddlex, float paddley,float dt)
	{
		if(ingame)
		{
			this.x += this.vector_x * dt;
			this.y += this.vector_y * dt;
			if(this.x <= 0)
				if(this.vector_x < 0)
					this.vector_x = -this.vector_x;
			if(x+sprite_width >= MyGdxGame.WIDTH)
				if(this.vector_x > 0)
					this.vector_x = -(this.vector_x);
			if(this.y + this.sprite_height >= MyGdxGame.HEIGHT)
				if(this.vector_y > 0)
					this.vector_y = -(this.def_vector_y);
		}
		else
		{
			this.x = paddlex;
			this.y = paddley+1;
		}
	}
	public void start_moving()
	{
		this.ingame = true;
	}

	
	//test
	public boolean hit_brick(Brick brick)
	{
		if(this.y <= brick.top_y() && this.y >= brick.top_y() - 6)
		{
			if(this.x <= brick.right_x() && this.x + this.sprite_width >= brick.Get_X())
			{
				this.vector_y = this.def_vector_y;
				return true;
			}
		}

		if(this.y + this.sprite_height >= brick.Get_Y() && this.y + this.sprite_height <= brick.Get_Y() + 6)
		{
			if(this.x <= brick.right_x() && this.x + this.sprite_width >= brick.Get_X())
			{
				this.vector_y = -this.def_vector_y;
				return true;
			}
		}
		
		if(this.x + this.sprite_width >= brick.Get_X() && this.x + this.sprite_width <= brick.Get_X() + 6)
		{
			if(this.y + this.sprite_height >= brick.Get_Y() && this.y <= brick.top_y())
			{
				if(this.vector_x > 0)
				{
					this.vector_x *= -1;
					return true;
				}
			}
		}
		
		if(this.x <= brick.right_x() && this.x >= brick.right_x() -6)
		{
			if(this.y + this.sprite_height >= brick.Get_Y() && this.y <= brick.top_y())
			{
				if(this.vector_x < 0)
				{
					this.vector_x *= -1;
					return true;
				}
			}
		}
		return false;
	}
	
	
	//test
	public void hit_paddle(Paddle paddle)
	{
		if(this.y <= paddle.Get_Y() + paddle.Get_Height() && this.y >= paddle.Get_Y() + paddle.Get_Height() - 10)
		{
			float center_of_ball = this.x + this.sprite_width/2;
			if(this.x <= paddle.Get_X() + paddle.Get_Width() && center_of_ball >= paddle.Get_X() + paddle.Get_Width())
			{
				this.vector_y = this.def_vector_y;
				this.vector_x = 50;
				return;
			}
			if(this.x + this.sprite_width >= paddle.Get_X() && center_of_ball <= paddle.Get_X())
			{
				this.vector_y = this.def_vector_y;
				this.vector_x = -50;
				return;
			}
			if(center_of_ball > paddle.Get_X() && center_of_ball < paddle.Get_X() + paddle.Get_Width())
			{
				this.vector_x = (center_of_ball - (paddle.Get_X() + paddle.Get_Width()/2));
				this.vector_y = this.def_vector_y;
				return;
			}
		}
		else return;
	}
	
	public boolean out_of_game()
	{
		if(this.y + this.sprite_height <= 0)
			return true;
		else
			return false;
	}
	public void reset(float X, float Y)
	{
		this.x = X;
		this.y = Y;
		this.ingame = false;		
	}
	

}
