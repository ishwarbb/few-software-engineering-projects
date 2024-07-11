package com.sismics.books.rest.resource.libraryresource;

import java.util.Date;

import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sismics.books.core.dao.jpa.LibBookDao;
import com.sismics.books.core.dao.jpa.LibBookRatingDao;

import com.sismics.books.core.model.context.AppContext;

import com.sismics.books.core.model.jpa.Book;
import com.sismics.books.core.model.jpa.LibBook;
import com.sismics.books.core.model.jpa.LibBookRating;

import com.sismics.books.rest.resource.BaseResource;
import com.sismics.rest.exception.ClientException;
import com.sismics.rest.exception.ForbiddenClientException;
import com.sismics.rest.util.ValidationUtil;

/**
 * RESTful resource for managing library books.
 */
@Path("/library/book")
public class GetLibBook extends BaseResource {

    /**
     * Fetches details of a single book.
     * 
     * @param bookId ID of the book to fetch
     * @return Response containing book details
     */
    @GET
    @Path("/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("bookId") String bookId) {
        // Authenticate the user first
        // if (!authenticate()) {
        //     return Response.status(Response.Status.UNAUTHORIZED).build();
        // }

        // Fetch the book from the database
        LibBookDao libBookDao = new LibBookDao();
        LibBook libBook = libBookDao.getLibBook(bookId);

        System.out.println("libBook query result: " + libBook);
        
        if (libBook == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        try {
            System.out.println("libBook: " + libBook.toString());
            // JSONObject json = new JSONObject(libBook.toString()); // Simplified for demonstration
            return Response.ok(libBook).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error processing book details").build();
        }
    }
}
