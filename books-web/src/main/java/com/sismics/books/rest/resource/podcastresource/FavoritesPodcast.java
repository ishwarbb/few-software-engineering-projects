package com.sismics.books.rest.resource.podcastresource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.restfb.types.User;
import com.sismics.books.core.dao.jpa.PodcastDao;
import com.sismics.books.core.dao.jpa.UserPodcastDao;
import com.sismics.books.core.model.jpa.Podcast;
import com.sismics.books.core.model.jpa.UserPodcast;
import com.sismics.books.core.model.jpa.Podcast;
import com.sismics.books.rest.resource.BaseResource;
import com.sismics.rest.exception.ClientException;
import com.sismics.rest.exception.ForbiddenClientException;
import com.sismics.rest.util.ValidationUtil;

@Path("/audio/podcast/favorites")
public class FavoritesPodcast extends BaseResource {
  @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(@FormParam("id") String id,
                        @FormParam("title") String title,
                        @FormParam("artist") String artist,
                        @FormParam("viewUrl") String viewUrl) throws JSONException {
        // Check authentication
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }

        // Validate input data
        ValidationUtil.validateRequired(id, "id");
        ValidationUtil.validateRequired(title, "title");
        ValidationUtil.validateRequired(artist, "artist");

        // Create a new UserPodcast entry
        UserPodcast userPodcast = new UserPodcast();
        userPodcast.setUserId(principal.getId());
        userPodcast.setPodcastId(id);
        userPodcast.setCreateDate(new Date());

        // Persist the UserPodcast entry
        String userPodcastId = new UserPodcastDao().create(userPodcast);

        // Build the response
        JSONObject response = new JSONObject();
        response.put("id", userPodcastId);
        return Response.ok().entity(response).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(@FormParam("id") String id) throws JSONException {
        // Check authentication
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }

        // Validate input data
        ValidationUtil.validateRequired(id, "id");

        // Delete the UserPodcast entry
        new UserPodcastDao().deleteByPodcast(principal.getId(), id);

        // Build the response
        JSONObject response = new JSONObject();
        response.put("message", "User Podcast removed successfully");
        return Response.ok().entity(response).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() throws JSONException {
        // Check authentication
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }

        System.out.println("In FavoritesPodcast GET method");

        // Get the user's favorite Podcasts
        List<UserPodcast> userPodcasts = new UserPodcastDao().getUserPodcasts(principal.getId());
        System.out.println("User Podcasts: " + userPodcasts);

        List<JSONObject> userPodcastsData = new ArrayList<JSONObject>();
        for (UserPodcast userPodcast : userPodcasts) {
            JSONObject userPodcastJson = new JSONObject();
            userPodcastJson.put("id", userPodcast.getId());
            userPodcastJson.put("podcastId", userPodcast.getPodcastId());
            userPodcastJson.put("userId", userPodcast.getUserId());
            userPodcastJson.put("createDate", userPodcast.getCreateDate());
            userPodcastsData.add(userPodcastJson);
        }

        System.out.println("User Podcasts: " + userPodcastsData);

        // Get the corresponding podcasts for the user
        List<Podcast> podcasts = new ArrayList<Podcast>();
        for (UserPodcast userPodcast : userPodcasts) {
            System.out.println("Podcast ID: " + userPodcast.getPodcastId());
            Podcast podcast = new PodcastDao().getById(userPodcast.getPodcastId());
            podcasts.add(podcast);
        }

        System.out.println("Podcasts: " + podcasts);

        List<JSONObject> podcastsJson = new ArrayList<JSONObject>();
        for (Podcast podcast : podcasts) {
            JSONObject podcastJson = new JSONObject();
            podcastJson.put("id", podcast.getId());
            podcastJson.put("title", podcast.getTitle());
            podcastJson.put("artist", podcast.getArtist());
            podcastJson.put("viewUrl", podcast.getUrlLink());
            podcastsJson.add(podcastJson);
        }

        System.out.println("Podcasts: " + podcastsJson);

        // Build the response
        JSONObject response = new JSONObject();
        response.put("userPodcasts", userPodcastsData);
        response.put("podcasts", podcastsJson);
        return Response.ok().entity(response).build();
    }
}
