package utlities;

import sem5.projekt.ind.controller.CollisionUnit;
import sem5.projekt.ind.models.CustomImage;

import com.badlogic.gdx.math.Vector2;

public class ObjectActionUnit {
	CollisionUnit dcu;
	CustomImage actor;

	public ObjectActionUnit(CustomImage actor, CollisionUnit dcu) {
		this.dcu = dcu;
		this.actor = actor;
	}

	public void dealDamage(CustomImage actor, float x, float y) {
	}


	public void die(CustomImage actor) {
		// actor.getCore().setActive(false);
	}

	private boolean overlaps(Vector2 pos1, Vector2 area1, Vector2 pos2,
			Vector2 area2) {
		return pos1.x <= pos2.x + area2.x && pos1.x + area1.x >= pos2.x
				&& pos1.y <= pos2.y + area2.y && pos1.y + area1.y >= pos2.y;
	}

	private boolean contains(Vector2 pos1, Vector2 area1, Vector2 pos2) {
		return pos1.x <= pos2.x && pos1.x + area1.x >= pos2.x
				&& pos1.y <= pos2.y && pos1.y + area1.y >= pos2.y;
	}
}
