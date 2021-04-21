package page.debug;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import common.debug.DebugCore.Accessor;
import common.debug.DebugCore.Entry;
import common.debug.DebugCore.StaticContainer;
import common.pack.UserProfile;
import page.MainFrame;

public class DebugFrame extends JFrame {

	public static class InfoPane extends JPanel {

		public static class Pair<A, B> {

			public A a;
			public B b;

			public Pair(A a, B b) {
				this.a = a;
				this.b = b;
			}

		}

		private static final long serialVersionUID = 1L;

		public final JButton back = new JButton("back");
		public final JButton refresh = new JButton("refresh");
		public final JLabel path = new JLabel();
		public final JLabel title = new JLabel();
		public final List<Pair<JLabel, JComponent>> list = new ArrayList<>();

		public Accessor accessor;

		public InfoPane(Accessor accessor) {
			this.setLayout(new GridBagLayout());
			init(accessor);
		}

		public void init(Accessor acc) {
			accessor = acc;
			this.removeAll();
			list.clear();
			title.setText(accessor.getShortName());
			path.setText(accessor.getPath());
			List<? extends Entry> entries = accessor.getEntries();
			GridBagConstraints cons = new GridBagConstraints();
			this.add(back, set(cons, 0, 0, 1, 1));
			this.add(refresh, set(cons, 0, 1, 1, 1));
			this.add(path, set(cons, 1, 0, 2, 1));
			this.add(title, set(cons, 2, 0, 2, 1));
			back.setEnabled(accessor.getParentEntry() != null);

			set(back, (e) -> init(accessor.getParentEntry().getParentAccessor()));
			set(refresh, (e) -> init(accessor));

			int i = 3;
			for (Entry e : entries) {
				JLabel field = new JLabel(e.getEntryName());
				this.add(field, set(cons, i, 0, 1, 1));
				boolean canAccess = e.canAccess();
				if (canAccess) {
					JButton access = new JButton(e.getValueText());
					access.addActionListener((x) -> init(e.getChild()));
					this.add(access, set(cons, i, 1, 1, 1));
					list.add(new Pair<JLabel, JComponent>(field, access));
				} else {
					JLabel value = new JLabel(e.getValueText());
					this.add(value, set(cons, i, 1, 1, 1));
					list.add(new Pair<JLabel, JComponent>(field, value));
				}
				i++;
			}
			this.revalidate();
			this.repaint();
		}

		private GridBagConstraints set(GridBagConstraints cons, int x, int y, int w, int h) {
			cons.gridx = y;
			cons.gridy = x;
			cons.gridwidth = w;
			cons.gridheight = h;
			return cons;
		}

		private void set(JButton btn, ActionListener a) {
			for (ActionListener e : btn.getActionListeners())
				btn.removeActionListener(e);
			btn.addActionListener(a);
		}

	}

	private static final long serialVersionUID = 1L;

	public final StaticContainer root = new StaticContainer(UserProfile.class, MainFrame.class);
	public final InfoPane main = new InfoPane(root);
	private final JScrollPane jsp = new JScrollPane(main, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

	public DebugFrame() {
		super("Debug Console");
		setLayout(new GridLayout(1, 1));
		this.setBounds(MainFrame.crect);
		this.setFont(MainFrame.font);
		this.add(jsp);
		this.setVisible(true);
	}

}
