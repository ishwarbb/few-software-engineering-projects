package com.sismics.books.core.service.AudioContent.AudioAdapters;

import com.sismics.books.core.model.jpa.AudioBook;
import org.codehaus.jackson.JsonNode;

public class Spotify_AudioBook extends AudioBook{
    public static final String ADAPTER_NAME = "Spotify_AudioBook";

    public AudioBook getAudioBookfromJSON(JsonNode json) {
        // Parse JSON and create AudioBook object
        String id = json.get("id").getTextValue();
        String title = json.get("name").getTextValue();
        String author = json.get("authors").get(0).get("name").getTextValue();
        String description = json.get("description").getTextValue();
        String viewUrl = json.get("href").getTextValue();

        AudioBook audioBook =  new AudioBook();
        audioBook.setId(id);
        audioBook.setTitle(title);
        audioBook.setAuthor(author);
        audioBook.setDescription(description);
        audioBook.setUrlLink(viewUrl);
    
        return audioBook;
    }

}
 