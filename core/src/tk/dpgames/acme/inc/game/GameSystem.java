package tk.dpgames.acme.inc.game;

import java.util.Random;

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
	public float direction = 0;
	public static Vox[][] voxes = new Vox[512][256];
	public static Texture cellSheet = new Texture("cell_sheet.png");
	public static float time; // Number of frames from begin if running at 60
								// FPS;
	public static 
	public GameSystem() {
		paused = true;
		time = 0.0f;
	}

	public void renderGame(SpriteBatch batch, OrthographicCamera camera) {
		float xc = camera.position.x;
		float yc = camera.position.y;
		float widthc = camera.viewportWidth;
		float heightc = camera.viewportHeight;
		xc -= widthc / 2;
		yc -= heightc / 2;
		for (int y = 0; y < voxes[0].length; y++) {
			for (int x = 0; x < voxes.length; x++) {
				if (voxes[x][y] != null && x * 16 - xc > 0 && x * 16 - xc < widthc && y * 16 - yc > 0 && y * 16 - yc < heightc
						&& true) {
					TextureRegion tex = new TextureRegion(cellSheet, voxes[x][y].texX, voxes[x][y].texY, 16, 16);
					batch.draw(tex, x * 16, y * 16, 0, 0, 16, 16, 1, 1, 0);
				}
			}
		}
	}

	public void tickGame(float delta) {
		time += delta * 60;
	}

	public void renderHud(float delta, SpriteBatch batch) {

	}

	public static void createWorld(LandType type, boolean caves, long seed) {
		Random r = new Random();
		r.setSeed(seed);
		switch (type) {
		case flat:
			int dif = 0;
			for (int x = 0; x < voxes.length; x++) {
				dif += 2 - r.nextInt(5);
				if (dif > 50) dif = 50;
				if (dif < -50) dif = -50;
				for (int y = 0; y < voxes[0].length; y++) {
					int yl = voxes[0].length - y - dif;
					if (r.nextInt(7) != 0 || !caves) {
						if (yl == voxes[0].length / 2) {
							voxes[x][y] = new VoxGrass();
						} else if (yl < voxes[0].length / 2 + 5 && yl > voxes[0].length / 2) {
							voxes[x][y] = new VoxDirt();
						} else if (yl > voxes[0].length / 2) {
							if (r.nextInt(7) != 0) {
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
			break;
		case notFlat:
		}
	}

	public enum LandType {
		flat, notFlat;
	}

}
