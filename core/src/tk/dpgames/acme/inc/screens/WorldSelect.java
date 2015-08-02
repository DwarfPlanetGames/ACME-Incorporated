package tk.dpgames.acme.inc.screens;

import tk.dwarfplanetgames.main.ui.Expansion;
import tk.dwarfplanetgames.main.ui.Stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WorldSelect implements Screen {

	private SpriteBatch batch;
	private Stage stage;
	// Background Expansion
	private Expansion backExp;

	@Override
	public void show() {
		batch = new SpriteBatch();
		stage = new Stage(new Texture("skin.png"));
		backExp = new Expansion(24 * Title.scale, 20 * Title.scale, Gdx.graphics.getWidth() - 48 * Title.scale,
				Gdx.graphics.getHeight() - 40 * Title.scale, 6, Title.scale*2, stage);
		stage.addActor(backExp);
	}

	@Override
	public void render(float delta) {
		Title.tickDrops(delta);

		// Clear
		Gdx.gl.glClearColor(0f, 0.2f, 0.4f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);

		batch.begin();
		Title.drawDrops(batch);
		stage.draw(batch);
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
