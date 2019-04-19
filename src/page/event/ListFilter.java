package page.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;

import event.Displayable;
import event.EventBase;
import page.JBTN;
import page.Page;

abstract class ListFilter<T extends Displayable> extends Page {

	private static final long serialVersionUID = 1L;

	protected final URLPage page;
	protected final JBTN conf = new JBTN(0, "confirm");

	protected ListFilter(int[] datas, URLPage ep, EventBase eb) {
		super(null);

		setBorder(BorderFactory.createEtchedBorder());
		page = ep;
		ep.setTime(eb);
	}

	protected void addListeners() {
		conf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				page.updateList();
			}
		});
	}

	protected abstract int[] getDatas();

	protected abstract List<T> getList();

	protected abstract void setDatas(int[] datas);

}
