package tk.dpgames.acme.inc.game;

import java.util.LinkedList;
import java.util.Random;

import tk.dpgames.acme.inc.H;
import tk.dpgames.acme.inc.screens.Title;
import tk.dpgames.acme.inc.voxes.Vox;
import tk.dpgames.acme.inc.voxes.VoxDirt;
import tk.dpgames.acme.inc.voxes.VoxGrass;
import tk.dpgames.acme.inc.voxes.VoxRock;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameSystem {

	public boolean paused = true;
	public static Vox[][] voxes = new Vox[512 * 2][256];

	public static Texture gradient = new Texture("Gradient.png");
	public static float[][] lighting = new float[512 * 2][256];
	public static LinkedList<Light> lights = new LinkedList<Light>();
	// Lighting algorithm:
	// lighting += lights.get().value / (distance(lights.get()) + 1f);

	public static Texture cellSheet = new Texture("cell_sheet.png");
	public static float time; // Number of frames from begin if running at 60
	private Texture backdrop = new Texture("backdrop.png");
	// FPS;
	public static float tickTime = 0.0f;
	public static Player player;
	public static float dayTime = 0;
	public static int sunlight = 0;

	public GameSystem() {
		paused = true;
		time = 0.0f;
		player = new Player();
		lights.add(player.light);
	}

	public void renderGame(SpriteBatch batch, OrthographicCamera camera) {

		// Set lighting values
		sunlight = (int) ((255 / 2f) + (float) (Math.sin(dayTime) * (255 / 2f)));

		// Set camera positions
		float xc = camera.position.x;
		float yc = camera.position.y;
		float widthc = camera.viewportWidth + 32;
		float heightc = camera.viewportHeight + 32;

		// Render sun and moon
		TextureRegion sun = new TextureRegion(cellSheet, 0, 32, 32, 32);
		batch.draw(sun, (int) (xc + Math.cos(dayTime) * (widthc * 2f)), (int) (yc - heightc / 2 + Math.sin(dayTime)
				* (heightc * 0.8f)), (int) (32 * Title.scale), (int) (32 * Title.scale));
		TextureRegion moon = new TextureRegion(cellSheet, 32, 32, 32, 32);
		batch.draw(moon, (int) (xc - Math.cos(dayTime) * (widthc * 2f)), (int) (yc - heightc / 2 - Math.sin(dayTime)
				* (heightc * 0.8f)), (int) (32 * Title.scale), (int) (32 * Title.scale));

		// Set camera locations to bottom right of screen
		xc -= widthc / 2 + 16;
		yc -= heightc / 2 + 16;

		// Render backdrop
		batch.draw(backdrop, xc - xc / 8f, yc - yc / 8f, (GameSystem.voxes.length * 16) / 8f,
				(GameSystem.voxes[0].length * 16) / 8f);

		// Render Voxes
		for (int y = 0; y < voxes[0].length; y++) {
			for (int x = 0; x < voxes.length; x++) {
				if (voxes[x][y] != null && x * 16 - xc > -16 && x * 16 - xc < widthc && y * 16 - yc > -16
						&& y * 16 - yc < heightc && true) {
					TextureRegion tex = new TextureRegion(cellSheet, voxes[x][y].texX, voxes[x][y].texY, 16, 16);
					batch.draw(tex, x * 16, y * 16, 0, 0, 16, 16, 1, 1, 0);
				}
				if (voxes[x][y] == null && x * 16 - xc > -16 && x * 16 - xc < widthc && y * 16 - yc > -16
						&& y * 16 - yc < heightc && true) {
					if (y < voxes[0].length / 2 - 50 && y > voxes[0].length / 2 - 100) {
						TextureRegion tex = new TextureRegion(cellSheet, 0, 16, 16, 16);
						batch.draw(tex, x * 16, y * 16, 0, 0, 16, 16, 1, 1, 0);
					}
				}
			}
		}
		
		// Render Player
		player.render(batch);
		
		// Render lighting
		for (int x = 0; x < voxes.length; x++) {
			for (int y = 0; y < voxes[0].length; y++) {
				if (x * 16 - xc > -16 && x * 16 - xc < widthc && y * 16 - yc > -16 && y * 16 - yc < heightc) {
					lighting[x][y] = 0;
					for (int i = 0; i < lights.size(); i++) {
						lighting[x][y] += lights.get(i).value / (H.getDist(lights.get(i).x, lights.get(i).y, x*16, y*16) / 16f + 1f);
					}
					if (!GameSystem.voxIsCovered(voxes[x][y], x, y) && voxes[x][y] != null) {
						lighting[x][y] += GameSystem.sunlight;
					} else if (!GameSystem.voxIsCovered(voxes[x][y], x, y)) {
						lighting[x][y] += 255f;
					}
					if (GameSystem.voxIsCovered(voxes[x][y], x, y) && voxes[x][y] == null && y >= voxes[0].length / 2 - 50) {
						lighting[x][y] += 255f * 0.5f;
					}
					if (lighting[x][y] > 255) lighting[x][y] = 255;
					if (lighting[x][y] < 0) lighting[x][y] = 0;
					TextureRegion grad = new TextureRegion(cellSheet, (int)((lighting[x][y] / 255f) * 16) * 16, 12*16, 1, 1);
					batch.draw(grad, x * 16, y * 16, 0, 0, 1, 1, 16, 16, 0);
				}
			}
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
			for (int x = 0; x < voxes.length; x++) {
				for (int y = 0; y < voxes[0].length; y++) {
					if (voxes[x][y] != null)
						voxes[x][y].tick();
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
		case flat:
			int dif = 0;
			// Create terrain
			for (int x = 0; x < voxes.length; x++) {
				dif += 2 - r.nextInt(5);
				if (dif > 50)
					dif = 50;
				if (dif < -50)
					dif = -50;
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
								voxes[x][y] = new VoxRock();
					}
				}
			}
			break;
		case notFlat:
		}
	}

	public enum LandType {
		flat, notFlat;
	}

}
