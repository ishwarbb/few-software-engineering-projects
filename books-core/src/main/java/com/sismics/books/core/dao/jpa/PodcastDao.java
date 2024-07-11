package com.sismics.books.core.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.sismics.books.core.model.jpa.AudioBook;
import com.sismics.books.core.model.jpa.Podcast;
import com.sismics.util.context.ThreadLocalContext;

/**
 * Book DAO.
 * 
 * @author bgamard
 */
public class PodcastDao {
    /**
     * Creates a new podcast.
     * 
     * @param podcast podcast
     * @return New ID
     * @throws Exception
     */
    public String create(Podcast podcast) {
        // Create the book
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        em.persist(podcast);
        return podcast.getId();
    }
    
    /**
     * Gets a podcast by its ID.
     * 
     * @param id Podcast ID
     * @return Podcast
     */
    public Podcast getById(String id) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        try {
            return(Podcast) em.find(Podcast.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }
}
