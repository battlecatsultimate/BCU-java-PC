package page.pack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import io.Writer;
import page.JBTN;
import page.Page;
import page.support.Exporter;
import page.support.Importer;
import util.pack.CasStore;
import util.pack.Pack;
import util.system.VImg;
import util.system.fake.FIBI;

public class CastleEditPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JList<String> jlst = new JList<>();
	private final JScrollPane jspst = new JScrollPane(jlst);
	private final JLabel jl = new JLabel();

	private final JBTN addc = new JBTN(0, "add");
	private final JBTN remc = new JBTN(0, "rem");
	private final JBTN impc = new JBTN(0, "import");
	private final JBTN expc = new JBTN(0, "export");

	private final Pack pack;
	private final CasStore cas;
	private List<VImg> list;

	private boolean changing = false;

	public CastleEditPage(Page p, Pack ac) {
		super(p);
		pack = ac;
		cas = ac.cs;

		ini();
		resized();
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
		set(jl, x, y, 800, 50, 1000, 1000);

	}

	private void addListeners() {
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		jlst.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (changing || arg0.getValueIsAdjusting())
					return;
				int ind = jlst.getSelectedIndex();
				ImageIcon ic = null;
				if (ind >= 0) {
					VImg s = list.get(ind);
					if (s != null)
						ic = s.getIcon();
				}
				jl.setIcon(ic);

			}

		});

		addc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getFile("Choose your file", null);
			}
		});

		impc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int ind = jlst.getSelectedIndex();
				if (ind >= 0) {
					VImg s = list.get(ind);
					if (s != null)
						getFile("Choose your file", s);
				}
			}
		});

		expc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int ind = jlst.getSelectedIndex();
				if (ind >= 0) {
					VImg s = list.get(ind);
					if (s != null)
						new Exporter((BufferedImage) s.getImg().bimg(), Exporter.EXP_IMG);
				}
			}
		});

		remc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int ind = jlst.getSelectedIndex();
				if (ind < 0)
					return;
				VImg vimg = list.remove(ind);
				int name = cas.indexOf(vimg);
				cas.remove(vimg);
				new File("./res/img/" + pack.id + "/cas/" + name + ".png");
				changing = true;
				setList();
				changing = false;
			}
		});

	}

	private void getFile(String str, VImg vimg) {
		BufferedImage bimg = new Importer(str).getImg();
		if (bimg == null)
			return;
		if (bimg.getWidth() != 128 && bimg.getHeight() != 256) {
			getFile("Wrong img size. Img size: w=128, h=256", vimg);
			return;
		}
		if (vimg == null)
			cas.add(vimg = new VImg(bimg));
		else
			vimg.setImg(FIBI.build(bimg));
		String path = "./res/img/" + pack.id + "/cas/";
		try {
			File file = new File(path + cas.nameOf(vimg) + ".png");
			Writer.check(file);
			ImageIO.write(bimg, "PNG", file);
		} catch (IOException e) {
			e.printStackTrace();
			getFile("Failed to save file", vimg);
			return;
		}
		changing = true;
		setList();
		changing = false;
	}

	private void ini() {
		add(back);
		add(jspst);
		add(jl);
		add(addc);
		add(remc);
		add(impc);
		add(expc);
		setList();
		addListeners();

	}

	private void setList() {
		int ind = jlst.getSelectedIndex();
		list = cas.getList();
		String[] str = new String[list.size()];
		for (int i = 0; i < str.length; i++)
			str[i] = cas.nameOf(list.get(i));
		jlst.setListData(str);
		if (ind < 0)
			ind = 0;
		if (ind >= cas.size())
			ind = cas.size() - 1;
		jlst.setSelectedIndex(ind);
		ImageIcon ic = null;
		if (ind >= 0) {
			VImg s = list.get(ind);
			if (s != null)
				ic = s.getIcon();
		}
		jl.setIcon(ic);

	}

}
