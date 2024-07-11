package com.sismics.books.core.model.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects;

@Entity
@Table(name = "T_PODCAST")
public class Podcast {
  /**
   * Podcast ID.
   */ 
  @Id
  @Column(name = "POD_ID_C", length = 36)
  private String id;
  
  /**
   * Title.
   */
  @Column(name = "POD_TITLE_C", length = 255)
  private String title;
  
  /**
   * Artist.
   */
  @Column(name = "POD_ARTIST_C",  length = 255)
  private String artist;
  
  /**
   * Publication date.
   */
  @Column(name = "POD_RELEASEDATE_D")
  private Date releaseDate;

  /**
   * Link to web-url.
   */
  @Column(name = "POD_URL", length = 500)
  private String urlLink;
  
  /**
   * Getter of id.
   * 
   * @return id
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
   * @return title
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
   * @return artist
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
      System.out.println("Podcast Artist: " + this.artist);
      System.out.println("Podcast Actual Artist: " + artist);
  }

  /**
   * Getter of releaseDate.
   * 
   * @return releaseDate
   */
  public Date getPublishDate() {
      return releaseDate;
  }

  /**
   * Setter of publishDate.
   * 
   * @param publishedDate publishDate
   */
  public void setPublishDate(Date releaseDate) {
      this.releaseDate = releaseDate;
  }

  /**
   * Getter of urlLink.
   * 
   * @return urlLink
   */
  public String getUrlLink(){
    return urlLink;
  }

  /**
   * Setter of urlLink.
   * 
   * @param urlLink urlLink
   */
  public void setUrlLink(String urlLink){
    this.urlLink = urlLink;
  }

  @Override
  public String toString() {
      return Objects.toStringHelper(this)
              .add("id", id)
              .add("title", title)
              .toString();
  }
}