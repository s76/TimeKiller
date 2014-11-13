package sem5.projekt.ind.models;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

abstract public class CustomPool<T>  {
		public int id;
		
		public final int max;
		public int peak;

		private final Array<T> freeObjects;

		public CustomPool () {
			this(16, Integer.MAX_VALUE);
		}

		public CustomPool (int initialCapacity) {
			this(initialCapacity, Integer.MAX_VALUE);
		}

		public CustomPool (int initialCapacity, int max) {
			freeObjects = new Array<T>(false, initialCapacity);
			this.max = max;
		}

		abstract protected T newObject ();

		public T obtain () {
			
			return freeObjects.size == 0 ? newObject() : freeObjects.pop();
		}

		public void free (T object) {
			if (object == null) throw new IllegalArgumentException("object cannot be null.");
			if (freeObjects.size < max) {
				freeObjects.add(object);
				peak = Math.max(peak, freeObjects.size);
			}
			
			if (object instanceof Poolable) {
				((Poolable)object).reset();
			}
		}

		public void freeAll (Array<T> objects) {
			if (objects == null) throw new IllegalArgumentException("object cannot be null.");
			for (int i = 0; i < objects.size; i++) {
				T object = objects.get(i);
				if (object == null) continue;
				if (freeObjects.size < max) freeObjects.add(object);
				if (object instanceof Poolable) ((Poolable)object).reset();
			}
			peak = Math.max(peak, freeObjects.size);
		}

		public void clear () {
			for ( T a : freeObjects ) {
				if ( a instanceof Disposable ) {
					((Disposable )a).dispose();
				}
			}
			freeObjects.clear();
		}

		public int getFree () {
			return freeObjects.size;
		}

		static public interface Poolable {
			public void reset ();
		}
}
