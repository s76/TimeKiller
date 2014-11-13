package sem5.projekt.ind.models;


import com.badlogic.gdx.math.Vector2;

public class CharacterCore {
//	public static enum TYPE { HERO, MONSTER  };
//	public static enum ATK_TYPE { MELEE, RANGER, SCUIDE };
//	public static enum MOVE_TYPE { ONGROUND, FLYING };
//	public static enum DAMAGE_TYPE { PHYSIC, MAGIC };
//	public static enum SPECIALITY { 
//		NONE,
//		MAGIC_RESIS, 
//		PHYSIC_RESIS, 		
//		MAGIC_IMMUNE, 
//		PHYSIC_IMMUNE
//	}
	
	private CustomImage img;
	
	public Vector2 boundsize;
//	public boolean dealingDamage;
	//public boolean active;
	public Vector2 atkArea;
//	public SPECIALITY speciality;
//	public MOVE_TYPE moveType;
//	public ATK_TYPE atkType;
//	public TYPE chartype;
//	public DAMAGE_TYPE dmgType;
//	public RepeatAction moveAction;
	public float speed;
	public Vector2 moveVector;
	
	public int atkPoint;
	public int hpPoint;
	public int baseHp;
	public int scorePoint;
	
	public CharacterCore (CustomImage img ) {
		this(img,0,0);
	}
	
	public CharacterCore (CustomImage img ,int w, int h) {
		this.img = img;
		boundsize = new Vector2 (w,h);
	}

	public Vector2 getBoundPosition () {
		return new Vector2( img.getX() , img.getY() );
	}
	
	public void reset (){
		this.hpPoint = baseHp;
	}
}
