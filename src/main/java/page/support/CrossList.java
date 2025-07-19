package page.support;

import main.MainBCU;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class CrossList<T> extends JList<T> {
    private Function<Integer, Boolean> check;
    private T[] list;

    private static final long serialVersionUID = 1L;

    public CrossList() {
        super();
        setCellRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> l, Object o, int ind, boolean s, boolean f) {
                JLabel jl = (JLabel) super.getListCellRendererComponent(l, o, ind, s, f);
                if (check != null && check.apply(ind)) {
                    jl.setText("<html><strike>" + list[ind] + "</strike></html>");
                    jl.setForeground(s ? Color.WHITE : !MainBCU.light ? Color.GRAY : Color.RED);
                } else {
                    jl.setText(list[ind].toString());
                }
                return jl;
            }
        });
    }

    public CrossList(T[] data) {
        this();
        setList(data);
    }

    public void setCheck(Function<Integer, Boolean> c) {
        check = c;
        revalidate();
    }

    public void setList(T[] data) {
        list = data;
        setListData(list);
    }
}
