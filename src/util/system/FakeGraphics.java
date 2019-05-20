package util.system;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Paint;
import java.awt.RenderingHints.Key;
import java.awt.geom.AffineTransform;

public interface FakeGraphics {

	public void drawImage(FakeImage bimg, int i, int j);

	public void drawImage(FakeImage bimg, int ix, int iy, int iw, int ih);

	public void drawLine(int i, int j, int x, int y);

	public void drawOval(int i, int j, int k, int l);

	public void drawRect(int x, int y, int x2, int y2);

	public void fillOval(int i, int j, int k, int l);

	public void fillRect(int x, int y, int w, int h);

	public Composite getComposite();

	public AffineTransform getTransform();

	public void rotate(double d);

	public void scale(int hf, int vf);

	public void setColor(Color red);

	public void setComposite(Composite c);

	public void setPaint(Paint gradientPaint);

	public void setRenderingHint(Key key, Object object);

	public void setTransform(AffineTransform at);

	public void translate(double x, double y);

}
