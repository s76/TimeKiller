package sem5.projekt.ind.models;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class ExtendedAnimation {
	public static final int NORMAL = 0;
	public static final int REVERSED = 1;
	public static final int LOOP = 2;
	public static final int LOOP_REVERSED = 3;
	public static final int LOOP_PINGPONG = 4;
	public static final int LOOP_RANDOM = 5;

	private final TextureRegion[] keyFrames;
	private float frameDuration;
	private float animationDuration;
	
	private int playMode = NORMAL;

	public boolean loopingFlag= true;
	
	public ExtendedAnimation(float frameDuration,
			Array<? extends TextureRegion> keyFrames ) {
		this.frameDuration = frameDuration;
		this.animationDuration = keyFrames.size * frameDuration;
		this.keyFrames = new TextureRegion[keyFrames.size];
		for (int i = 0, n = keyFrames.size; i < n; i++) {
			this.keyFrames[i] = keyFrames.get(i);
		}

		this.playMode = NORMAL;
	}

	public ExtendedAnimation(float frameDuration,
			Array<? extends TextureRegion> keyFrames, int playType) {

		this.frameDuration = frameDuration;
		this.animationDuration = keyFrames.size * frameDuration;
		this.keyFrames = new TextureRegion[keyFrames.size];
		for (int i = 0, n = keyFrames.size; i < n; i++) {
			this.keyFrames[i] = keyFrames.get(i);
		}

		this.playMode = playType;
	}

	public ExtendedAnimation(float frameDuration, TextureRegion... keyFrames) {
		this.frameDuration = frameDuration;
		this.animationDuration = keyFrames.length * frameDuration;
		this.keyFrames = keyFrames;
		this.playMode = NORMAL;
	}
	
	
	public void setFrameDuration (float frameDuration) {
		this.frameDuration = frameDuration;
		this.animationDuration = keyFrames.length * frameDuration;
	}
	
	public float getFrameDuration () {
		return frameDuration;
	}
	
	
	public void setAnimationDuration (float animationDuration ) {
		this.animationDuration = animationDuration;
		this.frameDuration = animationDuration / keyFrames.length;
	}
	
	public float getAnimationDuration () {
		return animationDuration;
	}

	
	public TextureRegion getKeyFrame(float stateTime, boolean looping) {
		int oldPlayMode = playMode;
		if (looping && (playMode == NORMAL || playMode == REVERSED)) {
			if (playMode == NORMAL)
				playMode = LOOP;
			else
				playMode = LOOP_REVERSED;
		} else if (!looping && !(playMode == NORMAL || playMode == REVERSED)) {
			if (playMode == LOOP_REVERSED)
				playMode = REVERSED;
			else
				playMode = LOOP;
		}

		TextureRegion frame = getKeyFrame(stateTime);
		playMode = oldPlayMode;
		return frame;
	}

	public TextureRegion getKeyFrame(float stateTime) {
		int frameNumber = getKeyFrameIndex(stateTime);
		return keyFrames[frameNumber];
	}

	public int getKeyFrameIndex(float stateTime) {
		if (keyFrames.length == 1)
			return 0;

		int frameNumber = (int) (stateTime / frameDuration);
		switch (playMode) {
		case NORMAL:
			frameNumber = Math.min(keyFrames.length - 1, frameNumber);
			break;
		case LOOP:
			frameNumber = frameNumber % keyFrames.length;
			break;
		case LOOP_PINGPONG:
			frameNumber = frameNumber % ((keyFrames.length * 2) - 2);
			if (frameNumber >= keyFrames.length)
				frameNumber = keyFrames.length - 2
						- (frameNumber - keyFrames.length);
			break;
		case LOOP_RANDOM:
			frameNumber = MathUtils.random(keyFrames.length - 1);
			break;
		case REVERSED:
			frameNumber = Math.max(keyFrames.length - frameNumber - 1, 0);
			break;
		case LOOP_REVERSED:
			frameNumber = frameNumber % keyFrames.length;
			frameNumber = keyFrames.length - frameNumber - 1;
			break;

		default:
			// play normal otherwise
			frameNumber = Math.min(keyFrames.length - 1, frameNumber);
			break;
		}

		return frameNumber;
	}

	public TextureRegion[] getKeyFrames() {
		return keyFrames;
	}

	public int getPlayMode() {
		return playMode;
	}

	public void setPlayMode(int playMode) {
		this.playMode = playMode;
	}

	public boolean isAnimationFinished(float stateTime) {
		int frameNumber = (int) (stateTime / frameDuration);
		return keyFrames.length - 1 < frameNumber;
	}
	
}