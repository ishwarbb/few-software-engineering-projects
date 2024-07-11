package com.sismics.books.core.dao.jpa.dto;

import javax.persistence.Id;

/**
 * Genre DTO.
 *
 * @author FlightVin 
 */
public class LibBookGenreDto {
    /**
     * Tag ID.
     */
    @Id
    private String id;
    
    /**
     * Name.
     */
    private String genreName;

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
     * Getter of genreName.
     *
     * @return the genreName
     */
    public String getGenreName() {
        return genreName;
    }

    /**
     * Setter of genreName.
     *
     * @param genreName genreName
     */
    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
