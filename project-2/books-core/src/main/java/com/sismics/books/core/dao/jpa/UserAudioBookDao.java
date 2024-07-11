package com.sismics.books.core.dao.jpa;

import java.util.Date;
import java.util.UUID;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.sismics.books.core.model.jpa.UserAudioBook;
import com.sismics.util.context.ThreadLocalContext;

/**
 * User audiobook DAO.
 *
 *
 */
public class UserAudioBookDao {
  /**
     * Creates a new user audiobook.
     * 
     * @param userAudioBook UserAudioBook
     * @return New ID
     * @throws Exception
     */
    public String create(UserAudioBook userAudioBook) {
        // Create the UUID
        userAudioBook.setId(UUID.randomUUID().toString());
        
        // Create the user book
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        em.persist(userAudioBook);
        
        return userAudioBook.getId();
    }

    /**
     * Return all user audiobooks
     * @param userAudioBookId User audiobook ID
     * @return User audiobook
     */
    public List<UserAudioBook> getUserAudioBooks(String userId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        try {
            Query q = em.createQuery("select uab from UserAudioBook uab where uab.userId = :userId and uab.deleteDate is null");
            q.setParameter("userId", userId);
            return q.getResultList();
        } catch (Exception e) {
            // Log the exception or handle it according to your application's error handling strategy
            e.printStackTrace();
            return null; // Return null in case of an error
        }
    }
    

     /**
     * Return a user audiobook by audiobook ID and user ID.
     * 
     * @param audioBookId AudioBook ID
     * @param userId User ID
     * @return User book
     */
    public UserAudioBook getByAudioBook(String audioBookId, String userId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select uab from UserAudioBook uab where uab.audioBookId = :audioBookId and uab.userId = :userId and uab.deleteDate is null");
        q.setParameter("audioBookId", audioBookId);
        q.setParameter("userId", userId);
        try {
            return (UserAudioBook) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Deletes a user audiobook by AudioBook ID and User ID.
     * 
     * @param userId User ID
     * @param audioBookId AudioBook ID
     */
    public void deleteByAudioBook(String userId, String audioBookId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
            
        System.out.println("In deleteByAudioBook");
        System.out.println("userId: " + userId);
        System.out.println("audioBookId: " + audioBookId);

        // Get the user book
        Query q = em.createQuery("select uab from UserAudioBook uab where uab.audioBookId = :audioBookId and uab.userId = :userId and uab.deleteDate is null");
        q.setParameter("audioBookId", audioBookId);
        q.setParameter("userId", userId);
        UserAudioBook userAudioBookDb = (UserAudioBook) q.getSingleResult();
        
        // Delete the user book
        Date dateNow = new Date();
        userAudioBookDb.setDeleteDate(dateNow);
    }
}