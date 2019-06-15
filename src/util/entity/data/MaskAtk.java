package util.entity.data;

import util.Data;

public interface MaskAtk {

	public default int getAltAbi() {
		return 0;
	}

	public default int getDire() {
		return 1;
	}

	public int getLongPoint();

	public default int getMove() {
		return 0;
	}

	public int[] getProc(int ind);

	public int getShortPoint();

	public default int getTarget() {
		return Data.TCH_N;
	}

	public boolean isRange();

	public default int loopCount() {
		return -1;
	}

}
