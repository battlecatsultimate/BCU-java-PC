package page;

import io.BCUWriter;
import main.MainBCU;
import main.Opts;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class MenuBarHandler {
    private static final JMenuBar bar = new JMenuBar();

    private static final List<JMenuItem> fileItems = new ArrayList<>();

    public static JMenuBar getBar() {
        return bar;
    }

    public static void initialize() {
        setFileItems();
        setViewItems();
        MainFrame.F.setJMenuBar(bar);
    }

    private static void setFileItems() {
        JMenu menu = new JMenu("File");
        JMenu history = new JMenu("History");

        bar.add(menu);
        bar.add(history);

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

        save.setEnabled(false);
        back.setEnabled(false);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> MainFrame.F.dispatchEvent(new WindowEvent(MainFrame.F, WindowEvent.WINDOW_CLOSING)));

        menu.add(save);
        menu.add(exit);
        history.add(back);

        fileItems.add(save);
        fileItems.add(back);
    }

    private static void setViewItems() {
        JMenu view = new JMenu("View");
        JCheckBoxMenuItem light = new JCheckBoxMenuItem("Light theme");
        JCheckBoxMenuItem background = new JCheckBoxMenuItem("Custom background");
        JCheckBoxMenuItem borderless = new JCheckBoxMenuItem("Borderless window");
        light.addActionListener(e -> MainFrame.setLightTheme(light.isSelected()));
        background.addActionListener(e -> MainFrame.setCustomBG(background.isSelected()));
        borderless.addActionListener(e -> MainFrame.setBorderless(borderless.isSelected()));
        // the states can change in the config page too, so they are read when the menu opens
        view.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                light.setSelected(MainBCU.light);
                background.setSelected(MainBCU.customBG);
                borderless.setSelected(MainBCU.borderless);

                // both only apply to the Nimbus theme
                light.setEnabled(MainBCU.nimbus);
                background.setEnabled(MainBCU.nimbus);
            }
            @Override
            public void menuDeselected(MenuEvent e) {
            }
            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });
        /**
         * dark/light theme toggle is technically functional,
         * but theres lots of side effects that aren't handled correctly when toggling inside of pages
         * so the button is simply commented out for now
         */
        // view.add(light);
        view.add(background);
        view.add(borderless);
        bar.add(view);
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
