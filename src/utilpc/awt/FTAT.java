package utilpc.awt;

import java.awt.geom.AffineTransform;

import common.util.system.fake.FakeTransform;

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
