package page.pack;

import page.JBTN;
import page.Page;
import utilpc.Backup;

import javax.swing.*;

public class BackupTreePage extends Page {

    private static final long serialVersionUID = 1L;

    private final JBTN back = new JBTN(0, "back");
    private final JBTN read = new JBTN(0, "read list");
    private final JBTN dele = new JBTN(0, "delete");
    private final JBTN rest = new JBTN(0, "restore");
    private final JBTN entr = new JBTN(0, "enter");
    private final JBTN sntr = new JBTN(0, "enter");
    private final JBTN diff = new JBTN(0, "diff");
    private final JBTN vers = new JBTN(0, "versions");
    private final JBTN rept = new JBTN(0, "partial restore");
    private final JBTN expt = new JBTN(0, "export");
    private final JLabel jln = new JLabel();
    private final JLabel jli = new JLabel();
    private final JList<Backup> jlm = new JList<>();
    private final JTree jls = new JTree();
    private final JTree jlf = new JTree();
    private final JScrollPane jspm = new JScrollPane(jlm);
    private final JScrollPane jsps = new JScrollPane(jls);
    private final JScrollPane jspf = new JScrollPane(jlf);

    public BackupTreePage(Page p, boolean ntr) {
        super(p);

        ini(ntr);
        resized();
    }

    @Override
    protected void resized(int x, int y) {
        setBounds(0, 0, x, y);
        set(back, x, y, 0, 0, 200, 50);
        set(jspm, x, y, 50, 100, 400, 800);
        set(read, x, y, 500, 100, 200, 50);
        set(rest, x, y, 500, 200, 200, 50);
        set(entr, x, y, 500, 300, 200, 50);
        set(dele, x, y, 500, 400, 200, 50);
        set(diff, x, y, 500, 500, 200, 50);
        set(vers, x, y, 500, 600, 200, 50);

        set(jln, x, y, 750, 50, 750, 50);
        set(jsps, x, y, 750, 100, 400, 800);
        set(rept, x, y, 1200, 100, 200, 50);
        set(expt, x, y, 1200, 200, 200, 50);
        set(sntr, x, y, 1200, 300, 200, 50);

        set(jli, x, y, 1450, 50, 750, 50);
        set(jspf, x, y, 1450, 100, 400, 800);
    }

    private void addListeners() {
        back.addActionListener(arg0 -> changePanel(getFront()));
    }

        private void ini(boolean ntr) {
        add(back);
        add(jspm);
        add(read);
        add(dele);
        add(rest);
        add(jsps);
        add(entr);
        add(rept);
        add(expt);
        add(jln);
        add(diff);
        add(vers);
        if (ntr) {
            add(jli);
            add(jspf);
            add(sntr);
        }
        addListeners();
    }
}
