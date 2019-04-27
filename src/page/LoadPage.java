package page;

import javax.swing.JLabel;

public class LoadPage extends Page {

	private static final long serialVersionUID = 1L;

	public static final String[] strs = new String[] {
			"update information (2/8 and 3/8 will take a long time if it's your first time opening BCU)", "file list",
			"files", "units", "enemies", "stages and imgs", "others", "custom data", "pack", "music", "finish" };

	public static int prog = 0, num = 0;

	private static LoadPage lp;

	public static void prog(int am, int tot, int pr) {
		if (lp == null)
			return;
		prog += am;
		lp.jl.setText("reading " + strs[prog] + ": " + (num = pr) + "/" + tot);
	}

	private JLabel jl = new JLabel("reading " + strs[0]);

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
