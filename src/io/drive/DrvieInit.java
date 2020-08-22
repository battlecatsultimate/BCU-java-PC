package io.drive;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.*;
import java.util.Collections;
import java.util.List;

public class DrvieInit {
	private static final String APPLICATION_NAME = "Google Drive API Test";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	private static final java.io.File CREDENTIAL_FOLDER = new java.io.File("C:/Users/user/Desktop/Utility/Credentials"); // Change
	// Directory
	private static final String CLIENT_NAME = "client_secret.json";

	private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);

	public static void main(String[] args) {
		System.out.println("CREDENTIAL_FOLDER : " + CREDENTIAL_FOLDER.getAbsolutePath());

		if (!CREDENTIAL_FOLDER.exists()) {
			CREDENTIAL_FOLDER.mkdirs();

			System.out.println("Created Folder : " + CREDENTIAL_FOLDER.getAbsolutePath());
			System.out.println("Copy File : " + CLIENT_NAME + " into folder above... and rerun this class");

			return;
		}

		try {

			final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

			Credential credential = getCredentials(HTTP_TRANSPORT);

			Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
					.setApplicationName(APPLICATION_NAME).build();

			FileList result = service.files().list().setPageSize(10).setFields("nextPageToken,files(id,name)")
					.execute();

			List<File> files = result.getFiles();

			if (files == null || files.isEmpty()) {
				System.out.println("No files found...");
			} else {
				System.out.println("Fils : ");
				for (File file : files) {
					System.out.printf("%s (%s)\n", file.getName(), file.getId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Credential getCredentials(final NetHttpTransport HTTP) throws IOException {
		java.io.File clientSecret = new java.io.File(CREDENTIAL_FOLDER, CLIENT_NAME);

		if (!clientSecret.exists()) {
			throw new FileNotFoundException(
					"Please copy" + CLIENT_NAME + " to folder : " + CREDENTIAL_FOLDER.getAbsolutePath());
		}

		InputStream ins = new FileInputStream(clientSecret);

		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(ins));

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP, JSON_FACTORY, clientSecrets,
				SCOPES).setDataStoreFactory(new FileDataStoreFactory(CREDENTIAL_FOLDER)).setAccessType("offline")
						.build();

		return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
	}
}
