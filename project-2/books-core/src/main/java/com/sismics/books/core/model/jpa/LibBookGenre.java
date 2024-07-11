package com.sismics.books.core.model.jpa;

import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Link between a book and a tag.
 * 
 * @author FlightVin
 */
@Entity
@Table(name = "T_LIB_BOOK_GENRE")
public class LibBookGenre implements Serializable {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Genre ID.
     */
    @Id
    @Column(name = "GEN_ID_C", length = 36)
    private String id;
    
    /**
     * Tag ID.
     */
    @Id
    @Column(name = "GEN_NAME_C", length = 36)
    private String genreName;

    /**
     * Lib book ID.
     */
    @Id
    @Column(name = "GEN_IDLIBBOOK_C", length = 36)
    private String libBookId;

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
     * Getter de libBookId.
     *
     * @return the libBookId
     */
    public String getLibBookId() {
        return libBookId;
    }

    /**
     * Setter de libBookId.
     *
     * @param documentId libBookId
     */
    public void setLibBookId(String libBookId) {
        this.libBookId = libBookId;
    }

    /**
     * Getter de genreName.
     *
     * @return the genreName
     */
    public String getGenreName() {
        return genreName;
    }

    /**
     * Setter de genreName.
     *
     * @param genreName genreName
     */
    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((libBookId == null) ? 0 : libBookId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) return false;
        LibBookGenre other = (LibBookGenre) obj;
        return Objects.equal(libBookId, other.libBookId) && Objects.equal(genreName, other.genreName);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("libBookId", libBookId)
                .add("genreName", genreName)
                .toString();
    }
}