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
import com.sismics.books.core.dao.jpa.LibBookGenreDao;

import com.sismics.books.core.model.context.AppContext;

import com.sismics.books.core.model.jpa.Book;
import com.sismics.books.core.model.jpa.LibBook;
import com.sismics.books.core.model.jpa.LibBookGenre;

import com.sismics.books.rest.resource.BaseResource;
import com.sismics.rest.exception.ClientException;
import com.sismics.rest.exception.ForbiddenClientException;
import com.sismics.rest.util.ValidationUtil;

@Path("/library/genre")
public class AddLibBookGenre extends BaseResource{
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  public Response add(
          @FormParam("libBookId") String libBookId, 
          @FormParam("genreName") String genreName) throws JSONException {
      if (!authenticate()) {
          throw new ForbiddenClientException();
      }
      
      // Fetch the Library book
      LibBookDao libBookDao = new LibBookDao();
      LibBook book = libBookDao.getLibBook(libBookId);
      if (book == null) {
        throw new ClientException("LibBookNotFound", "Library Book does not exist");
      }
      
      // Create the LibBookGenreDao
      LibBookGenreDao libBookGenreDao = new LibBookGenreDao();
      
      LibBookGenre libBookGenre = libBookGenreDao.getByBookIdAndName(libBookId, genreName);
      if (libBookGenre == null) {
          libBookGenre = new LibBookGenre();
          libBookGenre.setLibBookId(libBookId);
          libBookGenre.setGenreName(genreName);
          libBookGenreDao.create(libBookGenre);
      } else {
          throw new ClientException("LibBookGenreAlreadyAdded", "Library Book Genre already added");
      }
      
      JSONObject response = new JSONObject();
      response.put("id", libBookGenre.getId());
      response.put("libraryBookId", libBookGenre.getLibBookId());
      response.put("Genre Name", libBookGenre.getGenreName());
      return Response.ok().entity(response).build();
  }
} 
