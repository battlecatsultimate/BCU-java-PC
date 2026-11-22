package utilpc;

import main.MainBCU;

import javax.swing.*;
import javax.swing.plaf.basic.BasicHTML;
import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.font.LineMetrics;
import java.awt.font.TextAttribute;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.function.Consumer;

/**
 * Draws label text white with a black outline (Nimbus theme only, when enabled), we love bandaid fixes
 */
public class OutlineText {

	/**
	 * Paints the labels icon and text itself. Returns false when the label should be painted normally instead
	 * (option off, other theme, html or empty text)
	 */
	public static boolean paint(JLabel l, Graphics g) {
		if (!active())
			return false;

		String text = l.getText();

		// html text is drawn by Swing's own text view, which is stupid and dumb and i couldn't figure out how to outline it
		if (text == null || text.isEmpty() || l.getClientProperty(BasicHTML.propertyKey) != null)
			return false;

		int w = l.getWidth();
		int h = l.getHeight();

		if (w <= 0 || h <= 0)
			return false;
		Graphics2D g2 = (Graphics2D) g.create();

		try {
			if (l.isOpaque()) {
				g2.setColor(l.getBackground());
				g2.fillRect(0, 0, w, h);
			}

			Font font = l.getFont();
			FontMetrics fm = l.getFontMetrics(font);
			Icon icon = l.isEnabled() ? l.getIcon() : l.getDisabledIcon();
			Insets in = l.getInsets();
			Rectangle viewR = new Rectangle(in.left, in.top, w - in.left - in.right, h - in.top - in.bottom);
			Rectangle iconR = new Rectangle();
			Rectangle textR = new Rectangle();

			String clipped = SwingUtilities.layoutCompoundLabel(l, fm, text, icon, l.getVerticalAlignment(),
					l.getHorizontalAlignment(), l.getVerticalTextPosition(), l.getHorizontalTextPosition(), viewR,
					iconR, textR, l.getIconTextGap());

			if (icon != null)
				icon.paintIcon(l, g2, iconR.x, iconR.y);
			if (clipped == null || clipped.isEmpty())
				return true;

			drawText(g2, font, clipped, textR, textR.y + fm.getAscent(), l.isEnabled(), l.getForeground(), minX(iconR, textR));
		} finally {
			g2.dispose();
		}

		return true;
	}

	/**
	 * Draws the checkbox's own parts (box, background, border) normally, but the text white with a black outline.
	 * base paints the component the normal way. returns false when the component should be painted normally instead
	 */
	public static boolean paintCheck(AbstractButton b, Graphics g, Consumer<Graphics> base) {
		if (!active())
			return false;

		String text = b.getText();

		if (text == null || text.isEmpty() || b.getClientProperty(BasicHTML.propertyKey) != null)
			return false;
		int w = b.getWidth();
		int h = b.getHeight();
		if (w <= 0 || h <= 0)
			return false;

		Font font = b.getFont();
		FontMetrics fm = b.getFontMetrics(font);
		Insets in = b.getInsets();
		int gap = b.getIconTextGap();
		Dimension pref = b.getPreferredSize();

		// the size of the box is not available directly, so take it from what the layout reserves for it
		int iconW = Math.max(0, pref.width - in.left - in.right - gap - fm.stringWidth(text));
		int iconH = Math.max(0, pref.height - in.top - in.bottom);
		Icon box = new Icon() {
			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {
			}
			@Override
			public int getIconWidth() {
				return iconW;
			}
			@Override
			public int getIconHeight() {
				return iconH;
			}
		};

		Rectangle viewR = new Rectangle(in.left, in.top, w - in.left - in.right, h - in.top - in.bottom);
		Rectangle iconR = new Rectangle();
		Rectangle textR = new Rectangle();
		String clipped = SwingUtilities.layoutCompoundLabel(b, fm, text, box, b.getVerticalAlignment(),
				b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewR, iconR,
				textR, gap);

		// everything but the text
		Graphics2D gb = (Graphics2D) g.create();

		try {
			Area area = new Area(new Rectangle(0, 0, w, h));
			area.subtract(new Area(new Rectangle(textR.x - 2, textR.y - 1, textR.width + 4, textR.height + 2)));
			gb.clip(area);
			base.accept(gb);
		} finally {
			gb.dispose();
		}

		if (clipped == null || clipped.isEmpty())
			return true;

		Graphics2D g2 = (Graphics2D) g.create();

		try {
			drawText(g2, font, clipped, textR, textR.y + fm.getAscent(), b.isEnabled(), b.getForeground(), minX(iconR, textR));
		} finally {
			g2.dispose();
		}

		return true;
	}

	// Translucent box behind the text, dark for light text and light for dark text (alpha 0 to 255)
	private static final int HIGHLIGHT_ALPHA_DARK = 170, HIGHLIGHT_ALPHA_LIGHT = 190;

