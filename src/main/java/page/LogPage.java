package page;

import common.CommonStatic;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class LogPage extends Page {

    private static final long serialVersionUID = 1L;

    private final JBTN back = new JBTN(0, "back");
    private final JBTN show = new JBTN(0, "show");
    private final JL jllg = new JL(0, "logs");
    private final JL jlfi = new JL(0, "log");
    private final JL jldt = new JL(0, "date");
    private final JL jldv = new JL();
    private final Vector<File> files = new Vector<>();
    private final JList<File> logs = new JList<>();
    private final JTextArea text = new JTextArea();
    private final JScrollPane jslg = new JScrollPane(logs);
    private final JScrollPane jsfi = new JScrollPane(text);
    private final SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

    public LogPage(Page p) {
        super(p);

        ini();
        addListeners();
    }

    @Override
    protected void resized(int x, int y) {
        setBounds(0, 0, x, y);
        set(back, x, y, 0, 0, 200, 50);
        set(jllg, x, y, 50, 100, 300, 50);
        set(jlfi, x, y, 350, 100, 750, 50);

        set(jslg, x, y, 50, 150, 300, 800);
        set(jsfi, x, y, 350, 150, 750, 800);

        set(jldt, x, y, 1200, 150, 200, 50);
        set(jldv, x, y, 1400, 150, 350, 50);
        set(show, x, y, 1200, 225, 200, 50);
    }

    private void ini() {
        add(back);
        add(jllg);
        add(jlfi);
        add(jslg);
        add(jsfi);
        add(jldt);
        add(jldv);
        add(show);

        File log = new File(CommonStatic.ctx.getBCUFolder(), "./logs");
        if (log.exists() && log.isDirectory()) {
            File[] fs = log.listFiles();
            if (fs == null)
                return;

            files.addAll(Arrays.asList(fs));
            files.removeIf(f -> {
                try {
                    format.parse(f.getName());
                    return !(new Scanner(f).hasNextLine());
                } catch (Exception e) {
                    return true;
                }
            });
            files.sort((f1, f2) -> {
                try {
                    long s1Num = format.parse(f1.getName()).getTime();
                    long s2Num = format.parse(f2.getName()).getTime();
                    return Long.compare(s1Num, s2Num) * -1;
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            });
            logs.setListData(files);
        }

        show.setEnabled(false);
        text.setEditable(false);
        logs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void addListeners() {
        back.addActionListener(arg0 -> changePanel(getFront()));

        show.addActionListener(x -> {
            File f = logs.getSelectedValue();
            try {
                Desktop.getDesktop().open(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        logs.addListSelectionListener(x -> {
            boolean selected = logs.getSelectedIndex() > -1;
            show.setEnabled(selected);
            if (selected) {
                File f = logs.getSelectedValue();
                try {
                    List<String> s = Files.readAllLines(Paths.get(f.getPath()));
                    StringBuilder sb = new StringBuilder();
                    for (String str : s)
                        sb.append(str).append("\n");
                    text.setText(sb.toString());
                    text.setCaretPosition(0);

                    Date old = format.parse(f.getName());
                    String date = new SimpleDateFormat("yyyy MMM dd HH:mm:ss").format(old);
                    jldv.setText(date);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                text.setText(null);
                jldv.setText(null);
            }
        });
    }

    @Override
    protected JButton getBackButton() {
        return back;
    }
}
