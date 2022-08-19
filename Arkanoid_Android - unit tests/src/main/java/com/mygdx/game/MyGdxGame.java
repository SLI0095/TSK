package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class MyGdxGame extends ApplicationAdapter {
	public static int WIDTH = 1920;
	public static int HEIGHT = 1080;

	Playground playground;

	@Override
	public void create () {
		playground = new Playground();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		playground.handle_input();
		playground.update(Gdx.graphics.getDeltaTime());
		playground.render();
	}

	//@Override
	/*public void dispose () {
		batch.dispose();
		img.dispose();
	}*/
}
