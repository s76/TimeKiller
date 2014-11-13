package sem5.projekt.ind.controller;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class TimeDisplayer {
	private float m;
	private int timer;
	private Label label;
	public TimeDisplayer() {
		m=0;
		timer=0;
	}
	public void registerLabel(Label l ) {
		this.label = l;
	}
	
	public void update(float delta) {
		m += delta;
		if ( m >= timer+1 ) {
			timer++;
		} 
		label.setText("TIME: " + timer + " seconds");
	}
	public void reset() {
		m =0;
		timer = 0;
	}
}
