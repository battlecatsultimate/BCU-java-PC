package page.pack;

import common.CommonStatic;
import common.pack.Context;
import common.pack.Context.ErrType;
import common.pack.PackData.UserPack;
import common.pack.Source.Workspace;
import common.system.fake.FakeImage;
import common.util.pack.Background;
import main.MainBCU;
import page.JBTN;
import page.JTF;
import page.JTG;
import page.Page;
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

@SuppressWarnings("ResultOfMethodCallIgnored")
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
	private final JTF[] cs = new JTF[4];

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
		for (int i = 0; i < 4; i++)
			set(cs[i], x, y, 900 + 250 * i, 50, 200, 50);
		set(jl, x, y, 650, 150, 1600, 1000);
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
			cs[0].setEnabled(!bgr.top);
			cs[1].setEnabled(!bgr.top);
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

	}

	private void getFile(String str, Background bgr) {
		BufferedImage bimg = new Importer(str).getImg();
		if (bimg == null)
			return;
		if (bimg.getWidth() != 1024 && bimg.getHeight() != 1024) {
			getFile("Wrong img size. Img size: w=1024, h=1024", bgr);
			return;
		}
		if (bgr == null) {
			bgr = new Background(pack.getNextID(Background.class), MainBCU.builder.toVImg(bimg));
			pack.bgs.add(bgr);
		} else {
			bgr.img.setImg(MainBCU.builder.build(bimg));
			bgr.load();
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
		for (int i = 0; i < 4; i++)
			add(cs[i] = new JTF());
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
		top.setEnabled(b);
		for (int i = 0; i < 4; i++)
			cs[i].setEnabled(b);

		if (bgr != null) {
			top.setSelected(bgr.top);
			for (int i = 0; i < 4; i++)
				setCSText(i);
		} else {
			top.setSelected(false);
			for (int i = 0; i < 4; i++)
				cs[i].setText("");
		}

	}

	private void setCSText(int i) {
		int[] is = bgr.cs[i];
		String str = is[0] + "," + is[1] + "," + is[2];
		cs[i].setText(str);
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

}
