package page.info.edit;

import common.util.unit.Form;
import page.JBTN;
import page.Page;

public class PCoinEditPage extends Page {

    private static final long serialVersionUID = 1L;

    private final JBTN back = new JBTN(0, "back");
    private final JBTN addP = new JBTN(0, "Add Talent");
    private final JBTN remP = new JBTN(0, "Remove Talent");
    private final Form uni;

    public PCoinEditPage(Page p, Form u) {
        super(p);
        uni = u;

        ini();
        resized();
    }

    @Override
    protected void resized(int x, int y) {
        setBounds(0, 0, x, y);
        set(back, x, y, 0, 0, 200, 50);
    }

    private void addListeners() {
        back.addActionListener(arg0 -> changePanel(getFront()));
    }

    private void ini() {
        add(back);
        addListeners();
    }
}
