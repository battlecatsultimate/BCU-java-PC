package page;

import common.CommonStatic.FakeKey;

import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.TreeMap;

import static java.awt.event.KeyEvent.*;

public abstract class KeyHandler extends Page implements FakeKey {

	private static final long serialVersionUID = 1L;

	protected Map<Integer, Integer> press = new TreeMap<>();

	protected int[][] slots = new int[][] { { VK_Q, VK_W, VK_E, VK_R, VK_T }, { VK_A, VK_S, VK_D, VK_F, VK_G },
			{ VK_Z, VK_X, VK_C, VK_V, VK_B } };
	protected int[] act = new int[] { VK_1, VK_2, VK_3 };

	protected int[] lock = new int[] { VK_SHIFT };

	protected KeyHandler(Page p) {
		super(p);
	}

	@Override
	public synchronized boolean pressed(int type, int slot) {
		int key = (type == -2 ? lock : type == -1 ? act : slots[type])[slot];
		return press.containsKey(key) && press.get(key) != 2;
	}

	@Override
	public synchronized void remove(int type, int slot) {
		int key = (type == -2 ? lock : type == -1 ? act : slots[type])[slot];
		press.put(key, 2);
	}

	@Override
	protected synchronized void keyPressed(KeyEvent e) {
		press.put(e.getKeyCode(), 0);
	}

	@Override
	protected synchronized void keyReleased(KeyEvent e) {
		int val = e.getKeyCode();
		if (press.containsKey(val) && press.get(val) != 2)
			press.put(e.getKeyCode(), 1);
	}

	protected synchronized void updateKey() {
		for (int val : press.keySet())
			if (press.get(val) == 1)
				press.put(val, 2);
	}

}
