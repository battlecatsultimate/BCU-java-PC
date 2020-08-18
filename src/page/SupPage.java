package page;

import common.pack.IndexContainer;

public interface SupPage<T extends IndexContainer.Indexable<?, T>> {

	public T getSelected();

	public default Page getThis() {
		return (Page) this;
	}

}
