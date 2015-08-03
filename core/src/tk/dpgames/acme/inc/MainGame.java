package tk.dpgames.acme.inc;

import tk.dpgames.acme.inc.screens.Splash1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class MainGame extends Game {
	
	@Override
	public void create () {
		Gdx.input.setCatchBackKey(true);
		setScreen(new Splash1());
	}
}
