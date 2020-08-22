package io.drive;

import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

//This class is for testing, so you can ignore it

public class DriveDownload {
	public static void main(String[] args) throws IOException {
		Drive service = DriveUtil.getDriveService();

		FileList flist = service.files().list().execute();

		List<File> files = flist.getFiles();

		if (files == null || files.size() == 0) {
			System.out.println("No Files Found");
		} else {
			String version = "";
			String id = "";
			String name = "";

			for (File f : files) {

				if (f.getName().equals("Description.txt")) {
					System.out.println("Found");
					InputStream is = service.getRequestFactory()
							.buildDeleteRequest(service.files().get(f.getId()).buildHttpRequestUrl()).execute()
							.getContent();

					BufferedInputStream bis = new BufferedInputStream(is);

					byte[] buffer = new byte[4096];

					while ((bis.read(buffer)) != -1) {
						version += new String(buffer, StandardCharsets.UTF_8);
					}

					System.out.println(version);
				}
			}

			for (File f : files) {
				if (f.getName().equals("BCU_Android_" + version + ".apk")) {
					name = f.getName();
					id = f.getId();
				}
			}

			java.io.File f = new java.io.File("C:/Users/user/Desktop/Utility/Credentials/api/" + name);

			if (!f.exists()) {
				f.createNewFile();
			}

			OutputStream os = new FileOutputStream(f);

			MediaHttpDownloader d = service.files().get(id).getMediaHttpDownloader();
			d.download(service.files().get(id).buildHttpRequestUrl(), os);

			while (d.getDownloadState() != MediaHttpDownloader.DownloadState.MEDIA_COMPLETE) {
				System.out.println("Download : " + (d.getProgress() * 1000));
			}

			System.out.println(d.getProgress());

			System.out.println("END??");
		}
	}
}
