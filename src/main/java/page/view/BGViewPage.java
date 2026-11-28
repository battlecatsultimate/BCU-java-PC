package page.view;

import common.pack.Identifier;
import common.pack.PackData;
import common.pack.UserProfile;
import common.util.pack.Background;
import page.JBTN;
import page.Page;
import page.SupPage;
import page.info.StageFilterPage;
import utilpc.UtilPC;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import java.util.HashMap;
import java.util.Map;
import utilpc.OutlineText;
import main.MainBCU;
import page.JCB;
import page.MainLocale;

public class BGViewPage extends Page implements SupPage<Background> {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JCB preview = new JCB(MainLocale.PAGE, "previews");
	private final JBTN find = new JBTN(0, "stage");
	private final JList<Background> jlst = new JList<>();
	private final JScrollPane jspst = new JScrollPane(jlst);
	private final JLabel jl = new OutlineText.Label("");
	private final Map<Background, ImageIcon> thumbnailCache = new HashMap<>();

	public BGViewPage(Page p) {
		super(p);
		List<Background> bgs = new ArrayList<>();
		for (PackData pac : UserProfile.getAllPacks())
			bgs.addAll(pac.bgs.getList());

		jlst.setListData(bgs.toArray(new Background[0]));
		ini();
	}

	public BGViewPage(Page p, String pac) {
		super(p);
		jlst.setListData(new Vector<>(UserProfile.getAll(pac, Background.class)));
		ini();
	}

	public BGViewPage(Page front, String pac, Identifier<Background> bg) {
		this(front, pac);
		jlst.setSelectedValue(Identifier.get(bg), false);
	}

	@Override
	protected JButton getBackButton() {
		return back;
	}

	@Override
	public Background getSelected() {
		return jlst.getSelectedValue();
	}

	@Override
	protected void renew() {
		preview.setSelected(MainBCU.bgPreviews);
		applyPreviews();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);

		set(back, x, y, 0, 0, 200, 50); //back button
		set(preview, x, y, 500, 0, 350, 50);
		set(find, x, y, 250, 00, 200, 50); //find stage button
		set(jspst, x, y, 50, 100, 525, 1100); //list
		set(jl, x, y, 650, 100, 1600, 1100); // preview
	}

	@Override
	public synchronized void onTimer(int t) {
		super.onTimer(t);

		Background s = jlst.getSelectedValue();

		if (s == null)
			return;

		jl.setIcon(UtilPC.getBg(s, jl.getWidth(), jl.getHeight()));
	}

	private void addListeners() {
		back.addActionListener(arg0 -> changePanel(getFront()));

		jlst.addListSelectionListener(arg0 -> {
			if (needResize)
				return;

			if (arg0.getValueIsAdjusting())
				return;
			Background s = jlst.getSelectedValue();
			if (s == null)
				return;
			jl.setIcon(UtilPC.getBg(s, jl.getWidth(), jl.getHeight()));
		});

		find.addActionListener(x -> {
			if (jlst.getSelectedValue() == null)
				return;

			changePanel(new StageFilterPage(this, jlst.getSelectedValue().getStages()));
		});
	}

	private ImageIcon getThumbnail(Background bg) {
		ImageIcon cached = thumbnailCache.get(bg);
		if (cached != null)
			return cached;
		// Check whether this background was already loaded.
		boolean wasLoaded = bg.parts != null;
		ImageIcon thumbnail;
		try {
			thumbnail = UtilPC.getBg(bg, 180, 100);
		} finally {
			// Only unload resources that this thumbnail generation loaded.
			if (!wasLoaded && bg.parts != null)
				bg.unload();
		}
		thumbnailCache.put(bg, thumbnail);
		return thumbnail;
	}

	// Switches the list between the preview gallery and a plain text list
	private void applyPreviews() {
		if (MainBCU.bgPreviews) {
			jlst.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			jlst.setVisibleRowCount(0);
			jlst.setFixedCellWidth(200);
			jlst.setFixedCellHeight(125);
		} else {
			jlst.setLayoutOrientation(JList.VERTICAL);
			jlst.setVisibleRowCount(8);
			jlst.setFixedCellWidth(-1);
			jlst.setFixedCellHeight(-1);
		}

		jlst.revalidate();
		jlst.repaint();
	}

	private void setupBackgroundGallery() {
		applyPreviews();

		DefaultListCellRenderer renderer = new DefaultListCellRenderer();

		jlst.setCellRenderer((list, bg, index, isSelected, cellHasFocus) -> {
			JLabel label = (JLabel) renderer.getListCellRendererComponent(
					list, bg, index, isSelected, cellHasFocus
			);
			if (bg != null) {
				label.setText(bg.getID().toString());
				if (MainBCU.bgPreviews) {
					label.setHorizontalTextPosition(JLabel.CENTER);
					label.setVerticalTextPosition(JLabel.BOTTOM);
					label.setHorizontalAlignment(JLabel.CENTER);
					label.setIcon(getThumbnail(bg));
				} else {
					label.setIcon(null);
					label.setHorizontalTextPosition(JLabel.TRAILING);
					label.setVerticalTextPosition(JLabel.CENTER);
					label.setHorizontalAlignment(JLabel.LEADING);
				}
			}
			return label;
		});
	}

	private void ini() {
		setupBackgroundGallery();
		add(back);
		add(preview);
		preview.setSelected(MainBCU.bgPreviews);
		preview.setToolTipText(get(MainLocale.PAGE, "previewstip"));
		preview.addActionListener(e -> {
			MainBCU.bgPreviews = preview.isSelected();
			applyPreviews();
		});
		add(jspst);
		add(jl);
		add(find);
		addListeners();

	}

}
