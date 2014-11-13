package sem5.projekt.ind.controller;

import static sem5.projekt.ind.controller.CONST.CANNON_ORGIN_X;
import static sem5.projekt.ind.controller.CONST.CANNON_ORGIN_Y;
import static sem5.projekt.ind.controller.CONST.CANNON_X;
import static sem5.projekt.ind.controller.CONST.CANNON_Y;
import static sem5.projekt.ind.controller.CONST.GROUND;
import static sem5.projekt.ind.controller.CONST.WINDOW_SIZE_Y;
import sem5.projekt.ind.models.ExtendedAnimation;
import sem5.projekt.ind.object.realActors.Bullet;
import sem5.projekt.ind.object.realActors.Monster;
import sem5.projekt.ind.object.realActors.Monster.MONSTER_TYPE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/*
 * Wrapper in MainController, just to shorten the code
 */

public class MainControllerBackEnd {
	TextureResourceManager rm;
	private HealthDisplayer hd;
	private CollisionUnit col;
	private MobGen mobGen;
	private TimeDisplayer td;
	private BulletGen bulletGen;
	private StrengthBarDisplayer sbd;
	
	private Image cannon;
	/////
	Vector2 v = new Vector2();
	RotateByAction r;
	float k = 0;
	boolean flag = false;
	private boolean running;
	private boolean paused;
	
	
	public MainControllerBackEnd(TextureResourceManager rm) {
		this.rm = rm;
		this.hd = new HealthDisplayer();
		this.td = new TimeDisplayer();
		this.bulletGen = new BulletGen();
		this.sbd = new StrengthBarDisplayer();
		this.col = new CollisionUnit(hd);
		this.mobGen = new MobGen();
		mobGen.setCollisionUnit(col);
		this.running = true;
		
	}
	
	public void createForeGroundElements(Group group) {
		Skin skin = rm.getSkin();
		
		Array<AtlasRegion> foreground = rm.getFrameSet("misc/fore.atlas",
				"misc");
		
		//cannon
		createCannon(group,foreground.get(3) );
		
		//create cannon base
		createCannonBase(group, foreground.get(2) );
		
		// create health symbol
		createHealthSymbol(group, foreground.get(1));

		// ready full signal
		createFullStrengthSignal(group, foreground.get(4));
		
		//create strnegth fill 
		createFillStrengthSignal(group,foreground.get(4));
				
		// strength bar
		createStrengthBar(group, foreground.get(0));
		
		// pause button
		createPauseButton(group, skin);

		// health display
		createHealthDisplay(group, skin);
		
		//time label
		createTimeLabel(group, skin);

	}
	
	private void createFillStrengthSignal(Group group,
			AtlasRegion atlasRegion) {
		TiledDrawable td = new TiledDrawable(atlasRegion);
		sbd.registerStrengthFill(td,CONST.FILL_SIGNAL_X,CONST.FILL_SIGNAL_Y);
		group.addActor(sbd.getFillDisplayer());
	}

	public void createBackGroundElements(Group group) {
		Array<AtlasRegion> background = rm.getFrameSet("misc/background.atlas", "misc");
		//background image
		createBackground(group,background.get(0) );
		

		
		//create castle
		createCastle(group,background.get(1) );
		
		
	}
	
	public void createTemplates() {
		//create bullet template
		Array<AtlasRegion> bullet_array = rm.getFrameSet("misc/bullet.atlas","misc");
		int[] fly = {0 };
		int[] explode = {1,2,3,4,5 };
		
		createBulletTemplate(bulletGen,bullet_array,fly,explode);
		
		//create monster templates
		createMonsterTemplates(mobGen, col);
	}
	private void createBackground(Group group, AtlasRegion m) {
		Image backgroundImg = new Image(m);
		group.addActor(backgroundImg);
	}

	private void createTimeLabel(Group foreground, Skin skin) {
		Label l = new Label("TIME: 0", skin);
		l.setPosition(500, 35);
		l.setFontScaleX(1.5f);
		td.registerLabel(l);
		foreground.addActor(l);
	}

	private void createHealthDisplay(Group foreground, Skin skin) {
		Label label = new Label(Integer.toString(hd.getHealth()), skin);
		label.setPosition(70, 420);
		label.setFontScaleX(1.5f);
		foreground.addActor(label);
		hd.registerLabel(label);
	}

