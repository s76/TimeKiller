package sem5.projekt.ind.controller;

import sem5.projekt.ind.models.CustomPool;
import sem5.projekt.ind.object.realActors.Bullet;

import com.badlogic.gdx.utils.Array;

public class BulletGen {
	static Array<Bullet> templates;
	
	CustomPool<Bullet> bulletPool;
	private int bulletchoice;
	
	public BulletGen () {
		bulletchoice = 0;
		templates = new Array<Bullet>(4);
		bulletPool = new CustomPool<Bullet>(){
			@Override
			protected Bullet newObject() {
				Bullet b = templates.get(bulletchoice).clone();
				Bullet.setUpBulletNumber1(b);
				return b;
			}};
	}	
	
	public Bullet getBullet() {	
		return bulletPool.obtain();
	}
	public void setBulletChoice(int i ) {
		bulletchoice = i;
	}
	public void registerTemplate (Bullet m ) {
		templates.add(m);
		m.setPool(bulletPool);
	}
	
	public void clearTemplates() {
		templates.clear();
	}
	
}
