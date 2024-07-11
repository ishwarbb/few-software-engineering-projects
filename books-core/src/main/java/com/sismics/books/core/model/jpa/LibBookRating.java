package com.sismics.books.core.model.jpa;

import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entity representing book ratings.
 * 
 * @author FlightVin
 */
@Entity
@Table(name = "T_LIB_BOOK_RATING")
public class LibBookRating implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "RAT_ID_C", length = 36)
    private String id;

    @Column(name = "RAT_IDUSER_C", length = 36, nullable = false)
    private String userId;

    @Column(name = "RAT_IDLIBBOOK_C", length = 36, nullable = false)
    private String libBookId;

    @Column(name = "RAT_VALUE_C", nullable = false)
    private float rating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLibBookId() {
        return libBookId;
    }

    public void setLibBookId(String libBookId) {
        this.libBookId = libBookId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((libBookId == null) ? 0 : libBookId.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) return false;
        LibBookRating other = (LibBookRating) obj;
        return Objects.equal(libBookId, other.libBookId) && Objects.equal(userId, other.userId) && Objects.equal(rating, other.rating);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("libBookId", libBookId)
                .add("userId", userId)
                .add("rating", rating)
                .toString();
    }
}
