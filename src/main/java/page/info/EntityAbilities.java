package page.info;

import common.battle.BasisSet;
import common.battle.data.MaskEnemy;
import common.battle.data.MaskEntity;
import common.battle.data.MaskUnit;
import common.util.unit.Level;
import common.util.unit.LevelInterface;
import common.util.unit.Magnification;
import page.Page;
import utilpc.Interpret;
import utilpc.UtilPC;

import javax.swing.*;
import java.util.List;

public class EntityAbilities extends Page {

    private static final long serialVersionUID = 1L;

    private final MaskEntity me;
    private final LevelInterface lvl;
    private JLabel[] proc;
    private JLabel pcoin;

    public EntityAbilities(Page p, MaskEntity me, LevelInterface lv) {
        super(p);
        this.me = me;
        lvl = lv;

        ini();
        resized(true);
    }

    private void ini() {
        boolean isEnemy = me instanceof MaskEnemy;

        List<Interpret.ProcDisplay> ls = Interpret.getAbi(me);

        double[] mag = new double[2];

        if (lvl instanceof Level) {
            mag[0] = ((Level) lvl).getLv();
            mag[1] = ((Level) lvl).getPlusLv();
        } else if (lvl instanceof Magnification && me instanceof MaskEnemy) {
            mag[0] = ((Magnification) lvl).hp * ((MaskEnemy) me).multi(BasisSet.current()) / 100;
            mag[1] = ((Magnification) lvl).atk * ((MaskEnemy) me).multi(BasisSet.current()) / 100;
        }

        ls.addAll(Interpret.getProc(me, isEnemy, mag));

        proc = new JLabel[ls.size()];

        for (int i = 0; i < ls.size(); i++) {
            Interpret.ProcDisplay disp = ls.get(i);

            add(proc[i] = new JLabel(disp.toString()));

            proc[i].setBorder(BorderFactory.createEtchedBorder());
            proc[i].setIcon(disp.getIcon());
            Interpret.setUnderline(proc[i]);
        }

        if (!isEnemy && ((MaskUnit) me).getPCoin() != null && lvl instanceof Level) {
            String[] strs = UtilPC.lvText(((MaskUnit) me).getPack(), (Level) lvl);

            add(pcoin = new JLabel(strs[1]));

            pcoin.setBorder(BorderFactory.createEtchedBorder());
        }
        for (JLabel jl : proc) add(jl);
    }

    @Override
    protected JButton getBackButton() {
        return null;
    }

    @Override
    protected void resized(int x, int y) {
        setBounds(0, 0, x, y);

        int posY = 0;

        for (JLabel jLabel : proc) {
            set(jLabel, x, y, 0, posY, 1200, 50);
            posY += 50;
        }

        if (pcoin != null)
            set(pcoin, x, y, 0, posY, 750, 50);
    }

    public int getPWidth() {
        return (proc.length + (pcoin != null ? 1 : 0)) > 0 ? 1200 : 0;
    }

    public int getPHeight() {
        return (proc.length + (pcoin != null ? 1 : 0)) * 50;
    }
}
