package page.support;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.List;

public abstract class SortTable<T> extends AbJTable {

    private static final long serialVersionUID = 1L;

    private static final String[] dire = new String[]{"↑", "↓"};

    public List<T> list;
    protected int sort = 0, sign = 1;

    protected SortTable() {
        SortTable<T> t = this;

        getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (list == null)
                    return;
                int col = columnAtPoint(e.getPoint());
                col = lnk[col];
                if (col != sort)
                    sign = 1;
                else
                    sign = -sign;
                sort = col;
                list.sort(new Comp<>(t, sort, sign));
                setHeader();
            }
        });
    }

    @Override
    public int getColumnCount() {
        return getTit().length;
    }

    @Override
    public String getColumnName(int c) {
        c = lnk[c];
        return (sort == c ? dire[(sign + 1) / 2] : "") + getTit()[c];
    }

    @Override
    public int getRowCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    @Override
    public Object getValueAt(int r, int c) {
        if (r >= list.size())
            return null;
        return get(list.get(r), lnk[c]);
    }

    public void setList(List<T> l) {
        list = l;
        list.sort(new Comp<>(this, sort, sign));
    }

    protected abstract int compare(T e0, T e1, int c);

    protected abstract Object get(T t, int c);

    protected abstract String[] getTit();

    protected void setHeader() {
        for (int i = 0; i < getColumnCount(); i++)
            getColumnModel().getColumn(i).setHeaderValue(getColumnName(i));
    }

}

class Comp<T> implements Comparator<T> {

    private final SortTable<T> t;
    private final int c, s;

    protected Comp(SortTable<T> table, int col, int sign) {
        c = col;
        s = sign;
        t = table;
    }

    @Override
    public int compare(T e0, T e1) {
        return t.compare(e0, e1, c) * s;
    }

}
