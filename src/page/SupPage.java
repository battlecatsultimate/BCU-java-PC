package page;

import common.pack.IndexContainer;

public interface SupPage<T extends IndexContainer.Indexable<?, T>> {

	T getSelected();

	default Page getThisPage() {
		return (Page) this;
	}

}
