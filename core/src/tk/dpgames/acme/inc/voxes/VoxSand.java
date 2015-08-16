package tk.dpgames.acme.inc.voxes;

import tk.dpgames.acme.inc.game.GameSystem;

public class VoxSand extends Vox {

	public VoxSand() {
		super(16*4, 0);
	}
	
	@Override
	public void tick(int x, int y) {
		if (y - 1 < 0) return; 
		if (GameSystem.voxes[x][y-1] == null) {
			GameSystem.voxes[x][y-1] = this;
			GameSystem.voxes[x][y] = null;
		} else if (GameSystem.voxes[x][y-1].getData("water")) {
			GameSystem.voxes[x][y-1] = this;
			GameSystem.voxes[x][y] = null;
		}
	}

}
