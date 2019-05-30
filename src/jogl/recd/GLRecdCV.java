package jogl.recd;

import java.io.File;

import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;

import jogl.awt.GLCstd;

public class GLRecdCV extends GLRecorder {

	private final FrameGrabber fg;
	private final OpenCVFrameConverter.ToMat converter;
	private final FrameRecorder fr;
	private final String path;
	private boolean open;

	protected GLRecdCV(GLCstd scr, String str) throws Exception {
		super(scr);
		path = "./img/" + str + ".avi";
		fg = FrameGrabber.createDefault(0);
		fg.start();
		converter = new OpenCVFrameConverter.ToMat();
		Mat grab = converter.convert(fg.grab());
		fr = FrameRecorder.createDefault(path, grab.cols(), grab.rows());
		fr.start();
	}

	@Override
	public void end() {
		open = false;
		try {
			fg.stop();
			fr.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void quit() {
		end();
		new File(path).delete();
	}

	@Override
	public int remain() {
		return 0;
	}

	@Override
	public void start() {
		open = true;
	}

	@Override
	public void update() {
		if (open)
			try {
				fr.record(fg.grab());
			} catch (Exception e) {
				e.printStackTrace();
			}

	}

}
