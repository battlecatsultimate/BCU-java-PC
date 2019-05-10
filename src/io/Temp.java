package io;

import java.io.File;

public class Temp {

	public static void main(String[] args) {
		WebFileIO.download("http://battlecatsultimate.cf/api/resources/assets/080504.zip", new File("./img/Test.zip"),
				null);
	}

}
