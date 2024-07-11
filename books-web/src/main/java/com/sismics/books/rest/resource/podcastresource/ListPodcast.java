package com.sismics.books.rest.resource.podcastresource;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sismics.books.core.dao.jpa.PodcastDao;
import com.sismics.books.core.dao.jpa.UserPodcastDao;
import com.sismics.books.core.model.context.AppContext;
import com.sismics.books.core.model.jpa.Podcast;
import com.sismics.books.core.model.jpa.UserPodcast;
import com.sismics.books.core.service.AudioContent.AudioContentFactory;
import com.sismics.books.rest.resource.BaseResource;
import com.sismics.rest.exception.ClientException;
import com.sismics.rest.exception.ForbiddenClientException;
import com.sismics.rest.util.ValidationUtil;

@Path("/audio/podcast/list")
public class ListPodcast extends BaseResource{
  @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(
        @QueryParam("q") String q, 
        @QueryParam("provider") String provider, 
        @QueryParam("contentType") String contentType
        ) throws JSONException {

      if (!authenticate()) {
          throw new ForbiddenClientException();
      }

      // Validate input data
      ValidationUtil.validateRequired(q, "q");

      List<Podcast> podcasts = new ArrayList<Podcast>();

      try{
        // TODO : Valdlidate provider and contentType
        AppContext.getInstance().setAudioContentFactory(provider);
        if (contentType.equals("podcasts")) {
          AudioContentFactory  contentfactory = AppContext.getInstance().getAudioContentFactory();

          podcasts = contentfactory.getPodcasts(q);

        }

      } catch (Exception e) {
        throw new ClientException("No Podcasts found", e.getCause().getMessage(), e);
      }

      List<JSONObject> podcastsJson = new ArrayList<JSONObject>();
      for (Podcast podcast : podcasts) {
          JSONObject podcastJson = new JSONObject();
          podcastJson.put("id", podcast.getId());
          podcastJson.put("title", podcast.getTitle());
          podcastJson.put("artist", podcast.getArtist());
          podcastJson.put("viewUrl", podcast.getUrlLink());
          podcastsJson.add(podcastJson);
      }
  
      JSONObject response = new JSONObject();
      response.put("total", podcasts.size());
      response.put("podcasts", podcastsJson);
      return Response.ok().entity(response).build();
  }
}
