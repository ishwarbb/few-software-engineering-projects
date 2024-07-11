package com.sismics.books.rest.resource.libraryresource;


import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import com.sismics.books.core.dao.jpa.LibBookDao;
import com.sismics.books.core.dao.jpa.LibBookGenreDao;
import com.sismics.books.core.dao.jpa.LibBookRatingDao;
import com.sismics.books.core.dao.jpa.criteria.LibBookCriteria;

import com.sismics.books.core.dao.jpa.dto.LibBookDto;
import com.sismics.books.core.dao.jpa.dto.LibBookGenreDto;
import com.sismics.books.core.dao.jpa.dto.LibBookRatingDto;

import com.sismics.books.core.util.jpa.PaginatedList;
import com.sismics.books.core.util.jpa.PaginatedLists;
import com.sismics.books.core.util.jpa.SortCriteria;
import com.sismics.books.rest.resource.BaseResource;
import com.sismics.rest.exception.ForbiddenClientException;
import com.sismics.rest.exception.ServerException;

@Path("/library/list")
public class ListLibBooks extends BaseResource{
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(
            @QueryParam("limit") Integer limit,
            @QueryParam("offset") Integer offset,
            @QueryParam("sort_column") Integer sortColumn,
            @QueryParam("asc") Boolean asc,
            @QueryParam("search") String search,

            @QueryParam("filter_authors") String filter_authors,
            @QueryParam("filter_genres") String filter_genres,
            @QueryParam("filter_min_rating") Float filter_min_rating,
            @QueryParam("library_sort") Integer library_sort) throws JSONException {

                // filter_authors and filter_genres will receive a comma separated list of author names
                // library_sort: 0 - no sorting, 1 - by average rating, 2 - by number of rating
                // @SWAYAM, use the limit paramater to limit the number to 10 when calling for sorting

        if (!authenticate()) {
            throw new ForbiddenClientException();
        }
        
        JSONObject response = new JSONObject();
        List<JSONObject> books = new ArrayList<>();
        
        LibBookDao libBookDao = new LibBookDao();
        LibBookGenreDao genreDao = new LibBookGenreDao();
        LibBookRatingDao ratingDao = new LibBookRatingDao();
        PaginatedList<LibBookDto> paginatedList = PaginatedLists.create(null, offset); // changed limit here to handle the sort limit later
        SortCriteria sortCriteria = new SortCriteria(sortColumn, asc);
        LibBookCriteria criteria = new LibBookCriteria();

        criteria.setSearch(search);

        // authors filter
        if (!Strings.isNullOrEmpty(filter_authors)){
            List<String> authors = new ArrayList<>();

            String[] authors_array = filter_authors.split(",");
            for (String author : authors_array) {
                authors.add(author.trim());
            }

            criteria.setFilterAuthors(authors);
        } else {
            criteria.setFilterAuthors(null);
        }

        // genres filter
        if (!Strings.isNullOrEmpty(filter_genres)){
            List<String> genres = new ArrayList<>();

            String[] genre_array = filter_genres.split(",");
            for (String genre : genre_array) {
                genres.add(genre.trim());
            }

            criteria.setFilterGenres(genres);
        } else {
            criteria.setFilterGenres(null);
        }

        // minimum rating filter
        if (filter_min_rating != null){
            System.out.println("Filtering by minimum rating: " + filter_min_rating);
            criteria.setFilterMinRating(filter_min_rating);
        } else {
            criteria.setFilterMinRating((float)-1.0);
        }

        // library sorting criteria 
        if (library_sort != null && (library_sort == 1 || library_sort == 2)){
            criteria.setLibrarySort(library_sort);
        } else {
            criteria.setLibrarySort(0);
        }

        try {
            libBookDao.findByCriteria(paginatedList, criteria, sortCriteria);
        } catch (Exception e) {
            throw new ServerException("SearchError", "Error searching in library books", e);
        }

        int addedBooks = 0; // Counter to track the number of books added
        for (LibBookDto libBookDto : paginatedList.getResultList()) {
            if (limit != null && addedBooks >= limit) {
                break; // Break the loop if the limit has been reached
            }

            JSONObject book = new JSONObject();
            book.put("id", libBookDto.getId());
            book.put("title", libBookDto.getTitle());
            book.put("subtitle", libBookDto.getSubtitle());
            book.put("author", libBookDto.getAuthor());
            book.put("currently_logged_userid",principal.getId());
            book.put("currently_logged_username",principal.getName());

            // Get Genres
            List<LibBookGenreDto> genreDtoList = genreDao.getByLibBookId(libBookDto.getId());
            List<String> genres = new ArrayList<>();
            for (LibBookGenreDto libBookGenreDto : genreDtoList) {
                genres.add(libBookGenreDto.getGenreName());
            }
            book.put("genres", genres);

            // Get Ratings
            List<LibBookRatingDto> ratingDtoList = ratingDao.getByLibBookId(libBookDto.getId());
            List<Float> ratings = new ArrayList<>();
            for (LibBookRatingDto libBookRatingDto : ratingDtoList) {
                ratings.add(libBookRatingDto.getRating());
            }

            // calculating average and number of ratings
            int numRatings = ratings.size();
            float sumRatings = 0;
            for (Float rating : ratings) {
                sumRatings += rating;
            }
            float averageRating = (numRatings > 0) ? sumRatings / numRatings : 0;
            book.put("avg_rating", averageRating);
            book.put("num_ratings", numRatings);
            
            books.add(book);
        }

        response.put("total", books.size());
        response.put("books", books);

        response.put("Filtered Authors", criteria.getFilterAuthors());
        response.put("Filtered Genres", criteria.getFilterGenres());
        response.put("Minimum Rating Filter", criteria.getFilterMinRating());
        
        return Response.ok().entity(response).build();
    }
}
