package com.sismics.books.core.service.AudioContent.AudioAdapters;

import org.codehaus.jackson.JsonNode;

import com.sismics.books.core.model.jpa.Podcast;

public class iTunes_Podcast {
 
    public Podcast getPodcastfromJSON(JsonNode json){
        // Parse JSON and create Podcast object
        String id = json.get("collectionId").getNumberValue().toString();
        String title = json.get("collectionName").getTextValue();
        String artist = json.get("artistName").getTextValue();
        String viewUrl = json.get("trackViewUrl").getTextValue();

        Podcast podcast =  new Podcast();
        podcast.setId(id);
        podcast.setTitle(title);
        podcast.setArtist(artist);
        podcast.setUrlLink(viewUrl);

        System.out.println("====================================");
        System.out.println("In Adapter: ");
        System.out.println("Podcast = " + podcast.getId() + " " + podcast.getTitle() + " " + podcast.getArtist() + " " + podcast.getUrlLink());
    
        return podcast;
    }
}
