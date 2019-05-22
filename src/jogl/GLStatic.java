package jogl;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;

public class GLStatic {

	public static final boolean MIP = true;

	public static final GLProfile GLP;
	public static final GLCapabilities GLC;

	static {
		GLP = GLProfile.get(GLProfile.GL2);
		GLC = new GLCapabilities(GLP);

	}

}
