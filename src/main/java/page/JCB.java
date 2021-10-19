package page;

import common.util.lang.LocaleCenter;

import javax.swing.*;

public class JCB extends JCheckBox implements LocComp {

    private static final long serialVersionUID = 1L;

    private final LocSubComp lsc;

    public JCB() {
        lsc = new LocSubComp(this);
        setBorder(BorderFactory.createEtchedBorder());
    }

    public JCB(LocaleCenter.Binder binder) {
        this();
        lsc.init(binder);
    }

    public JCB(int i, String str) {
        this();
        lsc.init(i, str);
    }

    public JCB(String str) {
        this(-1, str);
    }

    @Override
    public LocSubComp getLSC() {
        return lsc;
    }
}
