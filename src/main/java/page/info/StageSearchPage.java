package page.info;

import common.CommonStatic;
import common.util.stage.MapColc;
import common.util.stage.SCDef.Line;
import common.util.stage.Stage;
import common.util.stage.StageMap;
import common.util.unit.Enemy;
import page.*;
import page.info.filter.EnemyFindPage;
import page.support.AnimLCR;
import utilpc.UtilPC;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StageSearchPage extends StagePage {
    private static final String[] ops = {"=", ">", "<"};
    private static final long serialVersionUID = 1L;
    private final JL mapN = new JL("Stagemap Name:");
    private final JL staN = new JL("Stage Name:");
    private final JTF mapName = new JTF();
    private final JTF stageName = new JTF();

    private final JTF HPCount = new JTF();
    private final JL baseHP = new JL(1, "ht00");
    private final JBTN greaterBaseHP = new JBTN();
    private byte HPChoice = -1;

    private final JTF WidthAmount = new JTF();
    private final JL StageWidth = new JL(1, "ht02");
    private final JBTN greaterWidth = new JBTN();
    private byte WidthChoice = -1;

    private final JBTN continuable = new JBTN();
    private byte ConChoice = -1;

    private EnemyFindPage efp;
    private final JL enemy = new JL(0, "enemy");
    private final JBTN adde = new JBTN(0,"add");
    private final JBTN reme = new JBTN(0,"rem");
    private final List<Enemy> eList = new ArrayList<>();
    private final JList<Enemy> enemies = new JList<>();
    private final JScrollPane enes = new JScrollPane(enemies);
    private final JTG eneOrop = new JTG(0,"orop");

    private final JBTN hasBoss = new JBTN();
    private byte BossChoice = -1;

    private final JBTN initSearch = new JBTN("Search");
    private boolean resultFound = false;

    private final JList<MapColc> jlmc = new JList<>();
    private final JScrollPane jspmc = new JScrollPane(jlmc);
    private final JList<StageMap> jlsm = new JList<>();
    private final JScrollPane jspsm = new JScrollPane(jlsm);
    private final JList<Stage> jlst = new JList<>();
    private final JScrollPane jspst = new JScrollPane(jlst);
    private StageMap[] stageMapsArr;
    private Stage[] stagesArr;

    public StageSearchPage(Page p) {
        super(p);
        ini();
        resized();
    }

    @Override
    protected void resized(int x, int y) {
        super.resized(x, y);
        set(strt, x, y, 400, 0, 200, 50);
        if (!resultFound) {
            set(mapN, x, y, 0, 50, 200, 50);
            set(mapName, x, y, 200, 50, 600, 50);
            set(staN, x, y, 0, 100, 200, 50);
            set(stageName, x, y, 200, 100, 600, 50);
            set(baseHP, x, y, 0, 150, 200, 50);
            set(greaterBaseHP, x, y, 200, 150, 200, 50);
            set(HPCount, x, y, 400, 150, 400, 50);
            set(StageWidth, x, y, 0, 200, 200, 50);
            set(greaterWidth, x, y, 200, 200, 200, 50);
            set(WidthAmount, x, y, 400, 200, 400, 50);
            set(continuable, x, y, 0, 250, 400, 50);
            set(hasBoss, x, y, 400, 250, 400, 50);

            set(enemy, x, y, 0, 300, 800, 50);
            set(enes, x, y, 0, 350, 800, 700);
            set(adde, x, y, 0, 1050, 250, 50);
            set(reme, x, y, 275, 1050, 250, 50);
            set(eneOrop, x, y, 550, 1050, 250, 50);

            set(initSearch, x, y, 0, 1150, 800, 50);
        } else if (jspmc.isVisible()) {
                set(jspsm, x, y, 0, 50, 400, 1150);
                set(jspmc, x, y, 400, 50, 400, 500);
                set(jspst, x, y, 400, 550, 400, 650);
        } else if (jspsm.isVisible()) {
            set(jspsm, x, y, 0, 50, 400, 1150);
            set(jspst, x, y, 400, 50, 400, 1150);
        } else
            set(jspst, x, y, 0, 50, 400, 1150);
        setVisibility();
    }

    private void setVisibility() {
        mapN.setVisible(!resultFound);
        mapName.setVisible(!resultFound);
        staN.setVisible(!resultFound);
        stageName.setVisible(!resultFound);
        baseHP.setVisible(!resultFound);
        greaterBaseHP.setVisible(!resultFound);
        HPCount.setVisible(!resultFound);
        StageWidth.setVisible(!resultFound);
        greaterWidth.setVisible(!resultFound);
        WidthAmount.setVisible(!resultFound);
        continuable.setVisible(!resultFound);
        hasBoss.setVisible(!resultFound);
        enemy.setVisible(!resultFound);
        enes.setVisible(!resultFound);
        adde.setVisible(!resultFound);
        reme.setVisible(!resultFound);
        eneOrop.setVisible(!resultFound);
        initSearch.setVisible(!resultFound);

        jspmc.setVisible(resultFound && jlmc.getModel().getSize() > 1);
        jspsm.setVisible(resultFound && stageMapsArr.length > 1);
        jspst.setVisible(resultFound);
    }

    private List<StageMap> searchSubchapter() {
        String str = mapName.getText().toLowerCase();
        List<StageMap> chaptersFound = new ArrayList<>();

        for (MapColc mc : MapColc.values())
            for (StageMap sm : mc.maps)
                if (UtilPC.damerauLevenshteinDistance(sm.toString().toLowerCase(),str) < 5)
                    chaptersFound.add(sm);
        return chaptersFound;
    }

    private List<Stage> searchStage(List<StageMap> mapsFound) {
        String str = stageName.getText().toLowerCase();
        List<Stage> stagesFound = new ArrayList<>();
        List<Integer> diffs = new ArrayList<>();
        int minDiff = 5;

        if (mapsFound.isEmpty()) {
            for (MapColc mc : MapColc.values())
                for (StageMap sm : mc.maps)
                    for (Stage s : sm.list) {
                        int diff = UtilPC.damerauLevenshteinDistance(s.toString().toLowerCase(), str);
                        if (diff <= minDiff) {
                            stagesFound.add(s);
                            diffs.add(diff);
                            minDiff = diff;
                        }
                    }
        } else
            for (StageMap sm : mapsFound)
                for (Stage s : sm.list) {
                    int diff = UtilPC.damerauLevenshteinDistance(s.toString().toLowerCase(), str);
                    if (diff <= minDiff) {
                        stagesFound.add(s);
                        diffs.add(diff);
                        minDiff = diff;
                    }
                }
        for (int i = 0; i < diffs.size(); i++) {
            if (diffs.get(i) <= minDiff)
                continue;
            diffs.remove(i);
            stagesFound.remove(i);
            i--;
        }
        return stagesFound;
    }

    private void addListeners() {

        back.removeActionListener(back.getActionListeners()[0]);
        back.addActionListener(l -> {
            if (resultFound) {
                resultFound = false;
                jlst.clearSelection();
            } else
                changePanel(getFront());
        });

        greaterBaseHP.addActionListener(l -> {
            if (HPChoice == 1)
                HPChoice = -1;
            else
                HPChoice++;
            greaterBaseHP.setText(ops[HPChoice + 1]);
        });

        greaterWidth.addActionListener(l -> {
            if (WidthChoice == 1)
                WidthChoice = -1;
            else
                WidthChoice++;
            greaterWidth.setText(ops[WidthChoice + 1]);
        });

        continuable.addActionListener(l -> {
            if (ConChoice == 1) {
                ConChoice = -1;
                continuable.setText(Page.get(1, "ht03") + ": Any");
            } else {
                ConChoice++;
                continuable.setText(Page.get(1, "ht03") + ": " + (ConChoice == 1));
            }
        });

        hasBoss.addActionListener(l -> {
            if (BossChoice == 1) {
                BossChoice = -1;
                hasBoss.setText("Has Boss: Any");
            } else {
                BossChoice++;
                hasBoss.setText("Has Boss: " + (BossChoice == 1));
            }
        });

        adde.addActionListener(l -> {
            if (efp == null)
                efp = new EnemyFindPage(getThis());
            changePanel(efp);
        });

        reme.addActionListener(l -> {
            for (Enemy e : enemies.getSelectedValuesList())
                eList.remove(e);
            enemies.setListData(eList.toArray(new Enemy[0]));
        });

        jlmc.addListSelectionListener(l -> {
            final MapColc mc = jlmc.getSelectedValue();
            if (mc == null) {
                jlsm.setListData(stageMapsArr);
                jlst.setListData(stagesArr);
            } else {
                final List<StageMap> sm = new ArrayList<>();
                for (int i = 0; i < stageMapsArr.length; i++)
                    if (stageMapsArr[i].getCont() == mc)
                        sm.add(stageMapsArr[i]);
                jlsm.setListData(sm.toArray(new StageMap[0]));
                jlsm.setSelectedIndex(0);

                final List<Stage> st = new ArrayList<>(sm.get(0).list.getList());
                final List<Stage> arr = Arrays.asList(stagesArr);
                st.removeIf(sta -> !arr.contains(sta));

                jlst.setListData(st.toArray(new Stage[0]));
                jlst.setSelectedIndex(0);
            }
        });

        jlsm.addListSelectionListener(l -> {
            final StageMap sm = jlsm.getSelectedValue();
            if (sm == null) {
                jlst.setListData(stagesArr);
            } else {
                final List<Stage> st = new ArrayList<>(sm.list.getList());
                final List<Stage> arr = Arrays.asList(stagesArr);
                st.removeIf(sta -> !arr.contains(sta));

                jlst.setListData(st.toArray(new Stage[0]));
                jlst.setSelectedIndex(0);
            }
        });

        jlst.addListSelectionListener(l -> setData(jlst.getSelectedValue()));

        initSearch.addActionListener(l -> startSearch());
    }

    @Override
    protected void renew() {
        if (efp != null && efp.getSelected() != null && !eList.contains(efp.getSelected())) {
            eList.add(efp.getSelected());
            enemies.setListData(eList.toArray(new Enemy[0]));
        }
    }

    private void ini() {
        add(mapN);
        add(staN);
        add(jspmc);
        add(jspsm);
        add(jspst);
        add(mapName);
        add(stageName);
        add(HPCount);
        add(baseHP);
        add(greaterBaseHP);
        add(WidthAmount);
        add(StageWidth);
        add(greaterWidth);
        add(continuable);
        add(hasBoss);
        add(enes);
        add(adde);
        add(reme);
        add(enemy);
        add(eneOrop);
        add(initSearch);

        greaterBaseHP.setText(ops[0]);
        greaterWidth.setText(ops[0]);
        continuable.setText(Page.get(1, "ht03") + ": Any");
        hasBoss.setText("Has Boss: Any");

        enemies.setCellRenderer(new AnimLCR());
        addListeners();
    }

    private void startSearch() {
        resultFound = false;
        List<MapColc> maps = new ArrayList<>();
        List<StageMap> chapters = new ArrayList<>();
        List<Stage> stages = new ArrayList<>();

        if (mapName.getText().length() > 0) {
            chapters.addAll(searchSubchapter());
            if (chapters.isEmpty())
                return;
        }
        if (stageName.getText().length() > 0) {
            stages.addAll(searchStage(chapters));
            if (stages.isEmpty())
                return;
        } else if (chapters.isEmpty()) {
            for (MapColc mc : MapColc.values())
                for (StageMap sm : mc.maps)
                    stages.addAll(sm.list.getList());
        } else
            for (StageMap sm : chapters)
                stages.addAll(sm.list.getList());
        if (chapters.size() > 0)
            stages.removeIf(st -> !chapters.contains(st.getCont()));

        final int BHP = CommonStatic.parseIntN(HPCount.getText());
        if (BHP > 0)
            stages.removeIf(st -> HPChoice == 1 ? st.health >= BHP : HPChoice == 0 ? st.health <= BHP : st.health != BHP);

        final int SWidth = CommonStatic.parseIntN(WidthAmount.getText());
        if (SWidth > 0)
            stages.removeIf(st -> WidthChoice == 1 ? st.len >= SWidth : WidthChoice == 0 ? st.len <= SWidth : st.len != SWidth);

        if (ConChoice != -1)
            stages.removeIf(st -> (ConChoice == 0) != st.non_con);

        if (BossChoice != -1) {
            stages.removeIf(st -> {
                boolean has = false;
                for (Line l : st.data.getSimple())
                    has |= l.boss == 1;
                return (BossChoice == 0) == has;
            });
        }

        if (!eneOrop.isSelected())
            for (int i = 0; i < eList.size(); i++) {
                final Enemy ene = eList.get(i);
                stages.removeIf(st -> !st.contains(ene));
            }
        else if (eList.size() != 0) {
            stages.removeIf(st -> {
                boolean b = false;
                for (int i = 0; i < eList.size(); i++)
                    b |= st.contains(eList.get(i));
                return !b;
            });
        }

        if (chapters.isEmpty()) {
            for (Stage s : stages)
                if (!chapters.contains(s.getCont()))
                    chapters.add(s.getCont());
        } else {
            chapters.removeIf(sm -> {
               boolean include = false;
               for (Stage st : stages) {
                   include |= st.getCont() == sm;
               }
               return !include;
            });
        }
        for (StageMap sm : chapters)
            if (!maps.contains(sm.getCont()))
                maps.add(sm.getCont());

        jlst.setListData(stagesArr = stages.toArray(new Stage[0]));
        jlsm.setListData(stageMapsArr = chapters.toArray(new StageMap[0]));
        jlmc.setListData(maps.toArray(new MapColc[0]));

        resultFound = stagesArr.length > 0;
    }
}
