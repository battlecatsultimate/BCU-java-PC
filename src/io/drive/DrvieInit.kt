package io.drive

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File
import com.google.api.services.drive.model.FileList
import java.io.*

object DrvieInit {
    private const val APPLICATION_NAME = "Google Drive API Test"
    private val JSON_FACTORY: JsonFactory = JacksonFactory.getDefaultInstance()
    private val CREDENTIAL_FOLDER = java.io.File("C:/Users/user/Desktop/Utility/Credentials") // Change

    // Directory
    private const val CLIENT_NAME = "client_secret.json"
    private val SCOPES = listOf<String>(DriveScopes.DRIVE)
    @JvmStatic
    fun main(args: Array<String>) {
        println("CREDENTIAL_FOLDER : " + CREDENTIAL_FOLDER.absolutePath)
        if (!CREDENTIAL_FOLDER.exists()) {
            CREDENTIAL_FOLDER.mkdirs()
            println("Created Folder : " + CREDENTIAL_FOLDER.absolutePath)
            println("Copy File : " + CLIENT_NAME + " into folder above... and rerun this class")
            return
        }
        try {
            val HTTP_TRANSPORT: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport()
            val credential = getCredentials(HTTP_TRANSPORT)
            val service: Drive = Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME).build()
            val result: FileList = service.files().list().setPageSize(10).setFields("nextPageToken,files(id,name)")
                    .execute()
            val files: List<File> = result.getFiles()
            if (files == null || files.isEmpty()) {
                println("No files found...")
            } else {
                println("Fils : ")
                for (file in files) {
                    System.out.printf("%s (%s)\n", file.name, file.id)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun getCredentials(HTTP: NetHttpTransport): Credential {
        val clientSecret = java.io.File(CREDENTIAL_FOLDER, CLIENT_NAME)
        if (!clientSecret.exists()) {
            throw FileNotFoundException(
                    "Please copy" + CLIENT_NAME + " to folder : " + CREDENTIAL_FOLDER.absolutePath)
        }
        val ins: InputStream = FileInputStream(clientSecret)
        val clientSecrets: GoogleClientSecrets = GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(ins))
        val flow: GoogleAuthorizationCodeFlow = GoogleAuthorizationCodeFlow.Builder(HTTP, JSON_FACTORY, clientSecrets,
                SCOPES).setDataStoreFactory(FileDataStoreFactory(CREDENTIAL_FOLDER)).setAccessType("offline")
                .build()
        return AuthorizationCodeInstalledApp(flow, LocalServerReceiver()).authorize("user")
    }
}
