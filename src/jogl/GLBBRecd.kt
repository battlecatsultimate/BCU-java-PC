package jogl

import common.battle.BattleField
import jogl.GLRecorder
import page.battle.BBRecd
import page.battle.BattleBox.OuterBox

internal class GLBBRecd(bip: OuterBox?, bf: BattleField?, path: String?, type: Int) : GLBattleBox(bip, bf, 0), BBRecd {
    private val glr: GLRecorder
    private var time = 0
    override fun end() {
        glr.end()
    }

    override fun info(): String {
        return "" + glr.remain()
    }

    override fun paint() {
        super.paint()
        if (bbp.bf.sb.time > time) {
            glr.update()
            time = bbp.bf.sb.time
        }
    }

    override fun quit() {
        glr.quit()
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        glr = GLRecorder.Companion.getIns(this, path, type, bip)
        glr.start()
    }
}
