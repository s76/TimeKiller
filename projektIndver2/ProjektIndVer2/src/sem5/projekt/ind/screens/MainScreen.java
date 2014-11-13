package sem5.projekt.ind.screens;

import sem5.projekt.ind.ProjektIndVer2;
import sem5.projekt.ind.controller.MainController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MainScreen implements Screen {
	ProjektIndVer2 game;
	MainController mctr;
	
	public MainScreen(ProjektIndVer2 game, MainController mctr) {
		this.game = game;
		this.mctr = mctr;
		mctr.setThisScreenAsMainScreen(this);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		if (mctr.isRunning()) {
			mctr.act(delta);
			mctr.draw(delta);
		} else {
			this.dispose();
			Gdx.app.exit();
		}	
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

	}
	
	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		mctr.dispose();
	}

	public void changeScreen(Screen pauseScreen) {
		game.setScreen(pauseScreen);
	}

	public Stage getStage() {
		return mctr.getStage();
	}

	public ProjektIndVer2 getGame() {
		// TODO Auto-generated method stub
		return game;
	}

}
