package sem5.projekt.ind;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Time-Killer-by-Sheep76";
		cfg.useGL20 = false;
		cfg.width = 800;
		cfg.height = 480;

		new LwjglApplication(new ProjektIndVer2(), cfg);

	}
}
