package page;

import common.util.lang.LocaleCenter.Binder;
import utilpc.PP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LocSubComp {

    static class LocBinder implements Binder {

        private final int loc;
        private final String info;
        private final LocSubComp par;

        LocBinder(LocSubComp par, int loc, String info) {
            this.par = par;
            this.loc = loc;
            this.info = info;
        }

        @Override
        public String getNameID() {
            return loc < 0 ? null : MainLocale.RENN[loc] + "_" + info;
        }

        @Override
        public String getNameValue() {
            return MainLocale.getLoc(loc, info);
        }

        @Override
        public String getTooltipID() {
            if (par.page == null)
                return null;
            return par.page.getClass().getSimpleName() + "_" + info;
        }

        @Override
        public String getToolTipValue() {
            if (par.page == null)
                return null;
            return MainLocale.getTTT(par.page.getClass().getSimpleName(), info);
        }

        @Override
        public Binder refresh() {
            return this;
        }

        @Override
        public void setNameValue(String str) {
            MainLocale.setLoc(loc, info, str);
        }

        @Override
        public void setToolTipValue(String str) {
            if (par.page == null)
                return;
            MainLocale.setTTT(par.page.getClass().getSimpleName(), info, str);
        }

    }

    protected final LocComp lc;
    protected Binder binder;
    protected Page page;

    public LocSubComp(LocComp comp) {
        lc = comp;
        lc.addMouseListener(new LSCPop(this));
    }

    protected void added(Page p) {
        page = p;
        update();
    }

    protected void init(Binder b) {
        binder = b;
        update();
    }

    protected void init(int i, String str) {
        init(new LocBinder(this, i, str));
    }

    protected void reLoc() {
        binder = binder.refresh();
        update();
    }

    public void update() {
        if (binder == null)
            return;
        lc.setText(binder.getNameValue());
        if (binder.getToolTipValue() != null)
            lc.setToolTipText(binder.getToolTipValue());
    }

}

class LSCPop extends MouseAdapter {

    private final LocSubComp lsc;

    protected LSCPop(LocSubComp comp) {
        lsc = comp;
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        if (arg0.getButton() == MouseEvent.BUTTON3 && lsc.page != null) {
            JPanel panel = new JPanel();
            PP size = new PP(lsc.page.getRootPage().getSize()).times(0.25);
            panel.setPreferredSize(size.toDimension());
            panel.setLayout(new BorderLayout());
            JPanel top = new JPanel(new GridLayout(2, 2));
            JL id1 = new JL(lsc.binder.getTooltipID());
            JL id0 = new JL(lsc.binder.getNameID());
            top.add(new JLabel("tooltip ID to edit: "));
            top.add(id1);
            top.add(new JLabel("name ID to edit: "));
            top.add(id0);
            panel.add(top, BorderLayout.PAGE_START);
            JTF jtf = new JTF(lsc.binder.getNameValue());
            panel.add(jtf, BorderLayout.PAGE_END);
            JTextPane jta = new JTextPane();
            jta.setText(lsc.binder.getToolTipValue());
            panel.add(new JScrollPane(jta), BorderLayout.CENTER);
            if (lsc.binder.getNameID() == null) {
                id0.setEnabled(false);
                jtf.setEnabled(false);
            }
            if (lsc.binder.getTooltipID() == null) {
                id1.setEnabled(false);
                jta.setEnabled(false);
            }
            int type = JOptionPane.OK_CANCEL_OPTION;
            int ok = JOptionPane.OK_OPTION;
            int res = JOptionPane.showConfirmDialog(null, panel, "", type);
            String str = jtf.getText();
            String ttt = jta.getText();
            if (res == ok && str != null && !str.equals(lsc.binder.getNameValue())) {
                lsc.binder.setNameValue(str);
                Page.renewLoc(lsc.page);
            }
            if (res == ok && ttt != null && !ttt.equals(lsc.binder.getToolTipValue())) {
                lsc.binder.setToolTipValue(ttt);
                Page.renewLoc(lsc.page);

            }
        }

    }

}
