package page;

import common.CommonStatic;
import io.BCUWriter;
import main.MainBCU;
import main.Opts;
import page.view.ViewBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class MenuBarHandler {

    static CommonStatic.Config cfg() {
        return CommonStatic.getConfig();
    }

    private static final JMenuBar bar = new JMenuBar();

    private static final List<JMenuItem> fileItems = new ArrayList<>();

    public static JMenuBar getBar() {
        return bar;
    }

    public static void initialize() {
        setFileItems();
        MainFrame.F.setJMenuBar(bar);
    }

    private static void setFileItems() {
        JMenu menu = new JMenu("File");
        JMenu history = new JMenu("History");
        JMenu settings = new JMenu("Settings");

        bar.add(menu);
        bar.add(history);
        bar.add(settings);

        int shortcut = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

        JMenuItem save = new JMenuItem("Save All");
        save.setAccelerator(KeyStroke.getKeyStroke('S', shortcut));
        save.addActionListener(e -> {
            BCUWriter.writeData();
            Opts.pop("Successfully saved data.", "Save Confirmation");
        });

        JMenuItem back = new JMenuItem("Go Back");
        back.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        back.addActionListener(e -> {
            if(MainFrame.getPanel().getBackButton() != null) {
                MainFrame.getPanel().getBackButton().doClick();
            }
        });

        JMenuItem home = new JMenuItem("Main Menu");
        home.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_HOME, 0));
        home.addActionListener(arg0 -> {
            MainFrame.changePanel(new MainPage());

        });

        JCheckBoxMenuItem realvl = new JCheckBoxMenuItem("Real Leveling");
        realvl.setAccelerator(KeyStroke.getKeyStroke('R', shortcut));
        realvl.addActionListener(e -> {
            cfg().realLevel = realvl.isSelected();
        });

        JCheckBoxMenuItem whitebg = new JCheckBoxMenuItem("White BG");
        whitebg.setAccelerator(KeyStroke.getKeyStroke('W', shortcut));
        whitebg.addActionListener(e -> {
            ViewBox.Conf.white = whitebg.isSelected();
        });

        JCheckBoxMenuItem fps = new JCheckBoxMenuItem("60fps");
        fps.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_NUM_LOCK, 0));
        fps.addActionListener(e -> {
            cfg().performanceModeAnimation = !cfg().performanceModeAnimation;
            cfg().performanceModeBattle = !cfg().performanceModeBattle; // Will need to add a setting that disables it on the config page.
        });

        save.setEnabled(false);
        back.setEnabled(false);
        home.setEnabled(false);
        fps.setEnabled(true);
        realvl.setEnabled(true);
        whitebg.setEnabled(true);

        menu.add(save);
        history.add(back);
        history.add(home);
        settings.add(fps);
        settings.add(realvl);
        settings.add(whitebg);

        fileItems.add(save);
        fileItems.add(back);
        fileItems.add(home);
        fileItems.add(fps);
        fileItems.add(realvl);
        fileItems.add(whitebg);
    }

    public static JMenuItem getFileItem(String n) {
        for (JMenuItem i : fileItems) {
            if (i.getText().equals(n))
                return i;
        }

        if(MainBCU.loaded) {
            System.out.println("Missing menu item: " + n);
        }

        return null;
    }
}
