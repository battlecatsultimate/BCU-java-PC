package page.awt;

import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Queue;

import page.battle.BBRecd;
import util.basis.BattleField;

class BBRecdAWT extends BattleBoxDef implements BBRecd {

	private static final long serialVersionUID = 1L;

	private final RecdThread th;
	private final Queue<BufferedImage> qb = new ArrayDeque<>();

	private int time = -1;

	public BBRecdAWT(OuterBox bip, BattleField bas, String out, boolean img) {
		super(bip, bas, 0);
		th = RecdThread.getIns(bip, qb, out, img ? RecdThread.PNG : RecdThread.MP4);
		th.start();
	}

	@Override
	public void end() {
		synchronized (th) {
			th.end = true;
		}
	}

	@Override
	public String info() {
		int size;
		synchronized (qb) {
			size = qb.size();
		}
		return "" + size;
	}

	@Override
	public void quit() {
		synchronized (th) {
			th.quit = true;
		}
	}

	@Override
	protected BufferedImage getImage() {
		BufferedImage bimg = super.getImage();
		if (bbp.bf.sb.time > time)
			synchronized (qb) {
				qb.add(bimg);
				time = bbp.bf.sb.time;
			}
		return bimg;
	}

}