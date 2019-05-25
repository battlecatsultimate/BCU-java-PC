package page.battle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

import javax.imageio.ImageIO;

import org.jcodec.api.awt.AWTSequenceEncoder;

import io.Writer;
import page.Page;
import util.basis.BattleField;

public interface BBRecd extends BattleBox {

	public void end();

	public String info();

	public void quit();

}

class BBRecdAWT extends BattleBoxDef implements BBRecd {

	private static final long serialVersionUID = 1L;

	private final RecdThread th;
	private final Queue<BufferedImage> qb = new ArrayDeque<>();

	private int time = -1;

	public BBRecdAWT(BattleInfoPage bip, BattleField bas, String out, boolean img) {
		super(bip, bas, 0);
		th = img ? new PNGThread(qb, out, bip) : new MP4Thread(qb, out, bip);
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

class MP4Thread extends RecdThread {

	final File file;

	private AWTSequenceEncoder encoder;

	MP4Thread(Queue<BufferedImage> list, String str, Page page) {
		super(list, page);
		file = new File("./img/" + str + ".mp4");
		try {
			encoder = AWTSequenceEncoder.create30Fps(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	void finish() {
		try {
			encoder.finish();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	void quit() {
		finish();
		Writer.delete(file);
	}

	@Override
	void recd(BufferedImage bimg) {
		try {
			encoder.encodeImage(bimg.getSubimage(0, 0, fw, fh));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

class PNGThread extends RecdThread {

	private final String path;

	private int count = 0;

	PNGThread(Queue<BufferedImage> list, String out, Page page) {
		super(list, page);
		path = "./img/" + out + "/";
		File f = new File(path);
		if (f.exists())
			Writer.delete(f);
	}

	@Override
	void finish() {

	}

	@Override
	void quit() {
		Writer.delete(new File(path));
	}

	@Override
	void recd(BufferedImage bimg) {
		count++;
		String str = "";
		if (count < 10000)
			str += "0";
		if (count < 1000)
			str += "0";
		if (count < 100)
			str += "0";
		if (count < 10)
			str += "0";
		str += count;
		File f = new File(path + str + ".png");
		Writer.check(f);
		try {
			ImageIO.write(bimg, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

abstract class RecdThread extends Thread {

	final Queue<BufferedImage> bimgs;
	final Page p;

	int fw, fh;

	boolean end, quit, finish;

	RecdThread(Queue<BufferedImage> list, Page page) {
		p = page;
		bimgs = list;
	}

	@Override
	public void run() {
		while (true) {
			int size = 0;
			synchronized (this) {
				if (quit)
					break;
				synchronized (bimgs) {
					size = bimgs.size();
				}
				if (bimgs.size() == 0 && end)
					break;
			}
			if (size > 0) {
				BufferedImage bimg;
				synchronized (bimgs) {
					bimg = bimgs.poll();
				}
				if (fw <= 0 || fh <= 0) {
					fw = bimg.getWidth() / 2 * 2;
					fh = bimg.getHeight() / 2 * 2;
				}
				if (fw <= 0 || fh <= 0)
					continue;
				recd(bimg);
				try {
					sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else
				try {
					sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		if (quit) {
			quit();
			return;
		}
		finish();
		p.callBack(null);
	}

	abstract void finish();

	abstract void quit();

	abstract void recd(BufferedImage bimg);

}
