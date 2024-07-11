package com.sismics.books.core.dao.jpa.dto;

import javax.persistence.Id;

/**
 * Rating DTO.
 *
 * @author FlightVin 
 */
public class LibBookRatingDto {
    /**
     * Tag ID.
     */
    @Id
    private String id;
    
    /**
     * Name.
     */
    private float rating;

    /**
     * Getter of id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter of id.
     *
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter of rating.
     *
     * @return the rating
     */
    public float getRating() {
        return rating;
    }

    /**
     * Setter of rating.
     *
     * @param rating rating
     */
    public void setRating(float rating) {
        this.rating = rating;
    }
}
