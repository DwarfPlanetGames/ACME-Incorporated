package tk.dpgames.acme.inc.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class Play implements Screen {

	@Override
	public void show() {
		switch (Gdx.app.getType()) {
		case Applet:
		case Desktop:
		case HeadlessDesktop:
		case WebGL:
			System.out.println("Starting desktop world");
			((Game)Gdx.app.getApplicationListener()).setScreen(new PlayDesktop());
			break;
		case Android:
		case iOS:
		default:
			System.out.println("Starting phone and tablet world");
			((Game)Gdx.app.getApplicationListener()).setScreen(new PlayDevice());
			break;
		
		}
	}

	@Override
	public void render(float delta) {
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
