package sem5.projekt.ind.screens;

import sem5.projekt.ind.ProjektIndVer2;
import sem5.projekt.ind.controller.MainController;
import sem5.projekt.ind.controller.Settings;
import sem5.projekt.ind.controller.TextureResourceManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PauseScreen implements Screen {

	private Stage stage;
	private TextureResourceManager rm;
	private MainController mc;
	private boolean running;
	
	public PauseScreen(MainController mc ) {
		this.mc = mc;
		stage = new Stage(800,480,true);
		this.rm = mc.getTextureResourceManager();
		this.running = true;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if (running) {
			stage.act(delta);
			stage.draw();
		} else {
			mc.getMainScreen().dispose();
			Gdx.app.exit();
		}	
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		
		TextButton quitButton = new TextButton("Quit", rm.getSkin());
		quitButton.setPosition(300, 100);
		quitButton.setSize(200, 60);
		quitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				running=false;
			}
		});
		stage.addActor(quitButton);
		
		final TextButton button2 = new TextButton("Mode: "+Settings.getModeName(), rm.getSkin()){
			@Override
			public boolean isPressed() {
				return false;
			}
		};
		button2.setPosition(300, 170);
		button2.setSize(130, 60);
		stage.addActor(button2);
		
		TextButton button = new TextButton(">>", rm.getSkin());
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if ( Settings.getModeName().equals("Easy") ){
					Settings.normal_mode();
					button2.setText("Mode: "+Settings.getModeName());
				} else if ( Settings.getModeName().equals("Normal") ){
					Settings.hard_mode();
					button2.setText("Mode: "+Settings.getModeName());
				} else if ( Settings.getModeName().equals("Hard") ){
					Settings.insane_mode();
					button2.setText("Mode: "+Settings.getModeName());
				} else if ( Settings.getModeName().equals("Insane") ){
					Settings.easy_mode();
					button2.setText("Mode: "+Settings.getModeName());
				}
				mc.reset();
			}
		});

		button.setPosition(440, 170);
		button.setSize(60, 60);
		stage.addActor(button);
		
		
		TextButton restartButton = new TextButton("Restart",  rm.getSkin());
		restartButton.setPosition(300, 240);
		restartButton.setSize(200, 60);
		restartButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				ProjektIndVer2 game = mc.getMainScreen().getGame();
				game.setScreen(mc.getMainScreen());
				game.setAsNewInputProcessor(mc.getMainScreen().getStage());
				mc.reset();
				mc.resume();
			}
		});
		
		stage.addActor(restartButton);
		
		TextButton resumeButton = new TextButton("Resume",  rm.getSkin());
		resumeButton.setPosition(300, 310);
		resumeButton.setSize(200, 60);
		resumeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				ProjektIndVer2 game = mc.getMainScreen().getGame();
				game.setScreen(mc.getMainScreen());
				game.setAsNewInputProcessor(mc.getMainScreen().getStage());
				mc.resume();
			}
		});
		stage.addActor(resumeButton);
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
	}

	public InputProcessor getStage() {
		return stage;
	}
	
}
