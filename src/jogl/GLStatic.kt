package jogl

import com.jogamp.opengl.GLCapabilities
import com.jogamp.opengl.GLProfile
import main.MainBCU

object GLStatic {
    const val MIP = false
    val GLP: GLProfile? = null
    val GLC: GLCapabilities? = null
    var ALWAYS_GLIMG = true
    var GLTEST: Boolean = !MainBCU.WRITE
    var JOGL_SHADER = true
    var ALL_BIMG = true

    init {
        GLP = GLProfile.get(GLProfile.GL2)
        GLC = GLCapabilities(GLP)
    }
}
