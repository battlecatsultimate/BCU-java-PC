package page;

import main.MainBCU;
import utilpc.PP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

public abstract class Page extends JPanel implements RetFunc {

	private static final long serialVersionUID = 1L;

	public static Color BGCOLOR = MainBCU.light || !MainBCU.nimbus ? new Color(255, 255, 255) : new Color(54, 54, 54);

	static {
		ToolTipManager.sharedInstance().setInitialDelay(100);
	}

	public static String get(int i, String s) {
		return MainLocale.getLoc(i, s);
	}

	public static String[] get(int i, String s, int m) {
		return MainLocale.getLoc(i, s, m);
	}

	public static void renewLoc(Page p) {
		MainLocale.redefine();
		while (p != null) {
			for (Component c : p.getComponents())
				if (c instanceof LocComp)
					((LocComp) c).reLoc();
			p.renew();
			p = p.front;
		}
	}

	public static void set(Component jc, int winx, int winy, int x, int y, int w, int h) {
		jc.setBounds(x * winx / 2300, y * winy / 1300, w * winx / 2300, h * winy / 1300);
	}

	protected static int size(int x, int y, int a) {
		return Math.min(a * x / 2300, a * y / 1300);
	}

	protected static PP size(int x, int y, int a, int v) {
		return new PP(a * x / 2300.0, v * y / 1300.0);
	}

	protected final Page front;

	private final List<Page> subPages = new ArrayList<>();
	private final Set<Field> accumulatedTables = new HashSet<>();

	private boolean resizing = false;
	public boolean needResize = true;

	private int adjusting;
	private PP previousDimension = getXY();

	protected Page(Page p) {
		System.out.println("==========");
		front = p;
		setBackground(BGCOLOR);
		setLayout(null);
		accumulateJTable(this.getClass());
	}

	private void accumulateJTable(Class<?> cls) {
		Field[] fields = cls.getDeclaredFields();

		for (int i = 0; i < cls.getDeclaredFields().length; i++) {
			System.out.println(fields[i].getName() + " = " + fields[i].getType());

			if (JTable.class.isAssignableFrom(fields[i].getType())) {
				accumulatedTables.add(fields[i]);
			}
		}

		if (cls.getSuperclass() != null && Page.class.isAssignableFrom(cls.getSuperclass()))
			accumulateJTable(cls.getSuperclass());
	}

	@Override
	public Component add(Component c) {
		if (c instanceof CustomComp) {
			CustomComp cc = (CustomComp) c;
			cc.added(this);
		}

		needResize = true;

		return super.add(c);
	}

	@Override
	public void callBack(Object newParam) {
	}

	public void assignSubPage(Page... pages) {
		if (pages == null)
			return;

		for(int i = 0; i < pages.length; i++) {
			Page page = pages[i];

			if (page == null)
				continue;

			if (!subPages.contains(page)) {
				subPages.add(page);
			}
		}
	}

	public synchronized final void componentResized(int x, int y) {
		if (resizing)
			return;
		resizing = true;
		int barHeight = MenuBarHandler.getBar().getHeight();
		resized(x, y - barHeight);
		if (front != null)
			front.componentResized(x, y);
		Component[] cs = getComponents();
		for (Component c : cs) {
			if (c instanceof Page)
				((Page) c).componentResized(x, y);
			fontSetter(c);
		}
		repaint();
		resizing = false;
	}

	public final Page getFront() {
		if (front != null) {
			front.setBackground(BGCOLOR);
		}
		return front;
	}

	public final PP getXY() {
		JRootPane jrp = MainFrame.F.getRootPane();
		return new PP(jrp.getWidth(), jrp.getHeight());
	}

	public boolean isAdj() {
		return adjusting > 0;
	}

	public void fireDimensionChanged() {
		needResize = true;
	}

	@Override
	public void remove(Component comp) {
		super.remove(comp);
		needResize = true;
	}

	/**
	 * When UI components must be resized
	 * This "must" not be used for updating UI
	 */
	private void resized() {
		PP dimension = getXY();

		if (!needResize && dimension.equals(previousDimension))
			return;

		needResize = false;

		Point p = dimension.toPoint();
		componentResized(p.x, p.y);
		previousDimension = dimension;

		for(int i = 0; i < subPages.size(); i++) {
			subPages.get(i).resized();
		}
	}

	protected void change(boolean b) {
		adjusting += b ? 1 : -1;
	}

	protected <T> void change(T t, Consumer<T> c) {
		adjusting++;
		c.accept(t);
		adjusting--;
	}

	protected synchronized void changePanel(Page p) {
		MainFrame.changePanel(p);
	}

	protected void exit() {
	}

	protected final void exitAll() {
		exit();
		if (front != null)
			front.exitAll();
	}

	protected final Page getRootPage() {
		return front == null ? this : front.getRootPage();
	}

	protected final Page getThis() {
		return this;
	}

	protected void keyPressed(KeyEvent e) {
	}

	protected void keyReleased(KeyEvent e) {
	}

	protected void keyTyped(KeyEvent e) {
	}

	protected void leave() {
	}

	protected void mouseClicked(MouseEvent e) {
	}

	protected void mouseDragged(MouseEvent e) {
	}

	protected void mouseMoved(MouseEvent e) {
	}

	protected void mousePressed(MouseEvent e) {
	}

	protected void mouseReleased(MouseEvent e) {
	}

	protected void mouseWheel(MouseEvent e) {
	}

	protected void renew() {
	}

	protected abstract void resized(int x, int y);

	protected abstract JButton getBackButton();

	public synchronized final void timer(int t) {
		resized();

		//Revalidate components
		updateTableFromPage(this);

		for(int i = 0; i < subPages.size(); i++) {
			updateTableFromPage(subPages.get(i));
		}

		onTimer(t);
	}

	private synchronized void updateTableFromPage(Page p) {
		try {
			for(Field field : accumulatedTables) {
				if (!field.isAccessible()) {
					field.setAccessible(true);
				}

				JTable result = (JTable) field.get(p);

				result.revalidate();
				result.repaint();
			}
		} catch (Exception ignored) {

		}
	}

	public synchronized void onTimer(int t) {

	}

	protected void windowActivated() {
	}

	protected void windowDeactivated() {
	}

	private void fontSetter(Component c) {
		if (c == null)
			return;
		c.setFont(MainFrame.font);
		if (c instanceof JScrollPane) {
			JScrollPane jsp = (JScrollPane) c;
			fontSetter(jsp.getViewport().getView());
		} else if (c instanceof JTable) {
			JTable t = (JTable) c;
			fontSetter(t.getTableHeader());
		} else if (c instanceof Container) {
			Container con = (Container) c;
			for (Component ic : con.getComponents())
				fontSetter(ic);

		}
	}

}
