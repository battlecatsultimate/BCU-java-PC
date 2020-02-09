package page.support;

public abstract class AnimTable<T> extends AbJTable {

	private static final long serialVersionUID = 1L;

	public abstract T[] getSelected();

	public abstract boolean insert(int dst, T[] data);

	public abstract boolean reorder(int dst, int[] ori);

}
