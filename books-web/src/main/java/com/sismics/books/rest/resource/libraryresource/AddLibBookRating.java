package com.sismics.books.rest.resource.libraryresource;

import java.util.Date;

import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

import java.lang.Float;
import java.lang.String;

@Path("/library/rating")
public class AddLibBookRating extends BaseResource{
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  public Response add(
          @FormParam("libBookId") String libBookId, 
          @FormParam("userId") String userId,
          @FormParam("rating") float rating) throws JSONException {
      if (!authenticate()) {
          throw new ForbiddenClientException();
      }
      
      // currently, userId is not being checked. @swayam, pass in username into this
      // if you need to, it'll still work

      // define a float lower and upper bound for rating
      Float ratingfl = new Float(rating);
      Float lowerLim = new Float(1.0);
      Float upperLim = new Float(10.0);
      String name = "rating";

      // ValidationUtil.checkRange(ratingfl, name, lowerLim, upperLim);

      // Fetch the Library book
      LibBookDao libBookDao = new LibBookDao();
      LibBook book = libBookDao.getLibBook(libBookId);
      if (book == null) {
        throw new ClientException("LibBookNotFound", "Library Book does not exist");
      }
      
      // Create the LibBookRatingDao
      LibBookRatingDao libBookRatingDao = new LibBookRatingDao();
      
      LibBookRating libBookRating = libBookRatingDao.getByBookIdAndUserId(libBookId, userId);
      if (libBookRating == null) {
          libBookRating = new LibBookRating();
          libBookRating.setLibBookId(libBookId);
          libBookRating.setUserId(userId);
          libBookRating.setRating(rating);
          libBookRatingDao.create(libBookRating);
      } else {
          throw new ClientException("LibBookRatingAlreadyAdded", "Library Book Rating already added by this user");
      }
      
      JSONObject response = new JSONObject();
      response.put("id", libBookRating.getId());
      response.put("libraryBookId", libBookRating.getLibBookId());
      response.put("userId", libBookRating.getUserId());
      response.put("rating", libBookRating.getRating());
      return Response.ok().entity(response).build();
  }
} 
