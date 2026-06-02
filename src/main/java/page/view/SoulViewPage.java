package page.view;

import common.pack.PackData;
import common.pack.Source;
import common.pack.UserProfile;
import common.util.anim.AnimCE;
import common.util.anim.AnimD;
import common.util.anim.EAnimI;
import common.util.pack.Soul;
import main.Opts;
import page.Page;
import page.anim.ImgCutEditPage;
import page.support.AnimLCR;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class SoulViewPage extends AbViewPage {

	private static final long serialVersionUID = 1L;

	private final JList<Soul> jlu = new JList<>();
	private final JScrollPane jspu = new JScrollPane(jlu);
	private final JLabel source = new JLabel("Source of enemy icon: DB");

	public SoulViewPage(Page p, Soul s) {
		this(p, s.getID().pack);
		jlu.setSelectedValue(s, true);
	}

	public SoulViewPage(Page p, String pac) {
		super(p);
		PackData pack = UserProfile.getPack(pac);
		if (pack != null)
			jlu.setListData(new Vector<>(pack.souls.getList()));
		ini();
	}

	public SoulViewPage(Page p) {
		super(p);
		Vector<Soul> v = new Vector<>();

		for(PackData pack : UserProfile.getAllPacks())
			v.addAll(pack.souls.getList());

		jlu.setListData(v);

		ini();
	}

	@Override
	protected void resized(int x, int y) {
		super.resized(x, y);
		set(source, x, y, 0, 50, 600, 50);
		if (!larges.isSelected()) {
			set(jspu, x, y, 50, 100, 300, 1100);
		} else {
			set(jspu, x, y, 100, 700, 300, 400);
		}

		jlu.setFixedCellHeight(size(x, y, 50));
		jspu.revalidate();
	}

	@Override
	protected void updateChoice() {
		Soul u = jlu.getSelectedValue();
		if (u != null)
			setAnim(u.anim);
	}

	private void addListeners() {

		jlu.addListSelectionListener(arg0 -> {
			if (arg0.getValueIsAdjusting())
				return;
			updateChoice();
		});

		ActionListener[] listeners = copy.getActionListeners();

		for(ActionListener listener : listeners)
			copy.removeActionListener(listener);

		copy.addActionListener(e -> {
			{
				if (jlu.getSelectedValuesList().size() <= 1) {
					Soul ene = jlu.getSelectedValue();

					if (ene != null) {
						PackData pack = ene.getCont();

						if (pack != null)
							if (pack instanceof PackData.DefPack)
								copyAnim();
							else if (pack instanceof PackData.UserPack) {
								if (((PackData.UserPack) pack).editable || ((PackData.UserPack) pack).desc.allowAnim)
									copyAnim();
								else {
									String pass = Opts.read("Enter the password : ");

									if (pass == null)
										return;

									if (((Source.ZipSource) ((PackData.UserPack) pack).source).zip.matchKey(pass)) {
										copyAnim();
									} else {
										Opts.pop("You typed incorrect password", "Incorrect password");
									}
								}
							}
					}
				} else {
					List<Soul> list = jlu.getSelectedValuesList();
					PackData pack = list.get(0).getCont();

					if (pack == null)
						return;

					if (pack instanceof PackData.UserPack) {
						if (!((PackData.UserPack) pack).editable && !((PackData.UserPack) pack).desc.allowAnim) {
							String pass = Opts.read("Enter the password : ");

							if (pass == null)
								return;

							if (!((Source.ZipSource) ((PackData.UserPack) pack).source).zip.matchKey(pass)) {
								Opts.pop("You typed incorrect password", "Incorrect password");
								return;
							}
						}
					}

					for (Soul ene : list)
						copyAnim(ene);
				}
			}
		});
	}

	private void ini() {
		preini();
		add(jspu);
		add(source);
		jlu.setCellRenderer(new AnimLCR());

		addListeners();

	}

	private void copyAnim() {
		EAnimI ei = vb.getEnt();
		if (ei == null || !(ei.anim() instanceof AnimD))
			return;
		AnimD<?, ?> eau = (AnimD<?, ?>) ei.anim();
		Source.ResourceLocation rl = new Source.ResourceLocation(Source.ResourceLocation.LOCAL, "new soul anim", Source.BasePath.SOUL);
		Source.Workspace.validate(rl);
		new AnimCE(rl, eau);
		changePanel(new ImgCutEditPage(getThis()));
	}

	private void copyAnim(Soul ene) {
		EAnimI ei = ene.anim.getEAnim(ene.anim.types()[0]);
		if (ei == null || ei.anim() == null)
			return;
		AnimD<?, ?> eau = (AnimD<?, ?>) ei.anim();
		Source.ResourceLocation rl = new Source.ResourceLocation(Source.ResourceLocation.LOCAL, ene.toString(), Source.BasePath.SOUL);
		Source.Workspace.validate(rl);
		new AnimCE(rl, eau);
		changePanel(new ImgCutEditPage(getThis()));
	}
}
