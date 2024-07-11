package com.sismics.books.rest.resource.audiobookresource;

import java.util.Date;
import java.util.List;
import java.io.Console;
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

import com.sismics.books.core.dao.jpa.AudioBookDao;
import com.sismics.books.core.dao.jpa.UserAudioBookDao;
import com.sismics.books.core.model.context.AppContext;
import com.sismics.books.core.model.jpa.AudioBook;
import com.sismics.books.core.model.jpa.UserAudioBook;
import com.sismics.books.core.service.AudioContent.AudioContentFactory;
import com.sismics.books.rest.resource.BaseResource;
import com.sismics.rest.exception.ClientException;
import com.sismics.rest.exception.ForbiddenClientException;
import com.sismics.rest.util.ValidationUtil;

@Path("/audio/audiobook/list")
public class ListAudioBook extends BaseResource{
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

      List<AudioBook> audioBooks = new ArrayList<AudioBook>();

      try{
        // TODO : Valdlidate provider and contentType
        AppContext.getInstance().setAudioContentFactory(provider);
        if (contentType.equals("audiobooks")) {
          AudioContentFactory  contentfactory = AppContext.getInstance().getAudioContentFactory();
          audioBooks = contentfactory.getAudioBooks(q);
        }
      } catch (Exception e) {
        throw new ClientException("No Audio Books found", e.getCause().getMessage(), e);
      }

      List<JSONObject> audioBooksJson = new ArrayList<JSONObject>();
      for (AudioBook audioBook : audioBooks) {
          JSONObject audioBookJson = new JSONObject();
          audioBookJson.put("id", audioBook.getId());
          audioBookJson.put("title", audioBook.getTitle());
          audioBookJson.put("author", audioBook.getAuthor());
          audioBookJson.put("description", audioBook.getDescription());
          audioBooksJson.add(audioBookJson);
      }
  
      JSONObject response = new JSONObject();
      response.put("total", audioBooks.size());
      response.put("audioBooks", audioBooksJson);
      return Response.ok().entity(response).build();
  }
}
