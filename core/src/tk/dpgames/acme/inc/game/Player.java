package tk.dpgames.acme.inc.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
	public float x;
	public float y;
	public float width = 12;
	public float height = 24;
	
	public Player() {
		x = (GameSystem.voxes.length / 2) * 16;
		y = (GameSystem.voxes[0].length / 2) * 16 + 16 * 5;
	}
	
	public void render(SpriteBatch batch) {
		
	}
}
