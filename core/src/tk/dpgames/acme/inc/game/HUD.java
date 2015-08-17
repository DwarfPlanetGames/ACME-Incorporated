package tk.dpgames.acme.inc.game;

import tk.dpgames.acme.inc.screens.Title;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HUD {

	public Device device;
	public static final Texture quikventory = new Texture("quikventory.png");
	public static final Texture heart = new Texture("heart.png");

	public HUD(Device device) {
		this.device = device;
	}

	public void render(SpriteBatch batch) {
		batch.draw(quikventory, 0, Gdx.graphics.getHeight() - 32 * Title.scale, quikventory.getWidth() * Title.scale,
				32 * Title.scale);
		for (int i = 0; i < GameSystem.player.health; i++) {
			batch.draw(heart, i * (16 * Title.scale) + 8 * Title.scale, Gdx.graphics.getHeight() - 32 * Title.scale - 16
					* Title.scale, 16 * Title.scale, 16 * Title.scale);
		}
	}

	public static enum Device {
		mobile, desktop,
	}

}
