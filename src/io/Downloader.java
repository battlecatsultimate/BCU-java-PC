package io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Consumer;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpBackOffIOExceptionHandler;
import com.google.api.client.http.HttpBackOffUnsuccessfulResponseHandler;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.ExponentialBackOff;

import main.Opts;

public class Downloader {

	private static final int CHUCK_SIZE = 131072;

	private static HttpTransport transport;

	public static boolean download(String url, File file, Consumer<Progress> c) {
		Writer.check(file);
		try {
			if (transport == null)
				transport = GoogleNetHttpTransport.newTrustedTransport();

			OutputStream out = new FileOutputStream(file);
			GenericUrl gurl = new GenericUrl(url);

			MediaHttpDownloader downloader = new MediaHttpDownloader(transport, new Handler());
			downloader.setChunkSize(CHUCK_SIZE);
			downloader.setProgressListener(new Progress(c));
			downloader.download(gurl, out);

			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			Opts.dloadErr(url);
			return false;
		}
	}

}

class Handler implements HttpRequestInitializer {

	@Override
	public void initialize(HttpRequest request) throws IOException {
		request.setUnsuccessfulResponseHandler(new HttpBackOffUnsuccessfulResponseHandler(new ExponentialBackOff()));
		request.setIOExceptionHandler(new HttpBackOffIOExceptionHandler(new ExponentialBackOff()));
	}

}
