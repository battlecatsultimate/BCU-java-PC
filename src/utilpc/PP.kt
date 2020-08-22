package utilpc

import common.system.P
import java.awt.Dimension
import java.awt.Point
import java.awt.Rectangle
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D

@Strictfp
class PP : P {
    constructor(d: Dimension) : super(d.getWidth(), d.getHeight()) {}
    constructor(d: Double, e: Double) : super(d, e) {}
    constructor(p: Point) : super(p.getX(), p.getY()) {}
    constructor(p: Point2D) : super(p.x, p.y) {}

    override fun copy(): PP {
        return PP(x, y)
    }

    override fun divide(p: P): PP {
        x /= p.x
        y /= p.y
        return this
    }

    override fun sf(p: P): PP {
        return PP(p.x - x, p.y - y)
    }

    override operator fun times(d: Double): PP {
        x *= d
        y *= d
        return this
    }

    override fun times(hf: Double, vf: Double): PP {
        x *= hf
        y *= vf
        return this
    }

    override operator fun times(p: P): PP {
        x *= p.x
        y *= p.y
        return this
    }

    fun toDimension(): Dimension {
        return Dimension(x as Int, y as Int)
    }

    fun toPoint(): Point {
        return Point(x as Int, y as Int)
    }

    fun toPoint2D(): Point2D {
        return Point2D.Double(x, y)
    }

    fun toRectangle(w: Int, h: Int): Rectangle {
        return Rectangle(x as Int, y as Int, w, h)
    }

    fun toRectangle(p: P): Rectangle {
        return Rectangle(x as Int, y as Int, p.x as Int, p.y as Int)
    }

    fun toRectangle2D(p: P): Rectangle2D {
        return Rectangle2D.Double(x, y, p.x, p.y)
    }
}
