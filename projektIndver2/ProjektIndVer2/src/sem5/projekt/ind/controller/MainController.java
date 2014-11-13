package sem5.projekt.ind.controller;

import sem5.projekt.ind.models.abstractModels.AbstractController;
import sem5.projekt.ind.screens.EndGameScreen;
import sem5.projekt.ind.screens.MainScreen;
import sem5.projekt.ind.screens.PauseScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MainController implements AbstractController {
	// /////
	private MainControllerBackEnd backend;
	// //////////

	private Stage stage;

	// ////////////=
	private Group background;
	private Group foreground;
	private Group mob;
	private Group bullet;
	

	// ///////////
	private boolean gameEnd;

	// ///
	private boolean initialzed;
//	// debug
//	ShapeRenderer shapeRenderer = new ShapeRenderer();

	////
	
	//////
	private MainScreen mainScreen;
	private PauseScreen pauseScreen;
	private EndGameScreen endgameScreen;
	
	public MainController(TextureResourceManager rm) {
		gameEnd = false;

		stage = new Stage(800,480,true);
		backend = new MainControllerBackEnd(rm);

		mob = new Group();
		bullet = new Group();
		foreground = new Group();
		background = new Group();

		stage.addActor(background);
		stage.addActor(mob);
		stage.addActor(bullet);
		stage.addActor(foreground);

		initialzed = false;
		
		pauseScreen= new PauseScreen(this);
		endgameScreen = new EndGameScreen(this);
	}
	
	public MainScreen getMainScreen() {
		return mainScreen;
	}
	
	
	public void initialize() {
		backend.createBackGroundElements(background);
		backend.createForeGroundElements(foreground);
		backend.createTemplates();
		initialzed = true;
	}


	public void act(float delta) {

		backend.act(delta, mob,bullet);
		stage.act(delta);

		if ( backend.isPaused() ) {
			mainScreen.getGame().setScreen(pauseScreen);
			mainScreen.getGame().setAsNewInputProcessor(pauseScreen.getStage());
		}
		if (backend.isGameEnd()) {
			gameEnd = true;
			mainScreen.getGame().setScreen(endgameScreen);
			mainScreen.getGame().setAsNewInputProcessor(endgameScreen.getStage());
		}
	}

	public void draw(float delta) {
		Gdx.graphics.getGL10().glClearColor(0, 0, 0, 0);
		Gdx.graphics.getGL10().glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.draw();

//		 shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
//		
//		 shapeRenderer.begin(ShapeType.Line);
//		 for (Monster m : backend.getCollisionUnit().getArrayMonster()) {
//		 shapeRenderer.setColor(0, 1, 0, 1);
//		 shapeRenderer.rect(m.getX(), m.getY(), m.getCore().boundsize.x,
//		 m.getCore().boundsize.y);
//		 }
//		 for (Bullet m : backend.getCollisionUnit().getArrayBullet()) {
//		 shapeRenderer.setColor(0, 1, 0, 1);
//		 shapeRenderer.rect(m.getX(), m.getY(), m.getCore().boundsize.x,
//		 m.getCore().boundsize.y);
//		 }
//		 shapeRenderer.end();
	}

	public boolean isGameEnd() {
		// TODO Auto-generated method stub
		return gameEnd;
	}

	public boolean isInitialized() {
		return initialzed;
	}

	public TextureResourceManager getTextureResourceManager() {
		// TODO Auto-generated method stub
		return backend.getTextureResourceManager();
	}

	public boolean isRunning() {
		return backend.isRunning();
	}

	public void dispose() {
		backend.getTextureResourceManager().dispose();
		pauseScreen.dispose();
		endgameScreen.dispose();
	}

	public void setThisScreenAsMainScreen(MainScreen screen1) {
		mainScreen= screen1;
	}
	public void resume() {
		backend.resume();
	}

	public Stage getStage() {
		return stage;
	}

	public void reset() {
		backend.reset();
		mob.clear();
	}
}
