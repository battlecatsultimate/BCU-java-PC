package page.support;

public interface ReorderListener<T> {

    default boolean add(T t) {
        return false;
    }

    void reordered(int ori, int fin);

    void reordering();

}
