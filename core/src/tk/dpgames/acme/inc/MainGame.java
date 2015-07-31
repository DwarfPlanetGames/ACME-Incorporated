package tk.dpgames.acme.inc;

import tk.dpgames.acme.inc.screens.Splash1;

import com.badlogic.gdx.Game;

public class MainGame extends Game {
	
	@Override
	public void create () {
		setScreen(new Splash1());
	}
}
