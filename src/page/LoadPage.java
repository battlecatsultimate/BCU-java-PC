package page;

import java.util.function.Consumer;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import io.Progress;

public class LoadPage extends Page implements Consumer<Progress> {

	private static final long serialVersionUID = 1L;

	public static LoadPage lp;

	public static void prog(String str) {
		if (lp != null)
			lp.set(str);
	}

	private JLabel jl = new JLabel();
	private JProgressBar jpb = new JProgressBar();

	private String temp;

	protected LoadPage() {
		super(null);
		lp = this;

		add(jl);
		add(jpb);
		resized();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(jl, x, y, 100, 500, 2000, 50);
		set(jpb, x, y, 100, 600, 2100, 50);
	}

	@Override
	protected void timer(int t) {
		if (temp != null) {
			jl.setText(temp);
			temp = null;
			jpb.setValue(0);
		}
	}

	private void set(String str) {
		temp = str;
	}

	@Override
	public void accept(Progress dl) {
		jpb.setMaximum(1000);
		jpb.setValue((int) (dl.prog * 1000));
	}

}
