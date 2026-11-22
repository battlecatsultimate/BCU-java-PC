package page;

import common.system.P;
import main.MainBCU;
import main.Opts;
import main.Printer;
import utilpc.BGImage;
import utilpc.PP;
import utilpc.Theme;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public static int x = 0, y = 0;
	public static Rectangle rect = null, crect = null;
	public static Font font = null;
	public static boolean closeClicked = false;

	public static String fontType = "Dialog";
	public static int fontStyle = Font.PLAIN;
	public static final int fontSize = 24;
	public static MainFrame F;

	private static Page mainPanel = null;

	public static void changePanel(Page p) {
		F.FchangePanel(p);

		JMenuItem back = MenuBarHandler.getFileItem("Go Back");

		if(back != null && p != null) {
			back.setEnabled(p.getBackButton() != null);
		}
	}

	/** Switches between the light and dark Nimbus theme from any screen
	 * this is currently unnused since this stuff was way too buggy, may revisit later
	 */
	public static void setLightTheme(boolean light) {
		if (!MainBCU.nimbus)
			return;

		MainBCU.light = light;

		if (light) {
			Theme.LIGHT.setTheme();
			Page.BGCOLOR = new Color(255, 255, 255);
		} else {
			Theme.DARK.setTheme();
			Page.BGCOLOR = new Color(40, 40, 40);
		}
		if (F != null) {
			if (mainPanel != null)
				mainPanel.setBackground(Page.BGCOLOR);

			// a poor attempt to fix font size issues
			Map<Component, Font> menuFonts = new HashMap<>();
			if (F.getJMenuBar() != null)
				collectMenuFonts(F.getJMenuBar(), menuFonts);
			SwingUtilities.updateComponentTreeUI(F);
			for (Map.Entry<Component, Font> e : menuFonts.entrySet())
				e.getKey().setFont(e.getValue());

			if (mainPanel != null)
				mainPanel.themeChanged();
		}
	}

	private static void collectMenuFonts(MenuElement element, Map<Component, Font> fonts) {
		Component c = element.getComponent();

		if (c != null)
			fonts.put(c, c.getFont());

		for (MenuElement sub : element.getSubElements())
			collectMenuFonts(sub, fonts);
	}
	// turns the custom menu Background on or off from any screen
	public static void setCustomBG(boolean enabled) {
		MainBCU.customBG = enabled;
		BGImage.reset();

		if (F != null)
			F.repaint();
	}

	// switches between the normal window and a borderless window covering the screen
	public static void setBorderless(boolean borderless) {
		MainBCU.borderless = borderless;

		if (F != null)
			F.FsetBorderless(borderless);
	}

	public static void exitAll() {
		if (mainPanel != null)
			mainPanel.exitAll();
	}

	public static Page getPanel() {
		return mainPanel;
	}

	public static void timer(int t) {
		if (mainPanel != null && !F.changingPanel)
			mainPanel.timer(t);
	}

	protected static void resized() {
		F.Fresized();
	}

	private static void setFonts(int f) {
		List<Object> ks = new ArrayList<>();
		List<Object> fr = new ArrayList<>();
		font = new Font(fontType, fontStyle, f);
		FontUIResource fontRes = new FontUIResource(font);
		for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				ks.add(key);
				fr.add(fontRes);
			}
		}
		for (int i = 0; i < ks.size(); i++)
			UIManager.put(ks.get(i), fr.get(i));
	}

	private boolean settingsize = false, changingPanel = false;

	// where the window was before it became borderless
	private Rectangle windowedBounds = null;

	public MainFrame(String ver) {
		super(Page.get(MainLocale.PAGE, "title") + " Ver " + ver);
		//setIcon();
		setLayout(null);
		addListener();
		sizer();
	}

	// Currently unused function used to officially add an icon to BCU, it currently works but will be left unused until an icon is oficially added to BCU assets
	/*private void setIcon() {
		try {
			Image icons = ImageIO.read(new File("IconDirectoryHere.png"));
			super.setIconImage(icons);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	public void initialize() {
		F = this;
		changePanel(new LoadPage());
		Fresized();
	}

	private static Rectangle screenBounds(Rectangle r) {
		Point c = new Point(r.x + r.width / 2, r.y + r.height / 2);

		for (GraphicsDevice d : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
			Rectangle b = d.getDefaultConfiguration().getBounds();

			if (b.contains(c))
				return b;
		}
		return new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	}

	private void FsetBorderless(boolean borderless) {
		if (isUndecorated() == borderless)
			return;

		Rectangle target;

		if (borderless) {
			windowedBounds = getBounds();
			target = screenBounds(windowedBounds);
		} else
			target = windowedBounds != null ? windowedBounds : crect;

		dispose();
		setUndecorated(borderless);

		if (target != null)
			setBounds(target);

		setVisible(true);
		Fresized();
	}

	public void sizer() {
		if (MainBCU.borderless)
			setUndecorated(true);

		if (crect == null) {
			PP screen = new PP(Toolkit.getDefaultToolkit().getScreenSize());
			rect = new PP(0, 0).toRectangle(new P(screen.x, screen.y));
			setBounds(rect);
			setVisible(true);
			int nx = rect.width - getRootPane().getWidth();
			int ny = rect.height - getRootPane().getHeight();
			crect = rect;
			if (nx != x || ny != y) {
				x = nx;
				y = ny;
			}
		} else {
			rect = MainBCU.borderless ? screenBounds(crect) : crect;
			setBounds(rect);
			setVisible(true);
		}
	}

	private void addListener() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				if (mainPanel != null)
					mainPanel.windowActivated();
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				JMenuItem menu = MenuBarHandler.getFileItem("Save All");
				if (menu != null)
					menu.setEnabled(false);

				if (!closeClicked) {
					boolean[] conf = Opts.confirmSave();

					if (conf != null)
						changePanel(new SavePage(conf));
					else if (menu != null)
						menu.setEnabled(true);
				} else {
					Opts.warnPop("Saving progress...\nPlease wait!", "Saving");
				}
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				if (mainPanel != null)
					mainPanel.windowDeactivated();
			}
		});

		Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
			if (event.getID() == KeyEvent.KEY_PRESSED && mainPanel != null)
				mainPanel.keyPressed((KeyEvent) event);
			if (event.getID() == KeyEvent.KEY_RELEASED && mainPanel != null)
				mainPanel.keyReleased((KeyEvent) event);
			if (event.getID() == KeyEvent.KEY_TYPED && mainPanel != null)
				mainPanel.keyTyped((KeyEvent) event);
		}, AWTEvent.KEY_EVENT_MASK);

		Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
			if (event.getID() == MouseEvent.MOUSE_PRESSED && mainPanel != null)
				mainPanel.mousePressed((MouseEvent) event);
			if (event.getID() == MouseEvent.MOUSE_RELEASED && mainPanel != null)
				mainPanel.mouseReleased((MouseEvent) event);
			if (event.getID() == MouseEvent.MOUSE_CLICKED && mainPanel != null)
				mainPanel.mouseClicked((MouseEvent) event);
		}, AWTEvent.MOUSE_EVENT_MASK);

		Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
			if (event.getID() == MouseEvent.MOUSE_MOVED && mainPanel != null)
				mainPanel.mouseMoved((MouseEvent) event);
			if (event.getID() == MouseEvent.MOUSE_DRAGGED && mainPanel != null)
				mainPanel.mouseDragged((MouseEvent) event);
		}, AWTEvent.MOUSE_MOTION_EVENT_MASK);

		Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
			if (event.getID() == MouseEvent.MOUSE_WHEEL && mainPanel != null)
				mainPanel.mouseWheel((MouseEvent) event);
		}, AWTEvent.MOUSE_WHEEL_EVENT_MASK);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent arg0) {
				setCrect();
			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				Fresized();
				setCrect();
			}
		});
	}

	private void FchangePanel(Page p) {
		if (p == null)
			return;
		changingPanel = true;
		if (mainPanel != null)
			if (p.getFront() == mainPanel)
				mainPanel.leave();
			else
				mainPanel.exit();
		add(p);
		if (mainPanel != null)
			remove(mainPanel);
		mainPanel = p;
		validate();
		p.renew();
		repaint();
		changingPanel = false;
	}

	private void Fresized() {
		if (settingsize)
			return;
		settingsize = true;
		int w = getRootPane().getWidth();
		int h = getRootPane().getHeight();
		try {
			setFonts(Page.size(w, h, fontSize));
		} catch (ConcurrentModificationException cme) {
			Printer.p("MainFrame", 217, "Failed to set Font");
		}
		if (mainPanel != null)
			mainPanel.componentResized(w, h);
		settingsize = false;
	}

	private void setCrect() {
		if (isUndecorated())
			return;
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle r = getBounds();
		if ((r.x + r.width) >= 0 && r.y >= 0 && r.x < d.width && (r.y - r.height) < d.height)
			crect = r;
	}
}
