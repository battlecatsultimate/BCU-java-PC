package page.info.edit;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.lang.reflect.Field;
import java.util.function.Consumer;

import javax.swing.JComponent;

import common.CommonStatic;
import common.pack.Context.ErrType;
import common.util.Data;
import common.util.lang.Editors.EditControl;
import common.util.lang.Editors.Editor;
import common.util.lang.Editors.EditorGroup;
import common.util.lang.Editors.EditorSupplier;
import common.util.lang.Editors;
import common.util.lang.ProcLang;
import page.JBTN;
import page.JL;
import page.JTF;
import page.Page;

public abstract class SwingEditor extends Editor {

	public static class BoolEditor extends SwingEditor {

		public final JBTN input;

		public BoolEditor(EditorGroup eg, Field field, String f) throws Exception {
			super(eg, field, f);
			input = new JBTN(ProcLang.get().get(eg.proc).get(f));
			input.setLnr(this::edit);
		}

		@Override
		public void resize(int x, int y, int x0, int y0, int w0, int h0) {
			Page.set(input, x, y, x0, y0, w0, h0);
		}

		@Override
		public void setData() {
			Object obj = par.obj;
			Data.err(() -> input.setSelected(obj != null && field.getBoolean(obj)));
			input.setEnabled(obj != null);
		}

		@Override
		protected void add(Consumer<JComponent> con) {
			con.accept(input);
		}

		private final void edit(ActionEvent fe) {
			Data.err(() -> field.set(par.obj, input.isSelected()));
			update();
		}

	}

	public static class EditCtrl implements EditorSupplier {

		@Override
		public Editor getEditor(EditControl<?> ctrl, EditorGroup group, String f, boolean edit) {
			try {
				Field field = ctrl.getField(f);
				Class<?> fc = field.getType();
				if (fc == int.class) {
					return new IntEditor(group, field, f, edit);
				}
				if (fc == boolean.class)
					return new BoolEditor(group, field, f);
				throw new Exception("unexpected class " + fc);
			} catch (Exception e) {
				CommonStatic.ctx.noticeErr(e, ErrType.ERROR, "failed to generate editor");
			}
			return null;
		}

	}

	public static class IntEditor extends SwingEditor {

		private final boolean edit;
		public final JL label;
		public final JTF input = new JTF();

		public IntEditor(EditorGroup eg, Field field, String f, boolean edit) throws Exception {
			super(eg, field, f);
			this.edit = edit;
			label = new JL(ProcLang.get().get(eg.proc).get(f));
			input.setLnr(this::edit);
		}

		@Override
		public void resize(int x, int y, int x0, int y0, int w0, int h0) {
			Page.set(label, x, y, x0, y0, 150, h0);
			Page.set(input, x, y, x0 + 150, y0, w0 - 150, h0);
		}

		@Override
		public void setData() {
			Object obj = par.obj;
			if (obj == null)
				input.setText("");
			else
				Data.err(() -> input.setText("" + field.getInt(obj)));
			input.setEnabled(edit && obj != null);
			update();
		}

		@Override
		protected void add(Consumer<JComponent> con) {
			con.accept(label);
			con.accept(input);
		}

		private final void edit(FocusEvent fe) {
			Object val = Data.ignore(() -> Integer.parseInt(input.getText()));
			if (val != null)
				Data.err(() -> field.set(par.obj, val));
			update();
		}

	}

	static {
		Editors.setEditorSupplier(new EditCtrl());
	}

	public SwingEditor(EditorGroup par, Field field, String f) throws Exception {
		super(par, field, f);
	}

	public abstract void resize(int x, int y, int x0, int y0, int w0, int h0);

	protected abstract void add(Consumer<JComponent> con);

}
