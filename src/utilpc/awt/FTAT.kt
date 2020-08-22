package utilpc.awt

import common.system.fake.FakeTransform
import java.awt.geom.AffineTransform

class FTAT(protected val t: AffineTransform) : FakeTransform {
    override fun getAT(): AffineTransform? {
        return t
    }
}
