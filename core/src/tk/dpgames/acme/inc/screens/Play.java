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
			((Game)Gdx.app.getApplicationListener()).setScreen(new PlayDesktop());
			break;
		case Android:
		case iOS:
		default:
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
