package page;

import com.google.gson.JsonParser;
import common.CommonStatic;
import common.battle.BasisLU;
import common.battle.BasisSet;
import common.battle.data.AtkDataModel;
import common.battle.data.CustomEntity;
import common.io.Backup;
import common.pack.Context;
import common.pack.Identifier;
import common.pack.PackData;
import common.pack.UserProfile;
import common.system.files.VFile;
import common.system.files.VFileRoot;
import common.util.anim.MaAnim;
import common.util.anim.Part;
import common.util.pack.Background;
import common.util.stage.*;
import common.util.unit.*;
import main.Opts;
import org.apache.commons.io.IOUtils;
import page.info.TreaTable;
import page.support.Exporter;
import utilpc.Interpret;
import utilpc.UtilPC;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;

public class BackupPage extends Page {

    private static final long serialVersionUID = 1L;

    private final JBTN back = new JBTN(0, "back");
    private final JBTN read = new JBTN(0, "read list");
    private final JBTN dele = new JBTN(0, "delete");
    private final JBTN rest = new JBTN(0, "restore");
    private final JBTN entr = new JBTN(0, "enter");
    private final JBTN sntr = new JBTN(0, "enter");
    private final JBTN rept = new JBTN(0, "restore partial");
    private final JBTN expt = new JBTN(0, "export");
    private final JLabel jln = new JLabel();
    private final JLabel jli = new JLabel();
    private final JList<Backup> jlm = new JList<>();
    private final JTree jls = new JTree();
    private final JTree jlf = new JTree();
    private final JScrollPane jspm = new JScrollPane(jlm);
    private final JScrollPane jsps = new JScrollPane(jls);
    private final JScrollPane jspf = new JScrollPane(jlf);

    private boolean changing;

    private Backup backup;
    private VFile sel;

    protected BackupPage(Page p, boolean ntr) {
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

        set(jln, x, y, 750, 50, 750, 50);
        set(jsps, x, y, 750, 100, 400, 800);
        set(rept, x, y, 1200, 100, 200, 50);
        set(expt, x, y, 1200, 200, 200, 50);
        set(sntr, x, y, 1200, 300, 200, 50);

        set(jli, x, y, 1450, 50, 750, 50);
        set(jspf, x, y, 1450, 100, 400, 800);

    }

    private void addListeners$0() {
        back.addActionListener(arg0 -> changePanel(getFront()));

        read.addActionListener(arg0 -> setList());

        dele.setLnr(x -> {
            if (jlm.getSelectedValue() == null)
                return;

            if (!Opts.conf())
                return;

            List<Backup> strs = jlm.getSelectedValuesList();

            boolean result = true;

            for(Backup backup : strs) {
                boolean temp = backup.delete();

                if(!temp) {
                    Opts.pop("Failed to delete backup : "+backup.getName(), "Deletion Failed");
                }

                result &= temp;
            }

            if(result) {
                Opts.pop("Deleted all selected backups", "Deletion Completed");
            }

            jlm.setListData(Backup.backups.toArray(new Backup[0]));
        });

        rest.setLnr(x -> {
            if (jlm.getSelectedValue() == null)
                return;

            if(Opts.conf("Are you sure that you want to restore this backup? This cannot be undone and restart is required")) {
                CommonStatic.getConfig().backupFile = jlm.getSelectedValue().getName();
                changePanel(new SavePage());
            }
        });

        entr.setLnr(x -> {
            if (jlm.getSelectedValue() == null)
                return;

            setTree(jlm.getSelectedValue().backup.tree, jlm.getSelectedValue().getName());
        });

        jlm.addListSelectionListener(arg0 -> {
            if (changing || jlm.getValueIsAdjusting())
                return;

            setBackup(jlm.getSelectedValue());
        });

    }

