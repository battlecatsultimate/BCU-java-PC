package page.pack;

import common.battle.BasisSet;
import common.pack.PackData;
import common.util.stage.CharaGroup;
import common.util.unit.Combo;
import common.util.unit.Form;
import page.MainLocale;
import page.Page;
import page.support.AbJTable;
import utilpc.Interpret;
import utilpc.UtilPC;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.stream.IntStream;

public class ComboEditTable extends AbJTable {

    private static final long serialVersionUID = 1L;

    private static String[] tit;
    private static String[] typ;

    static {
        redefine();
    }

    public static void redefine() {
        String str = MainLocale.getLoc(MainLocale.INFO, "unit");
        tit = new String[] { "name", "Lv.", MainLocale.getLoc(MainLocale.INFO, "desc"),
                MainLocale.getLoc(MainLocale.INFO, "group"), str + " 1", str + " 2", str + " 3", str + " 4",
                str + " 5" };
        typ = Interpret.getComboFilter(0);
    }

    private final Page fr;
    private PackData.UserPack pack;
    private final JComboBox<Integer> clvl = new JComboBox<>(IntStream.rangeClosed(0, 3).boxed().toArray(Integer[]::new));
    private final JComboBox<Integer> ctyp = new JComboBox<>(IntStream.range(0, typ.length).boxed().toArray(Integer[]::new));

    public ComboEditTable(Page p, PackData.UserPack pack) {
        super(tit);

        fr = p;
        this.pack = pack;

        clvl.setRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel jl = ((JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus));
                jl.setText(index == -1 ? Interpret.lvl[(int) value] : Interpret.lvl[index]);
                return jl;
            }
        });

        ctyp.setRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel jl = ((JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus));
                jl.setText(index == -1 ? typ[(int) value]  : typ[index]);
                return jl;
            }
        });

        setDefaultRenderer(CharaGroup.class, new DefaultTableCellRenderer() {

            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable l, Object o, boolean s, boolean f, int r, int c) {
                JLabel jl = (JLabel) super.getTableCellRendererComponent(l, c, s, f, r, c);
                CharaGroup group = (CharaGroup) o;
                if (group != null) {
                    jl.setText(group.name.isEmpty() ? group.id.toString() : group.name + " - " + group.id);
                    jl.setToolTipText(Interpret.getGroupTooltip(group));
                } else {
                    jl.setText("");
                    jl.setToolTipText(null);
                }
                return jl;
            }

        });

        setDefaultRenderer(Combo.class, new DefaultTableCellRenderer() {

            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable l, Object o, boolean s, boolean f, int r, int c) {
                JLabel jl = (JLabel) super.getTableCellRendererComponent(l, c, s, f, r, c);
                Combo com = (Combo) o;
                jl.setText(com != null ? Interpret.comboInfo(com, BasisSet.current(), false) : "?");
                return jl;
            }

        });

        setDefaultRenderer(Form.class, new DefaultTableCellRenderer() {

            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable l, Object o, boolean s, boolean f, int r, int c) {
                JLabel jl = (JLabel) super.getTableCellRendererComponent(l, c, s, f, r, c);
                Form form = (Form) o;
                jl.setText("");
                if (form == null) {
                    jl.setIcon(null);
                    return jl;
                }
                ImageIcon icon = UtilPC.getIcon(form.anim.getUni());
                if (icon != null) {
                    Image img = icon.getImage().getScaledInstance(60, 45, java.awt.Image.SCALE_SMOOTH);
                    jl.setIcon(new ImageIcon(img));
                    jl.setHorizontalAlignment(SwingConstants.CENTER);
                    jl.setVerticalAlignment(SwingConstants.CENTER);
                } else {
                    jl.setIcon(null);
                }
                return jl;
            }

        });
    }

    @Override
    public TableCellEditor getCellEditor(int r, int c) {
        c = lnk[c];
        Object v = getValueAt(r, c);
        Combo com = pack.combos.get(r);
        if (c == 1) {
            clvl.setSelectedIndex(com.lv);
            return new DefaultCellEditor(clvl);
        } else if (c == 2) {
            ctyp.setSelectedIndex(com.type);
            return new DefaultCellEditor(ctyp);
        }
        else if (v != null)
            return getDefaultEditor(v.getClass());
        else
            return super.getCellEditor(r, c);
    }

    public synchronized void clicked(Point p) {
        if (pack == null)
            return;
        int c = getColumnModel().getColumnIndexAtX(p.x);
        c = lnk[c];
        int r = p.y / getRowHeight();
        if (r < 0 || r >= pack.combos.size() || c < 3)
            return;
        if (c == 3 && pack.editable) {
            fr.callBack(new CharaGroupPage(fr, pack, false));
            return;
        }
        Form f = ((Form) get(pack.combos.get(r), c));
        if (f == null)
            return;
        fr.callBack(f.unit);
    }

    @Override
    public Class<?> getColumnClass(int c) {
        c = lnk[c];
        if (c == 2)
            return Combo.class;
        else if (c == 3)
            return CharaGroup.class;
        else if (c >= 4)
            return Form.class;
        else
            return String.class;
    }

    @Override
    public String getColumnName(int c) {
        return tit[lnk[c]];
    }

    @Override
    public int getRowCount() {
        return pack == null ? 0 : pack.combos.size();
    }

    @Override
    public Object getValueAt(int r, int c) {
        if (pack == null || r < 0 || c < 0 || r >= pack.combos.size() || c > lnk.length)
            return null;
        return get(pack.combos.get(r), lnk[c]);
    }

    @Override
    public boolean isCellEditable(int r, int c) {
        c = lnk[c];
        if (r < 0 || c < 0 || r > pack.combos.size() || c >= 3)
            return false;
        return pack.editable;
    }

    @Override
    public void setValueAt(Object o, int r, int c) {
        if (pack == null)
            return;
        Combo combo = pack.combos.get(r);
        c = lnk[c];

        if (c == 0)
            combo.name = (String) o;
        else if (c == 1)
            combo.lv = (int) o;
        else if (c == 2)
            combo.type = (int) o;
    }

    protected Object get(Combo t, int c) {
        if (c == 0)
            return t.name;
        if (c == 1)
            return Interpret.lvl[t.lv];
        if (c == 2)
            return t;
        if (c == 3)
            return t.group;
        if (c >= 4 && t.forms.length > c - 4) {
            return t.forms[c - 4];
        }
        return null;
    }

    public void setPreferredWidth(int x, int y) {
        getColumnModel().getColumn(lnk[0]).setPreferredWidth(Math.min(200 * x / 2300, 200 * y / 1300));
        getColumnModel().getColumn(lnk[2]).setPreferredWidth(Math.min(300 * x / 2300, 300 * y / 1300));
    }

    public void setPack(PackData.UserPack pack) {
        this.pack = pack;
    }
}
