package com.sismics.books.core.dao.jpa.dto;

import javax.persistence.Id;
import java.util.List;

/**
 * User book DTO.
 *
 * @author FlightVin 
 */
public class LibBookDto {
    /**
     * User book ID.
     */
    @Id
    private String id;
    
    /**
     * Title.
     */
    private String title;

    /**
     * Subtitle.
     */
    private String subtitle;
    
    /**
     * Author.
     */
    private String author;

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
     * Getter of title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter of title.
     *
     * @param title title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter of author.
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Setter of author.
     *
     * @param author author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Getter of subtitle.
     *
     * @return the subtitle
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * Setter of subtitle.
     *
     * @param subtitle subtitle
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
