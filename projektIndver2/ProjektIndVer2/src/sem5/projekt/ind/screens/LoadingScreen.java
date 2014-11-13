package sem5.projekt.ind.screens;

import sem5.projekt.ind.ProjektIndVer2;
import sem5.projekt.ind.controller.MainController;
import sem5.projekt.ind.controller.TextureResourceManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class LoadingScreen implements Screen {
	private static final int loading_x = 50;
	private static final int loading_y = 30;
	private static final int text_x = 90;
	private static final int text_y = 35;
	
	private ProjektIndVer2 game;
	private TextureResourceManager rm;
	private SpriteBatch batch;
	Animation loading;
	float stateTime;
	private AtlasRegion text;
	
	private MainController maincontroller;
	private boolean flag;
	
	public LoadingScreen(ProjektIndVer2 game) {
		this.game = game;
		this.rm = new TextureResourceManager();
		batch = new SpriteBatch(12);
		stateTime=0;
		maincontroller= new MainController(rm);
		flag= false;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stateTime+= delta;
		

		batch.begin();
		batch.draw(loading.getKeyFrame(stateTime, true), loading_x, loading_y);
		batch.draw(text, text_x, text_y, text.getRegionWidth(), text.getRegionHeight() - 5);
		batch.end();
		
		if(!flag ) {
			maincontroller.initialize();
			flag=true;
		}
		
		if ( maincontroller.isInitialized() ) {
			this.dispose();
			game.setScreen(new StartScreen(game,maincontroller));
		}
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		Array<AtlasRegion> loading_array = rm.getFrameSet("misc/loading.atlas", "misc");
		Array<AtlasRegion> loading_animation = rm.exactFrames(loading_array, 0,1,2,3,4,5,6,7);
		Array<AtlasRegion> loading_text = rm.exactFrames(loading_array,8);
		
		loading = new Animation(0.1f, loading_animation);
		text = loading_text.get(0);
	}

	@Override
	public void hide() {
		
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
		batch.dispose();
	}

}
