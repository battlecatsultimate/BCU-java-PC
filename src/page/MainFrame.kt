package page

import common.CommonStatic
import common.system.P
import main.Printer
import page.Page
import utilpc.PP
import java.awt.AWTEvent
import java.awt.Font
import java.awt.Rectangle
import java.awt.Toolkit
import java.awt.event.*
import java.util.*
import javax.swing.JFrame
import javax.swing.UIManager
import javax.swing.WindowConstants
import javax.swing.plaf.FontUIResource

class MainFrame(ver: String) : JFrame(Page.Companion.get(0, "title") + " Ver " + ver) {
    private var settingsize = false
    private var changingPanel = false
    fun initialize() {
        F = this
        changePanel(LoadPage())
        Fresized()
    }

    fun sizer() {
        if (crect == null) {
            val screen = PP(Toolkit.getDefaultToolkit().screenSize)
            rect = PP(0, 0).toRectangle(P(screen.x, screen.y))
            setBounds(rect)
            setVisible(true)
            val nx: Int = rect!!.width - getRootPane().getWidth()
            val ny: Int = rect!!.height - getRootPane().getHeight()
            crect = rect
            if (nx != x || ny != y) {
                x = nx
                y = ny
            }
        } else {
            rect = crect
            setBounds(rect)
            setVisible(true)
        }
    }

    private fun addListener() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE)
        addWindowListener(object : WindowAdapter() {
            override fun windowActivated(arg0: WindowEvent?) {
                if (mainPanel != null) mainPanel!!.windowActivated()
            }

            override fun windowClosing(arg0: WindowEvent?) {
                setVisible(false)
                if (mainPanel != null) mainPanel!!.exitAll()
                CommonStatic.def.exit(true)
            }

            override fun windowDeactivated(arg0: WindowEvent?) {
                if (mainPanel != null) mainPanel!!.windowDeactivated()
            }
        })
        Toolkit.getDefaultToolkit().addAWTEventListener(object : AWTEventListener {
            override fun eventDispatched(event: AWTEvent) {
                if (event.getID() == KeyEvent.KEY_PRESSED && mainPanel != null) mainPanel!!.keyPressed(event as KeyEvent)
                if (event.getID() == KeyEvent.KEY_RELEASED && mainPanel != null) mainPanel!!.keyReleased(event as KeyEvent)
                if (event.getID() == KeyEvent.KEY_TYPED && mainPanel != null) mainPanel!!.keyTyped(event as KeyEvent)
            }
        }, AWTEvent.KEY_EVENT_MASK)
        Toolkit.getDefaultToolkit().addAWTEventListener(object : AWTEventListener {
            override fun eventDispatched(event: AWTEvent) {
                if (event.getID() == MouseEvent.MOUSE_PRESSED && mainPanel != null) mainPanel!!.mousePressed(event as MouseEvent)
                if (event.getID() == MouseEvent.MOUSE_RELEASED && mainPanel != null) mainPanel!!.mouseReleased(event as MouseEvent)
                if (event.getID() == MouseEvent.MOUSE_CLICKED && mainPanel != null) mainPanel!!.mouseClicked(event as MouseEvent)
            }
        }, AWTEvent.MOUSE_EVENT_MASK)
        Toolkit.getDefaultToolkit().addAWTEventListener(object : AWTEventListener {
            override fun eventDispatched(event: AWTEvent) {
                if (event.getID() == MouseEvent.MOUSE_MOVED && mainPanel != null) mainPanel!!.mouseMoved(event as MouseEvent)
                if (event.getID() == MouseEvent.MOUSE_DRAGGED && mainPanel != null) mainPanel!!.mouseDragged(event as MouseEvent)
            }
        }, AWTEvent.MOUSE_MOTION_EVENT_MASK)
        Toolkit.getDefaultToolkit().addAWTEventListener(object : AWTEventListener {
            override fun eventDispatched(event: AWTEvent) {
                if (event.getID() == MouseEvent.MOUSE_WHEEL && mainPanel != null) mainPanel!!.mouseWheel(event as MouseEvent)
            }
        }, AWTEvent.MOUSE_WHEEL_EVENT_MASK)
        addComponentListener(object : ComponentAdapter() {
            override fun componentMoved(arg0: ComponentEvent?) {
                setCrect()
            }

            override fun componentResized(arg0: ComponentEvent?) {
                Fresized()
                setCrect()
            }
        })
    }

    private fun FchangePanel(p: Page?) {
        if (p == null) return
        changingPanel = true
        if (mainPanel != null) if (p.front === mainPanel) mainPanel!!.leave() else mainPanel!!.exit()
        add(p)
        if (mainPanel != null) remove(mainPanel)
        mainPanel = p
        validate()
        p.renew()
        repaint()
        changingPanel = false
    }

    private fun Fresized() {
        if (settingsize) return
        settingsize = true
        val w: Int = getRootPane().getWidth()
        val h: Int = getRootPane().getHeight()
        try {
            setFonts(Page.Companion.size(w, h, fontSize))
        } catch (cme: ConcurrentModificationException) {
            Printer.p("MainFrame", 217, "Failed to set Font")
        }
        if (mainPanel != null) mainPanel!!.componentResized(w, h)
        settingsize = false
    }

    private fun setCrect() {
        val d = Toolkit.getDefaultToolkit().screenSize
        val r: Rectangle = getBounds()
        if (r.x + r.width >= 0 && r.y >= 0 && r.x < d.width && r.y - r.height < d.height) crect = r
    }

    companion object {
        private const val serialVersionUID = 1L
        var x = 0
        var y = 0
        var rect: Rectangle? = null
        var crect: Rectangle? = null
        var font: Font? = null
        var fontType = "Dialog"
        var fontStyle = Font.PLAIN
        const val fontSize = 24
        var F: MainFrame? = null
        private var mainPanel: Page? = null
        fun changePanel(p: Page?) {
            F!!.FchangePanel(p)
        }

        fun getPanel(): Page? {
            return mainPanel
        }

        fun timer(t: Int) {
            if (mainPanel != null && !F!!.changingPanel) mainPanel!!.timer(t)
        }

        protected fun resized() {
            F!!.Fresized()
        }

        private fun setFonts(f: Int) {
            val ks: MutableList<Any> = ArrayList()
            val fr: MutableList<Any> = ArrayList()
            font = Font(fontType, fontStyle, f)
            val fontRes = FontUIResource(font)
            val keys: Enumeration<Any> = UIManager.getDefaults().keys()
            while (keys.hasMoreElements()) {
                val key: Any = keys.nextElement()
                val value: Any = UIManager.get(key)
                if (value is FontUIResource) {
                    ks.add(key)
                    fr.add(fontRes)
                }
            }
            for (i in ks.indices) UIManager.put(ks[i], fr[i])
        }
    }

    init {
        setLayout(null)
        addListener()
        sizer()
    }
}
