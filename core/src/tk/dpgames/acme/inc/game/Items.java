package tk.dpgames.acme.inc.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public enum Items {
	
	sword(new Item(new TextureRegion(Item.itemTexture,0,0)){
		void run() {
			hitPower = 3;
		}
	});
	
	;
	public Item item;
	
	private Items(Item item) {
		this.item = item;
	}
	
}
