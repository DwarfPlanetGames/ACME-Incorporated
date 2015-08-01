package tk.dpgames.acme.inc.screens;

import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Title implements Screen {

	private SpriteBatch batch;
	private Texture title = new Texture("title.png");
	private TextureRegion overlay = new TextureRegion(new Texture(
			"Gradient.png"));
	private Texture rainDrop = new Texture("rain_drop.png");

	private float time = 0;

	public static float scale;
	public static Music music = Gdx.audio.newMusic(Gdx.files
			.internal("title.mp3"));

	public LinkedList<RainDrop> rainDrops = new LinkedList<RainDrop>();

	@Override
	public void show() {
		time = 0;
		batch = new SpriteBatch();
		Random rand = new Random();
		rand.setSeed(System.nanoTime());
		for (int i = 0; i < Gdx.graphics.getHeight(); i++) {
			rainDrops.add(new RainDrop(i * 2, rand.nextInt(Gdx.graphics
					.getHeight()), rand.nextFloat() + 0.5f));
		}
	}

	@Override
	public void render(float delta) {
		scale = (Gdx.graphics.getHeight() / 720f) * 2f;
		time += delta;
		if (time < 5f && !Gdx.input.isTouched()) {
			overlay.setRegion((int) ((1 - time / 5f) * 255f), 0, 1, 1);
		}

		for (int i = 0; i < rainDrops.size(); i++) {
			if (!Gdx.input.isTouched()) {
				rainDrops.get(i).y -= rainDrops.get(i).speed * delta * 600;
			} else {
				rainDrops.get(i).y -= (rainDrops.get(i).speed * delta * 600) * Math.abs(rainDrops.get(i).x - Gdx.input.getX()) / (Gdx.graphics.getWidth()/2);
			}
			if (rainDrops.get(i).y < -64)
				rainDrops.get(i).y += Gdx.graphics.getHeight() + 64;
		}

		Gdx.gl.glClearColor(0f, 0.2f, 0.4f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		// /// - Render

		for (int i = 0; i < rainDrops.size(); i++) {
			batch.draw(rainDrop, rainDrops.get(i).x, rainDrops.get(i).y, 2 * rainDrops.get(i).speed, 64 * rainDrops.get(i).speed);
		}

		batch.draw(title, Gdx.graphics.getWidth() / 2 - (title.getWidth() / 2)
				* scale, Gdx.graphics.getHeight() - title.getHeight() * scale,
				title.getWidth() * scale, title.getHeight() * scale);
		batch.draw(overlay, 0, 0, 0, 0, 1, 1, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight(), 0);

		// /// - End Render
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

	public class RainDrop {
		float x = 0;
		float y = 0;
		float speed = 1;

		public RainDrop(float x, float y, float speed) {
			this.x = x;
			this.y = y;
			this.speed = speed;
		}
	}

}
