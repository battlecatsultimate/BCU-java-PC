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

    public EntityAbilities(Page p, MaskEntity me, int[] lv) {
        super(p);
        this.me = (me instanceof MaskUnit && ((MaskUnit) me).getPack().getPCoin() != null)
                ? ((MaskUnit) me).getPack().getPCoin().improve(lv)
                : me;
        lvl = lv;

        ini();
        resized();
    }

    private void ini() {
        boolean isEnemy = me instanceof MaskEnemy;
        List<String> a = Interpret.getAbi(me);
        abis = new JLabel[a.size()];
        for (int i = 0; i < a.size(); i++) {
            add(abis[i] = new JLabel(a.get(i)));
            abis[i].setBorder(BorderFactory.createEtchedBorder());
        }

        List<String> ls = Interpret.getProc(me, isEnemy, lvl[0] / 100.0);
        proc = new JLabel[ls.size()];
        for (int i = 0; i < ls.size(); i++) {
            add(proc[i] = new JLabel(ls.get(i)));
            proc[i].setBorder(BorderFactory.createEtchedBorder());
        }

        if (!isEnemy && ((MaskUnit) me).getPack().getPCoin() != null) {
            String[] strs = UtilPC.lvText(((MaskUnit) me).getPack(), lvl);
            add(pcoin = new JLabel(strs[1]));

            pcoin.setBorder(BorderFactory.createEtchedBorder());
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
