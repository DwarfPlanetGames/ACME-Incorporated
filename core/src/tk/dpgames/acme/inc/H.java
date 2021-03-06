package tk.dpgames.acme.inc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

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
		return (float) Math.atan2(y2 - y1, x2 - x1);
	}

	public static void clampCam(OrthographicCamera camera, int x1, int y1, int x2, int y2) {
		if ((int) (camera.position.x - camera.viewportWidth / 2) < x1)
			camera.position.x = (int) (x1 + camera.viewportWidth / 2);
		if ((int) (camera.position.x + camera.viewportWidth / 2) > x2)
			camera.position.x = (int) (x2 - camera.viewportWidth / 2);
		if ((int) (camera.position.y - camera.viewportHeight / 2) < y1)
			camera.position.y = (int) (y1 + camera.viewportHeight / 2);
		if ((int) (camera.position.y + camera.viewportHeight / 2) > y2)
			camera.position.y = (int) (y2 - camera.viewportHeight / 2);
	}

	public static float getDist(float x1, float y1, float x2, float y2) {
		return (float) (Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)));
	}

	public static int pointers() {
		int activeTouch = 0;
		for (int i = 0; i < 20; i++) {
			if (Gdx.input.isTouched(i))
				activeTouch++;
		}
		return activeTouch;
	}

	public static class Point {
		public float x;
		public float y;

		public Point(float x, float y) {
			this.x = x;
			this.y = y;
		}
	}

	public static class Segment {
		public Vector2 b;
		public Vector2 e;

		public Segment(Vector2 b, Vector2 e) {
			this.b = b;
			this.e = e;
		}
	}

	public static final class Overlapper {

		public static boolean intersectRectSeg(H.Segment segment, Rectangle rect) {
			if (Intersector.intersectSegments(segment.b, segment.e, new Vector2(rect.x, rect.y), new Vector2(rect.x, rect.y
					+ rect.height), null)) {
				// check left
				return true;
			}
			if (Intersector.intersectSegments(segment.b, segment.e, new Vector2(rect.x + rect.width, rect.y), new Vector2(
					rect.x + rect.width, rect.y + rect.height), null)) {
				// check right
				return true;
			}
			if (Intersector.intersectSegments(segment.b, segment.e, new Vector2(rect.x, rect.y), new Vector2(rect.x
					+ rect.width, rect.y), null)) {
				// check bottom
				return true;
			}
			if (Intersector.intersectSegments(segment.b, segment.e, new Vector2(rect.x, rect.y + rect.height), new Vector2(
					rect.x + rect.width, rect.y + rect.height), null)) {
				// check top
				return true;
			}
			return false;
		}

	}

}
