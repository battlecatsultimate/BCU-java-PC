package page.pack;

import common.pack.PackData.UserPack;
import page.JBTN;
import page.Page;

public class ComboEditPage extends Page {

    private final JBTN back = new JBTN(0, "back");

    private boolean changing = false;
    private final UserPack pack;
    private final boolean editable;

    public ComboEditPage(Page p, UserPack pac) {
        super(p);
        pack = pac;
        editable = pac.editable;
        ini();
    }

    @Override
    protected synchronized void resized(int x, int y) {
        setBounds(0, 0, x, y);
        set(back, x, y, 0, 0, 200, 50);
    }

    private void ini() {
        add(back);
        addBTNListeners();
    }

    private void addBTNListeners() {
        back.setLnr(x -> changePanel(getFront()));
    }
}
