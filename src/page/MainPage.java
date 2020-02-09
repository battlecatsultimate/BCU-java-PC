package page;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JLabel;

import common.CommonStatic.Account;
import common.util.pack.Pack;
import common.util.stage.MapColc;
import io.BCJSON;
import io.Writer;
import page.anim.DIYViewPage;
import page.anim.ImgCutEditPage;
import page.anim.MaAnimEditPage;
import page.anim.MaModelEditPage;
import page.basis.BasisPage;
import page.battle.BattleInfoPage;
import page.battle.RecdManagePage;
import page.event.EventLoadPage;
import page.info.StageViewPage;
import page.info.filter.EnemyFindPage;
import page.info.filter.UnitFindPage;
import page.internet.LoginPage;
import page.internet.WebMainPage;
import page.pack.BackupTreePage;
import page.pack.PackEditPage;
import page.pack.ResourcePage;
import page.view.BGViewPage;
import page.view.CastleViewPage;
import page.view.EffectViewPage;
import page.view.EnemyViewPage;
import page.view.MusicPage;
import page.view.UnitViewPage;

public class MainPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JLabel memo = new JLabel();
	private final JLabel seicon = new JLabel("Source of enemy icon: battlecats-db.com");
	private final JLabel sgifau = new JLabel("Author of GIF exporter: Kevin Weiner, FM Software");
	private final JBTN vuni = new JBTN(0, "vuni");
	private final JBTN vene = new JBTN(0, "vene");
	private final JBTN vsta = new JBTN(0, "vsta");
	private final JBTN vdiy = new JBTN(0, "vdiy");
	private final JBTN conf = new JBTN(0, "conf");
	private final JBTN veff = new JBTN(0, "veff");
	private final JBTN vcas = new JBTN(0, "vcas");
	private final JBTN vbgr = new JBTN(0, "vbgr");
	private final JBTN veif = new JBTN(0, "veif");
	private final JBTN vuif = new JBTN(0, "vuif");
	private final JBTN vmsc = new JBTN(0, "vmsc");
	private final JBTN bass = new JBTN(0, "bass");
	private final JBTN curr = new JBTN(0, "curr");
	private final JBTN pcus = new JBTN(0, "pcus");
	private final JBTN rply = new JBTN(0, "rply");
	private final JBTN caic = new JBTN(0, "caic");
	private final JBTN camm = new JBTN(0, "camm");
	private final JBTN cama = new JBTN(0, "cama");
	private final JBTN event = new JBTN(0, "event");
	private final JBTN gacha = new JBTN(0, "gacha");
	private final JBTN item = new JBTN(0, "item");
	private final JBTN cald = new JBTN(0, "calendar");
	private final JBTN save = new JBTN(0, "save");
	private final JBTN bckp = new JBTN(0, "backup");
	private final JBTN allf = new JBTN(0, "all file");
	private final JBTN intn = new JBTN(-1, "battlecatsultimate");

	public MainPage() {
		super(null);

		ini();
		resized();
	}

	@Override
	protected void renew() {
		Runtime.getRuntime().gc();
		curr.setEnabled(BattleInfoPage.current != null);
		setMemo();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(memo, x, y, 50, 30, 500, 50);
		set(seicon, x, y, 50, 60, 500, 50);
		set(sgifau, x, y, 50, 90, 800, 50);

		set(vuni, x, y, 600, 200, 200, 50);
		set(vene, x, y, 600, 300, 200, 50);
		set(veff, x, y, 600, 400, 200, 50);
		set(vcas, x, y, 600, 500, 200, 50);
		set(vbgr, x, y, 600, 600, 200, 50);
		set(vmsc, x, y, 600, 700, 200, 50);
		set(allf, x, y, 600, 800, 200, 50);

		set(conf, x, y, 900, 200, 200, 50);
		set(save, x, y, 900, 300, 200, 50);
		set(bass, x, y, 900, 400, 200, 50);
		set(bckp, x, y, 900, 500, 200, 50);
		set(curr, x, y, 900, 600, 200, 50);

		set(vsta, x, y, 1200, 200, 200, 50);
		set(veif, x, y, 1200, 300, 200, 50);
		set(vuif, x, y, 1200, 400, 200, 50);
		set(pcus, x, y, 1200, 500, 200, 50);
		set(rply, x, y, 1200, 600, 200, 50);

		set(vdiy, x, y, 1500, 200, 200, 50);
		set(caic, x, y, 1500, 300, 200, 50);
		set(camm, x, y, 1500, 400, 200, 50);
		set(cama, x, y, 1500, 500, 200, 50);

		set(event, x, y, 1800, 200, 200, 50);
		set(gacha, x, y, 1800, 300, 200, 50);
		set(item, x, y, 1800, 400, 200, 50);
		set(cald, x, y, 1800, 500, 200, 50);

		set(intn, x, y, 900, 100, 500, 50);
	}

	private void addListeners() {
		vuni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new UnitViewPage(getThis(), (Pack) null));
			}
		});

		vene.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new EnemyViewPage(getThis(), (Pack) null));
			}
		});

		vsta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new StageViewPage(getThis(), MapColc.MAPS.values()));
			}
		});

		vdiy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new DIYViewPage(getThis()));
			}
		});

		conf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new ConfigPage(getThis()));
			}
		});

		veff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new EffectViewPage(getThis()));
			}
		});

		vcas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new CastleViewPage(getThis()));
			}
		});

		vbgr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new BGViewPage(getThis(), null));
			}
		});

		veif.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new EnemyFindPage(getThis(), null));
			}
		});

		vuif.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new UnitFindPage(getThis(), null));
			}
		});

		bass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new BasisPage(getThis()));
			}
		});

		curr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(BattleInfoPage.current);
			}
		});

		pcus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new PackEditPage(getThis()));
			}
		});

		event.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new EventLoadPage(getThis(), 0, false));
			}
		});

		gacha.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new EventLoadPage(getThis(), 1, false));
			}
		});

		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new EventLoadPage(getThis(), 2, false));
			}
		});

		cald.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new EventLoadPage(getThis(), 3, false));
			}
		});

		caic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new ImgCutEditPage(getThis()));
			}
		});

		camm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new MaModelEditPage(getThis()));
			}
		});

		cama.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new MaAnimEditPage(getThis()));
			}
		});

		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Writer.writeData();
			}

		});

		vmsc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new MusicPage(getThis()));
			}

		});

		rply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new RecdManagePage(getThis()));
			}

		});

		bckp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new BackupTreePage(getThis(), true));
			}

		});

		allf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new ResourcePage(getThis()));
			}

		});

		intn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (BCJSON.ID > 0) {
					changePanel(new WebMainPage(getThis()));
					return;
				}
				if (Account.USERNAME.length() > 0 && Account.PASSWORD != 0) {
					try {
						int id = BCJSON.getID(Account.USERNAME);
						if (id > 0) {
							BCJSON.ID = id;
							changePanel(new WebMainPage(getThis()));
							return;
						}
					} catch (IOException e) {
					}
				}
				changePanel(new LoginPage(getThis()));
			}

		});

	}

	private void ini() {
		add(vuni);
		add(vene);
		add(vsta);
		add(vdiy);
		add(conf);
		add(veff);
		add(vcas);
		add(vbgr);
		add(veif);
		add(vuif);
		add(vmsc);
		add(bass);
		add(memo);
		add(curr);
		add(pcus);
		add(event);
		add(gacha);
		add(item);
		add(cald);
		add(caic);
		add(camm);
		add(cama);
		add(save);
		add(seicon);
		add(sgifau);
		// add(intn);
		add(rply);
		add(bckp);
		add(allf);
		setMemo();
		addListeners();
	}

	private void setMemo() {
		long f = Runtime.getRuntime().freeMemory();
		long t = Runtime.getRuntime().totalMemory();
		long m = Runtime.getRuntime().maxMemory();
		double per = 100.0 * (t - f) / m;
		memo.setText("memory used: " + (t - f >> 20) + " MB / " + (m >> 20) + " MB, " + (int) per + "%");

	}

}