	private void createMonsterTemplates(MobGen gen, CollisionUnit col) {
		Array<AtlasRegion> giftbox_array = rm.getFrameSet(
				"mob/giftbox.atlas", "mob");
		ExtendedAnimation giftbox_walk = new ExtendedAnimation(0.1f,
				rm.exactFrames(giftbox_array, 9, 10, 11, 12));
		ExtendedAnimation giftbox_die = new ExtendedAnimation(0.1f,
				rm.exactFrames(giftbox_array, 8, 5, 7, 6, 6, 6));
		giftbox_die.loopingFlag = false;
		ExtendedAnimation giftbox_attack = new ExtendedAnimation(0.2f,
				rm.exactFrames(giftbox_array, 0, 1, 2, 3, 4));
		ObjectMap<String, ExtendedAnimation> giftbox_set = new ObjectMap<String, ExtendedAnimation>();

		giftbox_set.put("walk", giftbox_walk);
		giftbox_set.put("die", giftbox_die);
		giftbox_set.put("attack", giftbox_attack);

		Monster img = new Monster(giftbox_set, "walk");
		img.setType(MONSTER_TYPE.GIFT_BOX);

		gen.registerTemplate(img);
		col.register("monster", img);

		Array<AtlasRegion> golemcave_array = rm.getFrameSet(
				"mob/golemcave.atlas", "mob");
		ExtendedAnimation golemcave_walk = new ExtendedAnimation(0.2f,
				rm.exactFrames(golemcave_array, 8, 9, 10, 11));
		ExtendedAnimation golemcave_die = new ExtendedAnimation(0.1f,
				rm.exactFrames(golemcave_array, 4, 5, 6, 7, 7, 7));
		golemcave_die.loopingFlag = false;
		ExtendedAnimation golemcave_attack = new ExtendedAnimation(0.25f,
				rm.exactFrames(golemcave_array, 0, 1, 2, 3));
		ObjectMap<String, ExtendedAnimation> golemcave_set = new ObjectMap<String, ExtendedAnimation>();

		golemcave_set.put("walk", golemcave_walk);
		golemcave_set.put("die", golemcave_die);
		golemcave_set.put("attack", golemcave_attack);

		img = new Monster(golemcave_set, "walk");
		img.setType(MONSTER_TYPE.BLUE_GOLEM);

		gen.registerTemplate(img);
		col.register("monster", img);

		Array<AtlasRegion> bat_array1 = rm.getFrameSet("mob/smallbat.atlas",
				"mob");
		//rm.checkOrder(bat_array);
		ExtendedAnimation bat_attack = new ExtendedAnimation(0.15f,
				rm.exactFrames(bat_array1, 0, 1, 2));
		ExtendedAnimation bat_die = new ExtendedAnimation(0.2f, rm.exactFrames(
				bat_array1, 3, 4, 5, 6, 6));
		bat_die.loopingFlag = false;
		ExtendedAnimation bat_walk = new ExtendedAnimation(0.1f,
				rm.exactFrames(bat_array1, 7, 8, 9, 10));
		ObjectMap<String, ExtendedAnimation> bat_set = new ObjectMap<String, ExtendedAnimation>();

		bat_set.put("attack", bat_attack);
		bat_set.put("die", bat_die);
		bat_set.put("walk", bat_walk);

		img = new Monster(bat_set, "walk");
		img.setType(MONSTER_TYPE.BAT);

		gen.registerTemplate(img);
		col.register("monster", img);
		
		Array<AtlasRegion> virture_array = rm.getFrameSet("mob/virture.atlas",
				"mob");
		//rm.checkOrder(bat_array);
		ExtendedAnimation virture_attack = new ExtendedAnimation(0.2f,
				rm.exactFrames(virture_array, 0, 1, 2));
		ExtendedAnimation virture_die = new ExtendedAnimation(0.2f, rm.exactFrames(
				virture_array, 3, 4, 5, 6, 6));
		virture_die.loopingFlag = false;
		ExtendedAnimation virture_walk = new ExtendedAnimation(0.1f,
				rm.exactFrames(virture_array, 7, 8, 9, 10));
		ObjectMap<String, ExtendedAnimation> virture_set = new ObjectMap<String, ExtendedAnimation>();

		virture_set.put("attack", virture_attack);
		virture_set.put("die", virture_die);
		virture_set.put("walk", virture_walk);

		img = new Monster(virture_set, "walk");
		img.setType(MONSTER_TYPE.VIRTURE);

		gen.registerTemplate(img);
		col.register("monster", img);
		
		Array<AtlasRegion> array = rm.getFrameSet("mob/ginger.atlas",
				"mob");
		//rm.checkOrder(array);
		ExtendedAnimation attack = new ExtendedAnimation(0.1f,
				rm.exactFrames(array, 0, 1, 2,3,4));
		ExtendedAnimation die = new ExtendedAnimation(0.2f, rm.exactFrames(
				array, 5, 6, 7, 8));
		die.loopingFlag = false;
		ExtendedAnimation walk = new ExtendedAnimation(0.1f,
				rm.exactFrames(array, 9, 10,12,11));
		ObjectMap<String, ExtendedAnimation> set = new ObjectMap<String, ExtendedAnimation>();

		set.put("attack", attack);
		set.put("die", die);
		set.put("walk", walk);

		img = new Monster(set, "walk");
		img.setType(MONSTER_TYPE.GINGER);

		gen.registerTemplate(img);
		col.register("monster", img);
	}

