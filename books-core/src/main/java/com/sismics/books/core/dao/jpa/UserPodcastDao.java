package com.sismics.books.core.dao.jpa;

import java.util.Date;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import java.util.List;

import com.sismics.books.core.model.jpa.UserPodcast;
import com.sismics.util.context.ThreadLocalContext;

/**
 * User podcast DAO.
 *
 *
 */
public class UserPodcastDao {
  /**
     * Creates a new user UserPodcast.
     * 
     * @param UserPodcast UserPodcast
     * @return New ID
     * @throws Exception
     */
    public String create(UserPodcast userPodcast) {
        // Create the UUID
        userPodcast.setId(UUID.randomUUID().toString());
        
        // Create the user book
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        em.persist(userPodcast);
        
        return userPodcast.getId();
    }

    /**
     * Return all user audiobooks
     * @param userAudioBookId User audiobook ID
     * @return User audiobook
     */
    public List<UserPodcast> getUserPodcasts(String userId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        try {
            Query q = em.createQuery("select pod from UserPodcast pod where pod.userId = :userId and pod.deleteDate is null");
            q.setParameter("userId", userId);
            return q.getResultList();
        } catch (Exception e) {
            // Log the exception or handle it according to your application's error handling strategy
            e.printStackTrace();
            return null; // Return null in case of an error
        }
    }

     /**
     * Return a user podcast by podcast ID and user ID.
     * 
     * @param podcastId podcast ID
     * @param userId User ID
     * @return User book
     */
    public UserPodcast getByPodcast(String podcastId, String userId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select upd from UserPodcast upd where upd.podcastId = :podcastId and upd.userId = :userId and upd.deleteDate is null");
        q.setParameter("podcastId", podcastId);
        q.setParameter("userId", userId);
        try {
            return (UserPodcast) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Deletes a user podcast by Podcast ID and User ID.
     * 
     * @param userId User ID
     * @param podcastId Podcast ID
     */
    public void deleteByPodcast(String userId, String podcastId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
            
        // Get the user book
        Query q = em.createQuery("select upd from UserPodcast upd where upd.podcastId = :podcastId and upd.userId = :userId and upd.deleteDate is null");
        q.setParameter("podcastId", podcastId);
        q.setParameter("userId", userId);
        UserPodcast userPodcastDb = (UserPodcast) q.getSingleResult();
        
        // Delete the user book
        Date dateNow = new Date();
        userPodcastDb.setDeleteDate(dateNow);
    }
}