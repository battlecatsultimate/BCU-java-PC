package page.pack;

import common.CommonStatic;
import common.battle.data.CustomEnemy;
import common.battle.data.CustomUnit;
import common.pack.Context;
import common.pack.Source;
import common.pack.UserProfile;
import common.util.unit.Form;
import common.util.unit.Enemy;
import common.util.unit.Unit;
import common.pack.PackData.UserPack;
import common.util.unit.Trait;
import common.pack.FixIndexList.FixIndexMap;
import main.MainBCU;
import page.JBTN;
import page.JTF;
import page.JTG;
import page.Page;
import page.support.Importer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class TraitEditPage extends Page {

    private final JList<Trait> jlct = new JList<>();
    private final JScrollPane jspct = new JScrollPane(jlct);

    private final JLabel jl = new JLabel();

    private final JBTN back = new JBTN(0, "back");
    private final JBTN addct = new JBTN(0, "add");
    private final JBTN remct = new JBTN(0, "rem");
    private final JBTN adicn = new JBTN(0, "icon");
    private final JBTN reicn = new JBTN(0, "remove icon");
    private final JTG altrg = new JTG(0, "affected by anti-traited");
    private final JTF ctrna = new JTF();

    private final UserPack packpack;
    private final FixIndexMap<Trait> pct;

    private boolean changing = false;
    private final boolean editable;
    private Trait t;

    public TraitEditPage(Page p, UserPack pac) {
        super(p);
        packpack = pac;
        pct = pac.traits;
        ini();
        editable = pac.editable;
    }

    @Override
    protected synchronized void resized(int x, int y) {
        setBounds(0, 0, x, y);
        set(back, x, y, 0, 0, 200, 50);
        set(addct, x, y, 50, 1000, 150, 50);
        set(remct, x, y, 200, 1000, 150, 50);
        set(jspct, x, y, 50, 100, 300, 800);
        set(altrg, x, y, 50, 950, 300, 50);
        set(ctrna, x, y, 50, 900, 300, 50);
        set(adicn, x, y, 400, 100, 150, 50);
        set(jl, x, y, 450, 150, 50, 50);
        set(reicn, x, y, 400, 200, 150, 50);
    }

    private void addListeners$0() {

        back.setLnr(x -> changePanel(getFront()));

        adicn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                getFile("Choose your file");
            }
        });

        reicn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                File file = ((Source.Workspace) packpack.source).getTraitIconFile(t.id);
                if (file.delete()) {
                    t.icon = null;
                    jl.setIcon(null);
                    reicn.setEnabled(false);
                }
            }
        });

    }

    private void addListeners$CG() {
        addct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                changing = true;
                t = new Trait(packpack.getNextID(Trait.class));
                pct.add(t);
                updateCTL();
                jlct.setSelectedValue(t, true);
                changing = false;
            }
        });
        remct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (t == null)
                    return;
                changing = true;
                List<Trait> list = pct.getList();
                int ind = list.indexOf(t) - 1;
                if (ind < 0 && list.size() > 1)
                    ind = 0;
                File file = ((Source.Workspace) packpack.source).getTraitIconFile(t.id);
                file.delete();
                list.remove(t);
                pct.remove(t);
                if (ind >= 0)
                    t = list.get(ind);
                else
                    t = null;
                updateCTL();
                changing = false;
            }
        });
        altrg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (t == null)
                    return;
                changing = true;
                t.targetType = !t.targetType;
                updateCTL();
                changing = false;
            }
        });
        jlct.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (changing || jlct.getValueIsAdjusting())
                    return;
                changing = true;
                t = jlct.getSelectedValue();
                updateCT();
                changing = false;
            }

        });

        ctrna.setLnr(x -> {
            String str = ctrna.getText();
            if (t.name.equals(str))
                return;
            if (str.equals("")) {
                ctrna.setText(t.name);
                return;
            }
            t.name = str;
        });
    }

    private void updateCTL() {
        jlct.setListData(pct.toArray());
        jlct.setSelectedValue(t, true);
        updateCT();
    }

    private void updateCT() {
        altrg.setEnabled(t != null && editable);
        remct.setEnabled(t != null && !isUsedTrait(t) && editable);
        ctrna.setEnabled(t != null && editable);
        adicn.setEnabled(t != null && editable);
        setIconImage(t);
        ctrna.setText("");
        if (t != null) {
            ctrna.setText(t.name);
            altrg.setSelected(t.targetType);
            if (t.icon != null)
                jl.setIcon(t.obtainIcon());
            else
                jl.setIcon(null);
        }
        reicn.setEnabled(t != null && t.icon != null && editable);
    }

    private boolean isUsedTrait(Trait tr) {
        Collection<UserPack> pacs = UserProfile.getUserPacks();
        for (UserPack pacc : pacs) {
            if (!pacc.desc.id.equals("000000") && (pacc.desc.dependency.contains(packpack.desc.id) || pacc.desc.id.equals(packpack.desc.id))) {
                for (Enemy en : pacc.enemies.getList())
                    if (((CustomEnemy) en.de).customTraits.contains(tr.id))
                        return true;
                for (Unit un : pacc.units.getList())
                    for (Form uf : un.forms)
                        if (((CustomUnit) uf.du).customTraits.contains(tr.id))
                            return true;
            }
        }
        return false;
    }

    private void ini() {
        add(back);
        add(addct);
        add(remct);
        add(jspct);
        add(altrg);
        add(ctrna);
        add(adicn);
        add(jl);
        add(reicn);
        jl.setIcon(null);
        addListeners$0();
        addListeners$CG();
        updateCTL();
    }

    private void getFile(String str) {
        BufferedImage bimg = new Importer(str).getImg();
        if (bimg == null)
            return;
        if (bimg.getWidth() != 41 && bimg.getHeight() != 41) {
            getFile("Wrong img size. Img size: w=41, h=41");
            return;
        }
        if (t.icon != null)
            t.icon.setImg(MainBCU.builder.build(bimg));
        else
            t.icon = MainBCU.builder.toVImg(bimg);
        try {
            File file = ((Source.Workspace) packpack.source).getTraitIconFile(t.id);
            Context.check(file);
            ImageIO.write(bimg, "PNG", file);
        } catch (IOException e) {
            CommonStatic.ctx.noticeErr(e, Context.ErrType.WARN, "failed to write file");
            getFile("Failed to save file");
            return;
        }
        updateCT();
        setIconImage(jlct.getSelectedValue());
    }

    private void setIconImage(Trait slt) {
        if (t == null)
            return;
        if (jlct.getSelectedValue() != slt) {
            changing = true;
            jlct.setSelectedValue(slt, true);
            changing = false;
        }
    }
}