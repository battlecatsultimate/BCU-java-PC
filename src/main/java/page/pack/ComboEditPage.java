package page.pack;

import common.battle.BasisSet;
import common.pack.Identifier;
import common.pack.PackData;
import common.pack.UserProfile;
import common.util.stage.CharaGroup;
import common.util.unit.Combo;
import common.util.unit.Form;
import common.util.unit.Unit;
import page.JBTN;
import page.JL;
import page.JTF;
import page.Page;
import page.info.filter.UnitFindPage;
import page.support.AnimLCR;
import page.support.ReorderList;
import page.support.UnitLCR;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class ComboEditPage extends Page {

    private static final long serialVersionUID = 1L;

    private PackData.UserPack pac;
    private Unit uni;
    private Form frm;

    private final BasisSet b = BasisSet.current();

    private final Vector<PackData.UserPack> vpack = new Vector<>(UserProfile.getUserPacks());
    private final JList<PackData.UserPack> jlp = new JList<>(vpack);
    private final JScrollPane jspp = new JScrollPane(jlp);
    private final JList<Unit> jlu = new JList<>();
    private final JScrollPane jspu = new JScrollPane(jlu);
    private final ReorderList<Form> jlf = new ReorderList<>();
    private final JScrollPane jspf = new JScrollPane(jlf);
    private final ComboEditTable jlc = new ComboEditTable(this, null);
    private final JScrollPane jspc = new JScrollPane(jlc);
    private final JTF comboname = new JTF();

    private final JBTN back = new JBTN(0, "back");
    private final JBTN addf = new JBTN(0, "add");
    private final JBTN addc = new JBTN(0, "addc");
    private final JBTN remcf = new JBTN(0, "remcf");
    private final JBTN remc = new JBTN(0, "remc");
    private final JBTN form = new JBTN(0, "form");

    private final JL lbp = new JL(0, "pack");
    private final JL lbu = new JL(0, "unit");
    private final JL lbf = new JL(0, "forms");
    private final JL jlna = new JL(0, "name");

    private final JBTN group = new JBTN(0, "group");

    private UnitFindPage ufp;
    private CharaGroupPage cgp;
    private final JBTN vuif = new JBTN(0,"vuif");

    private boolean changing = false;

    protected ComboEditPage(Page p, PackData.UserPack pack) {
        super(p);

        pac = pack;
        jlc.setPack(pack);

        ini();
    }

    @Override
    protected JButton getBackButton() {
        return back;
    }

    @Override
    protected void renew() {
        if (ufp != null && ufp.getList() != null) {
            changing = true;
            List<Unit> list = new ArrayList<>();
            for (Form f : ufp.getList())
                if (!list.contains(f.unit))
                    list.add(f.unit);
            jlu.setListData(list.toArray(new Unit[0]));
            jlu.clearSelection();
            if (!list.isEmpty()) {
                changing = false;
                jlu.setSelectedIndex(0);
                changing = true;
            }
            ufp = null;
            changing = false;
        } else if (pac == null) {
            jlu.setListData(new Unit[0]);
            jlc.clearSelection();
            jlc.setPack(null);
        } else if (cgp != null) {
            CharaGroup group = cgp.cg;
            Combo combo = pac.combos.get(jlc.getSelectedRow());
            combo.group = group;
            cgp = null;
        } else {
            jlf.allowDrag(pac.editable);
            List<Unit> unis = new ArrayList<>();
            for (PackData p : UserProfile.getAllPacks())
                for (Unit u : p.units.getList())
                    if (u.id.pack.equals(Identifier.DEF) || u.id.pack.equals(pac.getSID()) || pac.desc.dependency.contains(u.id.pack))
                        unis.add(u);
            jlu.setListData(unis.toArray(new Unit[0]));
            jlu.clearSelection();
            if (!unis.isEmpty())
                jlu.setSelectedIndex(0);
            jlc.setPack(pac);
        }
    }

    private void ini() {
        add(jspp);
        add(jspu);
        add(vuif);
        add(jspf);

        add(back);
        add(addf);
        add(remc);
        
        add(lbp);
        add(lbu);
        add(lbf);

        add(addc);
        add(remcf);
        add(form);
        add(jspc);
        add(comboname);

        add(jlna);
        add(group);

        jlu.setCellRenderer(new UnitLCR());
        jlf.setCellRenderer(new AnimLCR());
        jlc.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jlc.setCellSelectionEnabled(true);

        setPack(pac);

        addListeners$0();
        addListeners$1();
    }

    private void addListeners$0() {
        back.addActionListener(x -> changePanel(getFront()));

        jlp.addListSelectionListener(arg0 -> {
            if (changing || jlp.getValueIsAdjusting())
                return;
            changing = true;
            jlc.getSelectionModel().setSelectionInterval(0, 0);
            setPack(jlp.getSelectedValue());
            updateC();
            changing = false;
        });

        vuif.addActionListener(arg0 -> {
            if (ufp == null)
                ufp = new UnitFindPage(getThis(), pac.getSID(), pac.desc.dependency);
            changePanel(ufp);
        });

        jlu.addListSelectionListener(e -> {
            if (changing || jlu.getValueIsAdjusting())
                return;
            changing = true;
            setUnit(jlu.getSelectedValue());
            changing = false;
        });

        jlf.addListSelectionListener(e -> {
            if (changing || jlf.getValueIsAdjusting())
                return;
            changing = true;
            setForm(jlf.getSelectedValue());
            changing = false;
        });

        addf.addActionListener(x -> {
            if (changing || jlf.getValueIsAdjusting())
                return;
            changing = true;
            Combo combo = pac.combos.get(jlc.getSelectedRow());
            combo.addForm(frm);
            updateC();
            changing = false;
        });

        remcf.addActionListener(x -> {
            if (changing || jlf.getValueIsAdjusting())
                return;
            changing = true;
            Combo combo = pac.combos.get(jlc.getSelectedRow());
            combo.removeForm(jlc.getSelectedColumn() - 4 >= combo.forms.length || jlc.getSelectedColumn() - 4 < 0 ? combo.forms.length - 1 : jlc.getSelectedColumn() - 4);
            updateC();
            changing = false;
        });
        
        
    }

    private void addListeners$1() {
//        jlc.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                if (changing || e.getValueIsAdjusting())
//                    return;
//                changing = true;
//                updateC();
//                changing = false;
//            }
//        });

        jlc.addMouseListener(new MouseAdapter() { // FIXME selection listener doesn't react to column change; this is the workaround
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (changing)
                    return;
                changing = true;
                updateC();
                changing = false;
            }
        });

        addc.addActionListener(x -> {
            if (changing || jlf.getValueIsAdjusting())
                return;
            changing = true;
            Identifier<Combo> id = pac.getNextID(Combo.class);
            Combo combo = new Combo(id, "new combo", 0, 0, 1, frm);
            pac.combos.add(combo);
            jlc.getSelectionModel().setSelectionInterval(0, pac.combos.indexOf(combo));
            updateC();
            changing = false;
        });

        remc.addActionListener(x -> {
            if (changing || jlf.getValueIsAdjusting())
                return;
            changing = true;
            int sel = jlc.getSelectedRow();
            Combo combo = pac.combos.get(sel);
            if (sel > 0)
                sel--;
            jlc.setRowSelectionInterval(sel, sel);
            pac.combos.remove(combo);
            updateC();
            changing = false;
        });

        form.addActionListener(x -> {
            if (changing || jlf.getValueIsAdjusting())
                return;
            changing = true;
            int row = jlc.getSelectedRow();
            Combo combo = pac.combos.get(row);
            int formPos = jlc.getSelectedColumn() - 4;
            Form f = combo.forms[formPos];
            combo.forms[formPos] = f.unit.forms[(f.fid + 1) % f.unit.forms.length];
            updateC();
            changing = false;
        });

        comboname.setLnr(x -> {
            String str = comboname.getText();
            Combo combo = pac.combos.get(jlc.getSelectedRow());
            if (combo.name.equals(str))
                return;
            if (str.equals("")) {
                comboname.setText(combo.name);
                return;
            }
            combo.name = str;
        });
        
    }

    @Override
    protected void mouseClicked(MouseEvent e) {
        if (e.getSource() == jlc && !e.isShiftDown())
            jlc.clicked(e.getPoint());
    }

    @Override
    protected void resized(int x, int y) {
        setBounds(0, 0, x, y);
        set(back, x, y, 0, 0, 200, 50);

        set(lbp, x, y, 50, 100, 400, 50);
        set(jspp, x, y, 50, 150, 400, 600);

        set(jspc, x, y, 500, 150, 1450, 450);
        set(addc, x, y, 500, 600, 200, 50);
        set(remc, x, y, 750, 600, 200, 50);
        set(form, x, y, 1000, 600, 200, 50);
        set(remcf, x, y, 1250, 600, 200, 50);

        set(lbu, x, y, 700, 700, 300, 50);
        set(jspu, x, y, 700, 750, 300, 400);
        set(vuif, x, y, 700, 1150, 300, 50);
        set(lbf, x, y, 1000, 700, 300, 50);
        set(jspf, x, y, 1000, 750, 300, 250);
        set(addf, x, y, 1000, 1000, 300, 50);

        jlc.setRowHeight(50);
        jlc.getColumnModel().getColumn(2).setPreferredWidth(size(x, y, 300));
        jlc.setPreferredWidth(x, y);
    }

    @SuppressWarnings("UnusedAssignment")
    private void setPack(PackData.UserPack pack) {
        pac = pack;
        boolean pre = changing;
        if (jlp.getSelectedValue() != pack) {
            changing = true;
            jlp.setSelectedValue(pac, true);
            changing = pre;
        }
        changing = true;
        changing = pre;
        renew();
        if (pac == null || !pac.units.contains(uni))
            uni = null;
        setUnit(uni);
    }

    @SuppressWarnings("UnusedAssignment")
    private void setUnit(Unit unit) {
        uni = unit;
        boolean pre = changing;
        if (jlu.getSelectedValue() != uni) {
            changing = true;
            jlu.setSelectedValue(uni, true);
            changing = pre;
        }
        changing = true;
        if (unit == null)
            jlf.setListData(new Form[0]);
        else
            jlf.setListData(unit.forms);
        changing = pre;
        if (frm != null && frm.unit != unit)
            frm = null;
        setForm(uni != null ? uni.forms[0] : null);
    }

    private void setForm(Form f) {
        frm = f;
        if (jlf.getSelectedValue() != frm) {
            boolean boo = changing;
            changing = true;
            jlf.setSelectedValue(frm, true);
            changing = boo;
        }
        updateC();
    }

    private void updateC() {
        addc.setEnabled(frm != null && pac.editable);
        vuif.setEnabled(pac != null && pac.editable);

        int row = jlc.getSelectedRow();
        int column = jlc.getSelectedColumn();
        boolean comboSelected = pac != null && row != -1 && pac.combos.size() != 0;
        boolean isEditable = comboSelected && pac.editable;
        boolean hasFormToAdd = isEditable && frm != null;

        if (comboSelected) {
            Combo c = pac.combos.get(row);
            comboname.setText(c.name);
        } else
            comboname.setText("");

        comboname.setEnabled(isEditable);
        form.setEnabled(isEditable && column >= 4 && column <= pac.combos.get(row).forms.length + 3);
        remc.setEnabled(isEditable);
        remcf.setEnabled(hasFormToAdd && pac.combos.get(row).forms.length > 1);
        boolean check = hasFormToAdd && Arrays.stream(pac.combos.get(row).forms).noneMatch(fr -> fr.unit == frm.unit) && pac.combos.get(row).forms.length < 5;
        addf.setEnabled(check);
    }

    @Override
    public void callBack(Object newParam) {
        super.callBack(newParam);
        if (changing)
            return;
        changing = true;
        if (newParam instanceof CharaGroupPage)
            changePanel(cgp = (CharaGroupPage) newParam);
        updateC();
        changing = false;
    }
}