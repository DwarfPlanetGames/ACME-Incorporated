package tk.dpgames.acme.inc.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Item {
	
	public static final Texture itemTexture = new Texture("items.png");
	public TextureRegion tex;
	public float miningPower = 0;
	public float hitPower = 0;
	public boolean stackable;
	public String id = "item";

	public Item(TextureRegion tex, String id) {
		this.tex = tex;
		tex.setRegionWidth(16);
		tex.setRegionHeight(16);
		this.id = id;
		run();
	}
	
	protected void run() {
		
	}
	
	public void usedOnPoint(float x, float y) {
		
	}

}
