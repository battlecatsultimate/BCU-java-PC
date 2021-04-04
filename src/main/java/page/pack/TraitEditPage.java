package page.pack;

import common.CommonStatic;
import common.battle.data.CustomEnemy;
import common.battle.data.CustomUnit;
import common.util.unit.Form;
import common.util.unit.Enemy;
import common.util.unit.Unit;
import common.pack.PackData.UserPack;
import common.util.unit.CustomTrait;
import common.pack.FixIndexList.FixIndexMap;
import page.JBTN;
import page.JTF;
import page.JTG;
import page.Page;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TraitEditPage extends Page {

    private final String pack;

    private final JList<CustomTrait> jlct = new JList<>();
    private final JScrollPane jspct = new JScrollPane(jlct);


    private final JBTN back = new JBTN(0, "back");
    private final JBTN addct = new JBTN(0, "add");
    private final JBTN remct = new JBTN(0, "rem");
    private final JTG altrg = new JTG(0, "affected by anti-traited");
    private final JTF ctrna = new JTF();

    private final UserPack packpack;
    private final FixIndexMap<CustomTrait> pct;

    private boolean changing = false;
    private CustomTrait ct;

    public TraitEditPage(Page p, UserPack pac) {
        super(p);
        pack = pac.desc.id;
        packpack = pac;
        pct = pac.diyTrait;
        ini();
    }

    @Override
    protected synchronized void resized(int x, int y) {
        setBounds(0, 0, x, y);
        set(back, x, y, 0, 0, 200, 50);
        set(addct, x, y, 50, 1000, 150, 50);
        set(remct, x, y, 200, 1000, 150, 50);
        set(jspct, x, y, 50, 100, 300, 800);
        set(altrg, x, y, 50, 950, 300, 50);
        set(ctrna, x, y, 50, 900, 300, 50);
    }

    private void addListeners$0() {

        back.setLnr(x -> changePanel(getFront()));

    }

    private void addListeners$CG() {
        addct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                changing = true;
                ct = new CustomTrait(packpack.getNextID(CustomTrait.class));
                pct.add(ct);
                updateCTL();
                jlct.setSelectedValue(ct, true);
                changing = false;
            }
        });
        remct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (ct == null)
                    return;
                changing = true;
                List<CustomTrait> list = pct.getList();
                int ind = list.indexOf(ct) - 1;
                if (ind < 0 && list.size() > 1)
                    ind = 0;
                list.remove(ct);
                pct.remove(ct);
                if (ind >= 0)
                    ct = list.get(ind);
                else
                    ct = null;
                updateCTL();
                changing = false;
            }
        });
        altrg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (ct == null)
                    return;
                changing = true;
                if (ct.targetType)
                    ct.targetType = false;
                else
                    ct.targetType = true;
                updateCTL();
                changing = false;
            }
        });
        jlct.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (changing || jlct.getValueIsAdjusting())
                    return;
                changing = true;
                ct = jlct.getSelectedValue();
                updateCT();
                changing = false;
            }

        });

        ctrna.setLnr(x -> {
            String str = ctrna.getText();
            if (ct.name.equals(str))
                return;
            if (str.equals("")) {
                ctrna.setText(ct.name);
                return;
            }
            ct.name = str;
        });
    }

    private void updateCTL() {
        jlct.setListData(pct.toArray());
        jlct.setSelectedValue(ct, true);
        updateCT();
    }

    private void updateCT() {
        altrg.setEnabled(ct != null);
        remct.setEnabled(ct != null && !isUsedTrait(ct));
        ctrna.setEnabled(ct != null);
        ctrna.setText("");
        if (ct != null) {
            ctrna.setText(ct.name);
            altrg.setSelected(ct.targetType);
        }
    }

    private boolean isUsedTrait(CustomTrait ct) {
        for (Enemy en : packpack.enemies.getList())
            if (((CustomEnemy)en.de).customTraits.contains(ct.id))
                return true;
        for (Unit un : packpack.units.getList())
            for (Form uf : un.forms)
                if (((CustomUnit)uf.du).customTraits.contains(ct.id))
                    return true;
        return false;
    }

    private void ini() {
        add(back);
        add(addct);
        add(remct);
        add(jspct);
        add(altrg);
        add(ctrna);
        addListeners$0();
        addListeners$CG();
        updateCTL();
    }
}