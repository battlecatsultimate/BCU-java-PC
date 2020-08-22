package page;

public class DefaultPage extends Page {

    private static final long serialVersionUID = 1L;

    private final JBTN back = new JBTN(0, "back");

    protected DefaultPage(Page p) {
        super(p);

        ini();
        resized();
    }

    @Override
    protected void resized(int x, int y) {
        setBounds(0, 0, x, y);
        set(back, x, y, 0, 0, 200, 50);
    }

    private void addListeners() {
        back.setLnr(e -> changePanel(getFront()));
    }

    private void ini() {
        add(back);
        addListeners();
    }

}
