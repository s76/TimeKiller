package sem5.projekt.ind.models.abstractModels;

public interface AbstractMonster {
	public static enum CURRENT_ACTION { ATTACK, WALK, DIE };
	public void attack(float delta);
	public void walk();
	public void die();
}
