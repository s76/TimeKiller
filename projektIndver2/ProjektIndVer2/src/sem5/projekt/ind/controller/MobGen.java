package sem5.projekt.ind.controller;

import java.util.Random;

import sem5.projekt.ind.models.CustomPool;
import sem5.projekt.ind.object.realActors.Monster;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class MobGen {
	float timer;
	int nb;
	float prog_time;
	
	private static int number_mobGenFrequence;
	private static float time_mobGenFrequence;
	
	Array<Monster> templates;
	Array<CustomPool<Monster>> pools;

	private CollisionUnit col;

	private Random rd;
	private int choice;
	

	public MobGen () {
		rd = new Random();
		templates = new Array<Monster>(8);
		pools = new Array<CustomPool<Monster>>(8);
		timer = 1;
		nb = 0;
		prog_time = 0f;
		time_mobGenFrequence = Settings.time_mobGenFrequence.value;
		number_mobGenFrequence = (int) Settings.number_mobGenFrequence.value;
	}

	public void registerTemplate(Monster m) {
		templates.add(m);
		CustomPool<Monster> p = new CustomPool<Monster>() {
			@Override
			protected Monster newObject() {
				Monster m = templates.get(choice).clone();
				return m;
			};
		};
		m.setPool(p);
		pools.add(p);
	}

	public void reset() {
		timer = 1;
		nb = 0;
		prog_time = 0f;
		time_mobGenFrequence = Settings.time_mobGenFrequence.value;
		number_mobGenFrequence = (int) Settings.number_mobGenFrequence.value;
	}

	public void clearTemplates() {
		templates.clear();
	}

	public int getMobGenNumber(float delta) {
		timer += delta;
		if (timer > prog_time) {
			prog_time += time_mobGenFrequence;
			nb = (int) Math.log(timer / 2);
			nb = (nb+1)/2;
			return nb < number_mobGenFrequence ? nb:number_mobGenFrequence;
		}
		return 0;
	}

	public void setCollisionUnit(CollisionUnit col) {
		this.col = col;
	}

	public void generate(float delta, Group g) {
		int k = getMobGenNumber(delta);
		if (k == 0)
			return;
		for (int i = 0; i < k; i++) {
			choice = rd.nextInt(templates.size);
			Monster m = pools.get(choice).obtain();
			Monster.setUpMonster(m);
			g.addActor(m);
			col.register("monster", m);
		}
	}

	public void generate(float delta, Stage g) {
		int k = getMobGenNumber(delta);
		if (k == -1)
			return;
		for (int i = 0; i < k; i++) {
			choice = rd.nextInt(templates.size);
			Monster m = pools.get(rd.nextInt(templates.size)).obtain();
			Monster.setUpMonster(m);
			g.addActor(m);
			col.register("monster", m);
		}
	}
}
