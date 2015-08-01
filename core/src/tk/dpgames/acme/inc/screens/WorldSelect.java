package tk.dpgames.acme.inc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WorldSelect implements Screen {
	
	private SpriteBatch batch;
	
	@Override
	public void show() {
		batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		Title.tickDrops(delta);
		
		//Clear
		Gdx.gl.glClearColor(0f, 0.2f, 0.4f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		Title.drawDrops(batch);
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
