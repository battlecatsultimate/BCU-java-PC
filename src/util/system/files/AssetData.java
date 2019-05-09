package util.system.files;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.TreeMap;

import page.MainLocale;
import util.system.MultiLangCont;
import util.system.MultiLangFile;

public interface AssetData extends ByteData {

	public static AssetData getAsset(byte[] bs) {
		return new DefAsset(bs);
	}

	public static AssetData getAsset(VFile<AssetData> vf) {
		return new MultiLangAsset(vf);
	}

	public BufferedImage getImg(MultiLangFile mlf);

}

class DefAsset extends FileByte implements AssetData {

	public DefAsset(byte[] bs) {
		super(bs);
	}

	@Override
	public BufferedImage getImg(MultiLangFile mlf) {
		return getImg();
	}

}

class MultiLangAsset implements AssetData {

	private Map<String, AssetData> map = new TreeMap<>();

	public MultiLangAsset(VFile<AssetData> vf) {
		for (VFile<AssetData> f : vf.list())
			map.put(f.getName().substring(6), f.getData());
	}

	@Override
	public byte[] getBytes() {
		String loc = MainLocale.LOC_CODE[MainLocale.lang];
		AssetData ad = map.get(loc);
		if (ad == null)
			ad = map.values().iterator().next();
		return ad.getBytes();
	}

	@Override
	public BufferedImage getImg(MultiLangFile mlf) {
		if (!MultiLangCont.VFILE.containsKey(mlf))
			MultiLangCont.VFILE.put(mlf, this);
		return getImg();
	}

}
