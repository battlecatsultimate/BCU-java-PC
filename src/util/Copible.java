package util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.BCUException;
import main.Printer;

/**
 * TODO <br>
 * 1. EneRand access <br>
 * 2. Random number generator <br>
 * capable to copy: <br>
 * 1. Primary field <br>
 * 2. String field <br>
 * 3. Copible field <br>
 * 4. Array field of type 1~4 <br>
 * 5. Cloneable Collection field with generic type of 1~4
 */
public strictfp class Copible extends ImgCore implements Cloneable {

	public static final String NONC = "NONC_";

	public static final Set<Class<?>> OLD = new HashSet<Class<?>>();
	public static final Set<Class<?>> UNCHECKED = new HashSet<Class<?>>();

	public static final Map<Integer, Object> MAP = new HashMap<>();

	private static boolean checkField(Class<?> tc) {
		if (tc.isPrimitive())
			return true;
		if (tc == String.class)
			return true;
		boolean b0 = Copible.class.isAssignableFrom(tc);
		boolean b1 = NonCopible.class.isAssignableFrom(tc);
		if (b0 && b1)
			return false;
		if (b0 || b1)
			return true;
		if (tc.isArray())
			return checkField(tc.getComponentType());
		return false;
	}

	@SuppressWarnings("unchecked")
	private static List<Field> getField(Class<? extends Copible> cls) {
		List<Field> fl = new ArrayList<Field>();
		Field[] fs = cls.getDeclaredFields();
		for (Field f : fs)
			if (!Modifier.isStatic(f.getModifiers())) {
				f.setAccessible(true);
				fl.add(f);
			}
		Class<? extends Copible> sc = null;
		if (Copible.class.isAssignableFrom(cls) && Copible.class != cls.getSuperclass())
			sc = (Class<? extends Copible>) cls.getSuperclass();
		if (sc != null)
			fl.addAll(getField(sc));
		return fl;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Object hardCopy(Object obj) {
		if (obj == null)
			return null;
		Class<?> c = obj.getClass();
		if (c.isPrimitive() || c == String.class || NonCopible.class.isAssignableFrom(c))
			return obj;
		if (obj instanceof Copible)
			return ((Copible) obj).copy();
		if (MAP.containsKey(obj.hashCode()))
			return MAP.get(obj.hashCode());
		if (obj.getClass().isArray()) {
			Object ans = Array.newInstance(c.getComponentType(), Array.getLength(obj));
			for (int i = 0; i < Array.getLength(ans); i++)
				Array.set(ans, i, hardCopy(Array.get(obj, i)));
			MAP.put(obj.hashCode(), ans);
			return ans;
		}
		if (Collection.class.isAssignableFrom(c)) {
			Collection f2 = (Collection) obj;
			Collection f3 = null;
			try {
				f3 = f2.getClass().getConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (f3 != null)
				for (Object o : f2)
					f3.add(hardCopy(o));
			MAP.put(f2.hashCode(), f3);
			return f3;
		}
		throw new BCUException("cannot copy class " + obj.getClass());
	}

	protected Copible copy = null;

	@Override
	public Copible clone() {
		Copible c = copy();
		terminate();
		MAP.clear();
		UNCHECKED.removeAll(OLD);
		for (Class<?> cls : UNCHECKED)
			Printer.e("Copible", 80, "Unchecked: " + cls);
		OLD.addAll(UNCHECKED);
		UNCHECKED.clear();
		return c;
	}

	protected Copible copy() {
		if (copy != null)
			return copy;
		try {
			// copy primary types
			copy = (Copible) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		copy.copy = this;
		List<Field> lf = getField(getClass());
		check(lf);// TODO delete this line
		for (Field f : lf) {
			if (f.getName().startsWith(NONC))
				continue;
			try {
				f.set(this, hardCopy(f.get(this)));
			} catch (IllegalAccessException e3) {
				e3.printStackTrace();
			}
		}
		return copy;
	}

	@SuppressWarnings({ "rawtypes" })
	private void check(List<Field> lf) {
		for (Field f : lf) {
			Class<?> tc = f.getType();
			if (checkField(tc))
				continue;
			if (Collection.class.isAssignableFrom(tc)) {
				Collection f2 = null;
				try {
					f2 = (Collection) f.get(this);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				for (Object o : f2)
					if (!(o instanceof Copible))
						UNCHECKED.add(o.getClass());
				continue;
			}
			UNCHECKED.add(tc);
		}
	}

	@SuppressWarnings("rawtypes")
	private void terminate() {
		if (copy == null)
			return;
		Copible temp = copy;
		copy = null;
		if (temp != null)
			temp.terminate();
		List<Field> lf = getField(getClass());
		for (Field f : lf) {
			f.setAccessible(true);
			Class<?> tc = f.getType();
			if (Copible.class.isAssignableFrom(tc)) {
				Copible f2 = null;
				try {
					f2 = (Copible) f.get(this);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				if (f2 != null)
					f2.terminate();
			}
			if (tc.isArray() && Copible.class.isAssignableFrom(tc.getComponentType())) {
				Copible[] f2 = null;
				try {
					f2 = (Copible[]) f.get(this);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				if (f2 != null)
					for (Copible c : f2)
						if (c != null)
							c.terminate();
			}
			if (Collection.class.isAssignableFrom(tc)) {
				if (f.getName().equals("uncp"))
					continue;
				Collection f2 = null;
				try {
					f2 = (Collection) f.get(this);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				if (f2 != null)
					for (Object c : f2)
						if (c != null && c instanceof Copible)
							((Copible) c).terminate();
			}
		}
	}

}
