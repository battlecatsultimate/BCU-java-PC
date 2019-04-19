package util.stage;

import java.util.List;

import util.system.VImg;

public interface AbCastle {

	public VImg get(int ind);

	public int getCasID(VImg val);

	public List<VImg> getList();

	public int size();

}