    private void addListeners$1() {

        sntr.addActionListener(arg0 -> {
            if (sel == null || sel.getData() == null)
                return;

            String s0 = sel.getName();
            String s1 = sel.getParent().getName();

            boolean b0 = s0.equals("basis.json") || s1.equals("basis.json");
            boolean b1 = s0.equals("pack.json") || s1.equals("pack.json");
            boolean b2 = s0.endsWith(".maanim") || s1.endsWith(".maanim");

            if (!b0 && !b1 && !b2)
                return;

            if (b0) {
                InputStream is = sel.getData().getStream();

                if (is == null)
                    Opts.backupErr("read");

                setT$Basis(is);
            }
            if (b1) {
                InputStream is = sel.getData().getStream();

                if (is == null)
                    Opts.backupErr("read");

                setT$Pack(is, sel.getParent().getName());
            }
            if (b2) {
                Queue<String> qs = sel.getData().readLine();

                if (qs == null)
                    Opts.backupErr("read");

                setT$MA(qs);
            }
        });

        rept.addActionListener(arg0 -> {
            if (jlm.getSelectedValue() == null)
                return;

            if(Opts.conf("Are you sure that you want to restore specific file(s)? This cannot be undone and restart is required")) {
                CommonStatic.getConfig().backupFile = jlm.getSelectedValue().getName();
                CommonStatic.getConfig().backupPath = sel.getPath();
                changePanel(new SavePage());
            }
        });

        expt.addActionListener(arg0 -> new Exporter(sel.getData().getStream(), sel.getName()));

        jls.addTreeSelectionListener(arg0 -> {
            if (changing)
                return;

            Object obj;

            TreePath tp = jls.getSelectionPath();

            if (tp != null) {
                obj = tp.getLastPathComponent();

                if (obj != null)
                    obj = ((DefaultMutableTreeNode) obj).getUserObject();

                sel = obj instanceof VFile ? (VFile) obj : null;
            } else
                sel = null;

            setSele();
        });

    }

    private void addTree(DefaultMutableTreeNode par, VFile vf) {
        for (VFile c : vf.list()) {
            DefaultMutableTreeNode cur = new DefaultMutableTreeNode(c);
            par.add(cur);

            if (c.list() != null)
                addTree(cur, c);
        }
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

        if (ntr) {
            add(jli);
            add(jspf);
            add(sntr);
            setT();
        }

        setList();
        setTree(null, null);
        addListeners$0();
        addListeners$1();
    }

    private void setBackup(Backup bac) {
        if (backup != bac) {
            backup = bac;

            if (backup != jlm.getSelectedValue()) {
                boolean boo = changing;

                changing = true;

                jlm.setSelectedValue(backup, true);

                changing = boo;
            }
        }

        boolean b = backup != null;

        entr.setEnabled(b);
        dele.setEnabled(b);
        rest.setEnabled(b);

        setSele();
    }

    private void setList() {
        changing = true;
        jlm.setListData(Backup.backups.toArray(new Backup[0]));
        changing = false;

        setBackup(null);
    }

    private void setSele() {
        boolean b = sel != null;

        rept.setEnabled(b);

        expt.setEnabled(b && sel.list() == null);

        if (b && sel.getData() != null) {
            String size = "Size: ";

            if (sel.getData().size() < 10000)
                size += sel.getData().size() + " Bytes";
            else if (sel.getData().size() < 10000000)
                size += (sel.getData().size() >> 10) + " KB";
            else
                size += (sel.getData().size() >> 20) + " MB";

            jli.setText(size);
        } else
            jli.setText("");

    }

    private void setT() {
        jlf.setModel(new DefaultTreeModel(null));
    }

