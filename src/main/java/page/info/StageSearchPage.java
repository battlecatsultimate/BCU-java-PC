package page.info;

import common.util.stage.MapColc;
import common.util.stage.RandStage;
import common.util.stage.Stage;
import common.util.stage.StageMap;
import page.JBTN;
import page.JTF;
import page.Page;
import page.battle.BattleSetupPage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class StageSearchPage extends StagePage {

    private final JList<MapColc> jlmc = new JList<>();
    private final JScrollPane jspmc = new JScrollPane(jlmc);
    private final JList<Stage> jlst = new JList<>();
    private final JScrollPane jspst = new JScrollPane(jlst);
    private final JBTN dgen = new JBTN(0, "dungeon");
    private final JTF search = new JTF();

    public StageSearchPage (Page p, int ind) {
        super(p);
        jlmc.setListData(new Vector<>(MapColc.values()));
        jlmc.setSelectedIndex(ind);

        ini();
        resized();
    }

    @Override
    protected void resized(int x, int y) {
        super.resized(x, y);
        set(search, x, y, 0, 50, 800, 50);
        set(jspmc, x, y, 0, 100, 800, 600);
        set(jspst, x, y, 0, 700, 800, 600);
        set(dgen, x, y, 600, 0, 200, 50);
        set(strt, x, y, 400, 0, 200, 50);
    }

    private void searchStage(String str, MapColc mc) {
        if (str.length() > 0) {
            List<Stage> results = new ArrayList<>();
            if (mc == null) {
                for (int i = 0; i < jlmc.getModel().getSize(); i++)
                    for (StageMap stMap : jlmc.getModel().getElementAt(i).maps)
                        for (Stage s : stMap.list)
                            if (s.toString().contains(str))
                                results.add(s);
            } else
                for (StageMap stMap : mc.maps)
                    for (Stage s : stMap.list)
                        if (s.toString().contains(str))
                            results.add(s);
            Stage[] st = new Stage[results.size()];
            for (int i = 0; i < st.length; i++)
                st[i] = results.get(i);
            jlst.setListData(st);
        } else
            jlst.setListData(new Stage[0]);

        dgen.setLnr(x -> {
            Stage st = jlst.getSelectedValue();
            if (st == null)
                changePanel(new StageRandPage(getThis(), jlmc.getSelectedValue()));
            else {
                Stage s = RandStage.getStage(st.getCont());
                changePanel(new BattleSetupPage(getThis(), s, 0));
            }
        });
    }

    private void addListeners() {
        jlmc.addListSelectionListener(arg0 -> {
            searchStage(search.getText(), jlmc.getSelectedValue());
            jlst.setSelectedValue(0, true);
        });
        jlst.addListSelectionListener(arg0 -> super.setData(jlst.getSelectedValue()));
        search.setLnr(c -> searchStage(search.getText(), jlmc.getSelectedValue()));
    }

    private void ini() {
        add(jspmc);
        add(jspst);
        add(search);
        add(dgen);
        addListeners();
    }
}
