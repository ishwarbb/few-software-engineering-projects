package com.sismics.books.core.model.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects;

/**
 * Library book entity.
 * 
 * @author FLightVin
 */
@Entity
@Table(name = "T_LIB_BOOK")
public class LibBook implements Serializable {
    /**
     * Serial version UID.
     * ZERO clue what it's doing - will remove later and see
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Library book ID.
     */
    @Id
    @Column(name = "LBK_ID_C", length = 36)
    private String id;
    
    /**
     * Book ID.
     */
    @Id
    @Column(name = "LBK_IDBOOK_C", nullable = false, length = 36)
    private String bookId;
    
    /**
     * Deletion date.
     */
    @Column(name = "LBK_DELETEDATE_D")
    private Date deleteDate;
    
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
     * Getter of bookId.
     * 
     * @return bookId
     */
    public String getBookId() {
        return bookId;
    }

    /**
     * Setter of bookId.
     * 
     * @param bookId bookId
     */
    public void setBookId(String bookId) {
        this.bookId = bookId;
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
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
        // result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
        LibBook other = (LibBook) obj;
        if (bookId == null) {
            if (other.bookId != null) {
                return false;
            }
        } else if (!bookId.equals(other.bookId)) {
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
