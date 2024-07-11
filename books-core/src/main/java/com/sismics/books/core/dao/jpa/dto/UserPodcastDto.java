package com.sismics.books.core.dao.jpa.dto;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * User book DTO.
 *
 * @author bgamard 
 */
public class UserPodcastDto {
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
     * Artist.
     */
    private String artist;

    /**
     * Description
     */
    private String description;
    
    /**
     * Language (ISO 639-1).
     */
    private String language;
    
    /**
     * Publication date.
     */
    private Long publishTimestamp;
    
    /**
     * Creation date.
     */
    private Long createTimestamp;
    
    /**
     * Read date.
     */
    private Long readTimestamp;

    /**
     * Link to web-url
     */
    private String urlLink;

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
     * Getter of artist.
     *
     * @return the artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Setter of artist.
     *
     * @param artist artist
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Getter of language.
     *
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Setter of language.
     *
     * @param language language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Getter of publishTimestamp.
     *
     * @return the publishTimestamp
     */
    public Long getPublishTimestamp() {
        return publishTimestamp;
    }

    /**
     * Setter of publishTimestamp.
     *
     * @param publishTimestamp publishTimestamp
     */
    public void setPublishTimestamp(Long publishTimestamp) {
        this.publishTimestamp = publishTimestamp;
    }

    /**
     * Getter of createTimestamp.
     *
     * @return the createTimestamp
     */
    public Long getCreateTimestamp() {
        return createTimestamp;
    }

    /**
     * Setter of createTimestamp.
     *
     * @param createTimestamp createTimestamp
     */
    public void setCreateTimestamp(Long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    /**
     * Getter of readTimestamp.
     *
     * @return the readTimestamp
     */
    public Long getReadTimestamp() {
        return readTimestamp;
    }

    /**
     * Setter of readTimestamp.
     *
     * @param readTimestamp readTimestamp
     */
    public void setReadTimestamp(Long readTimestamp) {
        this.readTimestamp = readTimestamp;
    }

     /**
     * Getter of urlLink.
     *
     * @return the urlLink
     */
    public String getUrlLink() {
        return urlLink;
    }

    /**
     * Setter of urlLink.
     *
     * @param urlLink urlLink
     */
    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    /**
     * Getter of description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter of description.
     *
     * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    

}
