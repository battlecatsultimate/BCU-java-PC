package page.pack;

import common.pack.Identifier;
import common.pack.PackData;
import common.pack.Source;
import common.pack.UserProfile;
import common.util.anim.AnimCE;
import common.util.pack.Soul;
import common.util.stage.Music;
import main.Opts;
import page.*;
import page.support.AnimLCR;
import page.support.SoulLCR;

import javax.swing.*;
import java.util.Vector;
import java.util.stream.Collectors;

public class SoulEditPage extends Page {

    private static final long serialVersionUID = 1L;

    private final JBTN back = new JBTN(0, "back");
    private final JBTN adds = new JBTN(0, "add");
    private final JBTN rems = new JBTN(0, "rem");
    private final JBTN srea = new JBTN(0, "reassign");

    private final JL lbp = new JL(0, "pack");
    private final JL lbs = new JL(0, "soul");
    private final JL lbd = new JL(0, "seleanim");

    private final JTF jtfs = new JTF();

    private final Vector<PackData.UserPack> vpack = new Vector<>(UserProfile.getUserPacks());
    private final JList<PackData.UserPack> jlp = new JList<>(vpack);
    private final JScrollPane jspp = new JScrollPane(jlp);
    private final JList<Soul> jls = new JList<>();
    private final JScrollPane jsps = new JScrollPane(jls);

    private final JList<AnimCE> jld = new JList<>(new Vector<>(AnimCE.map().values().stream().filter(a -> a.id.base.equals(Source.BasePath.SOUL)).collect(Collectors.toList())));
    private final JComboBox<Music> jcbm = new JComboBox<>();
    private final JScrollPane jspd = new JScrollPane(jld);

    private PackData.UserPack pac;
    private Soul soul;
    private boolean changing = false;

    public SoulEditPage(Page p, PackData.UserPack pack) {
        super(p);

        ini(pack);
    }

    @Override
    protected JButton getBackButton() {
        return back;
    }

    @Override
    protected void renew() {
        setPack(pac);
    }

    @Override
    protected void resized(int x, int y) {
        setBounds(0, 0, x, y);

        set(back, x, y, 0, 0, 200, 50);

        int w = 50, dw = 150;

        set(lbp, x, y, w, 100, 400, 50);
        set(jspp, x, y, w, 150, 400, 600);

        w += 450;

        set(lbs, x, y, w, 100, 300, 50);
        set(jsps, x, y, w, 150, 300, 600);
        set(srea, x, y, w, 750, 300, 50);
        set(adds, x, y, w, 800, 150, 50);
        set(rems, x, y, w + dw, 800, 150, 50);
        set(jtfs, x, y, w, 850, 300, 50);
        set(jcbm, x, y, w, 900, 300, 50);

        w += 300;

        set(lbd, x, y, w, 100, 300, 50);
        set(jspd, x, y, w, 150, 300, 600);
    }

    private void addListeners() {
        back.setLnr(x -> changePanel(getFront()));

        jls.addListSelectionListener(x -> {
            if (changing || jls.getValueIsAdjusting())
                return;
            changing = true;
            setSoul(jls.getSelectedValue());
            changing = false;
        });

        jlp.addListSelectionListener(arg0 -> {
            if (changing || jlp.getValueIsAdjusting())
                return;
            changing = true;
            setPack(jlp.getSelectedValue());
            changing = false;
        });

        jld.addListSelectionListener(x -> {
            if (jld.getValueIsAdjusting())
                return;
            boolean editable = pac != null && pac.editable;
            boolean selected = jld.getSelectedValue() != null && jld.getSelectedValue().id.base.equals(Source.BasePath.SOUL);
            adds.setEnabled(editable && selected);
            rems.setEnabled(editable);
            srea.setEnabled(editable && selected);
        });
    }

    private void addListeners$1() {
        srea.addActionListener(x -> {
            if (jls.getSelectedValue() == null || jld.getSelectedValue() == null)
                return;

            if (Opts.conf(get(MainLocale.PAGE, "reasanim"))) {
                changing = true;

                Soul s = jls.getSelectedValue();
                s.anim = jld.getSelectedValue();

                changing = false;
            }
        });

        adds.addActionListener(x -> {
            changing = true;
            Soul s = new Soul(pac.getNextID(Soul.class), jld.getSelectedValue());
            pac.souls.add(s);
            jls.setListData(pac.souls.toRawArray());
            jls.setSelectedValue(s, true);
            setSoul(s);
            changing = false;
        });

        rems.addActionListener(x -> {
            if (!Opts.conf())
                return;

            changing = true;
            int ind = jls.getSelectedIndex();
            pac.souls.remove(soul);
            jls.setListData(pac.souls.toRawArray());
            if (ind >= 0)
                ind--;
            jls.setSelectedIndex(ind);
            setSoul(jls.getSelectedValue());
            changing = false;
        });
    }

    private void addListeners$2() {
        jtfs.setLnr(x -> soul.name = jtfs.getText().trim());

        jcbm.addActionListener(x -> {
            if (changing || soul == null)
                return;

            changing = true;

            Music m = (Music) jcbm.getSelectedItem();
            soul.audio = m != null ? m.getID() : null;

            changing = false;
        });
    }

    private void ini(PackData.UserPack pack) {
        add(back);

        add(lbp);
        add(jspp);

        add(lbs);
        add(jsps);
        add(lbd);
        add(jspd);

        add(srea);
        add(adds);
        add(rems);

        add(jtfs);
        add(jcbm);

        jls.setCellRenderer(new SoulLCR());
        jld.setCellRenderer(new AnimLCR());

        setPack(pack);
        addListeners();
        addListeners$1();
        addListeners$2();
    }

    private void setPack(PackData.UserPack pack) {
        pac = pack;
        boolean boo = changing;
        boolean exists = pac != null;
        changing = true;
        if (jlp.getSelectedValue() != pack)
            jlp.setSelectedValue(pac, true);

        if (exists) {
            jls.setListData(pac.souls.toRawArray());

            Vector<Music> vs = new Vector<>();
            vs.add(null);
            vs.addAll(UserProfile.getAll(pac.getSID(), Music.class));
            jcbm.setModel(new DefaultComboBoxModel<>(vs));
        } else {
            jls.setListData(new Soul[0]);
            jcbm.removeAllItems();
        }

        boolean editable = exists && pac.editable;
        boolean selected = jld.getSelectedValue() != null;
        adds.setEnabled(editable && selected);
        rems.setEnabled(editable && soul != null);
        srea.setEnabled(editable && soul != null);
        jcbm.setEnabled(editable && soul != null);

        if (!exists || !pac.souls.contains(soul))
            soul = null;
        setSoul(soul);

        changing = boo;
    }

    private void setSoul(Soul s) {
        soul = s;
        boolean boo = changing;
        changing = true;
        if (jls.getSelectedValue() != s)
            jls.setSelectedValue(s, true);

        if (s != null) {
            jtfs.setText(soul.name);
            jcbm.setSelectedItem(Identifier.get(s.audio));
        }

        boolean editable = s != null && pac.editable;
        rems.setEnabled(editable);
        srea.setEnabled(editable && jld.getSelectedValue() != null);
        jcbm.setEnabled(editable);
        jtfs.setEnabled(editable);

        changing = boo;
    }
}
