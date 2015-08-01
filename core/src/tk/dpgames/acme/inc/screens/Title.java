package tk.dpgames.acme.inc.screens;

import java.util.LinkedList;
import java.util.Random;

import tk.dwarfplanetgames.main.ui.Button;
import tk.dwarfplanetgames.main.ui.Stage;

import com.badlogic.gdx.Game;
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
	private static Texture rainDrop = new Texture("rain_drop.png");

	private float time = 0;

	public static float scale;
	public static Music music = Gdx.audio.newMusic(Gdx.files
			.internal("title.mp3"));

	public static LinkedList<RainDrop> rainDrops = new LinkedList<RainDrop>();

	private Stage stage;
	private Button buttonPlay;
	private Button buttonCredits;

	@Override
	public void show() {
		time = 0;
		batch = new SpriteBatch();
		Random rand = new Random();
		rand.setSeed(System.nanoTime());
		for (int i = 0; i < Gdx.graphics.getHeight(); i++) {
			rainDrops.add(new RainDrop(i * 2, rand.nextInt(Gdx.graphics
					.getHeight()), rand.nextFloat() + 0.5f + rand.nextFloat()));
		}

		// Setup title screen buttons
		stage = new Stage(new Texture("skin.png"));
		buttonPlay = new Button(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, 128, 64, new TextureRegion(
						new Texture("title_buttons.png"), 0, 0, 128, 64)) {
			public void tap(int localX, int localY) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new WorldSelect());
			}
		};
		stage.addActor(buttonPlay);
		buttonCredits = new Button(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 4, 128, 64, new TextureRegion(
						new Texture("title_buttons.png"), 0, 64, 128, 64)) {
			public void tap(int localX, int localY) {
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new Credits());
			}
		};
		stage.addActor(buttonCredits);
	}

	@Override
	public void render(float delta) {
		// Set scaling
		scale = (Gdx.graphics.getHeight() / 720f) * 2f;
		
		//Set button scale
		buttonPlay.width = (int)(128*scale);
		buttonPlay.height = (int)(64 * scale);
		buttonPlay.x = Gdx.graphics.getWidth() / 2 - buttonPlay.width / 2;
		buttonPlay.y = Gdx.graphics.getHeight() / 2 - (int)(32 * scale);
		buttonCredits.width = (int)(128*scale);
		buttonCredits.height = (int)(64 * scale);
		buttonCredits.x = Gdx.graphics.getWidth() / 2 - buttonCredits.width / 2;

		// Set timing and overlay
		time += delta;
		if (time < 5f) {
			overlay.setRegion((int) ((1 - time / 5f) * 255f), 0, 1, 1);
		}

		// Move rain drops
		tickDrops(delta);
		
		//Act stage
		stage.act(delta);
		
		//Clear
		Gdx.gl.glClearColor(0f, 0.2f, 0.4f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		// /// - Render
		
		drawDrops(batch);
		
		stage.draw(batch);

		batch.draw(title, Gdx.graphics.getWidth() / 2 - (title.getWidth() / 2)
				* scale, Gdx.graphics.getHeight() - title.getHeight() * scale,
				title.getWidth() * scale, title.getHeight() * scale);
		batch.draw(overlay, 0, 0, 0, 0, 1, 1, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight(), 0);

		// /// - End Render
		
		batch.end();
	}
	
	public static void tickDrops(float delta) {
		for (int i = 0; i < rainDrops.size(); i++) {
			if (!Gdx.input.isTouched()) {
				rainDrops.get(i).y -= rainDrops.get(i).speed * delta * 600;
				rainDrops.get(i).x -= rainDrops.get(i).speed * delta * 100;
			} else {
				/*rainDrops.get(i).y -= (rainDrops.get(i).speed * delta * 600)
						* Math.abs(rainDrops.get(i).x - Gdx.input.getX())
						/ (Gdx.graphics.getWidth() / 2);*/
				rainDrops.get(i).y -= rainDrops.get(i).speed * delta * 100;
				rainDrops.get(i).x -= rainDrops.get(i).speed * delta * 16;
			}
			if (rainDrops.get(i).y < -64) {
				rainDrops.get(i).y += Gdx.graphics.getHeight() + 64;
				rainDrops.get(i).x = rainDrops.get(i).xo;
			}
		}
	}
	
	public static void drawDrops(SpriteBatch batch) {
		for (int i = 0; i < rainDrops.size(); i++) {
			batch.draw(rainDrop, rainDrops.get(i).x, rainDrops.get(i).y,
					2 * rainDrops.get(i).speed, 64 * rainDrops.get(i).speed);
		}
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
		float xo;
		float x = 0;
		float y = 0;
		float speed = 1;

		public RainDrop(float x, float y, float speed) {
			this.x = x;
			this.y = y;
			this.speed = speed;
			xo = x;
		}
	}

}
