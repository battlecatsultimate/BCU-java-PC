package utilpc

import java.awt.Color
import java.awt.image.BufferedImage

object ReColor {
    val strs: Array<String>
    val strf: Array<String>
    private val iss: Array<IntArray>
    private val fs: Array<FloatArray?>
    fun transcolor(bimg: BufferedImage, coor: IntArray?, from: Int, to: Int) {
        var coor = coor
        if (coor == null) coor = intArrayOf(0, 0, bimg.getWidth(), bimg.getHeight())
        for (i in 0 until coor[2]) for (j in 0 until coor[3]) {
            val x = i + coor[0]
            val y = j + coor[1]
            val p: Int = bimg.getRGB(x, y)
            bimg.setRGB(x, y, real(p, from, to))
        }
    }

    private fun real(p: Int, from: Int, to: Int): Int {
        var r = p shr 16 and 0xff
        var g = p shr 8 and 0xff
        var b = p and 0xff
        val a = p shr 24 and 0xff
        val hsb = FloatArray(3)
        Color.RGBtoHSB(r, g, b, hsb)
        if (Math.abs(hsb[0] - fs[from]!![0]) > 5e-2) return p
        val agre = hsb[1] / fs[from]!![1] * fs[to]!![1]
        val alig = hsb[2] / fs[from]!![2] * fs[to]!![2]
        val c = Color.getHSBColor(fs[to]!![0], agre, alig)
        r = c.red
        g = c.green
        b = c.blue
        return b + (g shl 8) + (r shl 16) + (a shl 24)
    }

    init {
        strs = arrayOf("red", "alien", "zombie")
        strf = arrayOf("red", "alien", "zombie", "white", "black")
        iss = arrayOf(intArrayOf(255, 84, 1), intArrayOf(160, 255, 238), intArrayOf(184, 113, 255), intArrayOf(255, 255, 255), intArrayOf(0, 0, 0))
        fs = arrayOfNulls(5)
        for (i in 0..4) fs[i] = Color.RGBtoHSB(iss[i][0], iss[i][1], iss[i][2], FloatArray(3))
    }
}
