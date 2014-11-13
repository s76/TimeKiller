package sem5.projekt.ind.models;

import sem5.projekt.ind.controller.CollisionUnit;
import sem5.projekt.ind.models.CustomPool.Poolable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Scaling;

public abstract class CustomImage extends Widget implements Poolable {
	@SuppressWarnings("rawtypes")
	protected CustomPool pool;
	
	protected String defaultKeyAnimation;
	protected ObjectMap<String, ExtendedAnimation> animationSet;
	protected Scaling scaling;
	protected int align = Align.center; 
	protected boolean isDying;
	
	protected Vector2 delta_position = new Vector2(0, 0);
	protected ExtendedAnimation currentAnimation;
	protected float stateTime;
	protected float imageX, imageY, imageWidth, imageHeight;
	protected Drawable drawable;
	protected CollisionUnit col;
	
	public void reset(){
		setDying(false);
		currentAnimation = animationSet.get(defaultKeyAnimation);
	}
	
	abstract public CharacterCore getCore();
	
	abstract public CustomImage clone ();
	
	public CustomImage(ObjectMap<String, ExtendedAnimation> animationSet, String defaultkey ){
		this(animationSet, defaultkey, Scaling.stretch,
				Align.center);
	}

	public CustomImage(ObjectMap<String, ExtendedAnimation> animationSet,
			String defaultkey, Scaling scaling) {
		this(animationSet, defaultkey, scaling, Align.center);
	}

	public CustomImage(ObjectMap<String, ExtendedAnimation> animationSet,
			String defaultkey, Scaling scaling,
			int align) {
		this.animationSet = animationSet;
		this.defaultKeyAnimation = defaultkey;
		this.currentAnimation = animationSet.get(defaultkey);
		setDrawable(new TextureRegionDrawable(currentAnimation.getKeyFrame(0)));
		this.scaling = scaling;
		this.align = align;
		this.stateTime = 0;
		setWidth(getPrefWidth());
		setHeight(getPrefHeight());
	}

	public void addColiisionUnit(CollisionUnit collisionUnit) {
		this.col = collisionUnit;
	}
	
	public void setAnimation(String key) {
		this.stateTime = 0;
		this.currentAnimation = animationSet.get(key);
		if ( currentAnimation == null ) throw new GdxRuntimeException("Invalid animation key string");
		setDrawable(new TextureRegionDrawable(currentAnimation.getKeyFrame(0)));
		setWidth(getPrefWidth());
		setHeight(getPrefHeight());
	}

	public void layout() {
		if (drawable == null)
			return;

		float regionWidth = drawable.getMinWidth();
		float regionHeight = drawable.getMinHeight();
		float width = getWidth();
		float height = getHeight();

		Vector2 size = scaling.apply(regionWidth, regionHeight, width, height);
		imageWidth = size.x;
		imageHeight = size.y;

		if ((align & Align.left) != 0)
			imageX = 0;
		else if ((align & Align.right) != 0)
			imageX = (int) (width - imageWidth);
		else
			imageX = (int) (width / 2 - imageWidth / 2);

		if ((align & Align.top) != 0)
			imageY = (int) (height - imageHeight);
		else if ((align & Align.bottom) != 0)
			imageY = 0;
		else
			imageY = (int) (height / 2 - imageHeight / 2);
	}

	@SuppressWarnings("unchecked")
	protected void freeFromScene () {
		getParent().removeActor(this);
		if ( pool != null ) pool.free(this);
		if ( col != null ) col.remove(this);
	}
	
	public void draw(SpriteBatch batch, float parentAlpha) {
		if (isDying ) {
			if ( currentAnimation.isAnimationFinished(stateTime)) {
				freeFromScene();
				return;
			}
		}

		stateTime += Gdx.graphics.getDeltaTime();
		setDrawable(new TextureRegionDrawable(currentAnimation.getKeyFrame(
				stateTime, currentAnimation.loopingFlag)));

		validate();

		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

		float x = getX() - this.delta_position.x;
		float y = getY() - this.delta_position.y;
		float scaleX = getScaleX();
		float scaleY = getScaleY();

		if (drawable != null) {
			if (drawable.getClass() == TextureRegionDrawable.class) {
				TextureRegion region = ((TextureRegionDrawable) drawable)
						.getRegion();
				float rotation = getRotation();
				if (scaleX == 1 && scaleY == 1 && rotation == 0)
					batch.draw(region, x + imageX, y + imageY, imageWidth,
							imageHeight);
				else {
					batch.draw(region, x + imageX, y + imageY, getOriginX()
							- imageX, getOriginY() - imageY, imageWidth,
							imageHeight, scaleX, scaleY, rotation);
				}
			} else
				drawable.draw((SpriteBatch) batch, x + imageX, y + imageY, imageWidth
						* scaleX, imageHeight * scaleY);

		}

	}

	public void setDrawable(Skin skin, String drawableName) {
		setDrawable(skin.getDrawable(drawableName));
	}

	public void setDrawable(Drawable drawable) {
		if (this.drawable == drawable)
			return;
		if (drawable != null) {
			if (getPrefWidth() != drawable.getMinWidth()
					|| getPrefHeight() != drawable.getMinHeight())
				invalidateHierarchy();
		} else
			invalidateHierarchy();
		this.drawable = drawable;
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public void setScaling(Scaling scaling) {
		if (scaling == null)
			throw new IllegalArgumentException("scaling cannot be null.");
		this.scaling = scaling;
	}

	public void setAlign(int align) {
		this.align = align;
	}

	public float getMinWidth() {
		return 0;
	}

	public float getMinHeight() {
		return 0;
	}

	public float getPrefWidth() {
		if (drawable != null)
			return drawable.getMinWidth();
		return 0;
	}

	public float getPrefHeight() {
		if (drawable != null)
			return drawable.getMinHeight();
		return 0;
	}

	public float getImageX() {
		return imageX;
	}

	public float getImageY() {
		return imageY;
	}

	public float getImageWidth() {
		return imageWidth;
	}

	public float getImageHeight() {
		return imageHeight;
	}

	public void setDeltaPosition(int x, int y) {
		delta_position.set(x, y);
	}

	public Vector2 getDeltaPosition() {
		return delta_position;
	}

	public void resetStateTime() {
		stateTime= 0;	
	}
	
	
	public void setDying (boolean k){
		isDying = k;
	}
	
	public boolean isDying () {
		return isDying;
	}

	public void setPool(CustomPool pool) {
		this.pool = pool;
	}
}
