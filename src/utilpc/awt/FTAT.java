package utilpc.awt;

import common.system.fake.FakeTransform;

import java.awt.geom.AffineTransform;

public class FTAT implements FakeTransform {

    protected final AffineTransform t;

    protected FTAT(AffineTransform at) {
        t = at;
    }

    @Override
    public AffineTransform getAT() {
        return t;
    }

}
