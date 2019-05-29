package jogl.util;

import static com.jogamp.opengl.GL2.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;
import com.jogamp.opengl.util.texture.TextureData;

import jogl.GLStatic;
import main.MainBCU;

public class ResManager {

	public static final Map<GL2, ResManager> MAP = new HashMap<>();

	public static ResManager get(GL2 gl) {
		if (MAP.containsKey(gl))
			return MAP.get(gl);
		ResManager tm = new ResManager(gl);
		MAP.put(gl, tm);
		return tm;
	}

	private static String load(String name) throws IOException {
		String path = (MainBCU.WRITE ? "src/" : "") + "jogl/util/shader/" + name;
		List<String> ls = IOUtils.readLines(ClassLoader.getSystemResourceAsStream(path), Charset.defaultCharset());
		String source = "";
		for (String str : ls)
			source += str;
		return source;
	}

	protected int mode, para, prog;

	private final GL2 gl;

	private final Map<GLImage, Integer> mem = new HashMap<>();

	private ResManager(GL2 gl2) {
		gl = gl2;
		setupShader(gl);
	}

	public void dispose() {
		int n = mem.size();
		int[] tex = new int[n];
		int i = 0;
		for (int x : mem.values())
			tex[i++] = x;
		gl.glDeleteTextures(n, tex, 0);
		gl.glDeleteProgram(prog);
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

	private void readShader$0() throws IOException {
		int[] suc = new int[1];
		int vi = gl.glCreateShader(GL_VERTEX_SHADER);
		String vc = load("120.vp");
		gl.glShaderSource(vi, 1, new String[] { vc }, new int[] { vc.length() }, 0);
		gl.glCompileShader(vi);

		gl.glGetShaderiv(vi, GL_COMPILE_STATUS, suc, 0);
		if (suc[0] == 0) {
			int[] a = new int[1];
			byte[] b = new byte[512];
			gl.glGetShaderInfoLog(vi, 512, a, 0, b, 0);
			System.out.println("VS: " + new String(Arrays.copyOf(b, a[0])));
		}

		int fi = gl.glCreateShader(GL_FRAGMENT_SHADER);
		String fc = load("120.fp");
		gl.glShaderSource(fi, 1, new String[] { fc }, new int[] { fc.length() }, 0);
		gl.glCompileShader(fi);

		gl.glGetShaderiv(fi, GL_COMPILE_STATUS, suc, 0);
		if (suc[0] == 0) {
			int[] a = new int[1];
			byte[] b = new byte[512];
			gl.glGetShaderInfoLog(fi, 512, a, 0, b, 0);
			System.out.println("FS: " + new String(Arrays.copyOf(b, a[0])));
		}

		prog = gl.glCreateProgram();
		gl.glAttachShader(prog, vi);
		gl.glAttachShader(prog, fi);
		gl.glDeleteShader(vi);
		gl.glDeleteShader(fi);
		gl.glLinkProgram(prog);
		mode = gl.glGetUniformLocation(prog, "mode");
		para = gl.glGetUniformLocation(prog, "para");
	}

	private void readShader$1() {
		final ShaderCode vp0 = ShaderCode.create(gl, GL2ES2.GL_VERTEX_SHADER, this.getClass(), "shader", "shader/bin",
				"120", true);
		final ShaderCode fp0 = ShaderCode.create(gl, GL2ES2.GL_FRAGMENT_SHADER, this.getClass(), "shader", "shader/bin",
				"120", true);
		vp0.defaultShaderCustomization(gl, true, true);
		fp0.defaultShaderCustomization(gl, true, true);
		final ShaderProgram sp0 = new ShaderProgram();
		sp0.add(gl, vp0, System.err);
		sp0.add(gl, fp0, System.err);
		sp0.link(gl, System.err);
		sp0.validateProgram(gl, System.err);
		prog = sp0.program();
		mode = gl.glGetUniformLocation(prog, "mode");
		para = gl.glGetUniformLocation(prog, "para");
	}

	private void setupShader(GL2 gl) {
		try {
			if (GLStatic.JOGL_SHADER)
				readShader$1();
			else
				readShader$0();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
