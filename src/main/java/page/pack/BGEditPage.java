package page.pack;

import common.CommonStatic;
import common.pack.Context;
import common.pack.Context.ErrType;
import common.pack.PackData.UserPack;
import common.pack.Source.Workspace;
import common.system.fake.FakeImage;
import common.util.pack.Background;
import common.util.pack.bgeffect.BackgroundEffect;
import main.MainBCU;
import page.*;
import page.support.Exporter;
import page.support.Importer;
import page.view.BGViewPage;
import utilpc.UtilPC;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

@SuppressWarnings({"ResultOfMethodCallIgnored", "ForLoopReplaceableByForEach"})
public class BGEditPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JList<Background> jlst = new JList<>();
	private final JScrollPane jspst = new JScrollPane(jlst);
	private final JLabel jl = new JLabel();

	private final JBTN addc = new JBTN(0, "add");
	private final JBTN remc = new JBTN(0, "rem");
	private final JBTN impc = new JBTN(0, "import");
	private final JBTN expc = new JBTN(0, "export");
	private final JBTN copy = new JBTN(0, "copy");

	private final JTG top = new JTG("top");
	private final JTG overlay = new JTG(MainLocale.PAGE, "overlay");
	private final JL[] cl = new JL[5];
	private final JTF[] cs = new JTF[4];
	private final JL[] ol = new JL[3];
	private final JTF[] os = new JTF[3];
	private final JComboBox<String> eff = new JComboBox<>();

	private final UserPack pack;
	private BGViewPage bvp;
	private Background bgr;
	private boolean changing = false;

	public BGEditPage(Page p, UserPack ac) {
		super(p);
		pack = ac;
		ini();
		resized();
	}

	@Override
	protected JButton getBackButton() {
		return back;
	}

	@Override
	protected void renew() {
		if (bvp != null) {
			bgr = bvp.getSelected();
			if (bgr != null) {
				pack.bgs.add(bgr = bgr.copy(pack.getNextID(Background.class)));
				setList(bgr);
				File file = ((Workspace) pack.source).getBGFile(bgr.getID());
				try {
					Context.check(file);
					FakeImage.write(bgr.img.getImg(), "PNG", file);
				} catch (IOException e) {
					CommonStatic.ctx.noticeErr(e, ErrType.WARN, "Failed to save file");
					getFile("Failed to save file", bgr);
					return;
				}
			}
		}
		bvp = null;
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(jspst, x, y, 50, 100, 300, 1000);
		set(addc, x, y, 400, 100, 200, 50);
		set(impc, x, y, 400, 200, 200, 50);
		set(expc, x, y, 400, 300, 200, 50);
		set(remc, x, y, 400, 400, 200, 50);
		set(copy, x, y, 400, 500, 200, 50);
		set(top, x, y, 650, 50, 200, 50);
		set(overlay, x, y, 650, 1200, 200, 50);
		set(eff, x, y , 1900, 50, 200, 50);
		for (int i = 0; i < 4; i++) {
			set(cs[i], x, y, 900 + 250 * i, 50, 200, 50);
		}

		for(int i = 0; i < 5; i++) {
			set(cl[i], x, y, 900 + 250 * i, 0, 200, 50);
		}
		set(jl, x, y, 650, 150, 1600, 1000);

		for(int i = 0; i < 3; i++) {
			set(ol[i], x, y, 900 + 250 * i, 1150, 200, 50);
			set(os[i], x, y, 900 + 250 * i, 1200, 200, 50);
		}
		if (bgr != null)
			jl.setIcon(UtilPC.getBg(bgr, jl.getWidth(), jl.getHeight()));

	}

	private void addListeners$0() {
		back.addActionListener(arg0 -> changePanel(getFront()));

		addc.addActionListener(arg0 -> getFile("Choose your file", null));

		remc.addActionListener(arg0 -> {
			pack.bgs.remove(bgr);

			((Workspace) pack.source).getBGFile(bgr.getID()).delete();

			setList(null);
		});

		impc.addActionListener(arg0 -> getFile("Choose your file", bgr));

		expc.addActionListener(arg0 -> {
			if (bgr != null)
				new Exporter((BufferedImage) bgr.img.getImg().bimg(), Exporter.EXP_IMG);
		});

		copy.addActionListener(arg0 -> changePanel(bvp = new BGViewPage(getThis(), null)));

		jlst.addListSelectionListener(arg0 -> {
			if (changing || jlst.getValueIsAdjusting())
				return;
			setBG(jlst.getSelectedValue());
		});

	}

	private void addListeners$1() {
		top.addActionListener(arg0 -> {
			bgr.top = top.isSelected();
			bgr.ic = 1;
			bgr.load();
		});

		for (int i = 0; i < 4; i++) {
			int I = i;

			cs[i].addFocusListener(new FocusAdapter() {

				@Override
				public void focusLost(FocusEvent arg0) {
					int[] inp = CommonStatic.parseIntsN(cs[I].getText());
					if (inp.length == 3)
						bgr.cs[I] = new int[] { inp[0] & 255, inp[1] & 255, inp[2] & 255 };
					setCSText(I);

				}

			});
		}

		for(int i = 0; i < 3; i++) {
			final int I = i;

			os[i].addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					if(I % 3 != 2) {
						int[] inp = CommonStatic.parseIntsN(os[I].getText());

						if(inp.length == 3)
							bgr.overlay[I] = filterRGB(inp);
					} else {
						int alpha = CommonStatic.parseIntN(os[I].getText());

						bgr.overlayAlpha = filterRGB(alpha);
					}

					setOSText(I, I % 3 == 2);
				}
			});
		}

		eff.addActionListener(e -> {
			if(eff.getSelectedIndex() == 0) {
				bgr.effect = -1;
			} else {
				bgr.effect = eff.getSelectedIndex() - 1;
			}
		});

		overlay.setLnr(c -> {
			if(overlay.isSelected()) {
				if(bgr.overlay == null) {
					bgr.overlayAlpha = 55;
					bgr.overlay = new int[][] {
							{ 0, 0, 0 },
							{ 0, 0, 0 }
					};
				}

				for(int i = 0; i < 3; i++) {
					os[i].setEnabled(true);
					setOSText(i, i % 3 == 2);
				}
			} else {
				bgr.overlay = null;
				bgr.overlayAlpha = 0;

				for(int i = 0; i < os.length; i++) {
					os[i].setText(null);
					os[i].setEnabled(false);
				}
			}
		});
	}

	private void getFile(String str, Background bgr) {
		BufferedImage bimg = new Importer(str, Importer.FileType.PNG).getImg();
		if (bimg == null)
			return;

		if (!(bimg.getWidth() == 770 || bimg.getWidth() == 1024 || bimg.getHeight() == 512 || bimg.getHeight() == 1024)) {
			getFile("Its size must be one of these: 770x512, 770x1024, 1024x512, 1024x1024", bgr);
			return;
		}
		if (bgr == null) {
			bgr = new Background(pack.getNextID(Background.class), MainBCU.builder.toVImg(bimg));
			pack.bgs.add(bgr);
		} else {
			bgr.img.setImg(MainBCU.builder.build(bimg));
			bgr.forceLoad();
		}
		try {
			File file = ((Workspace) pack.source).getBGFile(bgr.id);
			Context.check(file);
			ImageIO.write(bimg, "PNG", file);
		} catch (IOException e) {
			CommonStatic.ctx.noticeErr(e, ErrType.WARN, "failed to write file");
			getFile("Failed to save file", bgr);
			return;
		}
		setList(bgr);
	}

	private void ini() {
		add(back);
		add(jspst);
		add(jl);
		add(addc);
		add(remc);
		add(impc);
		add(expc);
		add(copy);
		add(top);
		add(eff);
		add(overlay);
		for (int i = 0; i < 4; i++) {
			add(cs[i] = new JTF());
		}
		for(int i = 0; i < 5; i++) {
			add(cl[i] = new JL(MainLocale.PAGE, "bgcl"+i));
		}
		for(int i = 0; i < 3; i++) {
			add(os[i] = new JTF());
			add(ol[i] = new JL(MainLocale.PAGE, "bgcl"+(i+5)));
		}
		Vector<String> effVector = new Vector<>();

		effVector.add(get(MainLocale.PAGE, "none"));

		for(int i = 0; i < 10; i++) {
			effVector.add(get(MainLocale.PAGE, "bgeff"+i));
		}

		for(int i = 0; i < BackgroundEffect.jsonList.size(); i++) {
			String temp = get(MainLocale.PAGE, "bgjson"+BackgroundEffect.jsonList.get(i));

			if(temp.equals("bgjson"+BackgroundEffect.jsonList.get(i))) {
				temp = get(MainLocale.PAGE, "bgeffdum").replace("_", ""+BackgroundEffect.jsonList.get(i));
			}

			effVector.add(temp);
		}

		eff.setModel(new DefaultComboBoxModel<>(effVector));

		setList(null);
		addListeners$0();
		addListeners$1();
	}

	private void setBG(Background bg) {
		bgr = bg;

		if (jlst.getSelectedValue() != bg) {
			boolean boo = changing;
			changing = true;
			jlst.setSelectedValue(bg, true);
			changing = boo;
		}

		boolean b = bgr != null;
		remc.setEnabled(b);
		impc.setEnabled(b);
		expc.setEnabled(b);
		overlay.setEnabled(b);
		eff.setEnabled(b);

		for (int i = 0; i < 4; i++) {
			cs[i].setEnabled(b);
		}

		for(int i = 0; i < 5; i++) {
			cl[i].setEnabled(b);
		}

		for(int i = 0; i < 3; i++) {
			os[i].setEnabled(b);
			ol[i].setEnabled(b);
		}

		if (b) {
			bgr.check();
			top.setEnabled(bgr.parts.length > Background.TOP);

			top.setSelected(bgr.top);

			for (int i = 0; i < 4; i++) {
				setCSText(i);
			}

			if(bgr.overlay != null) {
				for(int i = 0; i < 3; i++) {
					setOSText(i, i % 3 == 2);
				}
			} else {
				for(int i = 0; i < 3; i++) {
					os[i].setText(null);
					os[i].setEnabled(false);
				}
			}

			if(bgr.effect == -1) {
				eff.setSelectedIndex(0);
			} else {
				eff.setSelectedIndex(bgr.effect + 1);
			}
		} else {
			top.setEnabled(false);
			for (int i = 0; i < 4; i++) {
				cs[i].setText(null);
			}
			for(int i = 0; i < 3; i++) {
				os[i].setText(null);
			}
		}
	}

	private void setCSText(int i) {
		int[] is = bgr.cs[i];
		String str = is[0] + "," + is[1] + "," + is[2];
		cs[i].setText(str);
	}

	private void setOSText(int i, boolean alpha) {
		if(bgr.overlay == null) {
			os[i].setText(null);
			return;
		}

		if(alpha) {
			os[i].setText(""+bgr.overlayAlpha);
		} else {
			os[i].setText(bgr.overlay[i][0]+","+bgr.overlay[i][1]+","+bgr.overlay[i][2]);
		}
	}

	private void setList(Background bcgr) {
		bgr = bcgr;
		int ind = jlst.getSelectedIndex();
		Background[] arr = pack.bgs.toArray();
		if (ind < 0)
			ind = 0;
		if (ind >= arr.length)
			ind = arr.length - 1;
		boolean boo = changing;
		changing = true;
		jlst.setListData(arr);
		jlst.setSelectedIndex(ind);
		if(bcgr == null && arr.length > 0) {
			bgr = arr[0];
		}
		changing = boo;
		setBG(bgr);
	}

	private int[] filterRGB(int[] inp) {
		for(int i = 0; i < inp.length; i++)
			inp[i] = filterRGB(inp[i]);

		return inp;
	}

	private int filterRGB(int i) {
		return Math.max(0, Math.min(i, 255));
	}
}
