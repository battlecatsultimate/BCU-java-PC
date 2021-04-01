package page.pack;

import common.pack.PackData.UserPack;
import page.JBTN;
import page.Page;

public class TraitEditPage extends Page {

    private final String pack;

    private final JBTN back = new JBTN(0, "back");
    private final JBTN addcg = new JBTN(0, "add");
    private final JBTN remcg = new JBTN(0, "rem");
    private final JBTN altrg = new JBTN(0, "Anti-Traited Affected");

    public TraitEditPage(Page p, UserPack pac) {
        super(p);
        pack = pac.desc.id;
        ini();
    }

    @Override
    protected synchronized void resized(int x, int y) {
        setBounds(0, 0, x, y);
        set(back, x, y, 0, 0, 200, 50);
    }

    private void addListeners$0() {

        back.setLnr(x -> changePanel(getFront()));

    }

    private void ini() {
        add(back);
        addListeners$0();
    }
}