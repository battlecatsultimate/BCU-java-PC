package page.info.edit;

import common.battle.data.CustomUnit;
import common.util.Data;
import common.util.lang.ProcLang;
import main.MainBCU;
import page.JL;
import page.Page;
import page.info.filter.TraitList;
import utilpc.Interpret;
import utilpc.Theme;
import utilpc.UtilPC;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import java.util.stream.IntStream;

public class PCoinEditTable2 extends Page {

    private static final long serialVersionUID = 1L;

    private static class TalentInfo {

        private final String name;
        private final int key;

        private TalentInfo(String text, int ID) {
            name = text;
            key = ID;
        }

        @Override
        public String toString() { return name; }
        private int getValue() { return key; }
    }

    private static class NPList extends JList<TalentInfo> {
        private static final long serialVersionUID = 1L;

        protected static int[] ints = IntStream.rangeClosed(1, 60).toArray();

        protected NPList(boolean edit) {
            if (MainBCU.nimbus)
                setSelectionBackground(MainBCU.light ? Theme.LIGHT.NIMBUS_SELECT_BG : Theme.DARK.NIMBUS_SELECT_BG);
            setEnabled(edit);
            setListIcons();
        }
        protected void setListIcons() {
            setCellRenderer(new DefaultListCellRenderer() {

                private static final long serialVersionUID = 1L;

                @Override
                public Component getListCellRendererComponent(JList<?> l, Object o, int ind, boolean s, boolean f) {
                    JLabel jl = (JLabel) super.getListCellRendererComponent(l, o, ind, s, f);
                    TalentInfo td = (TalentInfo) o;
                    int[] val = Data.PC_CORRES[td.getValue()];
                    switch (val[0]) {
                        case Data.PC_AB:
                            for (int i = 0; i < Data.ABI_TOT; i++)
                                if (((val[1] >> i) & 1) == 1) {
                                    jl.setIcon(UtilPC.createIcon(0, i));
                                }
                            break;
                        case Data.PC_P:
                        case Data.PC_IMU:
                            jl.setIcon(UtilPC.createIcon(1, val[1]));
                            break;
                        case Data.PC_BASE:
                            jl.setIcon(UtilPC.createIcon(4, val[1]));
                            break;
                    }
                    return jl;
                }
            });
        }
    }

    private final JL abil = new JL();
    private final CustomUnit unit;
    private int ind;
    private final TraitList tlst;
    private final JScrollPane jspt;
    private final NPList nlst;
    private final JScrollPane jspn;
    private final boolean editable;
    private boolean changing;

    public PCoinEditTable2(Page p, CustomUnit cu, boolean edit) {
        super(p);
        unit = cu;
        editable = edit;
        tlst = new TraitList(editable);
        jspt = new JScrollPane(tlst);
        nlst = new NPList(editable);
        jspn = new JScrollPane(nlst);

        ini();
    }
    @Override
    protected void resized(int x, int y) {
        set(abil, x, y, 0, 0, 450, 50);
        set(jspn, x, y, 0, 50, 225, 600);
        set(jspt, x, y, 225, 50, 225, 600);
    }

    private void ini() {
        add(abil);
        add(jspt);
        add(jspn);
//        abil.setLayout(new BoxLayout(abil, BoxLayout.X_AXIS));
        abil.setHorizontalAlignment(SwingConstants.LEFT);
        abil.setHorizontalTextPosition(SwingConstants.RIGHT);

        setData(-1);
    }

    protected void setTalentList() {
        Vector<TalentInfo> traits = new Vector<>();
        Vector<TalentInfo> talents = new Vector<>();
        for (int i : NPList.ints) {
            int[] type = Data.PC_CORRES[i];
            TalentInfo dat = new TalentInfo(Interpret.PCTX[i], i);
            if (talents.contains(dat) || unit.pcoin == null)
                break;
            if (unit.pcoin.info.stream().anyMatch(v -> unit.pcoin.info.indexOf(v) != ind && v[0] == i))
                continue;

            if (type[0] == Data.PC_BASE)
                talents.add(dat);
            else if (type[0] == Data.PC_P && unit.getProc().getArr(type[1]).get(0) < 100)
                talents.add(dat);
            else if (type[0] == Data.PC_AB && (unit.abi & type[1]) == 0)
                talents.add(dat);
            else if (type[0] == Data.PC_TRAIT)
                traits.add(dat);
        }
        nlst.setListData(talents);
    }

    protected void setData(int i) {
        if (changing)
            return;
        changing = true;
        ind = i;
        reset();
        changing = false;
    }

    protected void reset() {
        if (ind == -1) {
            abil.setIcon(null);
            abil.setText("none");
        } else {
            int[] type = Data.PC_CORRES[unit.pcoin.info.get(ind)[0]];
            setLabel(type);
        }
        setTalentList();
    }

    protected void setLabel(int[] type) {
        String text = null;
        ImageIcon icon = null;
        if (type == null) {
            text = "unknown";
        } else if (type[0] == Data.PC_IMU || type[0] == Data.PC_P) {
            text = ProcLang.get().get(type[1]).full_name;
            icon = UtilPC.createIcon(1, type[1]);
        } else if (type[0] == Data.PC_AB) {
            for (int i = 0; i < Data.ABI_TOT; i++) {
                if (((type[1] >> i) & 1) == 1) {
                    text = Interpret.SABIS[i];
                    icon = UtilPC.createIcon(0, i);
                    break;
                }
            }
        } else if (type[0] == Data.PC_BASE) {
            text = "TBA";
//            text = nlst.getSelectedValue().toString() + (pdata[1] <= Data.PC2_SPEED || pdata[1] == Data.PC2_HB ? "+ " : "") + (pdata[1] <= Data.PC2_ATK ? "%" : "");
//            pCoin.setIcon(UtilPC.createIcon(4, pdata[1]));
        }
        abil.setText(text);
        abil.setIcon(UtilPC.getScaledIcon(icon, 40, 40));
    }

    @Override
    protected JButton getBackButton() {
        return null;
    }
}
