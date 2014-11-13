package utlities;

import com.badlogic.gdx.Gdx;

public class TouchMoveDetector {
	public enum TOUCH_MOVE {
		NO_MOVE, UP, DOWN, LEFT, RIGHT, UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT
	};

	private static final int gridSize = 15;
	private static final double TAN_60 = 1.733; // = COTAN_30
	private static final double TAN_30 = 0.577; // = COTAN_60
	private static TOUCH_MOVE[] array = new TOUCH_MOVE[10];
	private static int index = 0;
	private static boolean running = false;
	private static boolean LOG = false;
	
	
	/*
	 * single move  0.3 s
	 * comb move 1.8 s ~~ 2 s
	 */
	
	
	static TOUCH_MOVE getTouchMove() {
		if(LOG) System.out.println("running : " + running + " getTouchmove()");
		if (Gdx.input.isTouched()) {
			if(LOG) System.out.println("\t___level 2.1 getTouchmove()");
	
			running = true;
			
			int deltaX = Gdx.input.getDeltaX();
			int deltaY = Gdx.input.getDeltaY();

			if (deltaX == 0)
				deltaX = 1;
			int absDeltaX = Math.abs(deltaX);
			int absDeltaY = Math.abs(deltaY);

			if ((absDeltaX < gridSize) && (absDeltaY < gridSize)) {
				return TOUCH_MOVE.NO_MOVE;
			}

			float absTan = ((float) absDeltaY) / absDeltaX;
			if (absTan > TAN_60) {
				if (deltaY < 0) {
					if (index + 1 < 10)
						array[index++] = TOUCH_MOVE.UP;
					return TOUCH_MOVE.NO_MOVE;
				} else {
					if (index + 1 < 10)
						array[index++] = TOUCH_MOVE.DOWN;
					return TOUCH_MOVE.NO_MOVE;
				}
			} else if (absTan < TAN_30) {
				if (deltaX > 0) {
					if (index + 1 < 10)
						array[index++] = TOUCH_MOVE.RIGHT;
					return TOUCH_MOVE.NO_MOVE;
				} else {
					if (index + 1 < 10)
						array[index++] = TOUCH_MOVE.LEFT;
					return TOUCH_MOVE.NO_MOVE;
				}
			} else {
				if (deltaX > 0) {
					if (deltaY < 0) {
						if (index + 1 < 10)
							array[index++] = TOUCH_MOVE.UP_RIGHT;
						return TOUCH_MOVE.NO_MOVE;
					} else {
						if (index + 1 < 10)
							array[index++] = TOUCH_MOVE.DOWN_RIGHT;
						return TOUCH_MOVE.NO_MOVE;
					}
				} else {
					if (deltaY < 0) {
						if (index + 1 < 10)
							array[index++] = TOUCH_MOVE.UP_LEFT;
						return TOUCH_MOVE.NO_MOVE;
					} else {
						if (index + 1 < 10)
							array[index++] = TOUCH_MOVE.DOWN_LEFT;
						return TOUCH_MOVE.NO_MOVE;
					}
				}
			}
		} else {
			if (running) {
				running = false;
				if (index == 0 )  return TOUCH_MOVE.NO_MOVE;
				
				int counter1 = 1,counter2 = 0;
				TOUCH_MOVE tmp = TOUCH_MOVE.NO_MOVE;
				int last = index -1;
				
				for ( int i = index -2  ; i >= 0 ; i-- ) {
					if ( array[i] != array[last] ) {
						if ( tmp == TOUCH_MOVE.NO_MOVE ) {
							tmp = array[i];
						} 
						
						if ( array[i] != tmp ) {
							index = 0;
							return TOUCH_MOVE.NO_MOVE;
						} 
						
					} else {
						counter1 += 1;
					}
				}
				
				if ( counter1 >= index/2) {
					index = 0;
					return array[last];
				} else  {
					index = 0;
					return tmp;
				}
			}
			return TOUCH_MOVE.NO_MOVE;
		}
	}
}
