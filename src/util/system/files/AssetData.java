package util.system.files;

import java.io.File;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import page.MainLocale;
import util.system.MultiLangCont;
import util.system.MultiLangFile;
import util.system.fake.FakeImage;

public interface AssetData extends FileData {

	public static AssetData getAsset(byte[] bs) {
		return new DefAsset(bs);
	}

	public static AssetData getAsset(File f) {
		return new FileAsset(f);
	}

	public static AssetData getAsset(VFile<AssetData> vf) {
		return new MultiLangAsset(vf);
	}

	public byte[] getBytes();

	public FakeImage getImg(MultiLangFile mlf);

}

class DefAsset extends FileByte implements AssetData {

	protected DefAsset(byte[] bs) {
		super(bs);
	}

	@Override
	public FakeImage getImg(MultiLangFile mlf) {
		return getImg();
	}

}

class FileAsset extends FDFile implements AssetData {

	protected FileAsset(File f) {
		super(f);
	}

	@Override
	public FakeImage getImg(MultiLangFile mlf) {
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
		return getData().getBytes();
	}

	@Override
	public FakeImage getImg() {
		return getData().getImg();
	}

	@Override
	public FakeImage getImg(MultiLangFile mlf) {
		if (!MultiLangCont.VFILE.containsKey(mlf))
			MultiLangCont.VFILE.put(mlf, this);
		return getImg();
	}

	@Override
	public Queue<String> readLine() {
		return getData().readLine();
	}

	private AssetData getData() {
		String loc = MainLocale.LOC_CODE[MainLocale.lang];
		AssetData ad = map.get(loc);
		if (ad == null)
			ad = map.values().iterator().next();
		return ad;
	}

}