    private void setT$Basis(InputStream is) {
        String content = CommonStatic.ctx.noticeErr(() -> {
            try {
                return IOUtils.toString(is, StandardCharsets.UTF_8);
            } catch (Exception e) {
                return null;
            }
        }, Context.ErrType.WARN, "Failed to load basis data in this backup");

        if(content == null)
            return;

        BasisSet[] bas = BasisSet.getBackupSet(JsonParser.parseString(content));

        DefaultMutableTreeNode top = new DefaultMutableTreeNode("basis.json/");
        for (BasisSet bs : bas) {
            if (bs == null)
                continue;

            DefaultMutableTreeNode nbs = new DefaultMutableTreeNode(bs.name + "/");
            top.add(nbs);

            DefaultMutableTreeNode ntr = new DefaultMutableTreeNode("treasure/");
            nbs.add(ntr);

            for (int i = 0; i < Interpret.TREA.length; i++) {
                int ind = Interpret.TIND[i];

                String str = Interpret.TREA[ind] + ": " + TreaTable.tos(Interpret.getValue(ind, bs.t()), i + 1);

                DefaultMutableTreeNode net = new DefaultMutableTreeNode(str);
                ntr.add(net);
            }

            DefaultMutableTreeNode nlu = new DefaultMutableTreeNode("lineups/");
            nbs.add(nlu);

            for (BasisLU blu : bs.lb) {
                DefaultMutableTreeNode nli = new DefaultMutableTreeNode(blu.name + "/");
                nlu.add(nli);

                DefaultMutableTreeNode nlv = new DefaultMutableTreeNode("levels/");
                nli.add(nlv);

                for (Entry<Identifier<Unit>, Level> e : blu.lu.map.entrySet()) {
                    Unit u = Identifier.getOr(e.getKey(), Unit.class);

                    Form f = u.forms[u.forms.length - 1];

                    String str = u + " - " + UtilPC.lvText(f, e.getValue().getLvs())[0];

                    DefaultMutableTreeNode nuv = new DefaultMutableTreeNode(str);
                    nlv.add(nuv);
                }

                int[] nyc = blu.nyc;

                String strcs = "castle: " + nyc[0] + ", " + nyc[1] + ", " + nyc[2];

                DefaultMutableTreeNode ncs = new DefaultMutableTreeNode(strcs);
                nli.add(ncs);

                DefaultMutableTreeNode nlp = new DefaultMutableTreeNode("lineup/");
                nli.add(nlp);

                for (Form[] fs : blu.lu.fs)
                    for (Form f : fs)
                        if (f != null) {
                            DefaultMutableTreeNode nf = new DefaultMutableTreeNode(f.toString());
                            nlp.add(nf);
                        }
            }
        }

        jlf.setModel(new DefaultTreeModel(top));
    }

    private void setT$Entity(DefaultMutableTreeNode top, CustomEntity ce) {
        DefaultMutableTreeNode ti = new DefaultMutableTreeNode("info/");

        top.add(ti);

        ti.add(new DefaultMutableTreeNode("hp: " + ce.getHp()));
        ti.add(new DefaultMutableTreeNode("hb: " + ce.getHb()));
        ti.add(new DefaultMutableTreeNode("speed: " + ce.getSpeed()));
        ti.add(new DefaultMutableTreeNode("range: " + ce.getRange()));
        ti.add(new DefaultMutableTreeNode("barrier: " + ce.getProc().BARRIER.health));

        for (int i = 0; i < ce.getAtkCount(); i++) {
            AtkDataModel am = (AtkDataModel) ce.getAtkModel(i);

            ti = new DefaultMutableTreeNode(am + "/");

            top.add(ti);

            ti.add(new DefaultMutableTreeNode("atk: " + am.atk));
            ti.add(new DefaultMutableTreeNode("pre: " + am.pre));
            ti.add(new DefaultMutableTreeNode("LD: " + am.ld0 + " - " + am.ld1));
        }
    }

    private void setT$MA(Queue<String> qs) {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("maanim/");
        MaAnim ma = null;

        try {
            ma = new MaAnim(qs);
        } catch (Exception ignored) {

        }

        if (ma != null) {
            for (Part p : ma.parts) {
                DefaultMutableTreeNode prt = new DefaultMutableTreeNode(p.name + "/");
                top.add(prt);
            }
        }

        jlf.setModel(new DefaultTreeModel(top));
    }

