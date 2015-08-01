package tk.dwarfplanetgames.main.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Button extends Actor {

	TextureRegion tex;

	public Button(int x, int y, int width, int height, TextureRegion tex) {
		super(x, y, width, height);
		this.tex = tex;
	}

	@Override
	public void touchUp(int localX, int localY) {
		tap(localX, localY);
	}

	public abstract void tap(int localX, int localY);

	@Override
	public void draw(SpriteBatch batch, Texture skin) {
		batch.draw(tex, x, y, width, height);
	}

}
