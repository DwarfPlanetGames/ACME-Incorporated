package tk.dpgames.acme.inc.voxes;

import tk.dpgames.acme.inc.game.GameSystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class VoxWater extends Vox{
	
	public int value = 0;
	
	public VoxWater(int value) {
		super(0, 11*16);
		this.value = value;
		data = ""
				+ ":water"
				+ ":render"
				+ ":";
		canCollide = false;
		texY = 11*16;
	}
	
	@Override
	public void tick(int x, int y) {
		Vox[][] voxes = GameSystem.voxes;
		Vox u = voxes[x][y+1];
		Vox d = voxes[x][y-1];
		Vox l = voxes[x-1][y];
		Vox r = voxes[x+1][y];
		if (d == null) {
			voxes[x][y-1] = new VoxWater(value);
			voxes[x][y] = null;
			return;
		}
		if (d.getData("water")) {
			VoxWater dv = (VoxWater)d;
			value = dv.addWater(value);
		}
		if (r == null) {
			voxes[x+1][y] = new VoxWater(0);
			r = voxes[x+1][y];
		}
		if (r.getData("water") && value > 1) {
			VoxWater rv = (VoxWater)r;
			if (rv.value != value) {
				int nv = value - (value - rv.value) / 2;
				setWater(nv);
				rv.setWater(nv);
			}
		}
		if (l == null) {
			voxes[x-1][y] = new VoxWater(0);
			l = voxes[x-1][y];
		}
		if (l.getData("water") && value > 1) {
			VoxWater lv = (VoxWater)l;
			if (lv.value != value) {
				int nv = value - (value - lv.value) / 2;
				setWater(nv);
				lv.setWater(nv);
			}
		}
		if (l.getData("water")) {
			VoxWater lv = (VoxWater)l;
			if (lv.value <= 0) {
				voxes[x-1][y] = null;
			}
		}
		if (r.getData("water")) {
			VoxWater lv = (VoxWater)r;
			if (lv.value <= 0) {
				voxes[x+1][y] = null;
			}
		}
		if (value <= 0) {
			voxes[x][y] = null;
		}
	}
	
	public int addWater(int v) {
		// Returns water displacement
		value += v;
		if (value > 15) {
			int i = value - 15;
			value = 15;
			return i;
		}
		else if (value < 0 ) {
			int i = value;
			value = 0;
			return i;
		}
		else return 0;
	}
	
	public int setWater(int v) {
		// Returns water displacement
		value = v;
		if (value > 15) {
			int i = value - 15;
			value = 15;
			return i;
		}
		else if (value < 0 ) {
			int i = value;
			value = 0;
			return i;
		}
		else return 0;
	}
	
	@Override
	public void render(SpriteBatch batch, int x, int y) {
		texX = value * 16;
		if (GameSystem.voxes[x][y+1] != null) {
			if (GameSystem.voxes[x][y+1].getData("water")) {
				texX = 15*16;
			}
		}
		TextureRegion r = new TextureRegion(GameSystem.cellSheet,texX,11*16,16,16);
		batch.draw(r, x * 16, y * 16, 0, 0, 16, 16, 1, 1, 0);
	}
	
}
