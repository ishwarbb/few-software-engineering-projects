package com.sismics.books.core.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.sismics.books.core.dao.jpa.dto.LibBookGenreDto;
import com.sismics.books.core.model.jpa.LibBookGenre;
import com.sismics.books.core.model.jpa.LibBook;

import com.sismics.util.context.ThreadLocalContext;

/**
 * LibBookGenre DAO.
 * 
 * @author FlighVin
 */
public class LibBookGenreDao {
    /**
     * Creates a new LibBookGenre.
     * 
     * @param libBookGenre LibBookGenre
     * @return New ID
     * @throws Exception
     */
    public String create(LibBookGenre libBookGenre) {
        // Create the UUID
        libBookGenre.setId(UUID.randomUUID().toString());
        
        // Create the libBookGenre
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        // tag.setCreateDate(new Date());
        em.persist(libBookGenre);
        
        return libBookGenre.getId();
    }

    /**
     * Gets a LibBookGenre by its ID.
     * 
     * @param id LibBookGenre ID
     * @return LibBookGenre
     */
    public LibBookGenre getById(String id) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        try {
            return em.find(LibBookGenre.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }


    /**
     * Gets a LibBookGenre by it genreName and libBookId.
     * 
     * @return LibBookGenre
     */
    public LibBookGenre getByBookIdAndName(String libBookId, String genreName) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select ub from LibBookGenre ub where ub.libBookId = :libBookId and ub.genreName = :genreName");
        q.setParameter("genreName", genreName);
        q.setParameter("libBookId", libBookId);
        try {
            return (LibBookGenre) q.getSingleResult();
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
    public List<LibBookGenreDto> getByLibBookId(String libBookId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select t from LibBookGenre t where t.libBookId = :libBookId order by t.genreName");
        q.setParameter("libBookId", libBookId);

        List<LibBookGenre> l = q.getResultList();
        
        // Assemble results
        List<LibBookGenreDto> libBookGenreDtoList = new ArrayList<LibBookGenreDto>();
        for (LibBookGenre o : l) {
            int i = 0;
            LibBookGenreDto libBookGenreDto = new LibBookGenreDto();
            libBookGenreDto.setId((String) o.getId());
            libBookGenreDto.setGenreName((String) o.getGenreName());

            libBookGenreDtoList.add(libBookGenreDto);
        }
        return libBookGenreDtoList;    
    }
}
