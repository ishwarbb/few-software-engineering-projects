package com.sismics.books.core.service.AudioContent;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import com.sismics.books.core.model.jpa.AudioBook;
import com.sismics.books.core.model.jpa.Podcast;
import com.sismics.books.core.service.AudioContent.AudioAdapters.Spotify_AudioBook;
import com.sismics.books.core.service.AudioContent.AudioAdapters.Spotify_Podcast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpotifyAudioFactory extends AudioContentFactory {
    String CLIENT_ID = "6929c28167ae478da33f927f9dac8f19";
    String CLIENT_SECRET = "be7089931bda4a88ac9885c51efe06d1";

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

  private String getAuthToken(String CLIENT_ID, String CLIENT_SECRET) {
        try {
            URL url = new URL("https://accounts.spotify.com/api/token");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);

            String data = "grant_type=client_credentials&client_id=6929c28167ae478da33f927f9dac8f19&client_secret=be7089931bda4a88ac9885c51efe06d1";
            connection.getOutputStream().write(data.getBytes());

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Extract the API key from the response
                // do response["access_token"]
                String responseString = response.toString();
                String apiKey = responseString.substring(responseString.indexOf(":\"") + 2, responseString.indexOf("\",\""));
                return apiKey;
            } else {
                System.out.println("Request failed with response code: " + responseCode);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
  }

  public List<AudioBook> getAudioBooks(String query) {
      try {
          URL url = new URL("https://api.spotify.com/v1/search?query=" + query + "&type=audiobook");
          URLConnection connection = establishConnection(url);
          
          // TODO: Implement method to directly fetch AuthToken

          String authToken = getAuthToken(CLIENT_ID, CLIENT_SECRET);
          
          
          connection.setRequestProperty("Authorization", "Bearer " + authToken); // Setting Bearer token


          InputStream response = connection.getInputStream();
          
          JsonNode json = getRootNode(response);
          
          JsonNode results = json.get("audiobooks").get("items");
          List<AudioBook> audioBooks = new ArrayList<AudioBook>();

          if (results.isArray()) {
              Spotify_AudioBook SpotifyAudioBookAdapter = new Spotify_AudioBook();
              for (JsonNode result : results) {
                  AudioBook audioBook = SpotifyAudioBookAdapter.getAudioBookfromJSON(result);
                  audioBooks.add(audioBook);
              }
          }

          System.out.println("Audio Books Array: ");
          System.out.println(audioBooks);
          
          return audioBooks;
      } catch (Exception e) { 
          System.out.println(e);
          return new ArrayList<AudioBook>();
      }
  }

  public List<Podcast> getPodcasts(String query) {
      try {
        URL url = new URL("https://api.spotify.com/v1/search?query=" + query + "&type=episode" + "&market=es");
          URLConnection connection = establishConnection(url);

          String authToken = getAuthToken(CLIENT_ID, CLIENT_SECRET);

          
          connection.setRequestProperty("Authorization", "Bearer " + authToken); // Setting Bearer token
          InputStream response = connection.getInputStream();
          JsonNode json = getRootNode(response);
          
          JsonNode results = json.get("episodes").get("items");

          System.out.println("Fetched Results: " + results);

          List<Podcast> podcasts = new ArrayList<Podcast>();

          
          if (results.isArray()) {
              Spotify_Podcast spotifyPodcastAdapter = new Spotify_Podcast();
              for (JsonNode result : results) {
                  Podcast podcast = spotifyPodcastAdapter.getPodcastfromJSON(result);
                  System.out.println("Podcast receievd from adapter: ");
                  System.out.println(podcast.getId() + " " + podcast.getTitle() + " " + podcast.getArtist() + " " + podcast.getUrlLink());
                  podcasts.add(podcast);
              }
          }

          System.out.println("Podcasts Array: ");
          System.out.println(podcasts);
          
          return podcasts;
      } catch (Exception e) { 
          System.out.println(e);
          return new ArrayList<Podcast>();
      }
  }
}
