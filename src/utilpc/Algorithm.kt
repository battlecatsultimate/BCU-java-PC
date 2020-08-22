package utilpc

import main.Printer
import utilpc.Algorithm.StackRect
import java.awt.Color
import java.awt.image.BufferedImage
import java.util.*

object Algorithm {
    fun shift(bimg: BufferedImage, h: Double, s: Double, b: Double): BufferedImage {
        return ColorShift.shift(bimg, h, s, b)
    }

    fun stackRect(rects: Array<IntArray>): SRResult {
        return StackRect.stackRect(rects)
    }

    class SRResult(ans: Array<IntArray>, dim: IntArray) {
        var pos: Array<IntArray>
        var center = 0
        var w: Int
        var h: Int

        init {
            w = dim[0]
            h = dim[1]
            pos = ans
            for (i in ans.indices) if (ans[i][0] == 0 && ans[i][1] == 0) center = i
        }
    }

    private object ColorShift {
        private const val method = 0
        private fun mid(x: Float, t: Double): Float {
            return if (method == 0) `mid$seg`(x, t) else `mid$inv`(x, t)
        }

        private fun `mid$inv`(x: Float, t: Double): Float {
            val c = (2 * x - 1) / x
            val a = 2 * c - 1
            val b = 2 * c - 2 * c * c
            val f = (b / (t + a) + c).toFloat()
            return if (java.lang.Float.isFinite(f)) f else x
        }

        private fun `mid$seg`(x: Float, t: Double): Float {
            return ((1 - Math.abs(t)) * (x - 0.5) + t * 0.5 + 0.5).toFloat()
        }

        private fun proc(p0: Int, h: Double, s: Double, b: Double): Int {
            val r0 = p0 shr 16 and 0xff
            val g0 = p0 shr 8 and 0xff
            val b0 = p0 and 0xff
            val a = p0 shr 24 and 0xff
            val hsb = Color.RGBtoHSB(r0, g0, b0, null)
            return Color.HSBtoRGB((hsb[0] + h).toFloat(), mid(hsb[1], s), mid(hsb[2], b)) and (a shl 24 or 0xffffff)
        }

        fun shift(bimg: BufferedImage, h: Double, s: Double, b: Double): BufferedImage {
            val ans = BufferedImage(bimg.width, bimg.height, bimg.type)
            for (i in 0 until bimg.width) for (j in 0 until bimg.height) {
                val p0: Int = bimg.getRGB(i, j)
                val p1 = proc(p0, h, s, b)
                ans.setRGB(i, j, p1)
            }
            return ans
        }
    }

    private class StackRect private constructor(private val rects: Array<IntArray>) {
        private class Dot private constructor(private val x: Int = 0, private val y: Int = 0) {
            private var pre: Algorithm.StackRect.Dot? = null
            private var nxt: Algorithm.StackRect.Dot? = null
            private fun add(w: Int, h: Int): Algorithm.StackRect.Dot {
                var top: Algorithm.StackRect.Dot
                insert(Algorithm.StackRect.Dot(x, y + h))
                insert(Algorithm.StackRect.Dot(x + w, y + h).also { top = it })
                insert(Algorithm.StackRect.Dot(x + w, y))
                remove()
                top.cleanPre()
                top.cleanNxt()
                return top
            }

            private fun cleanNxt() {
                if (unnec()) {
                    val x = nxt
                    remove()
                    x!!.cleanNxt()
                    return
                }
                if (valid()) return
                if (invalid()) {
                    nxt!!.cleanNxt()
                    return
                }
                val dx = pre
                dx!!.x = nxt!!.x
                nxt!!.remove()
                remove()
                dx.cleanNxt()
            }

            private fun cleanPre() {
                if (unnec()) {
                    val x = pre
                    remove()
                    x!!.cleanPre()
                    return
                }
                if (valid()) return
                if (invalid()) {
                    pre!!.cleanPre()
                    return
                }
                val dx = nxt
                dx!!.y = pre!!.y
                pre!!.remove()
                remove()
                dx.cleanPre()
            }

            private fun copy(dire: Int): Algorithm.StackRect.Dot {
                val ans = Algorithm.StackRect.Dot(x, y)
                if (nxt != null && dire != -1) {
                    ans.nxt = nxt!!.copy(1)
                    ans.nxt.pre = ans
                }
                if (pre != null && dire != 1) {
                    ans.pre = pre!!.copy(-1)
                    ans.pre.nxt = ans
                }
                return ans
            }

            private fun delete() {
                if (nxt != null) nxt!!.delete()
                nxt = null
                pre = null
            }

            private fun findHead(): Algorithm.StackRect.Dot {
                return if (pre == null) this else pre!!.findHead()
            }

            private fun insert(d: Algorithm.StackRect.Dot) {
                if (nxt != null) nxt!!.pre = d
                d.nxt = nxt
                nxt = d
                d.pre = pre
            }

