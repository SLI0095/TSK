package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Playground {
	
	private SpriteBatch batch;
	private Ball ball;
	private Paddle paddle;
	private ArrayList<Brick> bricks;
	private Brick helpbrick;
	int lifes, number_of_bricks, score;
	BitmapFont font;
	boolean game_over;
	private Texture reset;
	
	public Playground()
	{
		
	}
	
	public Playground(boolean a)
	{
		init();		
	}
	
	public void init()
	{
		batch = new SpriteBatch();
		paddle = new Paddle("paddle2.png",MyGdxGame.WIDTH/2, 50);
		ball = new Ball("ball2.png", paddle.Get_X() + (paddle.Get_Width()/2), paddle.Get_Y() + paddle.Get_Height() + 1);
		bricks = new ArrayList<Brick>();
		helpbrick = new Brick("brick3.png",0,0);
		lifes = 3;
		number_of_bricks = 0;
		score = 0;
		game_over = false;
		reset = new Texture("reset.png");
		font = new BitmapFont(Gdx.files.internal("arial.fnt"));
	
		String s;
		FileHandle file = Gdx.files.internal("level.txt");
		s = file.readString();
		char c[] = s.toCharArray();
		for(int j = 0; j < c.length; j++)
		{
			if(c[j] == '-')
			{
				int temp = j - ((j/12) * 12);
				int temp2 = j/12 + 1;
				bricks.add(new Brick("brick3.png",10 + temp*(helpbrick.Get_Width()+10), MyGdxGame.HEIGHT - (20 + temp2*(helpbrick.Get_Height() + 5))));
				number_of_bricks++;
			}
		}
	}	
	public void handle_input()
	{
		if(Gdx.input.justTouched())
		{
			ball.start_moving();			
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.R))
		{
			init();
		}
		if((Gdx.input.getX() >= 1782 && Gdx.input.getX() <= 1910) && (Gdx.input.getY() >= 10 && Gdx.input.getY() <= 138))
        {
            init();
        }
	}
	
	
	//test
	public void update(float dt)
	{
	    if(!game_over)
	    {
            paddle.move(Gdx.input.getX());
            ball.move(paddle.Get_X() + paddle.Get_Width() / 2, paddle.Get_Y() + paddle.Get_Height(), dt);
            if (ball.out_of_game()) {
                lifes--;
                ball.reset(paddle.Get_X() + paddle.Get_Width() / 2, paddle.Get_Y() + paddle.Get_Height());
            }
            ball.hit_paddle(paddle);
            for (Brick brick : bricks) {
                if (brick.ingame() && ball.hit_brick(brick)) {
                    brick.destroy();
                    number_of_bricks--;
                    score += 10;
                }
            }
            if(number_of_bricks == 0 || lifes < 1)
                game_over = true;
        }
		
	}
	public void render()
	{
		batch.begin();
		font.draw(batch, "Lifes: " + lifes, 40, 40);
        font.draw(batch, "Score: " + score, 1750, 40);
		for(Brick brick: bricks)
		{
			if(brick.ingame())
				batch.draw(brick.Get_Texture(),brick.Get_X(),brick.Get_Y());
		}
		batch.draw(ball.Get_Texture(),ball.Get_X(),ball.Get_Y());
		batch.draw(reset, 1782, 942);
		batch.draw(paddle.Get_Texture(),paddle.Get_X(),paddle.Get_Y());
        if(game_over)
        {
            font.draw(batch, "Game over! Press restart to start over", MyGdxGame.HEIGHT/2, MyGdxGame.HEIGHT/2);
        }
		batch.end();
		
	}

}
