package tk.dwarfplanetgames.main.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Expansion extends Actor {

	public int offset = 5;
	public float scale = 1f;
	public TextureRegion center;
	public TextureRegion[] corners = new TextureRegion[4];
	public TextureRegion[] sides = new TextureRegion[4];
	public Stage stage;
	public int v = 0;

	public Expansion(float x, float y, float width, float height, int offset, Stage stage) {
		super((int) x, (int) y, (int) width, (int) height);
		this.offset = offset;
		this.stage = stage;
		make();
	}

	public Expansion(float x, float y, float width, float height, int offset, float scale, Stage stage, int v) {
		super((int) x, (int) y, (int) width, (int) height);
		this.offset = offset;
		this.scale = scale;
		this.stage = stage;
		this.v = v * 32;
		make();
	}

	private void make() {
		int off = 32 * 4 + v;
		center = new TextureRegion(stage.skin, off + offset, offset, 32 - offset * 2, 32 - offset * 2);
		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < 2; x++) {
				corners[x + (1 - y) * 2] = new TextureRegion(stage.skin, x * (32 - offset) + off, y * (32 - offset), offset,
						offset);
			}
		}
		sides[0] = new TextureRegion(stage.skin, off + offset, 0, 32 - offset * 2, offset);
		sides[1] = new TextureRegion(stage.skin, off + 32 - offset, offset, offset, 32 - offset * 2);
		sides[2] = new TextureRegion(stage.skin, off + offset, 32 - offset, 32 - offset * 2, offset);
		sides[3] = new TextureRegion(stage.skin, off, offset, offset, 32 - offset * 2);
	}

	@Override
	public void draw(SpriteBatch batch, Texture skin) {
		batch.draw(center, x + offset * scale, y + offset * scale, width - offset * scale * 2, height - offset * scale * 2);
		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < 2; x++) {
				batch.draw(corners[x + y * 2], this.x + x * (width - offset * scale), this.y + y * (height - offset * scale),
						0, 0, corners[x + y * 2].getRegionWidth(), corners[x + y * 2].getRegionHeight(), scale, scale, 0);
			}
		}
		// batch.draw(region, x, y, oX, oY, w, h, scaleX, scaleY, rotation);
		batch.draw(sides[0], x + offset * scale, y + height - offset * scale, 0, 0, 1, offset, width - offset * 2 * scale,
				scale, 0);
		batch.draw(sides[1], x + width - offset * scale, y + offset * scale, 0, 0, offset, 1, scale, height - offset * 2
				* scale, 0);
		batch.draw(sides[2], x + offset * scale, y, 0, 0, 1, offset, width - offset * 2 * scale, scale, 0);
		batch.draw(sides[3], x, y + offset * scale, 0, 0, offset, 1, scale, height - offset * 2 * scale, 0);
	}

}
