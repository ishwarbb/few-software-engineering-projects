package com.sismics.books.core.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.sismics.books.core.model.jpa.AudioBook;
import com.sismics.util.context.ThreadLocalContext;

/**
 * AudioBook DAO.
 * 
 * 
 */
public class AudioBookDao {
    /**
     * Creates a new audiobook.
     * 
     * @param audiobook audiobook
     * @return New ID
     * @throws Exception
     */

    public String create(AudioBook audiobook) {
        // TODO: Check if Audiobook already exists in the DAO.

        // Create the audiobook
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        em.persist(audiobook);
        Query q = em.createQuery("select ab from AudioBook ab where ab.id = :id");
        q.setParameter("id", audiobook.getId());
        System.out.println("Query Result: " + q.getSingleResult());
        return audiobook.getId();
    }
    
    /**
     * Gets a audiobook by its ID.
     * 
     * @param id Audio Book ID
     * @return Audio Book
     */
    public AudioBook getById(String id) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        try {
            // System.out.println("Trying to find book in DB");
            // Query q = em.createQuery("select ab from AudioBook ab where ab.id = :id");
            // q.setParameter("id", id);
            // System.out.println(id);
            // return (AudioBook) q.getSingleResult();
            return (AudioBook) em.find(AudioBook.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }
}
