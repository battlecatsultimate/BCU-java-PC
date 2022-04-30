package utilpc;

import common.CommonStatic;
import common.CommonStatic.Itf;
import common.battle.data.PCoin;
import common.pack.Context;
import common.pack.Identifier;
import common.system.VImg;
import common.system.fake.FakeImage;
import common.util.pack.Background;
import common.util.stage.Music;
import common.util.unit.Form;
import common.util.unit.Trait;
import io.BCMusic;
import io.BCUWriter;
import utilpc.awt.FG2D;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class UtilPC {

	public static class PCItr implements Itf {

		@Override
		public void save(boolean save, boolean exit) {
			CommonStatic.ctx.noticeErr(() -> BCUWriter.logClose(save), Context.ErrType.ERROR, "Save failed...");

			if(exit)
				System.exit(0);
		}

		@Override
		public long getMusicLength(Music f) {
			if (f.data == null) {
				return -1;
			}

			try {
				OggTimeReader otr = new OggTimeReader(f);

				return otr.getTime();
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		}

		@Override
		@Deprecated
		public File route(String path) {
			return new File(path);
		}

		@Override
		public void setSE(int ind) {
			BCMusic.setSE(ind);
		}

		@Override
		public void setBGM(Identifier<Music> mus) {
			BCMusic.play(mus);
		}

	}

	public static ImageIcon getBg(Background bg, int w, int h) {
		bg.check();

		int groundHeight = ((BufferedImage) bg.parts[Background.BG].bimg()).getHeight();
		int skyHeight = bg.top ? ((BufferedImage) bg.parts[Background.TOP].bimg()).getHeight() : 1020 - groundHeight;

		if(skyHeight < 0)
			skyHeight = 0;

		double r = h / (double) (groundHeight + skyHeight + 408);

		int fw = (int) (768 * r);
		int sh = (int) (skyHeight * r);
		int gh = (int) (groundHeight * r);
		int skyGround = (int) (204 * r);

		BufferedImage temp = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		Graphics2D g = (Graphics2D) temp.getGraphics();

		FG2D fg = new FG2D(g);

		if (bg.top && bg.parts.length > Background.TOP) {
			for (int i = 0; i * fw < w; i++)
				fg.drawImage(bg.parts[Background.TOP], fw * i, skyGround, fw, sh);

			fg.gradRect(0, 0, w, skyGround, 0, 0, bg.cs[0], 0, skyGround, bg.cs[1]);
		} else {
			fg.gradRect(0, 0, w, sh + skyGround, 0, 0, bg.cs[0], 0, sh + skyGround, bg.cs[1]);
		}

		for (int i = 0; i * fw < w; i++)
			fg.drawImage(bg.parts[Background.BG], fw * i, sh + skyGround, fw, gh);

		fg.gradRect(0, sh + gh + skyGround, w, skyGround, 0, sh + gh + skyGround, bg.cs[2], 0, h, bg.cs[3]);

		if(bg.overlay != null) {
			fg.gradRectAlpha(0, 0, w, h, 0, 0, bg.overlayAlpha, bg.overlay[1], 0, h, bg.overlayAlpha, bg.overlay[0]);
		}

		g.dispose();
		return new ImageIcon(temp);
	}

	public static BufferedImage getIcon(int type, int id) {
		type += id / 100;
		id %= 100;
		if (CommonStatic.getBCAssets().icon[type][id] == null)
			return null;
		return (BufferedImage) CommonStatic.getBCAssets().icon[type][id].getImg().bimg();
	}

	public static ImageIcon createIcon(int type, int id) {
		BufferedImage img = getIcon(type, id);
		if (img != null)
			return new ImageIcon(img);
		return null;
	}

	public static ImageIcon getIcon(VImg v) {
		FakeImage img = v.getImg();
		if (img == null)
			return null;
		if (img.bimg() == null)
			return null;
		return new ImageIcon((Image) img.bimg());
	}

	public static String[] lvText(Form f, ArrayList<Integer> lvs) {
		PCoin pc = f.du.getPCoin();
		if (pc == null)
			return new String[] { "Lv." + lvs.get(0), "" };
		else {
			String[] TraitsHolder = new String[pc.trait.size()];
			for (int i = 0 ; i < pc.trait.size() ; i++) {
				Trait trait = pc.trait.get(i);
				if (trait.BCTrait)
					TraitsHolder[i] = Interpret.TRAIT[trait.id.id];
				else
					TraitsHolder[i] = trait.name;
			}
			StringBuilder lab = new StringBuilder();

			if(pc.trait.size() > 0) {
				lab.append("[").append(Interpret.getTrait(TraitsHolder, 0)).append("]").append(" ");
			}

			lab.append(Interpret.PCTX[pc.info.get(0)[0]]);

			StringBuilder str = new StringBuilder("Lv." + lvs.get(0) + ", {");
			for (int i = 1; i < pc.info.size(); i++) {
				str.append(lvs.get(i)).append(",");
				lab.append(", ").append(getPCoinAbilityText(pc, i));
			}
			str.append(lvs.get(pc.info.size())).append("}");
			return new String[] {str.toString(), lab.toString()};
		}
	}

	public static String getPCoinAbilityText(PCoin pc, int index) {
		if(index < 0 || index >= pc.info.size())
			return null;

		return Interpret.PCTX[pc.info.get(index)[0]];
	}
}
