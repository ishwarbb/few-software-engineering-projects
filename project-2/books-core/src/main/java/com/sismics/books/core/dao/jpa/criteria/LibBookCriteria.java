package com.sismics.books.core.dao.jpa.criteria;

import java.util.List;

/**
 * User book criteria.
 *
 * @author FlightVin 
 */
public class LibBookCriteria {    
    /**
     * Search query.
     */
    private String search;

    /**
     * Filter Author query
     */
    private List<String> filter_authors;

    /**
     * Filter Genres query
     */
    private List<String> filter_genres;

    /**
     * Filter Rating Query
     */
    private Float filter_min_rating;

    /**
     * Sorting Query
     */
    private Integer library_sort;

    /**
     * Getter of search.
     *
     * @return the search
     */
    public String getSearch() {
        return search;
    }

    /**
     * Setter of search.
     *
     * @param search search
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * Getter of filter_authors.
     *
     * @return the filter_authors
     */
    public List<String> getFilterAuthors() {
        return filter_authors;
    }

    /**
     * Setter of filter_authors.
     *
     * @param filterAuthors filter authors
     */
    public void setFilterAuthors(List<String> filterAuthors) {
        this.filter_authors = filterAuthors;
    }

    /**
     * Getter of filter_genres.
     *
     * @return the filter_genres
     */
    public List<String> getFilterGenres() {
        return filter_genres;
    }

    /**
     * Setter of filter_genres.
     *
     * @param filterGenres filter genres
     */
    public void setFilterGenres(List<String> filterGenres) {
        this.filter_genres = filterGenres;
    }

    /**
     * Getter of filter_min_rating.
     *
     * @return the filter_min_rating
     */
    public Float getFilterMinRating() {
        return filter_min_rating;
    }

    /**
     * Setter of filter_min_rating.
     *
     * @param filterMinRating filter min rating
     */
    public void setFilterMinRating(Float filterMinRating) {
        this.filter_min_rating = filterMinRating;
    }

    /**
     * Getter of library_sort.
     *
     * @return the library_sort
     */
    public Integer getLibrarySort() {
        return library_sort;
    }

    /**
     * Setter of library_sort.
     *
     * @param library_sort library_sort min rating
     */
    public void setLibrarySort(Integer library_sort) {
        this.library_sort = library_sort;
    }
}
