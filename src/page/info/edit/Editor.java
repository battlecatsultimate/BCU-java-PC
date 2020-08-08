package page.info.edit;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import common.CommonStatic;
import common.util.Data.Proc;
import common.util.Data.Proc.IntType.BitCount;
import common.util.lang.ProcLang;
import common.util.lang.ProcLang.ItemLang;
import page.JBTN;
import page.JL;
import page.JTF;

public abstract class Editor {

	public static class BoolEditor extends Editor {

		public final JBTN input;

		public BoolEditor(EditorGroup eg, Field field, String f) throws Exception {
			super(eg, field, f);
			input = new JBTN(ProcLang.get().get(eg.proc).get(f));
			input.setLnr(this::edit);
		}

		public void setData(Object obj) throws Exception {
			this.obj = obj;
			input.setSelected(obj != null && field.getBoolean(obj));
			input.setEnabled(obj != null);
		}

		private final void edit(ActionEvent fe) {
			try {
				field.set(obj, input.isSelected());
			} catch (Exception e) {
				e.printStackTrace();
			}
			update();
		}

	}

	public static interface DispPipe {

		public String display(int val);

	}

	public static abstract class EditControl {

		private static class BitPipe implements EditPipe {

			private final int bits;

			private BitPipe(int bits) {
				this.bits = bits;
			}

			@Override
			public int edit(String str) {
				int val = CommonStatic.parseIntN(str);
				if (val < 0)
					return 0;
				if (val > (1 << bits) - 1)
					return (1 << bits) - 1;
				return val;
			}

		}

		private final Class<?> cls;

		public EditControl(Class<?> cls) {
			this.cls = cls;
		}

		public DispPipe getDispPipe(String proc, Field field, String f) throws Exception {
			DispPipe spec = Pipes.DISPMAP.get(proc + "." + f);
			if (spec != null)
				return spec;
			DispPipe pipe = Pipes.DISPMAP.get(f);
			if (pipe != null)
				return pipe;
			return Integer::toString;
		}

		public Editor getEditor(EditorGroup group, String f, boolean edit) throws Exception {
			Field field;
			if (f.contains(".")) {
				String[] strs = f.split("\\.");
				field = cls.getField(strs[0]).getType().getField(strs[1]);
			} else
				field = cls.getField(f);
			Class<?> fc = field.getType();
			if (fc == int.class) {
				EditPipe pipe = getEditPipe(group.proc, field, f);
				DispPipe disp = getDispPipe(group.proc, field, f);
				return new IntEditor(group, field, f, pipe, disp, edit);
			}
			if (fc == boolean.class)
				return new BoolEditor(group, field, f);
			throw new Exception("unexpected class " + fc);

		}

		public EditPipe getEditPipe(String proc, Field field, String f) throws Exception {
			BitCount count = field.getAnnotation(BitCount.class);
			if (count != null)
				return new BitPipe(count.value());
			EditPipe spec = Pipes.EDITMAP.get(proc + "." + f);
			if (spec != null)
				return spec;
			EditPipe pipe = Pipes.EDITMAP.get(f);
			if (pipe != null)
				return pipe;
			return CommonStatic::parseIntN;
		}

		public void update(EditorGroup g) {
		}

	}

	public static class EditorGroup {

		protected final String proc;
		protected final Class<?> cls;
		public final Editor[] list;
		private final EditControl ctrl;

		public EditorGroup(String proc, boolean edit) throws Exception {
			this.proc = proc;
			this.cls = Proc.class.getDeclaredField(proc).getType();
			ctrl = MAP.get(cls);
			ItemLang item = ProcLang.get().get(proc);
			String[] arr = item.list();
			list = new Editor[arr.length];
			for (int i = 0; i < arr.length; i++) {
				list[i] = ctrl.getEditor(this, arr[i], edit);
			}
		}

	}

	public static interface EditPipe {

		public int edit(String str);

	}

	public static class IntEditor extends Editor {

		private final EditPipe pipe;
		private final DispPipe disp;
		private final boolean edit;
		public final JL label;
		public final JTF input = new JTF();

		public IntEditor(EditorGroup eg, Field field, String f, EditPipe pipe, DispPipe disp, boolean edit)
				throws Exception {
			super(eg, field, f);
			this.pipe = pipe;
			this.disp = disp;
			this.edit = edit;
			label = new JL(ProcLang.get().get(eg.proc).get(f));
			input.setLnr(this::edit);
		}

		public void setData(Object obj) throws Exception {
			this.obj = obj;
			if (obj == null)
				input.setText("");
			else
				input.setText(disp.display(field.getInt(obj)));
			input.setEnabled(edit && obj != null);
			update();
		}

		private final void edit(FocusEvent fe) {
			try {
				Object val = pipe.edit(input.getText());
				if (val != null)
					field.set(obj, val);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private static class Pipes {

		private static final Map<String, EditPipe> EDITMAP = new HashMap<>();
		private static final Map<String, DispPipe> DISPMAP = new HashMap<>();

		static {
			EDITMAP.put("POIATK.mult", Pipes::lim_0_100);
			EDITMAP.put("VOLC.time", Pipes::lim_0);

			EDITMAP.put("prob", Pipes::lim_0_100);
			EDITMAP.put("mult", Pipes::lim_0);
			EDITMAP.put("time", Pipes::lim_inf);
			EDITMAP.put("itv", Pipes::lim_inf);
			EDITMAP.put("count", Pipes::lim_inf);

			DISPMAP.put("KB.dis", Pipes::trea_dis);
			DISPMAP.put("STOP.time", Pipes::trea_time);
			DISPMAP.put("SLOW.time", Pipes::trea_time);
			DISPMAP.put("WEAK.time", Pipes::trea_time);

			DISPMAP.put("prob", Pipes::app_perc);
			DISPMAP.put("mult", Pipes::app_perc);
			DISPMAP.put("time", Pipes::app_time);
			DISPMAP.put("itv", Pipes::app_time);
		}

		private static String app_perc(int v) {
			return v + "%";
		}

		private static String app_time(int v) {
			return v < 0 ? "infinite" : v + "f"; // TODO
		}

		private static int lim_0(String str) {
			int val = CommonStatic.parseIntN(str);
			if (val < 0)
				return 0;
			return val;
		}

		private static int lim_0_100(String str) {
			int val = CommonStatic.parseIntN(str);
			if (val < 0)
				return 0;
			if (val > 100)
				return 100;
			return val;
		}

		private static int lim_inf(String str) {
			int val = CommonStatic.parseIntN(str);
			if (val < -1)
				return -1;
			return val;
		}

		private static String trea_dis(int v) {
			return (int) (v * 1.2) + "";
		}

		private static String trea_time(int v) {
			return (int) (v * 1.2) + "f";
		}

	}

	public static final Map<Class<?>, EditControl> MAP = new HashMap<>();

	static {
		// TODO add controls
	}

	private final EditorGroup par;
	protected final Field field;

	protected Object obj;

	public Editor(EditorGroup par, Field field, String f) throws Exception {
		this.par = par;
		this.field = field;
	}

	protected final void update() {
		par.ctrl.update(par);
	}

}
