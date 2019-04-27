package page.battle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import io.BCMusic;
import main.Opts;
import page.JBTN;
import page.JTG;
import page.KeyHandler;
import page.Page;
import util.basis.BasisLU;
import util.basis.SBCtrl;
import util.basis.SBRply;
import util.basis.StageBasis;
import util.entity.AbEntity;
import util.entity.Entity;
import util.stage.Recd;
import util.stage.Stage;

public class BattleInfoPage extends KeyHandler {

	private static final long serialVersionUID = 1L;

	public static BattleInfoPage current = null;

	public static void redefine() {
		ComingTable.redefine();
		EntityTable.redefine();
	}

	private final JBTN back = new JBTN(0, "back");
	private final JBTN paus = new JBTN(0, "pause");
	private final JBTN next = new JBTN(0, "nextf");
	private final JBTN rply = new JBTN();
	private final EntityTable ut = new EntityTable(-1);
	private final ComingTable ct = new ComingTable(this);
	private final EntityTable et = new EntityTable(1);
	private final JScrollPane eup = new JScrollPane(ut);
	private final JScrollPane eep = new JScrollPane(et);
	private final JScrollPane ctp = new JScrollPane(ct);
	private final JLabel ebase = new JLabel();
	private final JLabel ubase = new JLabel();
	private final JLabel ecount = new JLabel();
	private final JLabel ucount = new JLabel();
	private final JLabel stream = new JLabel();
	private final JTG jtb = new JTG(0, "larges");
	private final BattleBox bb;
	private final StageBasis basis;

	private boolean pause = false;
	private Recd recd;

	public int spe = 0, upd = 0;

	public BattleInfoPage(Page p, Recd rec, int conf) {
		super(p);
		recd = rec;
		basis = rec.replay();
		if ((conf & 1) == 0)
			bb = new BattleBox(this, basis);
		else
			bb = new BBRecd(this, basis, rec.name, (conf & 4) != 0);
		jtb.setSelected((conf & 2) != 0);
		jtb.setEnabled((conf & 1) == 0);
		ct.setData(basis.st);

		ini();
		rply.setText(0, "save");
		resized();
	}

	protected BattleInfoPage(Page p, Stage st, int star, BasisLU bl, int[] ints) {
		super(p);
		long seed = new Random().nextLong();
		SBCtrl sb = new SBCtrl(this, st, star, bl, ints, seed);
		bb = new BBCtrl(this, sb);
		basis = sb;
		ct.setData(basis.st);

		ini();
		rply.setText(0, "rply");
		resized();
		current = this;
	}

	@Override
	public void callBack(Object o) {
		changePanel(getFront());
	}

	@Override
	protected synchronized void keyTyped(KeyEvent e) {
		if (spe > -5 && e.getKeyChar() == ',')
			spe--;
		if (spe < 5 && e.getKeyChar() == '.')
			spe++;
	}

	@Override
	protected void mouseClicked(MouseEvent e) {
		if (e.getSource() == bb)
			bb.click(e.getPoint(), e.getButton());
	}

	@Override
	protected void mouseDragged(MouseEvent e) {
		if (e.getSource() == bb)
			bb.drag(e.getPoint());
	}

	@Override
	protected void mousePressed(MouseEvent e) {
		if (e.getSource() == bb)
			bb.press(e.getPoint());
	}

	@Override
	protected void mouseReleased(MouseEvent e) {
		if (e.getSource() == bb)
			bb.release(e.getPoint());
	}

	@Override
	protected void mouseWheel(MouseEvent e) {
		if (e.getSource() == bb)
			bb.wheeled(e.getPoint(), ((MouseWheelEvent) e).getWheelRotation());
	}

	@Override
	protected void renew() {
		if (basis.getEBHP() * 100 < basis.st.mush)
			BCMusic.play(basis.st.mus1);
		else
			BCMusic.play(basis.st.mus0);
	}

