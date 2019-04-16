package decode.admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DecodePack {

	protected static final String list = "b484857901742afc";
	protected static final String pack = "89a0f99078419c28";

	private static boolean write = false;

	public static void main(String[] args) {
		PrintStream ps = null;
		if (write) {
			File log = new File("./log.txt");
			if (!log.getParentFile().exists())
				log.getParentFile().mkdirs();
			if (!log.exists())
				try {
					log.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
					return;
				}
			try {
				ps = new PrintStream(log);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				return;
			}
			System.setOut(ps);
			System.setErr(ps);
		}
		System.out.println("start");
		File f = new File("./raw/");
		File[] fs = f.listFiles();
		File[] fl = new File[fs.length / 2], fp = new File[fs.length / 2];
		int i = 0;
		for (File fi : fs)
			if (fi.getName().endsWith(".list"))
				fl[i++] = fi;
		for (File fi : fs)
			if (fi.getName().endsWith(".pack") || fi.getName().endsWith("2.jpg")) {
				String sp = fi.getName();
				sp = sp.substring(0, sp.length() - 5);
				for (i = 0; i < fl.length; i++)
					if (fl[i].getName().startsWith(sp))
						fp[i] = fi;
			}
		System.out.println("files classified");
		for (i = 0; i < fl.length; i++) {
			byte[] bs = null;
			try {
				bs = Files.readAllBytes(fl[i].toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			String[] strs = new String(decrypt(list, bs)).split("\n");
			String sp = fl[i].getName();
			sp = sp.substring(0, sp.length() - 5);
			ListFilePack lf = new ListFilePack(sp, strs);
			lf.readBytes(fp[i]);
			lf.writeAll();
			System.out.println("decode(" + (i + 1) + "/" + fl.length + "): " + sp);
		}
		System.out.println("done");
		if (write)
			ps.close();
	}

	protected static byte[] decrypt(String key, byte[] enc) {
		try {
			Key k = new SecretKeySpec(key.getBytes(), "AES");
			Cipher c = Cipher.getInstance("AES");
			c.init(Cipher.DECRYPT_MODE, k);
			return c.doFinal(enc);
		} catch (Exception ex) {
			System.out.println(new String(enc));
			ex.printStackTrace();
		}
		return null;
	}

}
