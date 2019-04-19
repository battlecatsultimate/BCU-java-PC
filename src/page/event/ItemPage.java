package page.event;

import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import event.Item;
import page.MainFrame;
import page.Page;

public class ItemPage extends URLPage {

	private static final long serialVersionUID = 1L;

	public static int[] dss = new int[0];

	private final Vector<Item> vi = new Vector<>();
	private final JList<Item> jli = new JList<>(vi);
	private final JScrollPane jspi = new JScrollPane(jli);
	private final JList<String> jltp = new JList<>();
	private final JScrollPane jsptp = new JScrollPane(jltp);
	private final FItem fi = new FItem(dss, this);

	private Item item = null;

	protected ItemPage(Page p) {
		super(p, 2);

		ini();
		resized();
	}

	@Override
	protected void exit() {
		dss = fi.getDatas();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		super.resized(x, y);
		set(jspi, x, y, 650, 100, 400, 800);
		set(jsptp, x, y, 1100, 100, 800, 800);
		set(fi, x, y, 50, 100, 550, 800);
		jli.setFont(MainFrame.font);
		jltp.setFont(MainFrame.font);
		fi.componentResized(x, y);
	}

	@Override
	protected void updateList() {
		vi.clear();
		vi.addAll(fi.getList());
		jli.setListData(vi);
		jltp.setListData(new String[0]);
	}

	private void addListeners() {

		jli.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				item = jli.getSelectedValue();
				if (item == null)
					return;
				jltp.setListData(item.getStrings());
			}
		});

	}

	private void ini() {
		init();
		add(jspi);
		add(jsptp);
		add(fi);
		updateList();
		addListeners();
	}

}
