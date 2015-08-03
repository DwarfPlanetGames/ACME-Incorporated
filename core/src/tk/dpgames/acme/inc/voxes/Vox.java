package tk.dpgames.acme.inc.voxes;

public abstract class Vox {
	
	String data = "";
	public int texX = 0, texY = 0;
	public boolean canMine = true;
	public float temper = 1f; // 1 would be dirt, 2 would be rock
	
	public Vox(int texX, int texY, String data) {
		this.data = data;
		this.texX = texX;
		this.texY = texY;
	}
	
	public void click(float distance){};
	
	public void mined(){};
	
	public void placed(){};
	
}
