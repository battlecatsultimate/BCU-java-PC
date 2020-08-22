package jogl

import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.GLEventListener
import com.jogamp.opengl.awt.GLCanvas
import jogl.util.ResManager
import java.awt.AWTException
import java.awt.Robot
import java.awt.image.BufferedImage

abstract class GLCstd protected constructor() : GLCanvas(GLStatic.GLC), GLEventListener {
    override fun dispose(drawable: GLAutoDrawable) {
        ResManager.Companion.get(drawable.getGL().getGL2()).dispose()
    }

    val screen: BufferedImage?
        get() = try {
            val r = bounds
            val p = locationOnScreen
            r.x = p.x
            r.y = p.y
            Robot().createScreenCapture(r)
        } catch (e: AWTException) {
            e.printStackTrace()
            null
        }

    override fun init(drawable: GLAutoDrawable) {}
    override fun reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {}

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        addGLEventListener(this)
    }
}
