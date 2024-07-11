package com.sismics.books.rest.resource.audiobookresource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import com.sismics.books.core.dao.jpa.AudioBookDao;
import com.sismics.books.core.dao.jpa.UserAudioBookDao;
import com.sismics.books.core.model.jpa.AudioBook;
import com.sismics.books.core.model.jpa.UserAudioBook;
import com.sismics.books.rest.resource.BaseResource;
import com.sismics.rest.exception.ClientException;
import com.sismics.rest.exception.ForbiddenClientException;
import com.sismics.rest.util.ValidationUtil;

@Path("/audio/audiobook/favorites")
public class FavoritesAudioBook extends BaseResource {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(@FormParam("id") String id,
                        @FormParam("title") String title,
                        @FormParam("author") String author,
                        @FormParam("description") String description,
                        @FormParam("viewUrl") String viewUrl) throws JSONException {
        // Check authentication
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }

        // Validate input data
        ValidationUtil.validateRequired(id, "collectionId");
        ValidationUtil.validateRequired(title, "title");
        ValidationUtil.validateRequired(author, "author");
        ValidationUtil.validateRequired(description, "description");

        // Create a new UserAudioBook entry
        UserAudioBook userAudioBook = new UserAudioBook();
        userAudioBook.setUserId(principal.getId());
        userAudioBook.setAudioBookId(id);
        userAudioBook.setCreateDate(new Date());

        // Persist the UserAudioBook entry
        String userAudioBookId = new UserAudioBookDao().create(userAudioBook);

        // Build the response
        JSONObject response = new JSONObject();
        response.put("id", userAudioBookId);
        return Response.ok().entity(response).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(@QueryParam("id") String id) throws JSONException {
        // Check authentication
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }

        // Validate input data
        ValidationUtil.validateRequired(id, "id");

        // Delete the UserAudioBook entry
        new UserAudioBookDao().deleteByAudioBook(principal.getId(), id);

        // Build the response
        JSONObject response = new JSONObject();
        response.put("message", "User audiobook removed successfully");
        return Response.ok().entity(response).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() throws JSONException {
        // Check authentication
        if (!authenticate()) {
            throw new ForbiddenClientException();
        }

        // Get the user's favorite audiobooks
        List<UserAudioBook> userAudioBooks = new UserAudioBookDao().getUserAudioBooks(principal.getId());

        List<JSONObject> userAudioBooksData = new ArrayList<JSONObject>();
        for (UserAudioBook userAudioBook : userAudioBooks) {
            JSONObject userAudioBookJson = new JSONObject();
            userAudioBookJson.put("id", userAudioBook.getId());
            userAudioBookJson.put("audioBookId", userAudioBook.getAudioBookId());
            userAudioBookJson.put("userId", userAudioBook.getUserId());
            userAudioBookJson.put("createDate", userAudioBook.getCreateDate());
            userAudioBooksData.add(userAudioBookJson);
        }

        // Get the corresponding audiobooks for the user
        List<AudioBook> audioBooks = new ArrayList<AudioBook>();
        for (UserAudioBook userAudioBook : userAudioBooks) {
            AudioBook audioBook = new AudioBookDao().getById(userAudioBook.getAudioBookId());
            audioBooks.add(audioBook);
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

        // Build the response
        JSONObject response = new JSONObject();
        response.put("userAudioBooks", userAudioBooksData);
        response.put("audioBooks", audioBooksJson);
        return Response.ok().entity(response).build();
    }
}
