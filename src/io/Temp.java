package io;

import java.io.File;

public class Temp {

	public static void main(String[] args) {
		Downloader.download("http://battlecatsultimate.cf/api/resources/assets/080504.zip", new File("./img/Test.zip"),
				null);
	}

}
