package com.sismics.books.core.model.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects;

/**
 * User book entity.
 * 
 * @author bgamard
 */
@Entity
@Table(name = "T_USER_PODCAST")
public class UserPodcast implements Serializable {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * User book ID.
     */
    @Id
    @Column(name = "UPD_ID_C", length = 36)
    private String id;
    
    /**
     * Book ID.
     */
    @Id
    @Column(name = "UPD_IDPOD_C", nullable = false, length = 36)
    private String podcastId;
    
    /**
     * User ID.
     */
    @Id
    @Column(name = "UPD_IDUSER_C", nullable = false, length = 36)
    private String userId;
    
    /**
     * Creation date.
     */
    @Column(name = "UPD_CREATEDATE_D", nullable = false)
    private Date createDate;
    
    /**
     * Deletion date.
     */
    @Column(name = "UPD_DELETEDATE_D")
    private Date deleteDate;
    
    /**
     * listened date.
     */
    @Column(name = "UPD_LISTENEDDATE_D")
    private Date listenedDate;
    
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
     * Getter of podcastId.
     * 
     * @return podcastId
     */
    public String getPodcastId() {
        return podcastId;
    }

    /**
     * Setter of boopodcastIdkId.
     * 
     * @param podcastId podcastId
     */
    public void setPodcastId(String podcastId) {
        this.podcastId = podcastId;
    }

    /**
     * Getter of userId.
     * 
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter of userId.
     * 
     * @param userId userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter of createDate.
     * 
     * @return createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Setter of createDate.
     * 
     * @param createDate createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Getter of deleteDate.
     * 
     * @return deleteDate
     */
    public Date getDeleteDate() {
        return deleteDate;
    }

    /**
     * Setter of deleteDate.
     * 
     * @param deleteDate deleteDate
     */
    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    /**
     * Getter of listenedDate.
     * 
     * @return listenedDate
     */
    public Date getReadDate() {
        return listenedDate;
    }

    /**
     * Setter of listenedDate.
     * 
     * @param listenedDate listenedDate
     */
    public void setReadDate(Date listenedDate) {
        this.listenedDate = listenedDate;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((podcastId == null) ? 0 : podcastId.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UserPodcast other = (UserPodcast) obj;
        if (podcastId == null) {
            if (other.podcastId != null) {
                return false;
            }
        } else if (!podcastId.equals(other.podcastId)) {
            return false;
        }
        if (userId == null) {
            if (other.userId != null) {
                return false;
            }
        } else if (!userId.equals(other.userId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .toString();
    }
}
