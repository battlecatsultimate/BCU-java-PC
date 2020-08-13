package page;

import javax.swing.JLabel;

import common.util.stage.MapColc;
import io.Writer;
import page.anim.DIYViewPage;
import page.anim.ImgCutEditPage;
import page.anim.MaAnimEditPage;
import page.anim.MaModelEditPage;
import page.basis.BasisPage;
import page.battle.BattleInfoPage;
import page.battle.RecdManagePage;
import page.info.StageViewPage;
import page.info.filter.EnemyFindPage;
import page.info.filter.UnitFindPage;
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
	private final JBTN save = new JBTN(0, "save");
	private final JBTN bckp = new JBTN(0, "backup");
	private final JBTN allf = new JBTN(0, "all file");

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
	}

	private void addListeners() {
		vuni.setLnr(() -> new UnitViewPage(this, (String) null));
		vene.setLnr(() -> new EnemyViewPage(this, (String) null));
		vsta.setLnr(() -> new StageViewPage(this, MapColc.values()));
		vdiy.setLnr(() -> new DIYViewPage(this));
		conf.setLnr(() -> new ConfigPage(this));
		veff.setLnr(() -> new EffectViewPage(this));
		vcas.setLnr(() -> new CastleViewPage(this));
		vbgr.setLnr(() -> new BGViewPage(this, null));
		veif.setLnr(() -> new EnemyFindPage(this, null));
		vuif.setLnr(() -> new UnitFindPage(this, null));
		bass.setLnr(() -> new BasisPage(this));
		curr.setLnr(() -> BattleInfoPage.current);
		// FIXME pcus.setLnr(() -> new PackEditPage(this));
		caic.setLnr(() -> new ImgCutEditPage(this));
		camm.setLnr(() -> new MaModelEditPage(this));
		cama.setLnr(() -> new MaAnimEditPage(this));
		save.setLnr((e) -> Writer.writeData());
		vmsc.setLnr(() -> new MusicPage(this));
		rply.setLnr(() -> new RecdManagePage(this));
		// FIXME bckp.setLnr(() -> new BackupTreePage(this, true));
		allf.setLnr(() -> new ResourcePage(this));

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
		add(caic);
		add(camm);
		add(cama);
		add(save);
		add(seicon);
		add(sgifau);
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
