package page.info.edit;


import common.battle.data.CustomUnit;
import common.battle.data.PCoin;
import common.util.Data;
import common.util.pack.Background;
import common.util.stage.Music;
import common.util.unit.Form;
import page.JBTN;
import page.Page;
import page.SupPage;
import utilpc.UtilPC;

import javax.swing.*;
import java.util.Vector;

public class PCoinEditPage extends Page implements SwingEditor.EditCtrl.Supplier {

    private static final long serialVersionUID = 1L;

     // indexes 10-12 aren't needed
    private final JBTN back = new JBTN(0, "back");
    private final JBTN add = new JBTN(0, "add");
    private final JBTN rem = new JBTN(0, "rem");
    private final JList<String> coin = new JList<>();
    private final JScrollPane jspc = new JScrollPane(coin);
    private final boolean editable;
    private final CustomUnit unit;
    private final PCoinEditTable2 pcet;
    private final JBTN info = new JBTN(0, "so i've got this new anime plot");
    boolean changing = false;

    public PCoinEditPage(Page p, Form u, boolean edi) {
        super(p);
        unit = (CustomUnit) u.du;
        editable = edi;
        pcet = new PCoinEditTable2(this, unit, editable);

        ini();
        resized();
    }

    @Override
    protected JButton getBackButton() {
        return back;
    }

    @Override
    protected void resized(int x, int y) {
        setBounds(0, 0, x, y);
        set(back, x, y, 0, 0, 200, 50);
        set(jspc, x, y, 450, 400, 300, 500);
        set(add, x, y, 450, 900, 150, 50);
        set(rem, x, y, 600, 900, 150, 50);
        set(pcet, x, y, 800, 250, 900, 1200);
        set(info, x, y, 850, 950, 200, 50);
    }

    private void addListeners() {
        back.addActionListener(arg0 -> changePanel(getFront()));

        add.addActionListener(arg0 -> {
            if (changing)
                return;
            changing = true;
            if (unit.pcoin == null)
                unit.pcoin = new PCoin(unit);

            int size = unit.pcoin.info.size(); // 5
            int[] base = PCoinEditTable2.BASE_TALENT.clone();
            for (int i = 0; i < unit.pcoin.info.size(); i++)
                for (int info : unit.pcoin.info.stream().sorted((a, b) -> b[0] - a[0]).mapToInt(a -> a[0]).toArray())
                    if (info == base[0])
                        base[0]++;
            base[1] = Data.PC_CORRES[base[0]][2] > 0 ? 10 : 1;
            unit.pcoin.info.add(base);
            unit.pcoin.max = new int[size + 1];
            for (int i = 0; i < size + 1; i++)
                unit.pcoin.max[i] = unit.pcoin.info.get(i)[1];
            setCoins(size);
            changing = false;
        });

        //PCoin Structure:
        //[0] = ability identifier, [1] = max lv, [2,4,6,8] = min lv values, [3,5,7,9] = max lv values, [10,11,12] = ???

        rem.addActionListener(arg0 -> {
            if (changing)
                return;
            changing = true;
            unit.pcoin.info.remove(coin.getSelectedIndex());
            if (unit.pcoin.info.size() == 0)
                unit.pcoin = null;
            else {
                unit.pcoin.max = new int[unit.pcoin.info.size()];
                for (int i = 0; i < unit.pcoin.info.size(); i++)
                    unit.pcoin.max[i] = unit.pcoin.info.get(i)[1];
            }

            setCoins(coin.getSelectedIndex());
            changing = false;
        });

        coin.addListSelectionListener(x -> {
            if (changing)
                return;
            changing = true;
            boolean selected = !coin.isSelectionEmpty();
            rem.setEnabled(editable && selected);
            pcet.setData(coin.getSelectedIndex());
            changing = false;
        });
    }

    private void ini() {
        add(back);
        add(add);
        add(rem);
        add(jspc);
        add(pcet);
        coin.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addListeners();
        setCoins(-1);
    }

    protected void resetList(int ind) {
        if (unit.pcoin != null) {
            Vector<String> talents = new Vector<>();
            PCoin p = unit.pcoin;
            p.update();
            for (int i = 0; i < p.max.length; i++)
                talents.add("talent " + (i + 1) + (unit.pcoin.info.get(i)[13] == 1 ? "*: " : ": ") + UtilPC.getPCoinAbilityText(p, i));
            coin.setListData(talents);
            coin.setSelectedIndex(Math.min(ind, talents.size() - 1));
        } else {
            coin.setListData(new String[0]);
        }
    }

    protected void setCoins(int ind) {
        resetList(ind);
        add.setEnabled(editable && (unit.pcoin == null || unit.pcoin.max.length < 8));
        rem.setEnabled(editable && coin.getSelectedIndex() != -1);
        pcet.setData(coin.getSelectedIndex());
    }

    @Override
    public SupPage<Background> getBGSup(SwingEditor.IdEditor<Background> edi) {
        return null;
    }

    @Override
    public SupPage<Music> getMusicSup(SwingEditor.IdEditor<Music> edi) {
        return null;
    }

    @Override
    public SupPage<?> getEntitySup(SwingEditor.IdEditor<?> edi) {
        return null;
    }
}