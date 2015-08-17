package tk.dpgames.acme.inc.voxes;

import tk.dpgames.acme.inc.game.GameSystem;
import tk.dpgames.acme.inc.game.Light;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class VoxFlame extends Vox {
	
	public static final Texture fire = new Texture("fire.png");
	private float anim;
	private Light light;
	
	public VoxFlame(int x, int y) {
		super(0, 0);
		data = ":render:fire:";
		anim = MathUtils.random(0.0f,4.0f);
		canCollide = false;
		light = new Light(200,x*16,y*16);
		GameSystem.lights.add(light);
	}
	
	@Override
	public void render(SpriteBatch batch, int x, int y) {
		anim += Gdx.graphics.getDeltaTime() * 12;
		TextureRegion reg = new TextureRegion(fire, ((int) anim % 4) * 16, 0, 16,18);
		batch.draw(reg,x*16,y*16);
	}
	
	@Override
	public void destroy() {
		GameSystem.lights.remove(light);
	}
	
}
