package sem5.projekt.ind.controller;

import sem5.projekt.ind.models.ExtendedAnimation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

public class TextureResourceManager implements Disposable {
	ObjectMap<String, ObjectMap<String, Array<AtlasRegion>>> om;
	Array<ObjectMap<String, ExtendedAnimation>> animationRef;
	Array<TextureAtlas> atlasRef;
	Skin skin;
	
	@Override
	public void dispose() {
		for (int i = 0; i < atlasRef.size; i++) {
			atlasRef.get(i).dispose();
		}
		skin.dispose();
		animationRef.clear();
		om.clear();
	}

	public TextureResourceManager() {
		om = new ObjectMap<String, ObjectMap<String, Array<AtlasRegion>>>();
		animationRef = new Array<ObjectMap<String, ExtendedAnimation>>();
		atlasRef = new Array<TextureAtlas>();
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
	}

	public Skin getSkin() {
		return skin;
	}
	public ObjectMap<String, Array<AtlasRegion>> loadImageSet(String setname,
			String[] actionname, Array<AtlasRegion>... array) {
		ObjectMap<String, Array<AtlasRegion>> k = new ObjectMap<String, Array<AtlasRegion>>();
		for (int i = 0; i < actionname.length; i++) {
			k.put(actionname[i], array[i]);
		}
		om.put(setname, k);
		return k;
	}

	public Array<AtlasRegion> getFrameSet(String f, String dir) {
		TextureAtlas ta = new TextureAtlas(Gdx.files.internal(f),
				Gdx.files.internal(dir));
		atlasRef.add(ta);
		return ta.getRegions();
	}

	public Array<AtlasRegion> exactFrames(Array<AtlasRegion> frames,
			int... indices) {
		Array<AtlasRegion> k = new Array<AtlasRegion>(indices.length);
		for (int i = 0; i < indices.length; i++) {
			k.add(frames.get(indices[i]));
		}
		return k;
	}

	public void checkOrder (Array<AtlasRegion> frame) {
		for(int i =0;i<frame.size;i++ ) {
			System.out.println(i+":" +frame.get(i).name);
		}
	}
}
