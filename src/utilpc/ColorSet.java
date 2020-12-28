package utilpc;

import javax.swing.*;
import java.awt.*;

public class ColorSet {
	public final Color CONTROL;

	public final Color INFO;
	public final Color NIMBUS_BASE;
	public final Color NIMBUS_ALERT_YELLOW;
	public final Color NIMBUS_DISABLED_TEXT;
	public final Color NIMBUS_FOCUS;
	public final Color NIMBUS_GREEN;
	public final Color NIMBUS_INFO_BLUE;
	public final Color NIMBUS_LIGHT_BG;
	public final Color NIMBUS_ORANGE;
	public final Color NIMBUS_RED;
	public final Color NIMBUS_SELECT_TEXT;
	public final Color NIMBUS_SELECT_BG;
	public final Color NIMBUS_TEXT;

	ColorSet(Color control, Color info, Color nb, Color nay, Color ndt, Color nf, Color ng, Color nib, Color nlb,
			Color no, Color nr, Color nst, Color nsb, Color nt) {
		this.CONTROL = control;
		this.INFO = info;
		this.NIMBUS_BASE = nb;
		this.NIMBUS_ALERT_YELLOW = nay;
		this.NIMBUS_DISABLED_TEXT = ndt;
		this.NIMBUS_FOCUS = nf;
		this.NIMBUS_GREEN = ng;
		this.NIMBUS_INFO_BLUE = nib;
		this.NIMBUS_LIGHT_BG = nlb;
		this.NIMBUS_ORANGE = no;
		this.NIMBUS_RED = nr;
		this.NIMBUS_SELECT_TEXT = nst;
		this.NIMBUS_SELECT_BG = nsb;
		this.NIMBUS_TEXT = nt;
	}

	public void setTheme() {
		UIManager.put("control", CONTROL);
		UIManager.put("info", INFO);
		UIManager.put("nimbusBase", NIMBUS_BASE);
		UIManager.put("nimbusAlertYellow", NIMBUS_ALERT_YELLOW);
		UIManager.put("nimbusDisabledText", NIMBUS_DISABLED_TEXT);
		UIManager.put("nimbusFocus", NIMBUS_FOCUS);
		UIManager.put("nimbusGreen", NIMBUS_GREEN);
		UIManager.put("nimbusInfoBlue", NIMBUS_INFO_BLUE);
		UIManager.put("nimbusLightBackground", NIMBUS_LIGHT_BG);
		UIManager.put("nimbusOrange", NIMBUS_ORANGE);
		UIManager.put("nimbusRed", NIMBUS_RED);
		UIManager.put("nimbusSelectedText", NIMBUS_SELECT_TEXT);
		UIManager.put("nimbusSelectionBackground", NIMBUS_SELECT_BG);
		UIManager.put("text", NIMBUS_TEXT);
		UIManager.put("List[Selected].textForeground", NIMBUS_TEXT);
		UIManager.put("Table.textForeground", NIMBUS_TEXT);

		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
