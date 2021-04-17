package page.pack;

import common.battle.BasisSet;
import common.pack.Identifier;
import common.pack.PackData;
import common.pack.UserProfile;
import common.util.unit.Combo;
import common.util.unit.Form;
import common.util.unit.Unit;
import page.JBTN;
import page.JL;
import page.Page;
import page.basis.ComboListTable;
import page.support.AnimLCR;
import page.support.ReorderList;
import page.support.UnitLCR;
import utilpc.Interpret;

import javax.swing.*;
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
    private final ComboListTable jlc = new ComboListTable(this, b.sele.lu);
    private final JScrollPane jspc = new JScrollPane(jlc);
    private final JComboBox<String> ctypes = new JComboBox<>(Interpret.getComboFilter(0));
    private final JComboBox<String> clvls = new JComboBox<>(new String[] { "Sm", "M", "L", "XL" });

    private final JBTN back = new JBTN(0, "back");
    private final JBTN addf = new JBTN(0, "add");
    private final JBTN addc = new JBTN(0, "add combo");
    private final JBTN remcf = new JBTN(0, "remove form");

    private final JL lbp = new JL(0, "pack");
    private final JL lbu = new JL(0, "unit");
    private final JL lbf = new JL(1, "forms");

    private boolean changing = false;

    protected ComboEditPage(Page p, PackData.UserPack pack) {
        super(p);

        pac = pack;

        ini();
        resized();
    }

    private void ini() {
        add(jspp);
        add(jspu);
        add(jspf);

        add(back);
        add(addf);

        add(lbp);
        add(lbu);
        add(lbf);

        add(addc);
        add(remcf);
        add(ctypes);
        add(clvls);
        add(jspc);

        jlu.setCellRenderer(new UnitLCR());
        jlf.setCellRenderer(new AnimLCR());
        jlc.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ctypes.setEnabled(false);
        clvls.setEnabled(false);

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
            Combo combo = jlc.list.get(jlc.getSelectedRow());
            combo.addForm(frm);
            updateC();
            changing = false;
        });
    }

    private void addListeners$1() {
        jlc.getSelectionModel().addListSelectionListener(x -> {
            if (changing || x.getValueIsAdjusting())
                return;
            changing = true;
            updateC();
            changing = false;
        });

        addc.addActionListener(x -> {
            if (changing || jlf.getValueIsAdjusting())
                return;
            changing = true;
            Combo combo = new Combo(pac.getNextID(Combo.class), "new combo", 0, 0, 1, frm);
            pac.combos.add(combo);
            jlc.setList(pac.combos.getRawList());
            jlc.getSelectionModel().setSelectionInterval(0, pac.combos.indexOf(combo));
            updateC();
            changing = false;
        });

        ctypes.addActionListener(x -> {
            if (changing || jlf.getValueIsAdjusting())
                return;
            changing = true;
            Combo combo = jlc.list.get(jlc.getSelectedRow());
            combo.setType(ctypes.getSelectedIndex());
            changing = false;
        });

        clvls.addActionListener(x -> {
            if (changing || jlf.getValueIsAdjusting())
                return;
            changing = true;
            Combo combo = jlc.list.get(jlc.getSelectedRow());
            combo.setLv(clvls.getSelectedIndex());
            changing = false;
        });

        remcf.addActionListener(x -> {
            if (changing || jlf.getValueIsAdjusting())
                return;
            changing = true;
            Combo combo = jlc.list.get(jlc.getSelectedRow());
            combo.removeForm(combo.forms.length - 1);
            updateC();
            changing = false;
        });
    }

    @Override
    protected void resized(int x, int y) {
        setBounds(0, 0, x, y);
        set(back, x, y, 0, 0, 200, 50);

        set(lbp, x, y, 50, 100, 400, 50);
        set(jspp, x, y, 50, 150, 400, 600);

        set(lbu, x, y, 500, 100, 300, 50);
        set(jspu, x, y, 500, 150, 300, 600);

        set(lbf, x, y, 800, 100, 300, 50);
        set(jspf, x, y, 800, 150, 300, 600);
        set(addf, x, y, 800, 750, 300, 50);

        set(jspc, x, y, 50, 800, 1250, 450);
        set(addc, x, y, 1300, 800, 300, 50);
        set(ctypes, x, y, 1300, 850, 450, 50);
        set(clvls, x, y, 1600, 800, 150, 50);
        set(remcf, x, y, 1300, 900, 225, 50);

        jlc.setRowHeight(50);
        jlc.getColumnModel().getColumn(1).setPreferredWidth(size(x, y, 300));
    }

    private void setPack(PackData.UserPack pack) {
        pac = pack;
        boolean pre = changing;
        if (jlp.getSelectedValue() != pack) {
            changing = true;
            jlp.setSelectedValue(pac, true);
            changing = pre;
        }
        changing = true;
        if (pac == null) {
            jlu.setListData(new Unit[0]);
            jlc.clearSelection();
            jlc.setList(new ArrayList<>());
        } else {
            jlf.allowDrag(pac.editable);
            jlu.setListData(pac.units.toRawArray());
            jlu.clearSelection();
            jlc.setList(pac.combos.getList());
        }
        changing = pre;
        if (pac == null || !pac.units.contains(uni))
            uni = null;
        setUnit(uni);
    }

    private void setUnit(Unit unit) {
        uni = unit;
        boolean pre = changing;
        if (jlu.getSelectedValue() != uni) {
            changing = true;
            jlu.setSelectedValue(uni, true);
            changing = pre;
        }
        boolean editable = unit != null && pac.editable;
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
        boolean editable = frm != null && pac.editable;
        addc.setEnabled(editable);
        boolean size = jlc.list.size() > 0 && jlc.getSelectedRow() != -1;
        if (size) {
            Combo c = jlc.list.get(jlc.getSelectedRow());
            ctypes.setSelectedIndex(c.type);
            clvls.setSelectedIndex(c.lv);
        }
        ctypes.setEnabled(size);
        clvls.setEnabled(size);
        boolean esize = editable && size;
        remcf.setEnabled(esize && jlc.list.get(jlc.getSelectedRow()).forms.length > 1);
        boolean check = esize && Arrays.stream(jlc.list.get(jlc.getSelectedRow()).forms).noneMatch(fr -> fr.uid.id == frm.uid.id);
        addf.setEnabled(check);
    }
}
