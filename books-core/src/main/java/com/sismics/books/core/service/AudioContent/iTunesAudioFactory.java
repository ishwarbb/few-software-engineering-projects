package com.sismics.books.core.service.AudioContent;

// Import Paginated List
import com.sismics.books.core.util.jpa.PaginatedList;
import com.sismics.books.core.service.AudioContent.AudioAdapters.iTunes_AudioBook;
import com.sismics.books.core.service.AudioContent.AudioAdapters.iTunes_Podcast;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.sismics.books.core.model.jpa.AudioBook;
import com.sismics.books.core.model.jpa.Podcast;


public class iTunesAudioFactory extends AudioContentFactory {
    
    private URLConnection establishConnection(URL url) throws Exception {
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("Accept-Charset", "utf-8");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.62 Safari/537.36");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        return connection;
    }

    private JsonNode getRootNode(InputStream inputStream) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readValue(inputStream, JsonNode.class);
        return rootNode;
    }


    public List<AudioBook> getAudioBooks(String query) {
        try {
            URL url = new URL("https://itunes.apple.com/search?term=" + query + "&media=audiobook");
            URLConnection connection = establishConnection(url);
            InputStream response = connection.getInputStream();
            JsonNode json = getRootNode(response);
            
            JsonNode results = json.get("results");
            List<AudioBook> audioBooks = new ArrayList<AudioBook>();

            if (results.isArray()) {
                iTunes_AudioBook iTunesAudioBookAdapter = new iTunes_AudioBook();
                for (JsonNode result : results) {
                    AudioBook audioBook = iTunesAudioBookAdapter.getAudioBookfromJSON(result);
                    audioBooks.add(audioBook);
                }
            }

            return audioBooks;
        } catch (Exception e) { 
            System.out.println(e);
            return new ArrayList<AudioBook>();
        }
    }

    public List<Podcast> getPodcasts(String query) {
        try {
            URL url = new URL("https://itunes.apple.com/search?term=" + query + "&media=podcast");
            URLConnection connection = establishConnection(url);
            InputStream response = connection.getInputStream();
            JsonNode json = getRootNode(response);
            
            JsonNode results = json.get("results");
            List<Podcast> podcasts = new ArrayList<Podcast>();

            
            if (results.isArray()) {
                iTunes_Podcast iTunesPodcastAdapter = new iTunes_Podcast();
                for (JsonNode result : results) {
                    Podcast podcast = iTunesPodcastAdapter.getPodcastfromJSON(result);
                    podcasts.add(podcast);
                }
            }

            System.out.println("Podcasts: " + podcasts);
            
            return podcasts;
        } catch (Exception e) { 
            System.out.println(e);
            return new ArrayList<Podcast>();
        }
    }
}
