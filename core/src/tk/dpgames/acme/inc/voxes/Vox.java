package tk.dpgames.acme.inc.voxes;

import tk.dpgames.acme.inc.game.GameSystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Vox {
	
	public static final Texture outline = new Texture("vox_outline.png");
	
	public String data = "";
	public int texX = 0, texY = 0;
	public boolean canMine = true;
	public boolean canCollide = true;
	public float temper = 1f; // 1 would be dirt, 2 would be rock
	public float health = 10f;
	protected int x,y;
	
	public Vox(int texX, int texY) {
		this.texX = texX;
		this.texY = texY;
	}
	
	public boolean getData(String str) {
		return data.contains(":"+str+":");
	}
	
	public static Vox getVox(int x, int y) {
		if (GameSystem.voxes[x][y] == null) return null;
		else return GameSystem.voxes[x][y];
	}
	
	public void click(float distance){};
	
	public void mined(){};
	
	public void placed(){};
	
	public void tick(int x, int y){
		this.x = x;
		this.y = y;
	};
	
	public void destroy(){}
	
	public void render(SpriteBatch batch, int x, int y){}
	
	public boolean mine(int x, int y,float delta, float power) {
		if (!canMine) {
			return false;
		}
		System.out.println("Mining; Health: " + health);
		health -= delta / (temper / power);
		if (health <= 0) {
			GameSystem.voxes[x][y] = null;
			mined();
			destroy();
			return true;
		}
		return false;
	}
	
}
