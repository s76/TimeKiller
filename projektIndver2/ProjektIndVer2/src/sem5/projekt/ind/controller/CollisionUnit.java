package sem5.projekt.ind.controller;

import sem5.projekt.ind.models.CustomImage;
import sem5.projekt.ind.models.abstractModels.AbstractBullet.STATUS;
import sem5.projekt.ind.object.realActors.Bullet;
import sem5.projekt.ind.object.realActors.Monster;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class CollisionUnit {
	private Array<Bullet> bulletarray;
	private Array<Monster> monsterarray;
	private HealthDisplayer hd;
	
	public CollisionUnit (HealthDisplayer hd) {
		this.hd = hd;
		bulletarray = new Array<Bullet> (16);
		monsterarray = new Array<Monster>(16);
	}
	
	public void register (String type, CustomImage object) {
		if ( "bullet".equals(type) ) {
			Bullet b = (Bullet) object;
			bulletarray.add(b );
			b.addColiisionUnit (this);
		} else if ( "monster".equals(type) ) {
			Monster m = (Monster)object;
			monsterarray.add(m);
			m.addColiisionUnit(this);
		} else {
			throw new RuntimeException("Invalid object type");
		}
	}


	public void checkCollision () {
		Rectangle rec1 = new Rectangle();
		Rectangle rec2 = new Rectangle();
		Vector2 pos = new Vector2();
		Vector2 area = new Vector2();
		
		for (Bullet b : bulletarray ) {
			if ( b.getStatus() == STATUS.AFTER_COLLISION ) {
				/*do nothing */
			} else if ( b.getStatus() == STATUS.BEFORE_COLLISION ) {
				for ( Monster m: monsterarray ) {
					boolean k =Intersector.overlaps(rec1.setPosition(b.getCore().getBoundPosition()).setSize(b.getCore().boundsize.x, b.getCore().boundsize.y), 
							rec2.setPosition(m.getCore().getBoundPosition()).setSize(m.getCore().boundsize.x, m.getCore().boundsize.y));
					if( k ) {
						b.collide();
					}
				}
			} else if ( b.getStatus() == STATUS.COLLIDING ) {
				int dmg = b.dealDamage(pos,area);
				for ( Monster m: monsterarray ) {
					boolean k = Intersector.overlaps(rec1.setPosition(pos).setSize(area.x, area.y), 
							rec2.setPosition(m.getCore().getBoundPosition()).setSize(m.getCore().boundsize.x, m.getCore().boundsize.y));
					if ( k ) {
						m.getCore().hpPoint -= dmg;
						if ( m.getCore().hpPoint <= 0 ) m.die();
					}
				}
			}
		}
		 
		for (Monster m : monsterarray  ) {
			int k = m.getDamageInflicted();
			if ( k != 0 ) {
				hd.update(-k);
			}
		}
		
	}
	
	public Array<Monster > getArrayMonster() {
		return monsterarray;
	}
	
	public Array<Bullet > getArrayBullet() {
		return bulletarray;
	}
	public void remove(CustomImage customImage) {
		if ( customImage instanceof Bullet ) {
			Bullet b = (Bullet) customImage;
			bulletarray.removeValue(b,true);
		} else if ( customImage instanceof  Monster ) {
			Monster m = (Monster) customImage;
			monsterarray.removeValue(m,true);
		} else {
			throw new RuntimeException("Invalid object type");
		}
	}

	public void reset() {
		bulletarray.clear();
		monsterarray.clear();
	}

}
