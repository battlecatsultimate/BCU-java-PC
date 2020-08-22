package page.support;

import common.pack.FixIndexList.FixIndexMap;
import main.Opts;

import java.util.Vector;
import java.util.function.Consumer;
import common.pack.IndexContainer;

public class RLFIM<T extends IndexContainer.Indexable<?, T>> extends ReorderList<T> implements ReorderListener<T> {

	private static final long serialVersionUID = 1L;

	public IndexContainer ic;
	public FixIndexMap<T> map;

	private final Runnable sta, end;
	private final Consumer<T> sel;
	private final IndexContainer.Constructor<T, T> gen;

	public RLFIM(Runnable sta, Runnable end, Consumer<T> sel, IndexContainer.Constructor<T, T> gen) {
		list = this;
		this.sta = sta;
		this.end = end;
		this.sel = sel;
		this.gen = gen;
	}

	public void addItem(Object o) {
		sta.run();
		T t = ic.add(map, gen);
		setListData(ic, map);
		setSelectedValue(t, true);
		sel.accept(t);
		end.run();
	}

	public void deleteItem(Object o) {
		if (!Opts.conf())
			return;
		sta.run();
		int ind = getSelectedIndex();
		T val = getSelectedValue();
		map.remove(val);
		setListData(ic, map);
		if (ind >= 0)
			ind--;
		setSelectedIndex(ind);
		sel.accept(getSelectedValue());
		end.run();
	}

	@Override
	public void reordered(int ori, int fin) {
		map.reorder(ori, fin);
		setListData(ic, map);
		end.run();
	}

	@Override
	public void reordering() {
		sta.run();
	}

	public void setListData(IndexContainer ic, FixIndexMap<T> map) {
		this.ic = ic;
		this.map = map;
		super.setListData(map == null ? null : map.toArray());
	}

	@Override
	@Deprecated
	public void setListData(T[] arr) {

	}

	@Override
	@Deprecated
	public void setListData(Vector<? extends T> vec) {

	}

}
