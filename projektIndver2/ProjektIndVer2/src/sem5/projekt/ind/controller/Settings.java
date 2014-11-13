package sem5.projekt.ind.controller;


public enum Settings {
	time_mobGenFrequence, 			// 0	
	time_strengthBarFull, 			// 1
	number_mobGenFrequence, 		// 2
	numbder_startHealth, 			// 3
	number_mobSpeedCoeffient, 		// 4
	number_mobHealthCoeeficent, 	// 5
	number_bulletBaseAttackPoint, 	// 6
	number_bulletBonusConst;		// 7
	
	public float value;
	
	private static final float[] easy_mode = { 3,1.3f,3,10000 ,1,1 ,10,50};
	private static final float[] normal_mode = { 2.6f, 1.3f,3,1000 ,1.25f,1,8,40};
	private static final float[] hard_mode = { 2.3f, 1.5f,4, 100 ,1.5f,1.25f,6,30};
	private static final float[] insane_mode = { 2f, 1.5f ,4,1,1.5f,1.25f,6,30};
	
	private static float[] set;
	
	public void setValue(float v) {
		value = v;
	}
	
	private static void update() {
		time_mobGenFrequence.setValue(set[0]);
		time_strengthBarFull.setValue(set[1]); 
		number_mobGenFrequence.setValue(set[2]);
		numbder_startHealth.setValue(set[3]);
		number_mobSpeedCoeffient.setValue(set[4]);
		number_mobHealthCoeeficent.setValue(set[5]);
		number_bulletBaseAttackPoint.setValue(set[6]);
		number_bulletBonusConst.setValue(set[7]);
	}
	
	public static void easy_mode() {
		set = easy_mode;
		update();
	}
	public static void normal_mode() {
		set = normal_mode;
		update();
	}
	public static void hard_mode() {
		set = hard_mode;
		update();
	}
	public static void insane_mode() {
		set = insane_mode;
		update();
	}
	
	public static String getModeName() {
		if ( set == easy_mode ) return "Easy";
		if ( set == normal_mode ) return "Normal";
		if ( set == hard_mode ) return "Hard";
		if ( set == insane_mode ) return "Insane";
		return null;
	}
}
