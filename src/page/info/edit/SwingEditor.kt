package page.info.edit

import common.CommonStatic
import common.pack.Context
import common.pack.Context.ErrType
import common.pack.Context.SupExc
import common.pack.IndexContainer.Indexable
import common.pack.PackData
import common.util.Data
import common.util.Data.Proc
import common.util.lang.Editors
import common.util.lang.Editors.EdiField
import common.util.lang.Editors.EditControl
import common.util.lang.Editors.EditorGroup
import common.util.lang.Editors.EditorSupplier
import common.util.lang.Formatter
import common.util.lang.ProcLang
import common.util.pack.Background
import common.util.unit.AbEnemy
import common.util.unit.Unit
import page.*
import utilpc.UtilPC
import java.awt.event.ActionEvent
import java.awt.event.FocusEvent
import java.awt.image.BufferedImage
import java.util.function.Consumer
import java.util.function.Supplier
import javax.swing.ImageIcon
import javax.swing.JComponent

abstract class SwingEditor(par: EditorGroup?, field: EdiField?, f: String?, var edit: Boolean) : Editors.Editor(par, field, f) {
    class BoolEditor(eg: EditorGroup, field: EdiField?, f: String?, edit: Boolean) : SwingEditor(eg, field, f, edit) {
        val input: JTG
        override fun resize(x: Int, y: Int, x0: Int, y0: Int, w0: Int, h0: Int) {
            Page.Companion.set(input, x, y, x0, y0, w0, h0)
        }

        override fun setData() {
            field.setData(par.obj)
            Data.Companion.err(Context.RunExc { input.isSelected = field.obj != null && field.getBoolean() })
            input.isEnabled = field.obj != null
        }

        protected override fun add(con: Consumer<JComponent?>) {
            con.accept(input)
        }

        private fun edit(fe: ActionEvent) {
            Data.Companion.err(Context.RunExc { field.setBoolean(input.isSelected) })
            update()
        }

        init {
            input = JTG(ProcLang.Companion.get().get(eg.proc).get(f))
            input.setLnr(Consumer { fe: ActionEvent -> edit(fe) })
        }
    }

    class EditCtrl(private val isEnemy: Boolean, table: EntityEditPage) : EditorSupplier {
        private val table: EntityEditPage
        override fun getEditor(ctrl: EditControl<*>, group: EditorGroup, f: String?, edit: Boolean): Editors.Editor? {
            try {
                val field: EdiField = ctrl.getField(f)
                val fc: Class<*> = field.getType()
                if (fc == Int::class.javaPrimitiveType) {
                    return IntEditor(group, field, f, edit)
                }
                if (fc == Boolean::class.javaPrimitiveType) return BoolEditor(group, field, f, edit)
                val proc: String = group.proc
                if (fc == PackData.Identifier::class.java) {
                    if (proc == "THEME") return IdEditor<Background>(group, field, f, Supplier<SupPage<Background>> { table.getBGSup() }, table, edit) else if (proc == "SUMMON") return if (isEnemy) IdEditor<AbEnemy>(group, field, f, Supplier<SupPage<AbEnemy>> { table.getEnemySup() }, table, edit) else IdEditor<Unit>(group, field, f, Supplier<SupPage<Unit>> { table.getUnitSup() }, table, edit)
                }
                throw Exception("unexpected class $fc")
            } catch (e: Exception) {
                CommonStatic.ctx.noticeErr(e, ErrType.ERROR, "failed to generate editor")
            }
            return null
        }

        init {
            this.table = table
        }
    }

    class IdEditor<T : Indexable<*, T>?>(par: EditorGroup, field: EdiField?, f: String?, page: Supplier<SupPage<T>>, table: EntityEditPage,
                                         edit: Boolean) : SwingEditor(par, field, f, edit) {
        private val page: Supplier<SupPage<T>>
        private val table: EntityEditPage
        val input: JBTN
        val jl: JL
        fun callback(id: PackData.Identifier<T>?) {
            Data.Companion.err(Context.RunExc { field.set(id) })
            update()
        }

        override fun resize(x: Int, y: Int, x0: Int, y0: Int, w0: Int, h0: Int) {
            page.Page.Companion.set(input, x, y, x0, y0, 150, h0)
            page.Page.Companion.set(jl, x, y, x0 + 150, y0, w0 - 150, h0)
        }

        protected override fun add(con: Consumer<JComponent?>) {
            con.accept(input)
            con.accept(jl)
        }

        protected override fun setData() {
            field.setData(par.obj)
            jl.text = "" + field.get()
            input.isEnabled = edit && field.obj != null
        }

        private fun edit(fe: ActionEvent) {
            val sup: SupPage<T> = page.get()
            table.putWait(this, sup)
            MainFrame.Companion.changePanel(sup.getThisPage())
        }

        init {
            this.page = page
            this.table = table
            input = JBTN(ProcLang.Companion.get().get(par.proc).get(f))
            jl = JL("")
            input.setLnr(Consumer { fe: ActionEvent -> edit(fe) })
        }
    }

    class IntEditor(eg: EditorGroup, field: EdiField?, f: String?, edit: Boolean) : SwingEditor(eg, field, f, edit) {
        val label: JL
        val input: JTF = JTF()
        override fun resize(x: Int, y: Int, x0: Int, y0: Int, w0: Int, h0: Int) {
            Page.Companion.set(label, x, y, x0, y0, 150, h0)
            Page.Companion.set(input, x, y, x0 + 150, y0, w0 - 150, h0)
        }

        override fun setData() {
            field.setData(par.obj)
            if (field.obj == null) input.text = "" else input.text = "" + field.getInt()
            input.isEnabled = edit && field.obj != null
        }

        protected override fun add(con: Consumer<JComponent?>) {
            con.accept(label)
            con.accept(input)
        }

        private fun edit(fe: FocusEvent) {
            val `val`: Any = Data.Companion.ignore<Int>(SupExc<Int> { input.text.toInt() })
            if (`val` != null) Data.Companion.err(Context.RunExc { field.set(`val`) })
            update()
        }

        init {
            this.edit = edit
            label = JL(ProcLang.Companion.get().get(eg.proc).get(f))
            input.setLnr(Consumer<FocusEvent> { fe: FocusEvent -> edit(fe) })
        }
    }

    class SwingEG(ind: Int, edit: Boolean, cb: Runnable?, ctx: Formatter.Context?) : EditorGroup(Proc.Companion.getName(ind), edit, cb) {
        val jlm: JL
        override fun setData(obj: Any?) {
            super.setData(obj)
            jlm.getLSC().update()
        }

        init {
            jlm = JL(this.getItem(ctx))
            val v: BufferedImage = UtilPC.getIcon(1, ind)
            if (v != null) jlm.icon = ImageIcon(v)
        }
    }

    abstract fun resize(x: Int, y: Int, x0: Int, y0: Int, w0: Int, h0: Int)
    abstract fun add(con: Consumer<JComponent?>)
}
