package page.event;

import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import event.Gacha;
import page.MainFrame;
import page.Page;

public class GachaPage extends URLPage {

	private static final long serialVersionUID = 1L;

	public static int[] dss = new int[0];

	private final Vector<Gacha> vg = new Vector<>();
	private final JList<Gacha> jlg = new JList<>(vg);
	private final JScrollPane jspg = new JScrollPane(jlg);
	private final JList<String> jltp = new JList<>();
	private final JScrollPane jsptp = new JScrollPane(jltp);

	private final FGacha fg = new FGacha(dss, this);

	private Gacha gacha = null;

	protected GachaPage(Page p) {
		super(p, 1);

		ini();
		resized();
	}

	@Override
	protected void exit() {
		dss = fg.getDatas();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		super.resized(x, y);
		set(jspg, x, y, 400, 100, 400, 800);
		set(jsptp, x, y, 850, 100, 800, 800);
		set(fg, x, y, 50, 100, 300, 1100);
		fg.componentResized(x, y);
		jlg.setFont(MainFrame.font);
		jltp.setFont(MainFrame.font);
	}

	@Override
	protected void updateList() {
		vg.clear();
		vg.addAll(fg.getList());
		jlg.setListData(vg);
		jltp.setListData(new String[0]);
	}

	private void addListeners() {

		jlg.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				gacha = jlg.getSelectedValue();
				if (gacha != null)
					jltp.setListData(gacha.getStrings());
			}
		});

	}

	private void ini() {
		init();
		add(fg);
		add(jspg);
		add(jsptp);
		updateList();
		addListeners();
	}

}
