package tk.dpgames.acme.inc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Splash1 implements Screen {
	
	private SpriteBatch batch;
	private BitmapFont font;
	private Texture logo = new Texture("logo.png");
	
	private float time = 0;
	
	@Override
	public void show() {
		time = 0;
		batch = new SpriteBatch();
		font = new BitmapFont();
	}

	@Override
	public void render(float delta) {
		time += delta;
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(logo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}
	
}
