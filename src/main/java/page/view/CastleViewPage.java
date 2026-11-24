package page.view;

import common.pack.Identifier;
import common.util.stage.CastleImg;
import common.util.stage.CastleList;
import page.JBTN;
import page.JL;
import page.MainLocale;
import page.Page;
import page.info.StageFilterPage;
import utilpc.UtilPC;

import javax.swing.*;
import java.util.Collection;
import java.util.Vector;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import utilpc.OutlineText;
import main.MainBCU;
import page.JCB;

public class CastleViewPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JCB preview = new JCB(MainLocale.PAGE, "previews");
	private final JBTN find = new JBTN(0, "stage");
	private final JList<CastleList> jlsm = new JList<>();
	private final JScrollPane jspsm = new JScrollPane(jlsm);
	private final JList<CastleImg> jlst = new JList<>();
	private final JScrollPane jspst = new JScrollPane(jlst);
	private final JLabel jl = new OutlineText.Label("");
	private final JL jbs = new JL(MainLocale.PAGE, "bspwn");
	private final JL bs = new JL();
	private final Map<CastleImg, ImageIcon> thumbnailCache = new HashMap<>();

	public CastleViewPage(Page p) {
		this(p, CastleList.map().values());
	}

	public CastleViewPage(Page p, CastleList sele) {
		this(p);
		jlsm.setSelectedValue(sele, true);
	}

	public CastleViewPage(Page p, Collection<CastleList> list) {
		super(p);
		Vector<CastleList> vec = new Vector<>(list);
		jlsm.setListData(vec);
		ini();
	}

	public CastleViewPage(Page p, Collection<CastleList> defcas, Identifier<CastleImg> id) {
		this(p, defcas);
		if (id != null) {
			CastleList abcas = (CastleList) id.getCont();
			jlsm.setSelectedValue(abcas, true);
			jlst.setSelectedValue(id.get(), true);
		}
	}

	public Identifier<CastleImg> getVal() {
		CastleImg img = jlst.getSelectedValue();
		return img == null ? null : img.getID();
	}

	@Override
	protected JButton getBackButton() {
		return back;
	}

	@Override
	protected void renew() {
		preview.setSelected(MainBCU.castlePreviews);
		applyPreviews();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);

		set(back, x, y, 0, 0, 200, 50); //back button
		set(preview, x, y, 250, 0, 350, 50);

		set(jspsm, x, y, 50, 150, 300, 1050); // pack selector
		set(jspst, x, y, 400, 150, 830, 1050); // castle selector
		set(jl, x, y, 1250, 500, 1000, 1000); // selected preview

		set(find, x, y, 50, 100, 200, 50); // find stage button
		set(jbs, x, y, 500, 100, 200, 50); // boss spawn 2
		set(bs, x, y, 400, 100, 100, 50); // boss spawn 1
	}

	private void addListeners() {
		back.addActionListener(arg0 -> changePanel(getFront()));

		jlsm.addListSelectionListener(arg0 -> {
			if (arg0.getValueIsAdjusting())
				return;
			CastleList sm = jlsm.getSelectedValue();
			if (sm == null)
				return;
			jlst.setListData(new Vector<>(sm.getList()));
			jlst.setSelectedIndex(0);
		});

		jlst.addListSelectionListener(arg0 -> {
			if (arg0.getValueIsAdjusting())
				return;
			CastleImg s = jlst.getSelectedValue();
			if (s == null)
				jl.setIcon(null);
			else {
				jl.setIcon(UtilPC.getIcon(s.img));
				bs.setText(String.valueOf(s.boss_spawn));
			}
		});

		find.addActionListener(x -> {
			if (jlst.getSelectedValue() == null)
				return;

			changePanel(new StageFilterPage(this, jlst.getSelectedValue().getStages()));
		});
	}

	private ImageIcon getThumbnail(CastleImg castle) {
		// Reuse an existing thumbnail if it has already been generated.
		if (thumbnailCache.containsKey(castle))
			return thumbnailCache.get(castle);
		ImageIcon original = UtilPC.getIcon(castle.img);
		if (original == null)
			return null;
		int originalWidth = original.getIconWidth();
		int originalHeight = original.getIconHeight();
		if (originalWidth <= 0 || originalHeight <= 0)
			return null;
		// Calculate a scale factor that preserves the aspect ratio.
		double scale = Math.min(
				100.0 / originalWidth,
				75.0 / originalHeight
		);
		int width = Math.max(1, (int) (originalWidth * scale));
		int height = Math.max(1, (int) (originalHeight * scale));
		Image scaled = original.getImage().getScaledInstance(
				width,
				height,
				Image.SCALE_SMOOTH
		);
		ImageIcon thumbnail = new ImageIcon(scaled);
		thumbnailCache.put(castle, thumbnail);
		return thumbnail;
	}
	// Switches the list between the preview gallery and a plain text list
	private void applyPreviews() {
		if (MainBCU.castlePreviews) {
			jlst.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			jlst.setVisibleRowCount(0);
			jlst.setFixedCellWidth(130);
			jlst.setFixedCellHeight(110);
		} else {
			jlst.setLayoutOrientation(JList.VERTICAL);
			jlst.setVisibleRowCount(8);
			jlst.setFixedCellWidth(-1);
			jlst.setFixedCellHeight(-1);
		}

		jlst.revalidate();
		jlst.repaint();
	}

	private void setupCastleGallery() {
		applyPreviews();
		DefaultListCellRenderer renderer = new DefaultListCellRenderer();
		jlst.setCellRenderer((list, castle, index, isSelected, cellHasFocus) -> {
			JLabel label = (JLabel) renderer.getListCellRendererComponent(
					list, castle, index, isSelected, cellHasFocus
			);
			if (castle != null) {
				label.setText(castle.getID().toString());
				if (MainBCU.castlePreviews) {
					label.setHorizontalTextPosition(JLabel.CENTER);
					label.setVerticalTextPosition(JLabel.BOTTOM);
					label.setHorizontalAlignment(JLabel.CENTER);
					label.setIcon(getThumbnail(castle));
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
		setupCastleGallery();
		add(back);
		add(preview);
		preview.setSelected(MainBCU.castlePreviews);
		preview.setToolTipText(get(MainLocale.PAGE, "previewstip"));
		preview.addActionListener(e -> {
			MainBCU.castlePreviews = preview.isSelected();
			applyPreviews();
		});
		add(find);
		add(jspsm);
		add(jspst);
		add(jl);
		add(jbs);
		add(bs);
		addListeners();
	}


}
