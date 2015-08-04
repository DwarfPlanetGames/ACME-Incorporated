package tk.dpgames.acme.inc.game;

import tk.dpgames.acme.inc.screens.Title;
import tk.dpgames.acme.inc.voxes.Vox;
import tk.dpgames.acme.inc.voxes.VoxDirt;
import tk.dpgames.acme.inc.voxes.VoxGrass;
import tk.dpgames.acme.inc.voxes.VoxRock;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameSystem {

	public boolean paused = true;
	public float direction = 0;
	public static Vox[][] voxes = new Vox[512][256];
	public static Texture cellSheet = new Texture("cell_sheet.png");

	public GameSystem() {
		paused = true;
	}

	public void renderGame(SpriteBatch batch) {
		for (int y = 0; y < voxes[0].length; y++) {
			for (int x = 0; x < voxes.length; x++) {
				if (voxes[x][y] != null) {
					TextureRegion tex = new TextureRegion(cellSheet,voxes[x][y].texX,voxes[x][y].texY,16,16);
					batch.draw(tex, x * 16 * Title.scale, y * 16 * Title.scale, 0, 0, 16, 16, Title.scale, Title.scale, 0);
				}
			}
		}
	}

	public void tickGame(float delta) {

	}

	public void renderHud(float delta, SpriteBatch batch) {

	}

	public static void createWorld(LandType type, boolean caves) {
		switch (type) {
		case flat:
			for (int y = 0; y < voxes[0].length; y++) {
				int yl = voxes[0].length - y;
				for (int x = 0; x < voxes.length; x++) {
					if (yl == voxes[0].length / 2) {
						voxes[x][y] = new VoxGrass();
					} else if (yl < voxes[0].length / 2 - 5 && yl > voxes[0].length / 2) {
						voxes[x][y] = new VoxDirt();
					} else {
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
