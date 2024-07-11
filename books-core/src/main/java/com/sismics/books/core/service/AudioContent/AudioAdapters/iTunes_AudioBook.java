package com.sismics.books.core.service.AudioContent.AudioAdapters;

import com.sismics.books.core.model.jpa.AudioBook;
import org.codehaus.jackson.JsonNode;

public class iTunes_AudioBook extends AudioBook{
    public static final String ADAPTER_NAME = "iTunes_AudioBook";

    public AudioBook getAudioBookfromJSON(JsonNode json) {
        // Parse JSON and create AudioBook object
        String id = json.get("collectionId").getNumberValue().toString();
        String title = json.get("collectionName").getTextValue();
        String author = json.get("artistName").getTextValue();
        String description = json.get("description").getTextValue();
        String viewUrl = json.get("collectionViewUrl").getTextValue();

        AudioBook audioBook =  new AudioBook();
        audioBook.setId(id);
        audioBook.setTitle(title);
        audioBook.setAuthor(author);
        audioBook.setDescription(description);
        audioBook.setUrlLink(viewUrl);

        System.out.println("Audio Book ID : " + id);
        return audioBook;
    }

}
 