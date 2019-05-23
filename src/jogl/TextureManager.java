package jogl;

import static com.jogamp.opengl.GL.GL_LINEAR;
import static com.jogamp.opengl.GL.GL_REPEAT;
import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static com.jogamp.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static com.jogamp.opengl.GL.GL_TEXTURE_MIN_FILTER;
import static com.jogamp.opengl.GL.GL_TEXTURE_WRAP_S;
import static com.jogamp.opengl.GL.GL_TEXTURE_WRAP_T;

import java.util.HashMap;
import java.util.Map;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.TextureData;

public class TextureManager {

	public static final Map<GL2, TextureManager> MAP = new HashMap<>();

	public static TextureManager get(GL2 gl) {
		if (MAP.containsKey(gl))
			return MAP.get(gl);
		TextureManager tm = new TextureManager(gl);
		MAP.put(gl, tm);
		return tm;
	}

	private final GL2 gl;

	private final Map<GLImage, Integer> mem = new HashMap<>();

	private TextureManager(GL2 gl2) {
		gl = gl2;
	}

	public int load(GLGraphics g, GLImage img) {
		img = img.root();
		if (mem.containsKey(img))
			return mem.get(img);
		int[] arr = new int[1];
		gl.glGenTextures(1, arr, 0);
		int id = arr[0];
		mem.put(img, id);
		g.bind(id);
		TextureData data = img.data;
		gl.glTexImage2D(GL_TEXTURE_2D, 0, data.getInternalFormat(), data.getWidth(), data.getHeight(), data.getBorder(),
				data.getPixelFormat(), data.getPixelType(), data.getBuffer());
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		return arr[0];
	}

}
