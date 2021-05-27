package page.pack;

import common.pack.PackData;
import page.JBTN;
import page.Page;

public class SoulEditPage extends Page {

    private static final long serialVersionUID = 1L;
    private final JBTN back = new JBTN(0, "back");

    public SoulEditPage(Page p, PackData.UserPack pac) {
        super(p);

        ini();
        resized();
    }

    @Override
    protected void resized(int x, int y) {
        setBounds(0, 0, x, y);
        set(back, x, y, 0, 0, 200, 50);
    }

    private void addListeners() {
        back.setLnr(x -> changePanel(getFront()));
    }

    private void ini() {
        add(back);
        addListeners();
    }
}
