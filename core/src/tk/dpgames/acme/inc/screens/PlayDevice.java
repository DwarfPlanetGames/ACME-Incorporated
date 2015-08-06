package tk.dpgames.acme.inc.screens;

import tk.dpgames.acme.inc.H;
import tk.dpgames.acme.inc.game.GameSystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayDevice extends InputAdapter implements Screen {

	public GameSystem game;
	public boolean hudOpen = false;
	public OrthographicCamera camera;

	private SpriteBatch gameBatch;
	private SpriteBatch hudBatch;
	private Texture joystick = new Texture("joystick.png");
	
	private boolean renderL = false;
	private boolean renderR = false;
	private H.Point jsL = new H.Point(64 * Title.scale, 64 * Title.scale);
	private H.Point jsR = new H.Point(Gdx.graphics.getWidth() - 64 * Title.scale - 64 * 2, 64 * Title.scale);
	private int rPointer = 0;
	private int lPointer = 0;

	@Override
	public void show() {
		game = new GameSystem();
		hudOpen = true;
		gameBatch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth() / Title.scale, Gdx.graphics.getHeight() / Title.scale);
		game.paused = false;
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		if (!game.paused) {
			game.tickGame(delta);
			H.clampCam(camera, 16 * 4, 16 * 4, (int) (GameSystem.voxes.length * 16) - 16 * 4,
					(int) (GameSystem.voxes[0].length * 16) - 16 * 4);
		}
		float t = 0.5f + (float) (Math.sin(GameSystem.dayTime) / 2f);
		H.clear(0, t, t);
		camera.position.x = GameSystem.player.x;
		camera.position.y = GameSystem.player.y;
		gameBatch.begin();
		game.renderGame(gameBatch, camera);
		gameBatch.end();
		camera.position.x = (int) camera.position.x;
		camera.position.y = (int) camera.position.y;
		camera.update();
		gameBatch.setProjectionMatrix(camera.combined);
		hudBatch.begin();
		if (hudOpen) {
			game.renderHud(delta, hudBatch);
		}
		if (renderL)
			hudBatch.draw(joystick, jsL.x, jsL.y, 64 * 2, 64 * 2);
		if (renderR)
			hudBatch.draw(joystick, jsR.x, jsR.y, 64 * 2, 64 * 2);
		GameSystem.player.mining = renderR;
		hudBatch.end();
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		jsL = new H.Point(64 * Title.scale, 64 * Title.scale);                                   
		jsR = new H.Point(Gdx.graphics.getWidth() - 64 * Title.scale - 64 * 2, 64 * Title.scale);
		
		jsL.x += 64;
		jsL.y += 64;
		jsR.x += 64;
		jsR.y += 64;
		
		GameSystem.player.mining = false;
		int x = screenX;
		int y = Gdx.graphics.getHeight() - screenY;
		if (H.getDist(x, y, jsR.x, jsR.y) <= 128) {
			GameSystem.player.direction = H.posToDir(jsR.x, jsR.y, x, y);
			GameSystem.player.mining = true;
			renderR = true;
			rPointer = pointer;
		}
		jsR.x -= 64;
		jsR.y -= 64;
		
		x = screenX;
		y = Gdx.graphics.getHeight() - screenY;
		if (H.getDist(x, y, jsL.x, jsL.y) <= 128) {
			if (y > jsL.y + 32) {
				if (GameSystem.player.touchingGround) {
					GameSystem.player.velY += GameSystem.player.jumpHeight;
				}
				GameSystem.player.touchingGround = false;
			}
			if (x > jsL.x) {
				GameSystem.player.velX = Math.abs(x - jsL.x);
			} else if (x < jsL.x) {
				GameSystem.player.velX = -Math.abs(x - jsL.x);
			}
			renderL = true;
			lPointer = pointer;
		}
		jsL.x -= 64;
		jsL.y -= 64;
		
		return true;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		jsL = new H.Point(64 * Title.scale, 64 * Title.scale);                                   
		jsR = new H.Point(Gdx.graphics.getWidth() - 64 * Title.scale - 64 * 2, 64 * Title.scale);
		
		jsL.x += 64;
		jsL.y += 64;
		jsR.x += 64;
		jsR.y += 64;
		
		if (pointer == rPointer) {
			int x = screenX;
			int y = Gdx.graphics.getHeight() - screenY;
			System.out.println("x=" + x + " y=" + y);
			if (H.getDist(x, y, jsR.x, jsR.y) <= 128) {
				GameSystem.player.direction = H.posToDir(jsR.x, jsR.y, x, y);
				renderR = true;
				rPointer = pointer;
			}
		}
		if (pointer == lPointer) {
			int x = screenX;
			int y = Gdx.graphics.getHeight() - screenY;
			if (H.getDist(x, y, jsL.x, jsL.y) <= 128) {
				if (y > jsL.y + 32) {
					if (GameSystem.player.touchingGround) {
						GameSystem.player.velY += GameSystem.player.jumpHeight;
					}
					GameSystem.player.touchingGround = false;
				}
				if (x > jsL.x) {
					GameSystem.player.velX = Math.abs(x - jsL.x);
				} else if (x < jsL.x) {
					GameSystem.player.velX = -Math.abs(x - jsL.x);
				}
				renderL = true;
				lPointer = pointer;
			}
		}
		
		jsL.x -= 64;
		jsL.y -= 64;
		jsR.x -= 64;
		jsR.y -= 64;
		
		return true;
	}
	
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (pointer == lPointer) renderL = false;
		if (pointer == rPointer) renderR = false;
		return false;
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

}
