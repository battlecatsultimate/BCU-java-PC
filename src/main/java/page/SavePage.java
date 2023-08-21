package page;

import common.CommonStatic;

import javax.swing.*;

public class SavePage extends Page {
    private static final long serialVersionUID = 1L;

    private final JLabel save = new JLabel(Page.get(MainLocale.PAGE, "savepro"));
    private boolean[] saveOpts = new boolean[]{true, false};

    protected SavePage() {
        super(null);

        MainFrame.closeClicked = true;

        ini();
        resized(true);

        new Thread(this::finishJob).start();
    }

    protected SavePage(boolean[] backup) {
        this();
        saveOpts = backup;
        setVisible(saveOpts[0]);
    }

    @Override
    protected JButton getBackButton() {
        return null;
    }

    @Override
    protected void resized(int x, int y) {
        setBounds(0, 0, x, y);
        set(save, x, y,900, 625, 500, 50);
    }

    private void ini() {
        add(save);
    }

    private void finishJob() {
        try {
            CommonStatic.def.save(saveOpts[0], false);

            setVisible(false);

            MainFrame.exitAll();

            changePanel(null);

            MainFrame.F.dispose();

            System.gc();

            Thread.sleep(5000);

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();

            System.exit(0);
        }
    }
}
