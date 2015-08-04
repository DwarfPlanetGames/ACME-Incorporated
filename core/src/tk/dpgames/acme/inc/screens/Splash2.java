package tk.dpgames.acme.inc.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Splash2 implements Screen {

	private SpriteBatch batch;
	private Texture logo = new Texture("charity.png");
	private TextureRegion overlay = new TextureRegion(new Texture("Gradient.png"));

	private float time = 0;

	@Override
	public void show() {
		time = 0;
		batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		time += delta;
		if (time < 5f && !Gdx.input.isTouched()) {
			overlay.setRegion((int) ((1 - time / 5f) * 255f), 0, 1, 1);
		} else {
			((Game) Gdx.app.getApplicationListener()).setScreen(new Title());
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(logo, 0, time * 5, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(overlay, 0, 0, 0, 0, 1, 1, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0);
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
		batch.dispose();
		logo.dispose();
	}

}
