package page.info;

import common.battle.data.MaskEnemy;
import common.battle.data.MaskEntity;
import common.battle.data.MaskUnit;
import common.util.unit.Form;
import page.Page;
import utilpc.Interpret;
import utilpc.UtilPC;

import javax.swing.*;
import java.util.List;

public class EntityAbilities extends Page {

    private static final long serialVersionUID = 1L;

    private final MaskEntity me;
    private final int[] lvl;
    private JLabel[] abis, proc;
    private JLabel pcoin;

    public EntityAbilities(Page p, MaskEntity entity, int[] lv) {
        super(p);
        me = entity;
        lvl = lv;

        ini();
        resized();
    }

    private void ini() {
        List<String> a = Interpret.getAbi(me);
        abis = new JLabel[a.size()];
        for (int i = 0; i < a.size(); i++) {
            add(abis[i] = new JLabel(a.get(i)));
            abis[i].setBorder(BorderFactory.createEtchedBorder());
        }

        if (me instanceof MaskEnemy) {
            List<String> ls = Interpret.getProc(me, true, lvl[0] / 100.0);
            proc = new JLabel[ls.size()];
            for (int i = 0; i < ls.size(); i++) {
                add(proc[i] = new JLabel(ls.get(i)));
                proc[i].setBorder(BorderFactory.createEtchedBorder());
            }
        } else {
            boolean pc = ((MaskUnit) me).getPack().getPCoin() != null;
            MaskUnit mu = pc ? ((MaskUnit) me).getPack().getPCoin().improve(lvl) : ((MaskUnit) me);

            List<String> ls = Interpret.getProc(mu, false, 1.0);
            proc = new JLabel[ls.size()];
            for (int i = 0; i < ls.size(); i++) {
                add(proc[i] = new JLabel(ls.get(i)));
                proc[i].setBorder(BorderFactory.createEtchedBorder());
            }
            if (pc) {
                String[] strs = UtilPC.lvText(mu.getPack(), lvl);
                add(pcoin = new JLabel(strs[1]));

                pcoin.setBorder(BorderFactory.createEtchedBorder());
            }
        }

        for (JLabel jl : abis) add(jl);
        for (JLabel jl : proc) add(jl);
    }

    @Override
    protected void resized(int x, int y) {
        setBounds(0, 0, x, y);

        int posY = 0;
        for (JLabel abi : abis) {
            set(abi, x, y, 0, posY, 550, 50);
            posY += 50;
        }
        for (JLabel jLabel : proc) {
            set(jLabel, x, y, 0, posY, 550, 50);
            posY += 50;
        }

        if (pcoin != null)
            set(pcoin, x, y, 0, posY, 550, 50);
    }

    public int getPWidth() {
        return (abis.length + proc.length + (pcoin != null ? 1 : 0)) > 0 ? 550 : 0;
    }

    public int getPHeight() {
        return (abis.length + proc.length + (pcoin != null ? 1 : 0)) * 50;
    }
}
