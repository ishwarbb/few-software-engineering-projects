package com.sismics.books.core.service.AudioContent.AudioAdapters;

import org.codehaus.jackson.JsonNode;

import com.sismics.books.core.model.jpa.Podcast;

public class Spotify_Podcast {
  public Podcast getPodcastfromJSON(JsonNode json){
    // Parse JSON and create Podcast object
    String id = json.get("id").getTextValue();
    String title = json.get("name").getTextValue();
    
    String artist = "N.A.";
    String viewUrl = json.get("href").getTextValue();

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
