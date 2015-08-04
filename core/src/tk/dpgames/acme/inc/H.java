package tk.dpgames.acme.inc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class H {

	public static int stringOccurrences(String findStr, String str) {
		int lastIndex = 0;
		int count = 0;
		while (lastIndex != -1) {
			lastIndex = str.indexOf(findStr, lastIndex);
			if (lastIndex != -1) {
				count++;
				lastIndex += findStr.length();
			}
		}
		return count;
	}

	public static boolean swap(boolean val) {
		if (val) {
			return false;
		} else {
			return true;
		}
	}

	public static void clear(float r, float g, float b) {
		Gdx.gl.glClearColor(r, g, b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public static void drawTex(SpriteBatch b, Texture tex, float x, float y) {

	}
	
	public static float posToDir(float x1, float y1, float x2, float y2) {
		return (float)Math.atan2(y2-y1, x2-x1);
	}

}
