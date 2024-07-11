package com.sismics.books.core.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.sismics.books.core.dao.jpa.dto.LibBookRatingDto;
import com.sismics.books.core.model.jpa.LibBookRating;
import com.sismics.books.core.model.jpa.LibBook;

import com.sismics.util.context.ThreadLocalContext;

/**
 * LibBookRating DAO.
 * 
 * @author FlighVin
 */
public class LibBookRatingDao {
    /**
     * Creates a new LibBookRating.
     * 
     * @param libBookRating LibBookRating
     * @return New ID
     * @throws Exception
     */
    public String create(LibBookRating libBookRating) {
        // Create the UUID
        libBookRating.setId(UUID.randomUUID().toString());
        
        // Create the libBookRating
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        // tag.setCreateDate(new Date());
        em.persist(libBookRating);
        
        return libBookRating.getId();
    }

    /**
     * Gets a LibBookRating by its ID.
     * 
     * @param id LibBookRating ID
     * @return LibBookRating
     */
    public LibBookRating getById(String id) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        try {
            return em.find(LibBookRating.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }


    /**
     * Gets a LibBookRating by it userId and libBookId.
     * 
     * @return LibBookRating
     */
    public LibBookRating getByBookIdAndUserId(String libBookId, String userId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select ub from LibBookRating ub where ub.libBookId = :libBookId and ub.userId = :userId");
        q.setParameter("libBookId", libBookId);
        q.setParameter("userId", userId);
        try {
            return (LibBookRating) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Returns the list of all LibBooks for a book
     * 
     * @param LibBookId LibBook ID
     * @return List of LibBooks
     */
    public List<LibBookRatingDto> getByLibBookId(String libBookId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select t from LibBookRating t where t.libBookId = :libBookId order by t.rating");
        q.setParameter("libBookId", libBookId);

        List<LibBookRating> l = q.getResultList();
        
        // Assemble results
        List<LibBookRatingDto> libBookRatingDtoList = new ArrayList<LibBookRatingDto>();
        for (LibBookRating o : l) {
            int i = 0;
            LibBookRatingDto libBookRatingDto = new LibBookRatingDto();
            libBookRatingDto.setId((String) o.getId());
            libBookRatingDto.setRating((float) o.getRating());

            libBookRatingDtoList.add(libBookRatingDto);
        }
        return libBookRatingDtoList;    
    }
}
