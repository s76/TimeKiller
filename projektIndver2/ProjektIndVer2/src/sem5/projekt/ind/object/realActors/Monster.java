package sem5.projekt.ind.object.realActors;

import static sem5.projekt.ind.controller.CONST.AIR;
import static sem5.projekt.ind.controller.CONST.GROUND;
import static sem5.projekt.ind.controller.CONST.MOB_MOVE_LIMIT_X;
import static sem5.projekt.ind.controller.CONST.WINDOW_SIZE_X;

import java.util.Random;

import sem5.projekt.ind.controller.Settings;
import sem5.projekt.ind.models.CharacterCore;
import sem5.projekt.ind.models.CustomImage;
import sem5.projekt.ind.models.ExtendedAnimation;
import sem5.projekt.ind.models.abstractModels.AbstractMonster;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Scaling;

public class Monster extends CustomImage implements AbstractMonster {

	private CharacterCore core;
	private CURRENT_ACTION current;

	public static Random rd = new Random();
	
	boolean attakInColddown;
	float attackTimer;
	float colddown;
	
	public static enum MONSTER_TYPE {
		BAT, GOLDEN_GOLEM, BLUE_GOLEM,  GIFT_BOX, VIRTURE, GINGER
	};

	private MONSTER_TYPE type;
	private int moveLimit;

	public Monster(ObjectMap<String, ExtendedAnimation> animationSet,
			String defaultkey) {
		this(animationSet, defaultkey, Scaling.stretch, Align.center);
	}

	public Monster(ObjectMap<String, ExtendedAnimation> animationSet,
			String defaultkey, Scaling scaling) {
		this(animationSet, defaultkey, scaling, Align.center);
	}

	public Monster(ObjectMap<String, ExtendedAnimation> animationSet,
			String defaultkey, Scaling scaling, int align) {
		super(animationSet, defaultkey, scaling, align);
		this.core = new CharacterCore(this);
		current = CURRENT_ACTION.WALK;
		attakInColddown = true;
		attackTimer = 0;
		current = CURRENT_ACTION.WALK;
	}
	
	public void setMoveLimit (int x) {
		moveLimit = x;
	}

	public static void setUpMonster(Monster m) {
		CharacterCore core;
		switch (m.type) {
			case BAT: {
				m.setDeltaPosition(28,70);
				m.setPosition(WINDOW_SIZE_X+ rd.nextInt(50),AIR - 100 + rd.nextInt(100));
				m.setBaseHp((int)(15*Settings.number_mobHealthCoeeficent.value));
				m.setMoveLimit(MOB_MOVE_LIMIT_X+10);
				core = m.getCore();
				core.atkPoint = 10;
				core.moveVector = new Vector2(-1.5f*Settings.number_mobSpeedCoeffient.value, 0);
				core.boundsize = new Vector2(42,41);
				break;
			}
			
			case VIRTURE: {
				m.setDeltaPosition(30,72);
				m.setPosition(WINDOW_SIZE_X+ rd.nextInt(50),AIR - 100 + rd.nextInt(100));
				m.setBaseHp((int)(25*Settings.number_mobHealthCoeeficent.value));
				m.setMoveLimit(MOB_MOVE_LIMIT_X+10);
				core = m.getCore();
				core.atkPoint = 15;
				core.moveVector = new Vector2(-1f*Settings.number_mobSpeedCoeffient.value, 0);
				core.boundsize = new Vector2(50,42);
				break;
			}
			
			case GOLDEN_GOLEM: {
				break;
			}
			case BLUE_GOLEM: {
				m.setDeltaPosition(60, 40);
				m.setPosition(WINDOW_SIZE_X+ rd.nextInt(50),GROUND);
				m.setBaseHp((int)(85*Settings.number_mobHealthCoeeficent.value));
				m.setMoveLimit(MOB_MOVE_LIMIT_X);
				core = m.getCore();
				core.atkPoint = 35;
				core.moveVector = new Vector2(-0.3f*Settings.number_mobSpeedCoeffient.value, 0);
				core.boundsize=new Vector2(135,130);
				break;
			}
			case GIFT_BOX: {
				m.setDeltaPosition(16, 9);
				m.setPosition(WINDOW_SIZE_X+ rd.nextInt(50),GROUND );
				m.setBaseHp((int)(25*Settings.number_mobHealthCoeeficent.value));
				m.setMoveLimit(MOB_MOVE_LIMIT_X);
				core = m.getCore();
				core.atkPoint = 22;
				core.moveVector = new Vector2(-0.75f*Settings.number_mobSpeedCoeffient.value, 0);
				core.boundsize = new Vector2(61,54);
				break;
			}
			case GINGER: {
				m.setDeltaPosition(34, 16);
				m.setPosition(WINDOW_SIZE_X+ rd.nextInt(50),GROUND );
				m.setBaseHp((int)(15*Settings.number_mobHealthCoeeficent.value));
				m.setMoveLimit(MOB_MOVE_LIMIT_X);
				core = m.getCore();
				core.atkPoint = 55;
				core.moveVector = new Vector2(-0.85f*Settings.number_mobSpeedCoeffient.value, 0);
				core.boundsize = new Vector2(40,92);
				break;
			}
			default: {
				throw new GdxRuntimeException("Invalid monster type");
			}
		}
	}

	@Override
	public Monster clone() {
		Monster b = new Monster(this.animationSet, this.defaultKeyAnimation,
				this.scaling, this.align);
		b.type = type;
		b.setPool(super.pool);
		return b;
	}

	@Override
	public void reset() {
		super.reset();
		switch (type) {
			case BAT: 
			case VIRTURE: {
				setPosition(WINDOW_SIZE_X+ rd.nextInt(50),AIR - 100 + rd.nextInt(100));
				break;
			}
			case BLUE_GOLEM: 
			case GINGER: 
			case GIFT_BOX: {
				setPosition(WINDOW_SIZE_X+ rd.nextInt(50),GROUND);
				break;
			}
			default: {
				throw new GdxRuntimeException("Invalid monster type");
			}
		}
		core.reset();
	}

	int flag = 0;

	public void setBaseHp (int hp ) {
		core.hpPoint = hp;
		core.baseHp = hp;
	}
	
	@Override
	public void act(float delta) {
		if (!this.isDying() && this.getX() <= moveLimit) {
			attack(delta);
		}
		if (!this.isDying() && this.getX() > moveLimit) {
			walk();
		}
		super.act(delta);
	}

	@Override
	public CharacterCore getCore() {
		return core;
	}

	public void die() {
		if (current != CURRENT_ACTION.DIE) {
			current = CURRENT_ACTION.DIE;
			setAnimation("die");
		}
		setDying(true);
	}
	

	
	public int getDamageInflicted() {
		return (current == CURRENT_ACTION.ATTACK && !attakInColddown) ? this.core.atkPoint : 0;
	}
	
	@Override
	public void attack(float delta) {
		if (current != CURRENT_ACTION.ATTACK) {
			current = CURRENT_ACTION.ATTACK;
			setAnimation("attack");
			colddown = super.currentAnimation.getAnimationDuration();
		}
		attackTimer += delta;
		if ( attackTimer >= colddown ) {
			attakInColddown = false;
			attackTimer = 0;
		} else {
			attakInColddown = true;
		}
	}

	@Override
	public void walk() {
		if (current != CURRENT_ACTION.WALK) {
			setAnimation("walk");
			current = CURRENT_ACTION.WALK;
		}
		addAction(Actions.moveBy(core.moveVector.x, core.moveVector.y));
	}

	public void setType(MONSTER_TYPE type) {
		this.type = type;
	}

}
