package page.pack;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import io.Reader;
import io.Writer;
import page.JBTN;
import page.JTF;
import page.JTG;
import page.Page;
import page.support.Exporter;
import page.support.Importer;
import page.view.BGViewPage;
import util.pack.BGStore;
import util.pack.Background;
import util.pack.Pack;
import util.system.VImg;
import util.system.fake.FIBI;
import util.system.fake.FakeImage;

public class BGEditPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JList<String> jlst = new JList<>();
	private final JScrollPane jspst = new JScrollPane(jlst);
	private final JLabel jl = new JLabel();

	private final JBTN addc = new JBTN(0, "add");
	private final JBTN remc = new JBTN(0, "rem");
	private final JBTN impc = new JBTN(0, "import");
	private final JBTN expc = new JBTN(0, "export");
	private final JBTN copy = new JBTN(0, "copy");

	private final JTG top = new JTG("top");
	private final JTF[] cs = new JTF[4];

	private final Pack pack;
	private final BGStore bg;
	private List<Background> list;
	private BGViewPage bvp;
	private Background bgr;
	private boolean changing = false;

	public BGEditPage(Page p, Pack ac) {
		super(p);
		pack = ac;
		bg = ac.bg;

		ini();
		resized();
	}

	@Override
	protected void renew() {
		if (bvp != null) {
			Background bgr = bvp.getSelected();
			if (bgr != null) {
				bg.add(bgr = bgr.copy(pack, bg.nextInd()));
				setList(bgr);
				String path = "./res/img/" + pack.id + "/bg/";
				try {
					File file = new File(path + bg.nameOf(bgr) + ".png");
					Writer.check(file);
					FakeImage.write(bgr.img.getImg(), "PNG", file);
				} catch (IOException e) {
					e.printStackTrace();
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
			jl.setIcon(new ImageIcon(bgr.getBg(jl.getWidth(), jl.getHeight())));

	}

	private void addListeners$0() {
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		addc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getFile("Choose your file", null);
			}
		});

		remc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				list.remove(bgr);
				bg.remove(bgr);
				int name = bg.indexOf(bgr);
				new File("./res/img/" + pack.id + "/bg/" + name + ".png");
				setList(null);
			}
		});

		impc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getFile("Choose your file", bgr);
			}
		});

		expc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (bgr != null)
					new Exporter(bgr.img.getImg().bimg(), Exporter.EXP_IMG);
			}
		});

		copy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(bvp = new BGViewPage(getThis(), null));
			}
		});

		jlst.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (changing || jlst.getValueIsAdjusting())
					return;
				setBG(jlst.getSelectedIndex());
			}

		});

	}

	private void addListeners$1() {
		top.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				bgr.top = top.isSelected();
				bgr.ic = 1;
				bgr.load();
				cs[0].setEnabled(!bgr.top);
				cs[1].setEnabled(!bgr.top);
			}
		});

		for (int i = 0; i < 4; i++) {
			int I = i;

			cs[i].addFocusListener(new FocusAdapter() {

				@Override
				public void focusLost(FocusEvent arg0) {
					int[] inp = Reader.parseIntsN(cs[I].getText());
					if (inp.length == 3)
						bgr.cs[I] = new Color(inp[0], inp[1], inp[2]);
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
		if (bgr == null)
			bgr = bg.add(new VImg(bimg));
		else {
			bgr.img.setImg(FIBI.build(bimg));
			bgr.load();
		}
		String path = "./res/img/" + pack.id + "/bg/";
		try {
			File file = new File(path + bg.nameOf(bgr) + ".png");
			Writer.check(file);
			ImageIO.write(bimg, "PNG", file);
		} catch (IOException e) {
			e.printStackTrace();
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

	private void setBG(int ind) {
		bgr = ind < 0 ? null : list.get(ind);

		if (jlst.getSelectedIndex() != ind) {
			boolean boo = changing;
			changing = true;
			jlst.setSelectedIndex(ind);
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
			if (bgr.top) {
				cs[0].setEnabled(false);
				cs[1].setEnabled(false);
			}
		} else {
			top.setSelected(false);
			for (int i = 0; i < 4; i++)
				cs[i].setText("");
		}

	}

	private void setCSText(int i) {
		float[] fs = bgr.cs[i].getRGBColorComponents(null);
		int[] is = new int[3];
		for (int j = 0; j < 3; j++)
			is[j] = (int) (fs[j] * 256 - 1e-5);
		String str = is[0] + "," + is[1] + "," + is[2];
		cs[i].setText(str);
	}

	private void setList(Background bcgr) {
		bgr = bcgr;
		int ind = jlst.getSelectedIndex();
		list = bg.getList();
		String[] str = new String[list.size()];
		for (int i = 0; i < str.length; i++)
			str[i] = bg.nameOf(list.get(i));
		if (bgr != null)
			ind = list.indexOf(bgr);
		if (ind < 0)
			ind = 0;
		if (ind >= bg.size())
			ind = bg.size() - 1;

		boolean boo = changing;
		changing = true;
		jlst.setListData(str);
		jlst.setSelectedIndex(ind);
		changing = boo;
		setBG(ind);
	}

}
