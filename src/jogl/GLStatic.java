package jogl;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;

public class GLStatic {

	public static final boolean MIP = false;
	public static final GLProfile GLP;
	public static final GLCapabilities GLC;

	public static boolean ALWAYS_GLIMG = true;

	static {
		GLP = GLProfile.get(GLProfile.GL2);
		GLC = new GLCapabilities(GLP);

	}

}
