package com.sismics.books.rest.resource.audiobookresource;

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

import com.sismics.books.core.dao.jpa.AudioBookDao;
import com.sismics.books.core.model.jpa.AudioBook;
import com.sismics.rest.util.ValidationUtil;

@Path("/audio/audiobook")
public class AddAudioBook { 
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAudioBook(
    @FormParam("id") String id,
    @FormParam("author") String author,
    @FormParam("title") String title,
    @FormParam("description") String description) throws JSONException {
        ValidationUtil.validateRequired(id, "id");
        ValidationUtil.validateRequired(author, "author");
        ValidationUtil.validateRequired(title, "title");
        ValidationUtil.validateRequired(description, "description");

        AudioBook audioBook = new AudioBook();
        audioBook.setId(id);
        audioBook.setTitle(title);
        audioBook.setAuthor(author);
        audioBook.setDescription(description);

        try {
          AudioBookDao audioBookDao = new AudioBookDao();

            String audiobookId = audioBookDao.create(audioBook);

            JSONObject jsonResponse = new JSONObject();

            jsonResponse.put("message", "Audiobook created successfully");
            jsonResponse.put("id", audiobookId);

            return Response.ok().entity(jsonResponse).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to create audiobook: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAudioBookById(@QueryParam("id") String id) {
        try {
          AudioBookDao audioBookDao = new AudioBookDao();
            // Get the audiobook by ID
            System.out.println("Fetching AudioBook!!! " + id);
            AudioBook audiobook = audioBookDao.getById(id.toString());
            System.out.println("Found Audiobook: " + audiobook);

            if (audiobook != null) {
                JSONObject jsonResponse = new JSONObject();

                jsonResponse.put("message", "Audiobook created successfully");
                jsonResponse.put("id", audiobook.getId());
                jsonResponse.put("author", audiobook.getAuthor());
                jsonResponse.put("title", audiobook.getTitle());
                jsonResponse.put("description", audiobook.getDescription());
                
                return Response.ok().entity(jsonResponse).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Audiobook not found with ID: " + id).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to retrieve audiobook: " + e.getMessage()).build();
        }
    }
}
