package tk.dpgames.acme.inc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class H {
	
	public static int stringOccurrences(String findStr, String str) {
		int lastIndex = 0;
		int count = 0;
		while (lastIndex != -1) {
			lastIndex = str.indexOf(findStr,lastIndex);
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
	
	public static void drawTex(SpriteBatch b, Texture tex, float x, float y) {
		
	}
	
}
