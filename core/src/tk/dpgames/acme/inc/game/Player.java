package tk.dpgames.acme.inc.game;

import java.util.LinkedList;

import tk.dpgames.acme.inc.H;
import tk.dpgames.acme.inc.voxes.Vox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
	public float x;
	public float y;
	public float width = 12;
	public float height = 24;
	public float direction = 0;
	public float velX = 0, velY = 0;
	public float gravity = 10f;
	public int voxX, voxY;
	public float texAnim = 0;
	public boolean touchingGround = false;
	public boolean mining = false;
	public PointMine miningByPoint = new PointMine(false, 0, 0);
	public float jumpHeight = 200f;
	public float jumpCont = 4f;
	public Light light;
	public int mineDistance = 8;
	public float miningPower = 500;

	private Texture tex = new Texture("player_anim.png");

	public Player() {
		x = (GameSystem.voxes.length / 2) * 16;
		y = (GameSystem.voxes[0].length / 2) * 16 + 16 * 10;
		voxX = (GameSystem.voxes.length / 2);
		voxY = (GameSystem.voxes[0].length / 2) + 5;
		light = new Light(550, x, y);
	}

	public class PointMine {
		public boolean mining;
		public int x, y;

		public PointMine(boolean mining, int x, int y) {
			this.mining = mining;
			this.x = x;
			this.y = y;
		}
	}

	public void tick(float delta) {
		velY -= gravity;
		velX *= 0.9;
		velY *= 0.9;
		if (velY * delta > height - 1)
			velY = (height - 1) / delta;
		if (velY * delta < -height + 1)
			velY = (-height + 1) / delta;
		if (velX * delta > width - 1)
			velX = (width - 1) / delta;
		if (velX * delta < -width + 1)
			velX = (-width + 1) / delta;
		if (y < 16 * 4) {
			y = 16 * 4;
			velY = 0;
		}
		if (y > GameSystem.voxes[0].length * 16 - 16 * 4) {
			y = GameSystem.voxes[0].length * 16 - 16 * 4;
			velY *= -1;
		}
		if (x < 16 * 4) {
			x = 16 * 4;
			velX *= -1;
		}
		if (x > GameSystem.voxes.length * 16 - 16 * 4) {
			x = GameSystem.voxes.length * 16 - 16 * 4;
			velX *= -1;
		}
		collide(delta);
		texAnim += Math.abs(velX * delta * 0.1f);
		y += velY * delta;
		x += velX * delta;
		light.x = x;
		light.y = y + height / 4;
		if (mining) {
			mine();
		} else if (miningByPoint.mining) {
			mineByPoint();
		}
		if (touchingGround) {
			velX *= 0.5;
		}
	}

	public void render(SpriteBatch batch) {
		int w = 14;
		int h = 26;
		TextureRegion reg;
		if (!touchingGround) {
			reg = new TextureRegion(tex, w, 0, w, h);
		} else if (velX <= -0.1f || velX >= 0.1f) {
			reg = new TextureRegion(tex, h + 2 + ((int) texAnim % 4) * w, 0, w, h);
		} else {
			reg = new TextureRegion(tex, 0, 0, w, h);
		}
		if (velX < 0) {
			reg.flip(true, false);
		}
		batch.draw(reg, x - 1, y - 1, w, h);
		if (mining)
			batch.draw(tex, x, y, (float) Math.cos(direction) * 64, (float) Math.sin(direction) * 64);
	}

	// public void mine() : currently broken
	// TODO fix public void mine()
	public void mine() {
		float delta = Gdx.graphics.getDeltaTime();

		// Player location
		System.out.println("Player Location");
		int xl = (int) (x);
		int yl = (int) (y + height / 2 - 8);

		// List of voxes touching ray
		System.out.println("List voxes");
		LinkedList<Vector2> points = new LinkedList<Vector2>();

		// Sift voxes
		System.out.println("Sift Voxes");
		Rectangle cam = GameSystem.getCameraRect();
		int cx = (int) cam.x / 16;
		int cy = (int) cam.y / 16;
		int cxe = cx + (int) cam.width / 16;
		int cye = cy + (int) cam.height / 16;
		for (int x = cx; x < cxe; x++) {
			for (int y = cx; y < cye; y++) {
				if (GameSystem.voxes[x][y] != null && H.getDist(this.x, this.y, x * 16, y * 16) <= mineDistance * 16) {
					// Vox location
					int xr = x * 16;
					int yr = y * 16;

					// Calculate rectangle
					Rectangle r = new Rectangle(xr, yr, 16, 16);

					// Calculate intersection
					for (int i = 0; i < mineDistance * 2; i++) {
						if (r.contains((float) (this.x + Math.cos(direction) * i * 8), (float) (this.y + Math.sin(direction)
								* i * 8))) {
							Vector2 a = new Vector2(x, y);
							if (!points.contains(a)) {
								points.add(a);
							}
						}
					}
				}
			}
		}
		System.out.println("Check point size");
		if (points.size() <= 0) {
			System.out.println("Smaller tan 1. quitting");
			return;
		}

		System.out.println("getting closest");
		Vector2 closest = new Vector2(points.getFirst());
		for (int i = 0; i < points.size(); i++) {
			if (H.getDist(xl, yl, points.get(i).x, points.get(i).y) < H.getDist(xl, yl, closest.x, closest.y)) {
				closest = new Vector2(points.get(i).x, points.get(i).y);
			}
		}
		System.out.println("checking canMine");
		if (GameSystem.voxes[(int) closest.x][(int) closest.y].canMine) {
			System.out.println("starting mine");
			GameSystem.voxes[(int) closest.x][(int) closest.y].mine((int) closest.x, (int) closest.y, delta, miningPower);
		} else {
			System.out.println("cannot mine");
		}
	}

	public void mineByPoint() {
		int voxX = miningByPoint.x / 16;
		int voxY = miningByPoint.y / 16;
		if (GameSystem.voxes[voxX][voxY] == null)
			return;
		GameSystem.voxes[voxX][voxY].mine(voxX, voxY, Gdx.graphics.getDeltaTime(), miningPower);
	}

	public void collide(float delta) {
		touchingGround = false;
		for (int x = (int) this.x / 16 - 8; x < (int) this.x / 16 + 8; x++) {
			if (x > GameSystem.voxes.length)
				continue;
			if (x < 0)
				continue;
			for (int y = (int) this.y / 16 - 8; y < (int) this.y / 16 + 8; y++) {
				if (y > GameSystem.voxes[0].length)
					continue;
				if (y < 0)
					continue;
				if (GameSystem.voxes[x][y] == null)
					continue;
				Vox vox = GameSystem.voxes[x][y];
				if (vox != null) {
					if (vox.canCollide) {
						Rectangle col = new Rectangle(x * 16, y * 16, 16, 16);
						if (col.overlaps(getBounds(Side.btm, delta))) {
							velY = 0;
							this.y = y * 16 + 16;
							touchingGround = true;
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
