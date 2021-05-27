package page.pack;

import common.pack.PackData;
import common.pack.UserProfile;
import common.util.pack.Soul;
import page.JBTN;
import page.JL;
import page.Page;

import javax.swing.*;
import java.util.Vector;

public class SoulEditPage extends Page {

    private static final long serialVersionUID = 1L;
    private final Vector<PackData.UserPack> vpack = new Vector<>(UserProfile.getUserPacks());
    private final JList<PackData.UserPack> jlp = new JList<>(vpack);
    private final JScrollPane jspp = new JScrollPane(jlp);
    private final JList<Soul> jls = new JList<>();
    private final JScrollPane jsps = new JScrollPane(jls);

    private final JBTN back = new JBTN(0, "back");

    private final JL lbp = new JL(0, "pack");
    private final JL lbs = new JL(0, "soul");

    private PackData.UserPack pac;
    private Soul soul;
    private boolean changing = false;

    public SoulEditPage(Page p, PackData.UserPack pack) {
        super(p);

        ini(pack);
        resized();
    }

    @Override
    protected void renew() {
        setPack(pac);
    }

    @Override
    protected void resized(int x, int y) {
        setBounds(0, 0, x, y);
        set(back, x, y, 0, 0, 200, 50);

        int w = 50;

        set(lbp, x, y, w, 100, 400, 50);
        set(jspp, x, y, w, 150, 400, 600);

        w += 450;

        set(lbs, x, y, w, 100, 300, 50);
        set(jsps, x, y, w, 150, 300, 600);
    }

    private void addListeners() {
        back.setLnr(x -> changePanel(getFront()));
    }

    private void ini(PackData.UserPack pack) {
        add(back);

        add(lbp);
        add(jspp);
        add(lbs);
        add(jsps);

        setPack(pack);
        addListeners();
    }

    private void setPack(PackData.UserPack pack) {
        pac = pack;
        if (jlp.getSelectedValue() != pack) {
            boolean boo = changing;
            changing = true;
            jlp.setSelectedValue(pac, true);
            changing = boo;
        }
    };
}
