package page.support;

public interface ReorderListener<T> {

	public default boolean add(T t) {
		return false;
	}

	public void reordered(int ori, int fin);

	public void reordering();

}
