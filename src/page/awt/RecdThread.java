package page.awt;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Queue;

import javax.imageio.ImageIO;

import org.jcodec.api.awt.AWTSequenceEncoder;

import io.Writer;
import page.battle.BattleBox.OuterBox;
import res.AnimatedGifEncoder;

public abstract class RecdThread extends Thread {

	private static class GIFThread extends RecdThread {

		private final AnimatedGifEncoder gif;

		protected GIFThread(Queue<BufferedImage> list, String path, OuterBox bip) {
			super(list, bip);
			gif = new AnimatedGifEncoder();
			gif.setDelay(33);
			Writer.writeGIF(gif, path);
		}

		@Override
		void finish() {
			gif.finish();
		}

		@Override
		void quit() {
			finish();
		}

		@Override
		void recd(BufferedImage bimg) {
			gif.addFrame(bimg);
		}

	}

	private static class MP4Thread extends RecdThread {

		final File file;

		private AWTSequenceEncoder encoder;

		MP4Thread(Queue<BufferedImage> list, String str, OuterBox page) {
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

	private static class PNGThread extends RecdThread {

		private final String path;

		private int count = 0;

		PNGThread(Queue<BufferedImage> list, String out, OuterBox bip) {
			super(list, bip);
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

	public static final int PNG = 0, MP4 = 1, GIF = 2;

	public static RecdThread getIns(OuterBox bip, Queue<BufferedImage> qb, String out, int type) {
		if (type == PNG)
			return new PNGThread(qb, out, bip);
		else if (type == MP4)
			new MP4Thread(qb, out, bip);
		else if (type == GIF)
			return new GIFThread(qb, out, bip);
		return null;
	}

	private final Queue<BufferedImage> bimgs;
	private final OuterBox p;

	int fw, fh;

	public boolean end, quit;

	protected RecdThread(Queue<BufferedImage> list, OuterBox bip) {
		p = bip;
		bimgs = list;
	}

	public int remain() {
		synchronized (bimgs) {
			return bimgs.size();
		}
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
		if (p != null)
			p.callBack(null);
	}

	abstract void finish();

	abstract void quit();

	abstract void recd(BufferedImage bimg);

}
