package sem5.projekt.ind.object.realActors;

import sem5.projekt.ind.controller.CONST;
import sem5.projekt.ind.controller.Settings;
import sem5.projekt.ind.controller.StrengthBarDisplayer;
import sem5.projekt.ind.models.CharacterCore;
import sem5.projekt.ind.models.CustomImage;
import sem5.projekt.ind.models.ExtendedAnimation;
import sem5.projekt.ind.models.abstractModels.AbstractBullet;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Scaling;

public class Bullet extends CustomImage implements AbstractBullet {
	
	private CharacterCore core;
	private STATUS status;
	private int bonusDmg= 0;
	
	public Bullet(ObjectMap<String, ExtendedAnimation> animationSet,
			String defaultkey) {
		this(animationSet, defaultkey, Scaling.stretch, Align.center);
	}

	public Bullet(ObjectMap<String, ExtendedAnimation> animationSet,
			String defaultkey, Scaling scaling) {
		this(animationSet, defaultkey, scaling, Align.center);
	}

	public Bullet(ObjectMap<String, ExtendedAnimation> animationSet,
			String defaultkey, Scaling scaling, int align) {
		super(animationSet, defaultkey, scaling, align);
		this.core = new CharacterCore(this);
		this.status = STATUS.BEFORE_COLLISION;
	}

	@Override
	public void act(float delta) {
		if ( this.getX() > CONST.WINDOW_SIZE_X || getY() > CONST.WINDOW_SIZE_Y ) freeFromScene();
		if (!this.isDying() ) {
			addAction(Actions.moveBy(core.moveVector.x, core.moveVector.y));
		}
		super.act(delta);		
	}

	@Override
	public void reset() {
		super.reset();
		status = STATUS.BEFORE_COLLISION;
	}

	@Override
	public Bullet clone() {
		Bullet b = new Bullet(this.animationSet, this.defaultKeyAnimation,
				this.scaling, this.align);
		b.setPool(super.pool);
		return b;
	}

	public int getBonusDmg(StrengthBarDisplayer sbd) {
		return sbd.getDamageBonus();
	}
	
	@Override
	public
	CharacterCore getCore() {
		return core;
	}

	public void collide() {
		setAnimation("explode");
		this.setDying(true);
		setStatus(STATUS.COLLIDING);
	}

	@Override
	public STATUS getStatus() {
		return status;
	}

	@Override
	public int dealDamage(Vector2 position, Vector2 area) {
		position.set(core.getBoundPosition());
		area.set( core.atkArea);
		setStatus(STATUS.AFTER_COLLISION);
		return core.atkPoint + bonusDmg;
	}

	@Override
	public void setStatus(STATUS status) {
		this.status = status;
	}

	public void setSpeed(float s) {
		this.core.speed = s;
	}

	public void setDirection(Vector2 v) {
		double l =  Math.sqrt((double)(v.x*v.x + v.y*v.y));
		float k = this.core.speed/(float) l ;
		this.core.moveVector.set(v.x*k, v.y*k);
	}

	public void setBonusDmg(int k ) {
		bonusDmg = k;
	}
	
	public int getDamage() {
		return core.atkPoint + bonusDmg;
	}
	public static void setUpBulletNumber1 (Bullet b) {
		b.setDeltaPosition(51,51);
		CharacterCore core = b.getCore();
		core.speed = 10;
		core.atkPoint = (int ) Settings.number_bulletBaseAttackPoint.value;
		core.boundsize = new Vector2 (15,15);
		core.moveVector= new Vector2 (0,0);
		core.atkArea = new Vector2(15,15);
	}

}
