package page.info.edit;

import common.CommonStatic;
import common.battle.data.CustomUnit;
import common.pack.UserProfile;
import common.util.Data;
import common.util.lang.ProcLang;
import org.jcodec.common.tools.MathUtil;
import page.*;
import utilpc.Interpret;

import javax.swing.*;

public class PCoinEditTable extends Page {

    private static final long serialVersionUID = 1L;

    private static class talentData {
        private final String name;
        private final int key;

        public talentData(String text, int ID) {
            name = text;
            key = ID;
        }

        @Override
        public String toString() { return name; }

        public int getValue() { return key; }
    }

    //ensures not every single talent is here, to avoid touching unused values, each number corresponds to a PC_CORRES array
    private final int[] allPC = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 50, 51, 52, 54, 56};

    private final CustomUnit unit;
    private final JComboBox<talentData> ctypes = new JComboBox<>();
    private final JL jPCLV = new JL(0,"Max Lv");
    private final JTF PCoinLV = new JTF();
    private final JBTN delet = new JBTN(0,"rem");
    private final PCoinEditPage pcedit;
    private final boolean editable;
    private final JL[] chance = new JL[8];
    private final JTF[] tchance = new JTF[8];
    public final int talent;

    private int cTypesY = 500;

    protected PCoinEditTable(Page p, CustomUnit u, int ind, boolean edi) {
        super(p);
        pcedit = (PCoinEditPage) p;
        unit = u;
        talent = ind;
        editable = edi;

        ini();
    }

    @Override
    protected void resized(int x, int y) {
        set(delet, x, y, 0, 0, 400, 50);
        set(jPCLV, x, y, 0, 50, 200, 50);
        set(PCoinLV, x, y, 200, 50, 200, 50);
        for (int i = 0; i < chance.length; i++) {
            set(chance[i], x, y, 0, 100 + i * 50, 200, 50);
            set(tchance[i], x, y, 200, 100 + i * 50, 200, 50);
        }
        set(ctypes, x, y, 0, cTypesY, 400, 50);
    }

    private void addListeners() {
        PCoinLV.setLnr(arg0 -> {
            String txt = PCoinLV.getText().trim();
            int v = CommonStatic.parseIntN(txt);
            unit.pcoin.info.get(talent)[1] = MathUtil.clip(v, 1, 10);
            enableSecondaries(Data.PC_CORRES[unit.pcoin.info.get(talent)[0]]);
        });

        delet.addActionListener(arg0 -> {
            unit.pcoin.info.remove(talent);
            pcedit.removed();
        });

        ctypes.addActionListener(x -> {
            talentData np = (talentData)ctypes.getSelectedItem();
            for (int[] check : unit.pcoin.info)
                if (check[0] == np.getValue()) {
                    ctypes.setSelectedItem(np);
                    return;
                }
            unit.pcoin.info.get(talent)[0] = np.getValue();
            unit.pcoin.info.get(talent)[10] = np.getValue();
            pcedit.setCoinTypes();
            setData();
        });

        for (int i = 0; i < tchance.length; i++) {
            int finalI = i + 2;
            tchance[i].setLnr(arg0 -> {
                String txt = tchance[finalI - 2].getText().trim();
                int[] v = CommonStatic.parseIntsN(txt);

                int[] vals = Data.PC_CORRES[unit.pcoin.info.get(talent)[0]];
                if (vals[0] == Data.PC_BASE && vals[1] == Data.PC2_COST)
                    v[0] = (int)(v[0] / 1.5);

                int ind = finalI % 2 == 0 ? 1 : -1;
                int w = v.length > 1 ? v[1] : unit.pcoin.info.get(talent)[finalI + ind];

                if (finalI < 4 || finalI < 6 && !(vals[1] == Data.P_SATK || vals[1] == Data.P_VOLC) || finalI < 8 && vals[1] == Data.P_WAVE || finalI > 8 && vals[1] == Data.P_VOLC) {
                    v[0] = Math.max(1, v[0]);
                    if (v.length > 1)
                        v[1] = Math.max(1, v[1]);
                }

                if (ind == 1) {
                    unit.pcoin.info.get(talent)[finalI] = Math.min(v[0], w);
                    unit.pcoin.info.get(talent)[finalI + ind] = Math.max(v[0], w);
                } else {
                    unit.pcoin.info.get(talent)[finalI] = Math.max(v[0], w);
                    unit.pcoin.info.get(talent)[finalI + ind] = Math.min(v[0], w);
                }

                setData();
            });
        }
    }

    private void ini() {
        add(delet);
        add(jPCLV);
        add(PCoinLV);
        add(ctypes);
        setCTypes(unit.pcoin != null && unit.pcoin.info.size() > talent);
        for (int i = 0; i < chance.length; i++) {
            add(chance[i] = new JL(0,i % 2 == 0 ? "Lv1 Value " + (1 + i / 2) : "Max Value " + (1 + i / 2)));
            add(tchance[i] = new JTF());
        }
        addListeners();
    }

    public void setCTypes(boolean coin) {
        if (coin) {
            for (int i : allPC) {
                int[] type = Data.PC_CORRES[i];
                talentData dat = new talentData(Interpret.PCTX[i], i);
                // Verify if another talent is using this value
                boolean unused = true;
                for (int j = 0; j < unit.pcoin.info.size(); j++)
                    if (j != talent && unit.pcoin.info.get(j)[0] == i) {
                        unused = false;
                        break;
                    }

                boolean add = type[0] == Data.PC_BASE;
                if (type[0] == Data.PC_P)
                    add = unit.getProc().getArr(type[1]).get(0) < 100;
                if (type[0] == Data.PC_AB)
                    add = (unit.abi & type[1]) == 0;
                if (type[0] == Data.PC_TRAIT)
                    add = !(unit.getTraits().contains(UserProfile.getBCData().traits.get(type[1])));
                if (add && unused) {
                    boolean duped = false;
                    for (int k = 0; k < ctypes.getItemCount(); k++)
                        if (ctypes.getItemAt(k).getValue() == i) {
                            duped = true;
                            break;
                        }
                    if (!duped)
                        ctypes.addItem(dat);
                } else {
                    ctypes.removeItem(dat);
                    ctypes.repaint(); //How do I make it remove items from the JCombobox :pain:
                }
            }
        } else
            ctypes.removeAll();
    }

    public void setData() {
        boolean pc = unit.pcoin != null && unit.pcoin.info.size() > talent;
        int[] type = pc ? Data.PC_CORRES[unit.pcoin.info.get(talent)[0]] : new int[]{-1, 0};
        PCoinLV.setEnabled(pc && editable && (type[0] == Data.PC_P || type[0] == Data.PC_BASE));
        if (!PCoinLV.isEnabled() && pc && editable)
            unit.pcoin.info.get(talent)[1] = 1;

        if (pc) {
            PCoinLV.setText("" + unit.pcoin.info.get(talent)[1]);
            int tal = unit.pcoin.info.get(talent)[0];
            for (int i = 0; i < ctypes.getItemCount(); i++)
                if (ctypes.getItemAt(i).getValue() == tal) {
                    ctypes.setSelectedIndex(i);
                    break;
                }
            enableSecondaries(type);
            for (int i = 0; i < tchance.length; i++)
                if (i >= 2 || !(type[0] == Data.PC_BASE && type[1] == Data.PC2_COST))
                    tchance[i].setText("" + unit.pcoin.info.get(talent)[2 + i]);
                else
                    tchance[i].setText("" + (int)(unit.pcoin.info.get(talent)[2 + i] * 1.5));
        }
        else {
            enableSecondaries(type);
            PCoinLV.setText("");
            for (JTF t : tchance)
                t.setText("");
        }
        ctypes.setEnabled(pc && editable);
        delet.setEnabled(pc && editable);
    }

    //Enables or disables text fields, depending on the needed values for the proc
    private void enableSecondaries(int[] pdata) {
        cTypesY = 500;
        if (pdata[0] == -1 || pdata[0] == Data.PC_AB || pdata[0] == Data.PC_TRAIT || !editable || pdata[0] == Data.PC_IMU) {
            for (JTF jtf : tchance)
                jtf.setVisible(false);
            for (JL jl : chance)
                jl.setVisible(false);
            cTypesY -= 400;
        }
        if (pdata[0] == Data.PC_BASE) {
            for (int i = 0; i < tchance.length; i++) {
                chance[i].setVisible(i < 2);
                tchance[i].setVisible(i < 2);
            }
            cTypesY -= 300;
            String text = (pdata[1] < Data.PC2_COST ? "+" : "") + ctypes.getSelectedItem().toString() + (pdata[1] < Data.PC2_SPEED ? "%" : "");
            chance[0].setText(text + " (Lv1)");
            chance[1].setText(text + " (Lv" + unit.pcoin.info.get(talent)[1] + ")");
        }
        if (pdata[0] == Data.PC_P) {
            int procChance = unit.getProc().getArr(pdata[1]).get(0);
            unit.pcoin.info.get(talent)[2] = MathUtil.clip(unit.pcoin.info.get(talent)[2], 1, 100 - procChance);
            unit.pcoin.info.get(talent)[3] = MathUtil.clip(unit.pcoin.info.get(talent)[3], 1, 100 - procChance);
            //This ensures raw proc chance + talent proc chance doesn't goes above 100%

            int maxlv = unit.pcoin.info.get(talent)[1];
            ProcLang.ItemLang lang = ProcLang.get().get(Data.Proc.getName(pdata[1]));
            String[] langText = lang.list();
            chance[0].setVisible(true);
            chance[1].setVisible(true);
            tchance[0].setVisible(true);
            tchance[1].setVisible(true);
            chance[0].setText(lang.get(langText[0]).getNameValue() + "(Lv1)");
            chance[1].setText(lang.get(langText[0]).getNameValue() + "(Lv" + maxlv + ")");

            boolean Field4 = langText.length >= 4;
            cTypesY -= Field4 ? 0 : 100;
            chance[6].setVisible(Field4);
            chance[7].setVisible(Field4);
            tchance[6].setVisible(Field4);
            tchance[7].setVisible(Field4);
            if (Field4) {
                chance[6].setText(lang.get(langText[3]).getNameValue() + "(Lv1)");
                chance[7].setText(lang.get(langText[3]).getNameValue() + "(Lv" + maxlv + ")");
            }

            boolean Field3 = langText.length >= 3;
            cTypesY -= Field3 ? 0 : 100;
            chance[4].setVisible(Field3);
            chance[5].setVisible(Field3);
            tchance[4].setVisible(Field3);
            tchance[5].setVisible(Field3);
            if (Field3) {
                chance[4].setText(lang.get(langText[2]).getNameValue() + "(Lv1)");
                chance[5].setText(lang.get(langText[2]).getNameValue() + "(Lv" + maxlv + ")");
            }

            boolean Field2 = langText.length >= 2;
            cTypesY -= Field2 ? 0 : 100;
            chance[2].setVisible(Field2);
            chance[3].setVisible(Field2);
            tchance[2].setVisible(Field2);
            tchance[3].setVisible(Field2);
            if (Field2) {
                chance[2].setText(lang.get(langText[1]).getNameValue() + "(Lv1)");
                chance[3].setText(lang.get(langText[1]).getNameValue() + "(Lv" + maxlv + ")");
            }
        }
        if (editable && pdata[0] != -1)
            for (int i = 0; i < tchance.length; i++)
                if (!tchance[i].isVisible())
                    unit.pcoin.info.get(talent)[2 + i] = 0;
                else
                    unit.pcoin.info.get(talent)[2 + i] = Math.max(0, unit.pcoin.info.get(talent)[2 + i]);
    }
}
