package page.support;

import javax.annotation.Nonnull;

public abstract class AnimTable<T> extends AbJTable {
	public AnimTable(@Nonnull String[] title) {
		super(title);
	}

	private static final long serialVersionUID = 1L;

	public abstract T[] getSelected();

	public abstract boolean insert(int dst, T[] data, int[] rows);

	public abstract boolean reorder(int dst, int[] ori);

}