            private fun invalid(): Boolean {
                if (pre == null || nxt == null) return false
                var b = x >= pre!!.x
                b = b and (y >= pre!!.y)
                b = b and (x >= nxt!!.x)
                b = b and (y >= nxt!!.y)
                return b
            }

            private fun remove() {
                if (pre != null) pre!!.nxt = nxt
                if (nxt != null) nxt!!.pre = pre
                nxt = null
                pre = nxt
            }

            private fun unnec(): Boolean {
                return if (pre == null || nxt == null) false else pre!!.x == x && x == nxt!!.x || pre!!.y == y && y == nxt!!.y
            }

            private fun valid(): Boolean {
                if (pre == null || nxt == null) return true
                var b = x <= pre!!.x
                b = b and (y <= pre!!.y)
                b = b and (x <= nxt!!.x)
                b = b and (y <= nxt!!.y)
                return b
            }
        }

        private var min: Int
        private var len: Int
        private var count = 0
        private var best: Array<IntArray?>? = null
        private val set: MutableSet<Int> = TreeSet<Int>()
        private fun detRep(ans: Array<IntArray?>, i: Int): Boolean {
            if (!rawRep(i)) return false
            if (ans[i - 1] == null) return true
            return ans[i - 1]!![1] > ans[i]!![1]
        }

        private fun operate(ans: Array<IntArray?>, dots: Algorithm.StackRect.Dot) {
            count++
            var operated = false
            for (i in ans.indices) if (ans[i] == null) {
                operated = true
                if (rawRep(i) && ans[i - 1] == null) continue
                ans[i] = intArrayOf(0, 0, rects[i][0], rects[i][1])
                var d: Algorithm.StackRect.Dot? = dots
                while (d != null) {
                    if (d.invalid()) {
                        d = d.nxt
                        continue
                    }
                    ans[i]!![0] = d.x
                    ans[i]!![1] = d.y
                    if (detRep(ans, i)) {
                        d = d.nxt
                        continue
                    }
                    val hash: Int = Arrays.deepHashCode(ans)
                    val dim = dim(ans)
                    if (restrict(dim) && !set.contains(hash)) {
                        set.add(hash)
                        val ndot = d.copy(0).add(ans[i]!![2], ans[i]!![3]).findHead()
                        operate(ans, ndot)
                        ndot.delete()
                    }
                    d = d.nxt
                }
                ans[i] = null
            }
            if (!operated) {
                val dim = dim(ans)
                if (restrict(dim)) {
                    min = dim[0] * dim[1]
                    len = dim[0] + dim[1]
                    best = ans.clone()
                    for (i in best!!.indices) best!![i] = best!![i]!!.clone()
                }
            }
        }

        private fun rawRep(i: Int): Boolean {
            return i > 0 && rects[i - 1][0] == rects[i][0] && rects[i - 1][1] == rects[i][1]
        }

        private fun restrict(dim: IntArray): Boolean {
            if (dim[0] * dim[1] < min) return true
            if (dim[0] * dim[1] > min) return false
            return if (dim[0] + dim[1] < len) true else dim[0] + dim[1] == len && best == null
        }

        private fun stack(): Array<IntArray?>? {
            Arrays.sort<IntArray>(rects, getComp(DEFTYPE))
            val ans = arrayOfNulls<IntArray>(rects.size)
            operate(ans, Algorithm.StackRect.Dot())
            Printer.p("StackRect", 176, rects.size.toString() + "->" + count)
            return best
        }

        companion object {
            private const val DEFTYPE = 0
            fun stackRect(rects: Array<IntArray>): SRResult {
                val rs = Array(rects.size) { IntArray(3) }
                for (i in rects.indices) rs[i] = intArrayOf(rects[i][0], rects[i][1], i)
                val res = StackRect(rs).stack()
                val ans = Array(rects.size) { IntArray(3) }
                for (i in rects.indices) ans[i] = intArrayOf(res!![i]!![0], res[i]!![1], rs[i][2])
                Arrays.sort<IntArray>(ans, Comparator { o0: IntArray, o1: IntArray -> Integer.compare(o0[2], o1[2]) })
                return SRResult(ans, dim(res))
            }

            private fun dim(ans: Array<IntArray?>?): IntArray {
                var w = 0
                var h = 0
                for (a in ans!!) if (a != null) {
                    w = Math.max(w, a[0] + a[2])
                    h = Math.max(h, a[1] + a[3])
                }
                return intArrayOf(w, h)
            }

            private fun getComp(t: Int): Comparator<IntArray> {
                return Comparator { o1: IntArray, o2: IntArray -> if (o1[1] != o2[1]) Integer.compare(o2[1], o1[1]) else Integer.compare(o1[0], o2[0]) }
            }
        }

        init {
            var w = 0
            var h = 0
            for (a in rects) if (a != null) {
                w = Math.max(w, a[0])
                h = Math.max(h, a[1])
            }
            min = rects.size * w * h
            len = (Math.ceil(Math.sqrt(rects.size.toDouble())) * (w + h)).toInt()
        }
    }
}
