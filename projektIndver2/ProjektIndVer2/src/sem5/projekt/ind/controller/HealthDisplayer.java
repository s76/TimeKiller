package sem5.projekt.ind.controller;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class HealthDisplayer {
	private int hp;
	private Label label;
	
	public HealthDisplayer () {
		hp = (int)Settings.numbder_startHealth.value;
	}
	
	
	public void registerLabel(Label label) {
		this.label = label;
	}
	
	public void update(int h ) {
		hp += h;
		label.setText(Integer.toString(hp));
	}
	
	public int getHealth(){
		return hp;
	}

	public void reset() {
		hp = (int)Settings.numbder_startHealth.value;
		label.setText(Integer.toString(hp));
	}
}
