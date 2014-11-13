package sem5.projekt.ind.models.abstractModels;

import com.badlogic.gdx.math.Vector2;

public interface AbstractBullet {
	public static enum STATUS { BEFORE_COLLISION, COLLIDING, AFTER_COLLISION };
	public void collide();
	public int dealDamage(Vector2 position, Vector2 area);
	public STATUS getStatus();
	public void setStatus(STATUS status);
	public void setSpeed(float s);
	public void setDirection(Vector2 v);
}
