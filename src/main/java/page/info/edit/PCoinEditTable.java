package page.info.edit;

import common.CommonStatic;
import common.battle.data.CustomUnit;
import common.pack.Identifier;
import common.pack.PackData;
import common.pack.UserProfile;
import common.util.Data;
import common.util.lang.ProcLang;
import common.util.unit.Trait;
import main.MainBCU;
import page.*;
import page.info.filter.TraitList;
import utilpc.Interpret;
import utilpc.Theme;
import utilpc.UtilPC;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PCoinEditTable extends Page {

    private static final long serialVersionUID = 1L;

    private static class TalentInfo {

        private final String name;
        private final int value;

        private TalentInfo(String text, int value) {
            name = text;
            this.value = value;
        }

        @Override
        public String toString() { return name; }
        private int getValue() { return value; }
    }

    private static class NPList extends JList<TalentInfo> {
        private static final long serialVersionUID = 1L;

        protected static int[] ints = IntStream.rangeClosed(1, 65)
                .filter(v -> v != 29 && v != 42 && v != 43).toArray(); // TODO: see if auto is possible

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
                        case Data.PC_TRAIT:
                            jl.setIcon(UtilPC.createIcon(3, val[1]));
                            break;
                        default:
                            System.out.println("Unknown: " + val[1]);
                            break;
                    }
                    return jl;
                }
            });
        }
    }

    protected static final int[] BASE_TALENT = new int[]{ 1, 10, 0, 0, 0, 0, 0, 0, 0, 0, 1, 8, -1, 0 }; // TODO: on increase, set 12th index to -1
    private final JTG soup = new JTG(0, "super");
    private final JBTN sett = new JBTN(0, "set");
    private final JL abil = new JL();
    private final TraitList tlst;
    private final JScrollPane jspt;
    private final NPList nlst;
    private final JScrollPane jspn;
    private final JL max = new JL(0, "maxlv");
    private final JTF maxt = new JTF();
    private final JL[] modl = new JL[4];
    private final JTF[] modt = new JTF[modl.length];
    private final CustomUnit unit;
    private final PackData.UserPack pack;
    private final PCoinEditPage pcep;
    private int ind;
    private final boolean editable;
    private boolean changing;

    public PCoinEditTable(PCoinEditPage p, CustomUnit cu, boolean edit) {
        super(p);
        pcep = p;
        unit = cu;
        pack = (PackData.UserPack) PackData.UserPack.getPack(unit.pack.uid.pack);
        editable = edit;
        tlst = new TraitList(false);
        jspt = new JScrollPane(tlst);
        nlst = new NPList(editable);
        jspn = new JScrollPane(nlst);

        ini();
    }
    @Override
    protected void resized(int x, int y) {
        set(jspn, x, y, 0, 0, 275, 600);
        set(sett, x, y, 0, 600, 200, 50);

        set(abil, x, y, 325, 0, 350, 50);
        set(max, x, y, 325, 50, 150, 50);
        set(maxt, x, y, 475, 50, 200, 50);
        for (int i = 0; i < modl.length; i++) {
            set(modl[i], x, y, 325, 100 + 50 * i, 150, 50);
            set(modt[i], x, y, 475, 100 + 50 * i, 200, 50);
        }
        set(jspt, x, y, 700, 0, 275, 600);
        set(soup, x, y, 1000, 0, 200, 50);
    }

    private void ini() {
        add(abil);
        add(soup);
        add(jspt);
        add(jspn);
        add(soup);
        add(max);
        add(maxt);
        add(sett);
        nlst.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tlst.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        for (int i = 0; i < modl.length; i++) {
            add(modl[i] = new JL());
            add(modt[i] = new JTF());
        }

        addListeners();
        setData(-1);
    }

    private void addListeners() {
        soup.addActionListener(x -> {
            if (changing)
                return;
            changing = true;
            unit.pcoin.info.get(ind)[13] = soup.isSelected() ? 1 : 0;
            pcep.resetList(ind);
            changing = false;
        });

        maxt.addActionListener(x -> {
            if (changing)
                return;
            changing = true;
            int m = CommonStatic.parseIntN(maxt.getText());
            unit.pcoin.info.get(ind)[1] = unit.pcoin.max[ind] = Math.max(m, 1);
            reset(false);
            changing = false;
        });

        for (int i = 0; i < modt.length; i++) {
            JTF mod = modt[i];
            if (changing)
                return;
            changing = true;
            int finalI = i;
            mod.addActionListener(x -> {
                if (ind == -1)
                    return;
                changing = true;
                int a, b;
                int[] mods = CommonStatic.parseIntsN(mod.getText());
                if (mods.length == 1) {
                    a = b = mods[0];
                } else if (mods.length == 2) {
                    a = Math.min(mods[0], mods[1]);
                    b = Math.max(mods[0], mods[1]);
                } else {
                    reset(false);
                    changing = false;
                    return;
                }
                int[] data = unit.pcoin.info.get(ind);
                data[2 + finalI * 2] = a;
                data[3 + finalI * 2] = b;
                reset(false);
                changing = false;
            });
            changing = false;
        }

        nlst.addListSelectionListener(x -> {
            if (changing)
                return;
            changing = true;
            TalentInfo ti = nlst.getSelectedValue();
            sett.setEnabled(ti != null && unit.pcoin.info.get(ind)[0] != ti.getValue());
            changing = false;
        });

        sett.addActionListener(x -> {
            if (nlst.getSelectedIndex() == -1 || changing)
                return;
            changing = true;
            TalentInfo ti = nlst.getSelectedValue();
            int[] base = BASE_TALENT.clone();
            base[0] = ti.getValue();
            base[1] = unit.pcoin.max[ind] = Data.PC_CORRES[base[0]][2] > 0 ? 10 : 1;
            unit.pcoin.info.set(ind, base);
            reset(true);
            changing = false;
        });

        tlst.addListSelectionListener(x -> {
            if (changing)
                return;
            changing = true;
            unit.pcoin.trait.clear();
            if ((unit.pcoin.info.get(ind)[12] = tlst.getSelectedIndex()) > -1)
                unit.pcoin.trait.addAll(tlst.getSelectedValuesList());
            resetList();
            changing = false;
        });
    }

    protected void resetList() {
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
            else if (type[0] == Data.PC_P)
                talents.add(dat);
            else if (type[0] == Data.PC_AB && (unit.abi & type[1]) == 0)
                talents.add(dat);
            else if (type[0] == Data.PC_TRAIT && unit.traits.stream().noneMatch(ut -> type[1] == ut.id.id))
                traits.add(dat);
        }
        if (unit.pcoin == null)
            talents.addAll(traits);
        else
            talents.addAll(traits.stream().filter(t -> unit.pcoin.trait.stream().noneMatch(pt -> Data.PC_CORRES[t.getValue()][1] == pt.id.id)).collect(Collectors.toList()));
        nlst.setListData(talents);

        if (unit.pcoin != null && ind != -1) {
            for (TalentInfo ti : talents) {
                if (ti.getValue() == unit.pcoin.info.get(ind)[0]) {
                    nlst.setSelectedValue(ti, true);
                    break;
                }
            }
            int[] type = Data.PC_CORRES[nlst.getSelectedValue().getValue()];
            Vector<Trait> vt = traits.stream()
                    .map(t -> Identifier.parseInt(Data.PC_CORRES[t.getValue()][1], Trait.class).get())
                    .filter(t -> type[0] != Data.PC_TRAIT || type[1] != t.id.id)
                    .collect(Collectors.toCollection(Vector::new));
            vt.addAll(pack.traits.getList());
            for (PackData.UserPack p : UserProfile.getUserPacks())
                if (pack.desc.dependency.contains(p.desc.id))
                    vt.addAll(p.traits.getList());
            vt.removeIf(t -> unit.traits.stream().anyMatch(ut -> t == ut));
            tlst.setListData(vt);
            if (unit.pcoin.info.get(ind)[12] > -1)
                tlst.setSelectedIndices(unit.pcoin.trait.stream().mapToInt(vt::indexOf).toArray());
        }
    }

    protected void setData(int i) {
        if (changing)
            return;
        changing = true;
        ind = i;
        reset(true);
        changing = false;
    }

    protected void reset(boolean full) {
        if (full) {
            pcep.resetList(ind);
            resetList();
        }

        if (ind == -1) {
            abil.setIcon(null);
            abil.setText("none");
            nlst.setEnabled(false);
            tlst.setEnabled(false);
            maxt.setEnabled(false);
            soup.setEnabled(false);
            for (JTF modif : modt)
                modif.setEnabled(false);
        } else {
            unit.pcoin.verify();
            int[] data = unit.pcoin.info.get(ind);
            int[] type = Data.PC_CORRES[data[0]];
            setLabel(type, ind);
            nlst.setEnabled(editable);
            tlst.setEnabled(editable && (unit.pcoin.info.stream().noneMatch(d -> d[12] > -1) || data[12] > -1));
            soup.setEnabled(editable);
            soup.setSelected(data[13] == 1);

            ProcLang.ItemLang lang = type[2] == 0 ? null : ProcLang.get().get(type[1]);
            maxt.setEnabled(type[2] > 0 && editable);
            maxt.setText(String.valueOf(data[1]));
            for (int i = 0; i < modl.length; i++) {
                JL label = modl[i];
                JTF modif = modt[i];
                modif.setEnabled(i < type[2] && editable);
                if (i >= type[2]) {
                    label.setText(null);
                    modif.setText(null);
                } else {
                    label.setText(type[0] == Data.PC_BASE
                            ? nlst.getSelectedValue().toString() // TODO: figure out better way to do this (proc_talent_XX.json?)
                            : lang.get(lang.list()[type[1] == Data.P_BSTHUNT ? i + 1 : i]).getNameValue());
                    modif.setText(twoInts(data[2 + i * 2], data[3 + i * 2]));
                }
            }
        }
        sett.setEnabled(false);
    }

    protected void setLabel(int[] type, int ind) {
        ImageIcon icon = null;
        if (type[0] == Data.PC_IMU || type[0] == Data.PC_P) {
            icon = UtilPC.createIcon(1, type[1]);
        } else if (type[0] == Data.PC_AB) {
            for (int i = 0; i < Data.ABI_TOT; i++) {
                if (((type[1] >> i) & 1) == 1) {
                    icon = UtilPC.createIcon(0, i);
                    break;
                }
            }
        } else if (type[0] == Data.PC_BASE) {
            icon = UtilPC.createIcon(4, type[1]);
        } else if (type[0] == Data.PC_TRAIT) {
            icon = UtilPC.createIcon(3, type[1]);
        }
        abil.setText(UtilPC.getPCoinAbilityText(unit.pcoin, ind));
        abil.setIcon(icon != null ? UtilPC.getScaledIcon(icon, 40, 40) : null);
    }

    @Override
    protected JButton getBackButton() {
        return null;
    }

    public static String twoInts(int a, int b) {
        return a + " -> " + b;
    }
}