    private void setT$Pack(InputStream is, String id) {
        String content = CommonStatic.ctx.noticeErr(() -> {
            try {
                return IOUtils.toString(is, StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }, Context.ErrType.WARN, "Failed to load pack data in this backup");

        if(content == null)
            return;

        PackData p = CommonStatic.ctx.noticeErr(() -> {
            try {
                PackData.UserPack pack = UserProfile.readBackupPack(content, id);
                pack.load();

                return pack;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }, Context.ErrType.WARN, "Failed to load pack data in this backup");

        if(p == null)
            return;

        DefaultMutableTreeNode top = new DefaultMutableTreeNode(p + "/");
        DefaultMutableTreeNode tene = new DefaultMutableTreeNode("enemies/");

        top.add(tene);

        for (Enemy e : p.enemies.getList()) {
            DefaultMutableTreeNode te = new DefaultMutableTreeNode(e + "/");
            tene.add(te);
            setT$Entity(te, (CustomEntity) e.de);
        }

        DefaultMutableTreeNode tuni = new DefaultMutableTreeNode("units/");
        top.add(tuni);

        for (Unit u : p.units.getList()) {
            DefaultMutableTreeNode tu = new DefaultMutableTreeNode(u + "/");
            tuni.add(tu);

            for (Form f : u.forms) {
                DefaultMutableTreeNode tf = new DefaultMutableTreeNode(f + "/");
                tu.add(tf);

                setT$Entity(tf, (CustomEntity) f.du);
            }
        }

        DefaultMutableTreeNode tulv = new DefaultMutableTreeNode("UnitLevel/");
        top.add(tulv);

        for (UnitLevel ul : p.unitLevels.getList()) {
            DefaultMutableTreeNode tlv = new DefaultMutableTreeNode(ul.toString());

            tlv.add(tlv);
        }

        DefaultMutableTreeNode tmc = new DefaultMutableTreeNode("stages/");
        top.add(tmc);

        String[] stastr = get(1, "t", 7);

        for (StageMap sm : MapColc.get(p.getSID()).maps) {
            DefaultMutableTreeNode tsm = new DefaultMutableTreeNode(sm + "/");

            tmc.add(tsm);

            for (Stage st : sm.list) {
                DefaultMutableTreeNode tst = new DefaultMutableTreeNode(st + "/");
                tsm.add(tst);

                SCDef.Line[] info = st.data.getSimple();

                Object[][] data = new Object[info.length][7];

                for (int i = 0; i < info.length; i++) {
                    int ind = info.length - i - 1;

                    data[ind][1] = info[i].enemy.pack+" - "+info[i].enemy.id;
                    data[ind][0] = info[i].boss == 1 ? "boss " : "";
                    data[ind][2] = info[i].multiple;
                    data[ind][3] = info[i].number == 0 ? "infinite" : info[i].number;
                    data[ind][4] = info[i].castle_0 + "%";
                    data[ind][5] = info[i].spawn_0;

                    if (info[i].respawn_0 == info[i].respawn_1)
                        data[ind][6] = info[i].respawn_0;
                    else
                        data[ind][6] = info[i].respawn_0 + "~" + info[i].respawn_1;
                }

                for (Object[] dat : data) {
                    DefaultMutableTreeNode te = new DefaultMutableTreeNode("" + dat[0] + dat[1] + "/");
                    tst.add(te);

                    for (int i = 2; i < dat.length; i++) {
                        DefaultMutableTreeNode ti = new DefaultMutableTreeNode(stastr[i] + ": " + dat[i]);

                        te.add(ti);
                    }
                }
            }
        }

        DefaultMutableTreeNode tbg = new DefaultMutableTreeNode("background/");
        top.add(tbg);

        for (Background bg : p.bgs.getList()) {
            DefaultMutableTreeNode tb = new DefaultMutableTreeNode(bg + "/");
            tbg.add(tb);
        }

        DefaultMutableTreeNode tcg = new DefaultMutableTreeNode("CharaGroup/");
        top.add(tcg);

        for (CharaGroup cg : p.groups.getList()) {
            DefaultMutableTreeNode tc = new DefaultMutableTreeNode(cg + "/");
            tcg.add(tc);

            for (Unit u : cg.set) {
                DefaultMutableTreeNode tu = new DefaultMutableTreeNode(u + "/");
                tc.add(tu);
            }
        }
        DefaultMutableTreeNode tlr = new DefaultMutableTreeNode("LvRestrict/");

        for (LvRestrict lr : p.lvrs.getList()) {
            DefaultMutableTreeNode tl = new DefaultMutableTreeNode(lr + "/");
            tlr.add(tl);

            for (CharaGroup cg : lr.res.keySet()) {
                String str = cg.getID().pack + cg + ": " + Form.lvString(lr.res.get(cg));

                DefaultMutableTreeNode tc = new DefaultMutableTreeNode(str);
                tl.add(tc);
            }

            String str = "General: " + Form.lvString(lr.all);

            DefaultMutableTreeNode tc = new DefaultMutableTreeNode(str);
            tl.add(tc);

            for (int i = 0; i < lr.rares.length; i++) {
                str = Interpret.RARITY[i] + ": " + Form.lvString(lr.rares[i]);

                tc = new DefaultMutableTreeNode(str);

                tl.add(tc);
            }
        }

        top.add(tlr);

        jlf.setModel(new DefaultTreeModel(top));
    }

    private void setTree(VFileRoot vfr, String name) {
        if (vfr == null) {
            jls.setModel(new DefaultTreeModel(null));

            return;
        }

        DefaultMutableTreeNode top = new DefaultMutableTreeNode(name + "/");

        addTree(top, vfr);

        jls.setModel(new DefaultTreeModel(top));
    }

}
