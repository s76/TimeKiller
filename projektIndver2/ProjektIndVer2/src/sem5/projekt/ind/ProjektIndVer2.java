package sem5.projekt.ind;

import sem5.projekt.ind.controller.Settings;
import sem5.projekt.ind.controller.TextureResourceManager;
import sem5.projekt.ind.screens.LoadingScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class ProjektIndVer2 extends Game {
	TextureResourceManager rManager;
	
	@Override
	public void create() {
		rManager = new TextureResourceManager();
		Settings.easy_mode();
		this.setScreen(new LoadingScreen(this));
	}

	public void setAsNewInputProcessor(InputProcessor ip) {
		Gdx.input.setInputProcessor(ip);
	}
}
