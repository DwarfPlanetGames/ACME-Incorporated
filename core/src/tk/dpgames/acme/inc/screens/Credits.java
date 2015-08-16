package tk.dpgames.acme.inc.screens;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Credits implements Screen {
	private float time = 0;
	private float tickTime = 0;
	public static final String[] credits = new String[]{
		"",
		"Lead Programmer and Project Director",
		"Brandon Dyer",
		"","","","","",
		"Programmers",
		"Brandon Dyer",
		"Ian Green",
		"Austin White",
		"","","","","",
		"Music",
		"Oculus",
		"Unwritten Return",
		"Tempting Secrets",
		"Mellowtron",
		"Reformat",
		"","","","","",
		"Composers",
		"Luke Howard",
		"Kevin MacLeod",
		"","","","","","",
		"The Most Important",
		"You",
		
	};
	private SpriteBatch batch;
	private BitmapFont font;
	private int seed = 0;
	private Random rand;

	@Override
	public void show() {
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("default.fnt"));
        font.setColor(Color.WHITE);
        font.getData().setScale(((float)Gdx.graphics.getHeight() / 720f) * 1.1f);
        rand = new Random();
	}

	@Override
	public void render(float delta) {
		rand.setSeed(seed);
		time += Gdx.input.isTouched() ? delta * 0.66f : delta * 2;
		tickTime += delta;
		if (tickTime > 1f/2f) {
			tickTime = 0;
			seed++;
		}
		Title.tickDrops(delta);
		if (time * 100 > credits.length * 75 + Gdx.graphics.getHeight()) {
			((Game)Gdx.app.getApplicationListener()).setScreen(new Title());
		}

		// Clear
		Gdx.gl.glClearColor(0f, 0.2f, 0.4f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		Title.drawDrops(batch);
		for(int i=1 ; i < credits.length; i++){
			if (credits[i - 1].equals("")) {
				font.getData().setScale(((float)Gdx.graphics.getHeight() / 720f) * 1.6f);
			} else {
				font.getData().setScale(((float)Gdx.graphics.getHeight() / 720f) * 1.1f);
			}
        	font.draw(batch, credits[i], Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 2 - i*75 + time*100);
        }
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
