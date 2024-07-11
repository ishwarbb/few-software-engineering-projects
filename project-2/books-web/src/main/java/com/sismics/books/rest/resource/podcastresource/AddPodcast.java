package com.sismics.books.rest.resource.podcastresource;

import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sismics.books.core.dao.jpa.PodcastDao;
import com.sismics.books.core.model.jpa.Podcast;
import com.sismics.rest.util.ValidationUtil;

@Path("/audio/podcast")
public class AddPodcast { 
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPodcast(
    @FormParam("id") String id,
    @FormParam("artist") String artist,
    @FormParam("title") String title,
    @FormParam("viewUrl") String viewUrl) throws JSONException {
        ValidationUtil.validateRequired(id, "id");
        ValidationUtil.validateRequired(artist, "artist");
        ValidationUtil.validateRequired(title, "title");
        ValidationUtil.validateRequired(viewUrl, "viewUrl");

        Podcast podcast = new Podcast();
        podcast.setId(id);
        podcast.setTitle(title);
        podcast.setArtist(artist);
        podcast.setUrlLink(viewUrl);

        System.out.println("In AddPodcast");
        System.out.println("Podcast : " + podcast.getId() + " " + podcast.getArtist() + " " + podcast.getTitle() + " " + podcast.getUrlLink());
        
        try {
            PodcastDao podcastDao = new PodcastDao();

            String podcastId = podcastDao.create(podcast);

            JSONObject jsonResponse = new JSONObject();

            jsonResponse.put("message", "Podcast created successfully");
            jsonResponse.put("id", podcastId);

            return Response.ok().entity(jsonResponse).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to create podcasr: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPodcastById(@QueryParam("id") String id) {
        try {
            PodcastDao podcastDao = new PodcastDao();
            Podcast podcast = podcastDao.getById(id);

            if (podcast != null) {
                JSONObject jsonResponse = new JSONObject();

                jsonResponse.put("message", "Podcast created successfully");
                jsonResponse.put("id", podcast.getId());
                jsonResponse.put("artist", podcast.getArtist());
                jsonResponse.put("title", podcast.getTitle());
                jsonResponse.put("viewUrl", podcast.getUrlLink());
                
                return Response.ok().entity(jsonResponse).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Podcast not found with ID: " + id).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to retrieve podcast: " + e.getMessage()).build();
        }
    }
}
