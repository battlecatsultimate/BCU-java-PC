package jogl.util

import com.jogamp.opengl.GL
import com.jogamp.opengl.GL2
import com.jogamp.opengl.GL2ES2
import com.jogamp.opengl.GLException
import com.jogamp.opengl.util.glsl.ShaderCode
import com.jogamp.opengl.util.glsl.ShaderProgram
import com.jogamp.opengl.util.texture.TextureData
import common.battle.data.DataEntity
import common.io.assets.Admin
import common.io.assets.Admin.StaticPermitted
import common.io.assets.AssetLoader
import common.io.assets.AssetLoader.AssetHeader
import common.io.assets.AssetLoader.AssetHeader.AssetEntry
import common.io.json.JsonEncoder
import common.io.json.Test
import common.io.json.Test.JsonTest_0.JsonD
import common.io.json.Test.JsonTest_2
import common.pack.Source.AnimLoader
import common.pack.Source.ResourceLocation
import common.pack.Source.SourceAnimLoader
import common.pack.Source.SourceAnimSaver
import common.pack.Source.Workspace
import common.pack.Source.ZipSource
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCPlayer
import jogl.GLStatic
import main.MainBCU
import org.apache.commons.io.IOUtils
import page.JL
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.io.IOException
import java.nio.Buffer
import java.nio.charset.Charset
import java.util.*

class ResManager private constructor(gl2: GL2) {
    var mode = 0
    var para = 0
    var prog = 0
    private val gl: GL2
    private val mem: MutableMap<GLImage, Int> = HashMap<GLImage, Int>()
    fun dispose() {
        val n = mem.size
        val tex = IntArray(n)
        var i = 0
        for (x in mem.values) tex[i++] = x
        if (n > 0) gl.glDeleteTextures(n, tex, 0)
        gl.glDeleteProgram(prog)
    }

    fun load(g: GLGraphics, img: GLImage): Int {
        var img: GLImage = img
        img = img.root()
        if (mem.containsKey(img)) return mem[img]!!
        val arr = IntArray(1)
        gl.glGenTextures(1, arr, 0)
        val id = arr[0]
        mem[img] = id
        g.bind(id)
        val data: TextureData = img.data
        val w: Int = data.getWidth()
        val h: Int = data.getHeight()
        val pf: Int = data.getPixelFormat()
        val pt: Int = data.getPixelType()
        val bf: Buffer = data.getBuffer()
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, data.getInternalFormat(), w, h, data.getBorder(), pf, pt, bf)
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT)
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT)
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR)
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR)
        return arr[0]
    }

    @Throws(IOException::class)
    private fun `readShader$0`() {
        val suc = IntArray(1)
        val vi: Int = gl.glCreateShader(GL2ES2.GL_VERTEX_SHADER)
        val vc = load("120.vp")
        gl.glShaderSource(vi, 1, arrayOf(vc), intArrayOf(vc.length), 0)
        gl.glCompileShader(vi)
        gl.glGetShaderiv(vi, GL2ES2.GL_COMPILE_STATUS, suc, 0)
        if (suc[0] == 0) {
            val a = IntArray(1)
            val b = ByteArray(512)
            gl.glGetShaderInfoLog(vi, 512, a, 0, b, 0)
            println("VS: " + String(Arrays.copyOf(b, a[0])))
        }
        val fi: Int = gl.glCreateShader(GL2ES2.GL_FRAGMENT_SHADER)
        val fc = load("120.fp")
        gl.glShaderSource(fi, 1, arrayOf(fc), intArrayOf(fc.length), 0)
        gl.glCompileShader(fi)
        gl.glGetShaderiv(fi, GL2ES2.GL_COMPILE_STATUS, suc, 0)
        if (suc[0] == 0) {
            val a = IntArray(1)
            val b = ByteArray(512)
            gl.glGetShaderInfoLog(fi, 512, a, 0, b, 0)
            println("FS: " + String(Arrays.copyOf(b, a[0])))
        }
        prog = gl.glCreateProgram()
        gl.glAttachShader(prog, vi)
        gl.glAttachShader(prog, fi)
        gl.glDeleteShader(vi)
        gl.glDeleteShader(fi)
        gl.glLinkProgram(prog)
        mode = gl.glGetUniformLocation(prog, "mode")
        para = gl.glGetUniformLocation(prog, "para")
    }

    private fun `readShader$1`() {
        val vp0: ShaderCode = ShaderCode.create(gl, GL2ES2.GL_VERTEX_SHADER, this.javaClass, "shader", "shader/bin",
                "130", true)
        val fp0: ShaderCode = ShaderCode.create(gl, GL2ES2.GL_FRAGMENT_SHADER, this.javaClass, "shader", "shader/bin",
                "130", true)
        vp0.defaultShaderCustomization(gl, true, true)
        fp0.defaultShaderCustomization(gl, true, true)
        val sp0 = ShaderProgram()
        sp0.add(gl, vp0, System.err)
        sp0.add(gl, fp0, System.err)
        sp0.link(gl, System.err)
        sp0.validateProgram(gl, System.err)
        prog = sp0.program()
        mode = gl.glGetUniformLocation(prog, "mode")
        para = gl.glGetUniformLocation(prog, "para")
    }

    private fun setupShader(gl: GL2) {
        try {
            if (GLStatic.JOGL_SHADER) try {
                `readShader$1`()
            } catch (e: GLException) {
                `readShader$0`()
            } else `readShader$0`()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        val MAP: MutableMap<GL2, ResManager> = HashMap<GL2, ResManager>()
        operator fun get(gl: GL2): ResManager? {
            if (MAP.containsKey(gl)) return MAP[gl]
            val tm = ResManager(gl)
            MAP[gl] = tm
            return tm
        }

        @Throws(IOException::class)
        private fun load(name: String): String {
            val path = (if (MainBCU.WRITE) "src/" else "") + "jogl/util/shader/" + name
            val ls = IOUtils.readLines(ClassLoader.getSystemResourceAsStream(path), Charset.defaultCharset())
            var source = ""
            for (str in ls) source += str
            return source
        }
    }

    init {
        gl = gl2
        setupShader(gl)
    }
}
