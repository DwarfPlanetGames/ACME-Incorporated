package tk.dpgames.acme.inc.screens;

import tk.dpgames.acme.inc.game.GameSystem;
import tk.dpgames.acme.inc.game.GameSystem.LandType;
import tk.dwarfplanetgames.main.ui.Button;
import tk.dwarfplanetgames.main.ui.Expansion;
import tk.dwarfplanetgames.main.ui.Stage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WorldSelect implements Screen {

	private SpriteBatch batch;
	private Stage stage;
	// Background Expansion
	private Expansion backExp;
	private Button buttonBack;

	private GlyphLayout layout;
	private BitmapFont font;

	@Override
	public void show() {
		batch = new SpriteBatch();
		stage = new Stage(new Texture("skin.png"));
		backExp = new Expansion(24 * Title.scale, 20 * Title.scale, Gdx.graphics.getWidth() - 48 * Title.scale,
				Gdx.graphics.getHeight() - 40 * Title.scale, 6, Title.scale * 2, stage, 0);
		stage.addActor(backExp);
		Expansion buttonBackBack = new Expansion(30 * Title.scale, Gdx.graphics.getHeight() - 26 * Title.scale - 32
				* Title.scale, 64 * Title.scale, 32 * Title.scale, 6, Title.scale, stage, 1);
		stage.addActor(buttonBackBack);
		buttonBack = new Button(buttonBackBack, 6, new TextureRegion(new Texture("back.png"))) {
			public void tap(int localX, int localY) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new Title());
			}
		};
		stage.addActor(buttonBack);
		Button go = new Button(0,0,(int)(128*Title.scale),(int)(64*Title.scale),new TextureRegion(new Texture("title_buttons.png"),0,0,128,64)){
			public void tap(int localX, int localY) {
				GameSystem.createWorld(LandType.flat, true);
				((Game)Gdx.app.getApplicationListener()).setScreen(new Play());
			}
		};
		stage.addActor(go);
		font = new BitmapFont(Gdx.files.internal("default.fnt"));
		font.setColor(1.0f,0.7f,0.5f,1f);
		font.getData().setScale(((float) Gdx.graphics.getHeight() / 720f) * 1.6f);
		layout = new GlyphLayout(font, "Select World");
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
		font.draw(batch, "Select World", Gdx.graphics.getWidth() / 2 - layout.width / 2, backExp.y + backExp.height
				- backExp.offset - layout.height);
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
