package page.support;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.ArrayList;
import java.util.List;

import page.JTF;

public class ListJtfPolicy extends FocusTraversalPolicy {

	private final List<JTF> list = new ArrayList<>();
	private boolean end = false;

	public void add(JTF jtf) {
		if (!end)
			list.add(jtf);
	}

	public void end() {
		end = true;
	}

	@Override
	public Component getComponentAfter(Container cont, Component comp) {
		int ind = list.indexOf(comp);
		if (ind == -1)
			return list.get(0);
		if (ind + 1 >= list.size())
			ind = 0;
		else
			ind++;
		JTF jtf = list.get(ind);
		if (jtf.isEnabled())
			return jtf;
		else
			return getComponentAfter(cont, jtf);
	}

	@Override
	public Component getComponentBefore(Container cont, Component comp) {
		int ind = list.indexOf(comp);
		if (ind == -1)
			return list.get(0);
		if (ind == 0)
			ind = list.size() - 1;
		else
			ind--;

		JTF jtf = list.get(ind);
		if (jtf.isEnabled())
			return jtf;
		else
			return getComponentAfter(cont, jtf);
	}

	@Override
	public Component getDefaultComponent(Container cont) {
		return null;
	}

	@Override
	public Component getFirstComponent(Container cont) {
		return list.get(0);
	}

	@Override
	public Component getLastComponent(Container aContainer) {
		return list.get(list.size() - 1);
	}

}
