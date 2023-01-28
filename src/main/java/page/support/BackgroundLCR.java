package page.support;

import common.util.pack.Background;

import javax.swing.*;
import java.awt.*;

public class BackgroundLCR extends DefaultListCellRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if(!(component instanceof JLabel))
            return component;

        if(!(value instanceof Background))
            return component;

        JLabel jl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        //Set text here

        return jl;
    }
}
