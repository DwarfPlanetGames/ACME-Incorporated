package tk.dpgames.acme.inc.game;

import tk.dpgames.acme.inc.voxes.Vox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player {
	public float x;
	public float y;
	public float width = 12;
	public float height = 24;
	public float direction = 0;
	public float velX = 0, velY = 0;
	public float gravity = 5f;
	public int voxX, voxY;
	public float texAnim = 0;

	private Texture tex = new Texture("player_anim.png");
	private Texture m = new Texture("cell_sheet.png");

	public Player() {
		x = (GameSystem.voxes.length / 2) * 16;
		y = (GameSystem.voxes[0].length / 2) * 16 + 16 * 5;
		voxX = (GameSystem.voxes.length / 2);
		voxY = (GameSystem.voxes[0].length / 2) + 5;
	}

	public void tick(float delta) {
		velY -= gravity;
		if (Gdx.input.isTouched()) {
			velY += 20;
		}
		velX += 1f;
		collide(delta);
		texAnim += velX * delta;
		y += velY * delta;
		x += velX * delta;
	}

	public void render(SpriteBatch batch) {
		TextureRegion reg;
		if (velY != 0){
			reg = new TextureRegion(tex, 12, 0, 12, 24);
		} else if (velX <= -0.1f || velX >= 0.1f){
			reg = new TextureRegion(tex,24 + ((int)texAnim % 4) * 12, 0, 12, 24);
		} else {
			reg = new TextureRegion(tex, 0, 0, 12, 24);
		}
		if (velX < 0) {
			reg.flip(true,false);
		}
		batch.draw(reg, x, y, width, height);
	}

	public void collide(float delta) {
		for (int x = (int) this.x / 16 - 8; x < (int) this.x / 16 + 8; x++) {
			if (x > GameSystem.voxes.length) continue;
			if (x < 0) continue;
			for (int y = (int) this.y / 16 - 8; y < (int) this.y / 16 + 8; y++) {
				if (y > GameSystem.voxes[0].length) continue;
				if (y < 0) continue;
				Vox vox = GameSystem.voxes[x][y];
				if (vox != null) {
					if (vox.canCollide) {
						Rectangle col = new Rectangle(x * 16, y * 16, 16, 16);
						if (col.overlaps(getBounds(Side.btm, delta))) {
							velY = 0;
							this.y = y * 16 + 16;
						}
						if (col.overlaps(getBounds(Side.top, delta))) {
							velY = 0;
							this.y = y * 16 - height;
						}
						if (col.overlaps(getBounds(Side.rit, delta))) {
							velX = 0;
							this.x = x * 16 - width;
						}
						if (col.overlaps(getBounds(Side.lft, delta))) {
							velX = 0;
							this.x = x * 16 + 16;
						}
					}
				}
			}
		}
	}

	public Rectangle getBounds(Side side, float delta) {
		Rectangle r;
		switch (side) {
		case top:
			r = new Rectangle(x + 2, y + height + velY * delta, width - 4, -velY * delta);
			if (r.height < 0)
				r.height = 0;
			return r;
		case btm:
			r = new Rectangle(x + 2, y + velY * delta, width - 4, -velY * delta);
			if (r.height > 0)
				r.height = 0;
			return r;
		case lft:
			r = new Rectangle(x + velX * delta, y + 2, -velX * delta, height - 4);
			if (r.width > 0)
				r.width = 0;
			return r;
		case rit:
			r = new Rectangle(x + width + velX * delta, y + 2, -velX * delta, height - 4);
			if (r.width < 0)
				r.width = 0;
			return r;
		default:
			r = new Rectangle(x, y, width, height);
			return r;
		}
	}
}
