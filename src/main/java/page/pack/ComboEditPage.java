package page.pack;

import common.pack.PackData.UserPack;
import common.system.Node;
import common.util.unit.Form;
import page.JBTN;
import page.JTF;
import page.Page;
import page.info.filter.UnitFindPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ComboEditPage extends Page {

    private final JBTN back = new JBTN(0, "back");
    private final JBTN add = new JBTN(0, "add");
    private final JBTN rem = new JBTN(0, "remove");
    private final JBTN units = new JBTN(0, "vuif");
    private final JList<Form> ul = new JList<>();
    private final JScrollPane jspul = new JScrollPane(ul);
    private final JTF ccna = new JTF();

    private UnitFindPage ufp;
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
    protected void renew() {
        if (ufp != null) {
            List<Form> lf = ufp.getList();
            if (lf != null)
                ul.setListData(Node.deRep(lf).toArray(new Form[0]));
        }
    }

    @Override
    protected synchronized void resized(int x, int y) {
        setBounds(0, 0, x, y);
        set(back, x, y, 0, 0, 200, 50);
        set(units, x, y, 1350, 100, 200, 50);
        set(add, x, y, 50, 1000, 150, 50);
        set(rem, x, y, 200, 1000, 150, 50);
        set(ccna, x, y, 50, 900, 300, 50);
        set(jspul, x, y, 1300, 150, 300, 600);
    }

    private void ini() {
        add(back);
        add(units);
        add(add);
        add(rem);
        add(ccna);
        add(jspul);
        addBTNListeners();
    }

    private void addBTNListeners() {
        back.setLnr(x -> changePanel(getFront()));

        units.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ufp == null)
                    ufp = new UnitFindPage(getThis());
                changePanel(ufp);
            }
        });
    }
}
