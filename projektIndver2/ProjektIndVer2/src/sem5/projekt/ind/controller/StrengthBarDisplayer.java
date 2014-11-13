package sem5.projekt.ind.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;

public class StrengthBarDisplayer {
	private Image full;
	private TiledDrawable fill;
	
	private static final int BAR_LENGTH=205;
	private static final int FILL_HEIGHT = 16;
	private static final float MIN_TIME = 0.25f;
	

	private static float time_strengthBarFull;
	private static int number_bulletBonusConst;
	
	private Actor actor;
	
	private float timer;
	private int fill_x,fill_y,fill_width;
	private int bonus;
	
	public StrengthBarDisplayer () {
		actor = new Actor(){
			@Override
			public void draw(SpriteBatch batch, float parentAlpha) {
				fill.draw(batch, fill_x, fill_y, fill_width, FILL_HEIGHT);
			}	
		};
		time_strengthBarFull = Settings.time_strengthBarFull.value;
		number_bulletBonusConst = (int) Settings.number_bulletBonusConst.value;
	}
	
	
	public void registerStrngthFulll(Image full) {
		this.full = full;
	}
	
	public void registerStrengthFill(TiledDrawable fill, int x,int y) {
		this.fill = fill;
		this.fill_x = x;
		this.fill_y = y;
	}
	
	public void update(float delta ) {
		timer += delta;
		if ( timer > time_strengthBarFull )  {
			timer = time_strengthBarFull;
			full.setVisible(true);
		} 
		fill_width = (int)(BAR_LENGTH * timer/time_strengthBarFull);
	}
	
	public int getDamageBonus () {
		float k = timer/time_strengthBarFull;
		if ( timer > MIN_TIME ) bonus = (int)(number_bulletBonusConst* (k*k*k));
		else bonus = 0;
		fill_width = 0;
		timer =0;
		full.setVisible(false);
		return bonus;
	}
	
	public void reset() {
		time_strengthBarFull = Settings.time_strengthBarFull.value;
		number_bulletBonusConst = (int) Settings.number_bulletBonusConst.value;
	}
	
	public Actor getFillDisplayer() {
		return actor;
	}
}
