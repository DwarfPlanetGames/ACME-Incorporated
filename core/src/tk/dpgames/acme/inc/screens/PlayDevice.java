package tk.dpgames.acme.inc.screens;

import tk.dpgames.acme.inc.H;
import tk.dpgames.acme.inc.game.GameSystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayDevice implements Screen {

	public GameSystem game;
	public boolean hudOpen = false;
	public OrthographicCamera camera;

	private SpriteBatch gameBatch;
	private SpriteBatch hudBatch;

	@Override
	public void show() {
		game = new GameSystem();
		hudOpen = true;
		gameBatch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth() / Title.scale, Gdx.graphics.getHeight() / Title.scale);
		game.paused = false;
	}

	@Override
	public void render(float delta) {
		if (!game.paused) {
			game.tickGame(delta);
			camera.position.y = GameSystem.time;
			H.clampCam(camera, 0, 0, (int)(GameSystem.voxes.length * 16), (int)(GameSystem.voxes[0].length * 16));
		}
		H.clear(0f, 0.2f, 0.4f);
		camera.update();
		gameBatch.begin();
		game.renderGame(gameBatch, camera);
		gameBatch.end();
		gameBatch.setProjectionMatrix(camera.combined);
		hudBatch.begin();
		if (hudOpen) {
			game.renderHud(delta, hudBatch);
		}
		hudBatch.end();
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
