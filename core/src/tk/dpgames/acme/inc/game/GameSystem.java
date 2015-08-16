package tk.dpgames.acme.inc.game;

import java.util.LinkedList;
import java.util.Random;

import tk.dpgames.acme.inc.H;
import tk.dpgames.acme.inc.screens.Title;
import tk.dpgames.acme.inc.voxes.Vox;
import tk.dpgames.acme.inc.voxes.VoxDirt;
import tk.dpgames.acme.inc.voxes.VoxGrass;
import tk.dpgames.acme.inc.voxes.VoxRock;
import tk.dpgames.acme.inc.voxes.VoxSand;
import tk.dpgames.acme.inc.voxes.VoxWater;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class GameSystem {

	public boolean paused = true;
	public static Vox[][] voxes;

	public static Texture gradient = new Texture("Gradient.png");
	public static float[][] lighting;
	public static LinkedList<Light> lights = new LinkedList<Light>();
	// Lighting algorithm:
	// lighting += lights.get().value / (distance(lights.get()) + 1f);

	public static Texture cellSheet = new Texture("cell_sheet.png");
	public static Texture background = new Texture("underground_background.png");
	public static float time; // Number of frames from begin if running at 60
	private Texture backdrop = new Texture("backdrop.png");
	// FPS;
	public static float tickTime = 0.0f;
	public static Player player;
	public static float dayTime = 0;
	public static int sunlight = 0;

	public static OrthographicCamera camera;

	public GameSystem(OrthographicCamera camera, int width, int height) {
		voxes = new Vox[width][height];
		lighting = new float[width][height];
		paused = true;
		time = 0.0f;
		player = new Player();
		lights.add(player.light);
		GameSystem.camera = camera;
	}

	public void renderGame(SpriteBatch batch, OrthographicCamera camera) {

		// Set lighting values
		sunlight = (int) ((255 / 2f) + (float) (Math.sin(dayTime) * (255 / 2f)));

		// Set camera positions
		float xc = camera.position.x;
		float yc = camera.position.y;
		float widthc = camera.viewportWidth + 64;
		float heightc = camera.viewportHeight + 64;

		// Render sun and moon
		TextureRegion sun = new TextureRegion(cellSheet, 0, 32, 32, 32);
		batch.draw(sun, (int) (xc + Math.cos(dayTime) * (widthc * 2f)), (int) (yc - heightc / 2 + Math.sin(dayTime)
				* (heightc * 0.8f)), (int) (32 * Title.scale), (int) (32 * Title.scale));
		TextureRegion moon = new TextureRegion(cellSheet, 32, 32, 32, 32);
		batch.draw(moon, (int) (xc - Math.cos(dayTime) * (widthc * 2f)), (int) (yc - heightc / 2 - Math.sin(dayTime)
				* (heightc * 0.8f)), (int) (32 * Title.scale), (int) (32 * Title.scale));

		// Set camera locations to bottom right of screen
		xc -= widthc / 2;
		yc -= heightc / 2;

		// Render backdrop
		batch.draw(backdrop, xc - xc / 8f, yc - yc / 8f, (GameSystem.voxes.length * 16) / 8f,
				(GameSystem.voxes[0].length * 16) / 8f);

		if (camera.position.y - camera.viewportHeight / 2 < (voxes[0].length / 2 - 50) * 16) {
			for (int x = -1; x < camera.viewportWidth / 32+1; x++) {
				for (int y = -1; y < camera.viewportHeight / 32+1; y++) {
					batch.draw(background,
							camera.position.x - camera.viewportWidth / 2 + x * 32 + (-camera.position.x / 2) % 32,
							camera.position.y - camera.viewportHeight / 2 + y * 32 + (-camera.position.y / 2) % 32, 32, 32);
				}
			}
		}

		// Render lighting
		for (int x = 0; x < voxes.length; x++) {
			for (int y = 0; y < voxes[0].length; y++) {
				if (x * 16 - xc > -16 && x * 16 - xc < widthc && y * 16 - yc > -16 && y * 16 - yc < heightc) {
					lighting[x][y] = 0;
					for (int i = 0; i < lights.size(); i++) {
						if (voxes[x][y] != null)
							lighting[x][y] += lights.get(i).value
									/ (H.getDist(lights.get(i).x, lights.get(i).y, x * 16, y * 16) / 16f + 1f);
						else
							lighting[x][y] += lights.get(i).value
									/ (H.getDist(lights.get(i).x, lights.get(i).y, x * 16, y * 16) / 17f + 1f);
					}
					if (!GameSystem.voxIsCovered(voxes[x][y], x, y)) {
						lighting[x][y] += GameSystem.sunlight;
					}
					if (!GameSystem.voxIsCovered(voxes[x][y], x, y) && y >= voxes[0].length / 2 - 10 && voxes[x][y] == null) {
						lighting[x][y] += 255f;
					}
					if (GameSystem.voxIsCovered(voxes[x][y], x, y) && voxes[x][y] == null && y >= voxes[0].length / 2 - 10) {
						lighting[x][y] += 255f * 0.5f;
					}
					if (lighting[x][y] > 255)
						lighting[x][y] = 255;
					if (lighting[x][y] < 0)
						lighting[x][y] = 0;
					if ((int) ((lighting[x][y] / 255f) * 16) > 0) {
						if (voxes[x][y] != null) {
							if (!voxes[x][y].getData("render")) {
								TextureRegion tex = new TextureRegion(cellSheet, voxes[x][y].texX, voxes[x][y].texY, 16, 16);
								batch.draw(tex, x * 16, y * 16, 0, 0, 16, 16, 1, 1, 0);
							} else {
								voxes[x][y].render(batch, x, y);
							}
							if (y < voxes[0].length / 2 - 10 && y > voxes[0].length / 2 - 50 && !voxes[x][y].canCollide) {
								TextureRegion tex = new TextureRegion(cellSheet, 0, 16, 16, 16);
								batch.draw(tex, x * 16, y * 16, 0, 0, 16, 16, 1, 1, 0);
							}
						}
						if (voxes[x][y] == null) {
							if (y < voxes[0].length / 2 - 10 && y > voxes[0].length / 2 - 50) {
								TextureRegion tex = new TextureRegion(cellSheet, 0, 16, 16, 16);
								batch.draw(tex, x * 16, y * 16, 0, 0, 16, 16, 1, 1, 0);
							}
						}
					}
					if (voxes[x][y] != null) {
					}
					TextureRegion grad = new TextureRegion(cellSheet, (int) (((lighting[x][y] / 255f) * 16) * 16), 12 * 16, 1,
							1);
					batch.draw(grad, x * 16, y * 16, 0, 0, 1, 1, 16, 16, 0);
				}
			}
		}

		// Render outline
		for (int x = 0; x < voxes.length; x++) {
			for (int y = 0; y < voxes[0].length; y++) {
				if (x * 16 - xc > -16 && x * 16 - xc < widthc && y * 16 - yc > -16 && y * 16 - yc < heightc) {
					if (voxes[x][y] != null)
						outlineVox(batch, x, y);
				}
			}
		}

		// Render Player
		player.render(batch);

	}

	public void outlineVox(SpriteBatch batch, int x, int y) {
		if (voxes[x][y] == null)
			return;
		if (!voxes[x][y].canCollide)
			return;

		if (voxes[x - 1][y] == null) {
			TextureRegion t = new TextureRegion(Vox.outline, 0, 0, 2, 18);
			batch.draw(t, x * 16 - 1, y * 16 - 1);
		}
		if (voxes[x + 1][y] == null) {
			TextureRegion t = new TextureRegion(Vox.outline, 16, 0, 2, 18);
			batch.draw(t, x * 16 + 15, y * 16 - 1);
		}
		if (voxes[x][y + 1] == null) {
			TextureRegion t = new TextureRegion(Vox.outline, 0, 0, 18, 2);
			batch.draw(t, x * 16 - 1, y * 16 + 15);
		}
		if (voxes[x][y - 1] == null) {
			TextureRegion t = new TextureRegion(Vox.outline, 0, 16, 18, 2);
			batch.draw(t, x * 16 - 1, y * 16 - 1);
		}
	}

	public static boolean voxIsCovered(Vox vox, int x, int y) {
		for (int i = y + 1; i < voxes[0].length; i++) {
			if (voxes[x][i] != null) {
				if (voxes[x][i].canCollide) {
					return true;
				}
			}
		}
		return false;
	}

	public void tickGame(float delta) {
		time += delta * 60;
		tickTime += delta;
		dayTime += delta * 0.05f;
		player.tick(delta);
		if (tickTime >= 1f / 3f) {
			tickTime -= 1f / 3f;
			float xc = camera.position.x;
			float yc = camera.position.y;
			float widthc = camera.viewportWidth + 64;
			float heightc = camera.viewportHeight + 64;
			xc -= widthc / 2f;
			yc -= heightc / 2f;
			for (int x = 0; x < voxes.length; x++) {
				for (int y = 0; y < voxes[0].length; y++) {
					if (x * 16 - xc > -16 && x * 16 - xc < widthc && y * 16 - yc > -16 && y * 16 - yc < heightc) {
						if (voxes[x][y] != null)
							voxes[x][y].tick(x,y);
					}
				}
			}
		}
	}

	public void renderHud(float delta, SpriteBatch batch) {

	}

	public static void createWorld(LandType type, boolean caves, long seed) {
		Random r = new Random();
		r.setSeed(seed);
		switch (type) {
		case notFlat:
		case flat:
			int dif = 0;
			// Create terrain
			for (int x = 0; x < voxes.length; x++) {
				dif += 2 - r.nextInt(5);
				if (dif > 10)
					dif = 10;
				if (dif < -10)
					dif = -10;
				for (int y = 0; y < voxes[0].length; y++) {
					int yl = voxes[0].length - y - dif;
					if (r.nextInt(24) != 0 || !caves) {
						if (yl == voxes[0].length / 2) {
							voxes[x][y] = new VoxGrass();
						} else if (yl < voxes[0].length / 2 + 5 && yl > voxes[0].length / 2) {
							voxes[x][y] = new VoxDirt();
						} else if (yl > voxes[0].length / 2) {
							if (r.nextInt(14) != 0) {
								voxes[x][y] = new VoxRock();
							} else {
								voxes[x][y] = new VoxDirt();
							}
						} else {
							voxes[x][y] = null;
						}
					}
				}
			}

			// Cut out caves
			for (int i = 0; i < voxes.length * 5; i++) {
				int s = r.nextInt(10);
				int xl = r.nextInt(voxes.length - s - 2) + 1;
				int yl = r.nextInt(voxes[0].length - s - 2) + 1;
				for (int x = xl - 1; x < xl + s + 1; x++) {
					for (int y = yl - 1; y < yl + s + 1; y++) {
						if (H.getDist(x, y, xl + s / 2f, yl + s / 2f) < s / 2f)
							voxes[x][y] = null;
						else if (H.getDist(x, y, xl + s / 2f, yl + s / 2f) < s / 2f + 1 && voxes[x][y] != null)
							if (r.nextInt(3) == 0)
								voxes[x][y] = new VoxDirt();
							else
								voxes[x][y] = new VoxSand();
					}
				}
			}
			break;
		}
	}

	public static Rectangle getCameraRect() {
		return new Rectangle(camera.position.x - camera.viewportWidth / 2f, camera.position.y - camera.viewportHeight,
				camera.viewportWidth, camera.viewportHeight);
	}

	public enum LandType {
		flat, notFlat;
	}

}