	private static boolean active() {
		return MainBCU.nimbus && (MainBCU.outlineText || MainBCU.highlightText);
	}

	// Left limit for the highlight box, so it doesn't cover an icon or the box of a checkbox in front of the text
	private static int minX(Rectangle iconR, Rectangle textR) {
		return iconR.width > 0 && iconR.x < textR.x ? iconR.x + iconR.width + 1 : Integer.MIN_VALUE;
	}

	private static void drawText(Graphics2D g2, Font font, String text, Rectangle textR, int baseline, boolean enabled, Color fg, int minX) {
		if (MainBCU.highlightText && !MainBCU.outlineText) {
			drawHighlighted(g2, font, text, textR, baseline, enabled, fg, minX);
			return;
		}

		int x = textR.x;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

		GlyphVector gv = font.createGlyphVector(g2.getFontRenderContext(), text);
		Path2D.Double s = new Path2D.Double(gv.getOutline(x, baseline));

		// the glyph outline has no underline, so it is added to the shape (see Interpret.setUnderline)
		if (TextAttribute.UNDERLINE_ON.equals(font.getAttributes().get(TextAttribute.UNDERLINE))) {
			LineMetrics lm = font.getLineMetrics(text, g2.getFontRenderContext());
			double t = Math.max(1, lm.getUnderlineThickness());

			s.append(new Rectangle2D.Double(x, baseline + lm.getUnderlineOffset() - t / 2 + 1, gv.getLogicalBounds().getWidth(), t), false);
		}

		//edit these values to make outlines bigger. One is minimum size, second is a divisor.
		g2.setStroke(new BasicStroke(Math.max(2f, font.getSize2D() / 3f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2.setColor(Color.BLACK);
		g2.draw(s);
		g2.setColor(enabled ? Color.WHITE : new Color(200, 200, 200));
		g2.fill(s);
	}

	//The Text in its normal color on a translucent rounded box, whose color is the opposite of the text
	private static void drawHighlighted(Graphics2D g2, Font font, String text, Rectangle textR, int baseline, boolean enabled, Color fg, int minX) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		boolean lightText = (0.299 * fg.getRed() + 0.587 * fg.getGreen() + 0.114 * fg.getBlue()) / 255 > 0.5;
		int pad = Math.max(3, Math.round(font.getSize2D() / 5f));
		int padY = Math.max(1, Math.round(font.getSize2D() / 12f));
		int x0 = Math.max(minX, textR.x - pad);
		int x1 = textR.x + textR.width + pad;
		float arc = Math.max(6f, font.getSize2D() / 2f);

		g2.setColor(lightText ? new Color(0, 0, 0, HIGHLIGHT_ALPHA_DARK) : new Color(255, 255, 255, HIGHLIGHT_ALPHA_LIGHT));
		g2.fill(new RoundRectangle2D.Float(x0, textR.y - padY, x1 - x0, textR.height + 2 * padY, arc, arc));

		g2.setFont(font);
		g2.setColor(enabled ? fg : lightText ? new Color(170, 170, 170) : new Color(110, 110, 110));
		// the underline (see Interpret.setUnderline) is drawn along with the text
		g2.drawString(text, textR.x, baseline);
	}

	// label that draws its text outlined or highlighted when an option is on, also used for slider labels
	public static class Label extends JLabel {

		private static final long serialVersionUID = 1L;

		private final JSlider slider;

		public Label(String text) {
			this(text, null);
		}

		public Label(String text, JSlider slider) {
			super(text);
			this.slider = slider;
		}
		// like the standard slider labels, the text always has the slider's font, which changes with the window size
		@Override
		public Font getFont() {
			return slider != null ? slider.getFont() : super.getFont();
		}

		@Override
		protected void paintComponent(Graphics g) {
			// slider labels are not laid out by a container, so they size themselves
			if (slider != null) {
				Dimension d = getPreferredSize();

				if (getWidth() != d.width || getHeight() != d.height)
					setSize(d);
			}

			if (!OutlineText.paint(this, g))
				super.paintComponent(g);
		}
	}

	//Replaces the number labels of a slider with ones that can be outlined
	public static void outlineLabels(JSlider slider) {
		Dictionary<?, ?> old = slider.getLabelTable();

		if (old == null)
			return;

		Hashtable<Integer, JComponent> table = new Hashtable<>();

		for (Enumeration<?> keys = old.keys(); keys.hasMoreElements(); ) {
			Object key = keys.nextElement();
			Object value = old.get(key);

			if (!(key instanceof Integer) || !(value instanceof JComponent))
				continue;

			if (value instanceof JLabel) {
				JLabel o = (JLabel) value;
				Label l = new Label(o.getText(), slider);

				l.setHorizontalAlignment(o.getHorizontalAlignment());
				l.setSize(l.getPreferredSize());
				table.put((Integer) key, l);
			} else
				table.put((Integer) key, (JComponent) value);
		}

		slider.setLabelTable(table);
	}
}
