package page.support;

import main.Printer;

import javax.swing.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class ReorderList<T> extends JList<T> implements Reorderable {

	private static final long serialVersionUID = 1L;

	private final DefaultListModel<T> model;

	protected final Map<Integer, T> copymap = new TreeMap<>();

	public ReorderListener<T> list;

	protected boolean copable = false;

	public ReorderList() {
		model = new DefaultListModel<>();
		setModel(model);
		try {
			setTransferHandler(new InListTH<>(this));
			setDragEnabled(true);
		} catch (Exception e) {
			Printer.e("ReorderList", 24, "cannot drag row");
		}
	}

	public ReorderList(T[] vec) {
		this();
		setListData(vec);

	}

	public ReorderList(Vector<T> vec) {
		this();
		setListData(vec);
	}

	public ReorderList(Vector<T> vec, Class<? extends T> cls, String str) {
		model = new DefaultListModel<>();
		setModel(model);
		try {
			setTransferHandler(new InListTH<>(this, cls, str));
			setDragEnabled(true);
			copable = true;
		} catch (Exception e) {
			Printer.e("ReorderList", 24, "cannot drag row");
		}

		setListData(vec);
	}

	public boolean add(T t) {
		if (t == null)
			return false;
		if (list.add(t))
			model.addElement(t);
		else
			return false;
		return true;
	}

	@Override
	public void reorder(int ori, int fin) {
		if (list != null)
			list.reordering();
		DefaultListModel<T> lm = (DefaultListModel<T>) getModel();
		T val = lm.get(ori);
		lm.remove(ori);
		if (fin > ori)
			fin--;
		lm.add(fin, val);
		if (list != null)
			list.reordered(ori, fin);
	}

	@Override
	public void setListData(T[] data) {
		model.clear();
		if (data != null)
			for (T t : data)
				model.addElement(t);
	}

	@Override
	public void setListData(Vector<? extends T> data) {
		model.clear();
		for (T t : data)
			model.addElement(t);
	}

}
