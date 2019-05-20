package util.system.fake;

import java.awt.Color;
import java.awt.Composite;
import java.awt.RenderingHints.Key;

public interface FakeGraphics {

	public void drawImage(FakeImage bimg, int i, int j);

	public void drawImage(FakeImage bimg, int ix, int iy, int iw, int ih);

	public void drawLine(int i, int j, int x, int y);

	public void drawOval(int i, int j, int k, int l);

	public void drawRect(int x, int y, int x2, int y2);

	public void fillOval(int i, int j, int k, int l);

	public void fillRect(int x, int y, int w, int h);

	public Composite getComposite();

	public FakeTransform getTransform();

	public void gradRect(int i, int j, int x, int k, int l, int y, Color color, int m, int n, Color color2);

	public void rotate(double d);

	public void scale(int hf, int vf);

	public void setColor(Color red);

	public void setComposite(Composite c);

	public void setRenderingHint(Key key, Object object);

	public void setTransform(FakeTransform at);

	public void translate(double x, double y);

}
