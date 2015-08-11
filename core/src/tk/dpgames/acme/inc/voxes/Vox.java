package tk.dpgames.acme.inc.voxes;

import tk.dpgames.acme.inc.game.GameSystem;

public abstract class Vox {
	
	String data = "";
	public int texX = 0, texY = 0;
	public boolean canMine = true;
	public boolean canCollide = true;
	public float temper = 1f; // 1 would be dirt, 2 would be rock
	public float health = 10f;
	
	public Vox(int texX, int texY) {
		this.texX = texX;
		this.texY = texY;
		health = 10f;
	}
	
	public void click(float distance){};
	
	public void mined(){};
	
	public void placed(){};
	
	public void tick(){};
	
	public void destroy(){}
	
	public void mine(int x, int y,float delta) {
		System.out.println("Mining");
		health -= delta / temper;
		if (health <= 0) {
			GameSystem.voxes[x][y] = null;
		}
	}
	
}
