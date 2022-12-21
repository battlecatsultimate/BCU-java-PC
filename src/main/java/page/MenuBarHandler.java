package page;

import io.BCUWriter;
import main.Opts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
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
        MainFrame.F.setJMenuBar(bar);
    }

    private static void setFileItems() {
        JMenu menu = new JMenu("File");
        bar.add(menu);
        int shortcut = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

        JMenuItem save = new JMenuItem("Save All");
        save.setAccelerator(KeyStroke.getKeyStroke('S', shortcut));
        save.addActionListener(e -> {
            BCUWriter.writeData();
            Opts.pop("Successfully saved data.", "Save Confirmation");
        });

        //FIXME Please find a way to organize menu bar
        JMenuItem back = new JMenuItem("Go Back");
        back.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        back.addActionListener(e -> {
            if(!(MainFrame.getPanel() instanceof MainPage) && !(MainFrame.getPanel() instanceof SavePage)) {
                MainFrame.changePanel(MainFrame.getPanel().getFront());
            }
        });

        save.setEnabled(false);
        back.setEnabled(false);

        menu.add(save);
        menu.add(back);
        fileItems.add(save);
        fileItems.add(back);
    }

    public static JMenuItem getFileItem(String n) {
        for (JMenuItem i : fileItems) {
            if (i.getText().equals(n))
                return i;
        }
        System.out.println("Missing menu item: " + n);
        return null;
    }
}
