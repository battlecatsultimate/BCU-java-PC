package page;

import javax.swing.JLabel;

public class LoadPage extends Page {

	private static final long serialVersionUID = 1L;

	private static LoadPage lp;

	public static void prog(String str) {
		if (lp == null)
			return;
		lp.jl.setText(str);
	}

	private JLabel jl = new JLabel("reading ");

	protected LoadPage() {
		super(null);
		lp = this;

		add(jl);
		resized();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(jl, x, y, 100, 500, 2000, 50);
	}

}
