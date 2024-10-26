package com.example.my_group_project;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class BookAPI {
    private static final String APPLICATION_NAME = "GoogleBooksJavaFXApp";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static Books getBooksService() throws GeneralSecurityException, IOException {
        return new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static List<Book> searchBooks(String query) throws IOException, GeneralSecurityException {
        Books books = getBooksService();
        Books.Volumes.List volumesList = books.volumes().list(query);
        Volumes volumes = volumesList.execute();
        List<Book> bookList = new ArrayList<>();
        if (volumes.getItems() != null) {
            for (Volume volume : volumes.getItems()) {
                String title = volume.getVolumeInfo().getTitle();
                String authors = String.join(", ", volume.getVolumeInfo().getAuthors());
                bookList.add(new Book(title, authors));
            }
        }
        return bookList;
    }
}

