package sem5.projekt.ind.screens;

import sem5.projekt.ind.ProjektIndVer2;
import sem5.projekt.ind.controller.MainController;
import sem5.projekt.ind.controller.Settings;
import sem5.projekt.ind.controller.TextureResourceManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class StartScreen implements Screen {
	private ProjektIndVer2 game;
	private Stage stage;
	
	private boolean running;
	private MainController maincontroller;

	public StartScreen(ProjektIndVer2 game, MainController maincontroller) {
		this.game = game;
		this.maincontroller = maincontroller;
		this.stage = new Stage(800,480,true);
		this.running=true;
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		if (running) {
			stage.act();
			stage.draw();
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
		TextureResourceManager rm = maincontroller.getTextureResourceManager();
		Array<AtlasRegion> frames = rm.getFrameSet("misc/starts.atlas", "misc");

		stage.addActor(new Image(rm.exactFrames(frames, 0).get(0)));
		Image m = new Image(rm.exactFrames(frames, 1).get(0));
		m.setPosition(150, 310);
		stage.addActor(m);
		
		TextButton button = new TextButton("Start game", rm.getSkin());
		button.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				dispose();
				MainScreen sc = new MainScreen(game,maincontroller);
				game.setScreen(sc);
				game.setAsNewInputProcessor(sc.getStage());
			}
		});
		button.setPosition(320, 240);
		button.setSize(200, 60);
		stage.addActor(button);

		final TextButton button2 = new TextButton("Mode: "+Settings.getModeName(), rm.getSkin()){
			@Override
			public boolean isPressed() {
				return false;
			}
		};
		button2.setPosition(320, 170);
		button2.setSize(130, 60);
		stage.addActor(button2);
		
		button = new TextButton(">>", rm.getSkin());
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
				maincontroller.reset();
			}
		});

		button.setPosition(460, 170);
		button.setSize(60, 60);
		stage.addActor(button);
		
		
		button = new TextButton("Quit", rm.getSkin());
		button.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				running = false;
			}
		});

		button.setPosition(320, 100);
		button.setSize(200, 60);
		stage.addActor(button);
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
		

	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