	@Override
	protected synchronized void resized(int x, int y) {
		setBounds(0, 0, x, y);
		if (jtb.isSelected()) {
			set(back, x, y, 0, 0, 200, 50);
			set(jtb, x, y, 2100, 0, 200, 50);
			set(paus, x, y, 700, 0, 200, 50);
			set(rply, x, y, 1000, 0, 200, 50);
			set(stream, x, y, 900, 0, 400, 50);
			set(next, x, y, 1300, 0, 200, 50);
			set(ebase, x, y, 240, 0, 600, 50);
			set(ubase, x, y, 1540, 0, 200, 50);
			set(bb, x, y, 50, 50, 1920, 1200);
			set(ctp, x, y, 0, 0, 0, 0);
			set(eep, x, y, 50, 100, 0, 0);
			set(eup, x, y, 50, 400, 0, 0);
			set(ecount, x, y, 50, 50, 0, 0);
			set(ucount, x, y, 50, 350, 0, 0);
		} else {
			set(back, x, y, 0, 0, 200, 50);
			set(jtb, x, y, 2100, 0, 200, 50);
			set(ctp, x, y, 50, 850, 1200, 400);
			set(eep, x, y, 50, 100, 600, 700);
			set(bb, x, y, 700, 300, 800, 500);
			set(paus, x, y, 700, 200, 200, 50);
			set(rply, x, y, 1000, 200, 200, 50);
			set(stream, x, y, 900, 200, 400, 50);
			set(next, x, y, 1300, 200, 200, 50);
			set(eup, x, y, 1650, 100, 600, 1100);
			set(ebase, x, y, 700, 250, 600, 50);
			set(ubase, x, y, 1300, 250, 200, 50);
			set(ecount, x, y, 50, 50, 600, 50);
			set(ucount, x, y, 1650, 50, 600, 50);
		}
		ct.setRowHeight(size(x, y, 50));
		et.setRowHeight(size(x, y, 50));
		ut.setRowHeight(size(x, y, 50));
	}

	@Override
	protected synchronized void timer(int t) {
		if (!pause) {
			upd++;
			if (spe < 0)
				if (upd % (1 - spe) != 0)
					return;
			basis.update();
			updateKey();
			if (spe > 0)
				for (int i = 0; i < Math.pow(2, spe); i++)
					basis.update();
			ct.update(basis.est);
			List<Entity> le = new ArrayList<>();
			List<Entity> lu = new ArrayList<>();
			for (Entity e : basis.le)
				(e.dire == 1 ? le : lu).add(e);
			et.setList(le);
			ut.setList(lu);
		}
		bb.paint(bb.getGraphics());
		AbEntity eba = basis.ebase;
		long h = eba.health;
		long mh = eba.maxH;
		ebase.setText("HP: " + h + "/" + mh + ", " + 10000 * h / mh / 100.0 + "%");
		ubase.setText("HP: " + basis.ubase.health);
		ecount.setText(basis.entityCount(1) + "/" + basis.st.max);
		ucount.setText(basis.entityCount(-1) + "/" + basis.max_num);
		resized();
		if (basis.getEBHP() * 100 < basis.st.mush && BCMusic.music != basis.st.mus1)
			BCMusic.play(basis.st.mus1);
		if (bb instanceof BBRecd) {
			BBRecd bbr = (BBRecd) bb;
			stream.setText("frame left: " + bbr.info());
		}
	}

	private void addListeners() {

		back.setLnr(x -> {
			BCMusic.DEF.stop();
			if (bb instanceof BBRecd) {
				BBRecd bbr = (BBRecd) bb;
				if (Opts.conf("Do you want to save this video?")) {
					bbr.end();
					return;
				} else {
					bbr.quit();
				}
			}
			changePanel(getFront());
		});

		rply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (basis instanceof SBCtrl)
					changePanel(new BattleInfoPage(getThis(), ((SBCtrl) basis).getData(), 0));
				if (basis instanceof SBRply)
					changePanel(new RecdSavePage(getThis(), recd));
			}
		});

		paus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pause = !pause;
			}
		});

		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pause = false;
				timer(0);
				pause = true;
			}
		});

	}

	private void ini() {
		add(back);
		add(eup);
		add(eep);
		add(ctp);
		add(bb);
		add(paus);
		add(next);
		add(ebase);
		add(ubase);
		add(ecount);
		add(ucount);
		add(jtb);
		if (bb instanceof BBRecd)
			add(stream);
		else
			add(rply);
		addListeners();
	}

}
