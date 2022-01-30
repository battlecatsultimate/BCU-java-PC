package page;

import common.CommonStatic;

import javax.swing.*;

public class SavePage extends Page {
    private static final long serialVersionUID = 1L;

    private final JLabel save = new JLabel(Page.get(MainLocale.PAGE, "savepro"));

    protected SavePage() {
        super(null);

        MainFrame.closeClicked = true;

        ini();
        resized();

        new Thread(this::finishJob).start();
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
            CommonStatic.def.save(true, false);

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