	private void createHealthSymbol(Group foreground2,
			AtlasRegion atlasRegion) {
		Image img = new Image(atlasRegion);
		img.setPosition(15, 408);
		foreground2.addActor(img);
	}

	private void createFullStrengthSignal(Group foreground2,
			AtlasRegion atlasRegion) {
		// ready to fire
		Image img = new Image(atlasRegion);
		img.setPosition(52, 17);
		
		img.setVisible(false);
		foreground2.addActor(img);
		
		sbd.registerStrngthFulll(img);
	}

	private void createStrengthBar(Group foreground,
			AtlasRegion atlasRegion) {
		Image img = new Image(atlasRegion);
		img.setPosition(14, 14);
		foreground.addActor(img);
		
	}

	

	private void createPauseButton(Group g, Skin skin) {
		TextButton im = new TextButton("II", skin);
		im.setSize(60, 60);
		im.setPosition(720, 400);
		im.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				pause();
			}
		});
		g.addActor(im);
	}

	private void createBulletTemplate(BulletGen bg2,
			Array<AtlasRegion> bullet_array, int[] fly, int[] explode) {
		ExtendedAnimation bullet_fly = new ExtendedAnimation(0f,
				rm.exactFrames(bullet_array, fly));
		ExtendedAnimation bullet_explode = new ExtendedAnimation(0.15f,
				rm.exactFrames(bullet_array,explode));
		bullet_explode.loopingFlag = false;
		ObjectMap<String, ExtendedAnimation> bullet_set = new ObjectMap<String, ExtendedAnimation>();

		bullet_set.put("fly", bullet_fly);
		bullet_set.put("explode", bullet_explode);

		Bullet bl = new Bullet(bullet_set, "fly");
		bulletGen.registerTemplate(bl);
	}

	private void createCastle(Group background2, AtlasRegion atlasRegion) {
		Image castle = new Image(atlasRegion);
		castle.setPosition(CONST.CASTLE_X, GROUND);
		background2.addActor(castle);
	}

	private void createCannonBase(Group background2, AtlasRegion atlasRegion) {
		Image cnbase = new Image(atlasRegion);
		cnbase.setPosition(CONST.CANNON_BASE_X, CONST.CANNON_BASE_Y);
		background2.addActor(cnbase);
	}

	private void createCannon(Group background2, AtlasRegion atlasRegion) {
		cannon = new Image(atlasRegion);
		cannon.setPosition(CANNON_X, CANNON_Y);
		cannon.setOrigin(CANNON_ORGIN_X, CANNON_ORGIN_Y);
		
		background2.addActor(cannon);
	}

	public CollisionUnit getCollisionUnit() {
		return col;
	}

	public MobGen getMobGenerator() {
		return mobGen;
	}

	public BulletGen getBulletGenerator() {
		return bulletGen;
	}
	
	private float last_x,last_y;
	private void handleTouchInput(float delta,Group g) {
		if (Gdx.input.isTouched()) {
			v.set(Gdx.input.getX() - (CANNON_X + CANNON_ORGIN_X),
					(WINDOW_SIZE_Y - Gdx.input.getY())
							- (CANNON_Y + CANNON_ORGIN_Y));
			float k = v.angle() - 180;
			if (k > -80 && k < 85) {
				sbd.update(delta);
				flag = true;
				last_x =v.x;
				last_y =v.y;
				cannon.addAction(Actions.rotateTo(k));
			} else {
				if(flag ) {
					fireBullet(g,v.set(last_x, last_y));
					flag = false;
				}
			}
		}
		if (flag == true && !Gdx.input.isTouched()) {
			fireBullet(g,v.set(v.x, v.y));
			flag = false;
		}
	}
	
	private void fireBullet(Group g,Vector2 set) {
		Bullet b = bulletGen.getBullet();
		Bullet.setUpBulletNumber1(b);
		b.setPosition(CANNON_X+CANNON_ORGIN_X, CANNON_Y + CANNON_ORGIN_Y-4);
		b.setDirection(v.set(-v.x, -v.y));
		b.setBonusDmg(sbd.getDamageBonus());

		g.addActor(b);
		col.register("bullet", b);
	}

	public void act ( float delta, Group mob,Group bullet ) {
		td.update(delta);
		col.checkCollision();
		mobGen.generate(delta, mob);
		handleTouchInput(delta,bullet);
	}

	public boolean isGameEnd() {
		return hd.getHealth() <= 0;
	}

	public TextureResourceManager getTextureResourceManager() {
		return rm;
	}

	public boolean isRunning() {
		return running;
	}

	public boolean isPaused() {
		// TODO Auto-generated method stub
		return paused;
	}

	public void pause() {
		paused=true;
	}
	public void resume() {
		paused=false;
	}

	public void reset() {
		hd.reset();
		mobGen.reset();
		td.reset();
		col.reset();
		sbd.reset();
	}
}
