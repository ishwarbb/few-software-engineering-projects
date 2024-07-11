package com.sismics.books.core.dao.jpa;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import com.sismics.books.core.dao.jpa.criteria.LibBookCriteria;

import com.sismics.books.core.dao.jpa.dto.LibBookDto;
import com.sismics.books.core.dao.jpa.dto.LibBookGenreDto;
import com.sismics.books.core.dao.jpa.dto.LibBookRatingDto;

import com.sismics.books.core.model.jpa.LibBook;
import com.sismics.books.core.util.jpa.PaginatedList;
import com.sismics.books.core.util.jpa.PaginatedLists;
import com.sismics.books.core.util.jpa.QueryParam;
import com.sismics.books.core.util.jpa.SortCriteria;
import com.sismics.util.context.ThreadLocalContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Collections;
import java.util.Comparator;

/**
 * Library book DAO.
 * 
 * @author FlightVin
 */
public class LibBookDao {
    /**
     * Creates a new library book.
     * 
     * @param libBook LibBook
     * @return New ID
     * @throws Exception
     */
    public String create(LibBook libBook) {
        // Create the UUID
        libBook.setId(UUID.randomUUID().toString());
        
        // Create the library book
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        em.persist(libBook);
        
        return libBook.getId();
    }

    /**
     * Deletes a library book.
     * 
     * @param id Library book ID
     */
    public void delete(String id) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
            
        // Get the library book
        Query q = em.createQuery("select ub from LibBook ub where ub.id = :id and ub.deleteDate is null");
        q.setParameter("id", id);
        LibBook libBookDb = (LibBook) q.getSingleResult();
        
        // Delete the library book
        Date dateNow = new Date();
        libBookDb.setDeleteDate(dateNow);
    }
    
    /**
     * Return a library book
     * @param libBookId Library book ID
     * @return Library book
     */
    public LibBook getLibBook(String libBookId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select ub from LibBook ub where ub.id = :libBookId and ub.deleteDate is null");
        q.setParameter("libBookId", libBookId);
        try {
            return (LibBook) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    /**
     * Return a library book by book ID
     * 
     * @param bookId Book ID
     * @return Library book
     */
    public LibBook getByBook(String bookId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select ub from LibBook ub where ub.bookId = :bookId and ub.deleteDate is null");
        q.setParameter("bookId", bookId);
        try {
            return (LibBook) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Searches lib books by criteria. : CURRENTLY returns everything
     * 
     * @param paginatedList List of lib books (updated by side effects)
     * @param criteria Search criteria
     * @return List of lib books
     * @throws Exception 
     */
    public void findByCriteria(PaginatedList<LibBookDto> paginatedList, LibBookCriteria criteria, SortCriteria sortCriteria) throws Exception {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        List<String> criteriaList = new ArrayList<String>();
        
        StringBuilder sb = new StringBuilder("select ub.LBK_ID_C c0, b.BOK_TITLE_C c1, b.BOK_SUBTITLE_C c2, b.BOK_AUTHOR_C c3, b.BOK_LANGUAGE_C c4, b.BOK_PUBLISHDATE_D c5");
        sb.append(" from T_BOOK b ");
        sb.append(" join T_LIB_BOOK ub on ub.LBK_IDBOOK_C = b.BOK_ID_C and ub.LBK_DELETEDATE_D is null ");
        
        // Adds search criteria: leave as is
        if (!Strings.isNullOrEmpty(criteria.getSearch())) {
            criteriaList.add(" (b.BOK_TITLE_C like :search or b.BOK_SUBTITLE_C like :search or b.BOK_AUTHOR_C like :search) ");
            parameterMap.put("search", "%" + criteria.getSearch() + "%");
        }        

        if (!criteriaList.isEmpty()) {
            sb.append(" where ");
            sb.append(Joiner.on(" and ").join(criteriaList));
        }
        
        // Perform the search
        QueryParam queryParam = new QueryParam(sb.toString(), parameterMap);


        List<Object[]> l = PaginatedLists.executePaginatedQuery(paginatedList, queryParam, sortCriteria);
        
        // Assemble results
        List<LibBookDto> libBookDtoList = new ArrayList<LibBookDto>();
        for (Object[] o : l) {
            int i = 0;
            LibBookDto libBookDto = new LibBookDto();
            libBookDto.setId((String) o[i++]);
            libBookDto.setTitle((String) o[i++]);
            libBookDto.setSubtitle((String) o[i++]);
            libBookDto.setAuthor((String) o[i++]);

            // Create filtering strategies based on criteria
            List<LibBookFilterStrategy> strategies = new ArrayList<LibBookFilterStrategy>();
            if (criteria.getFilterAuthors() != null) {
                strategies.add(new AuthorFilterStrategy(criteria.getFilterAuthors()));
            }
            if (criteria.getFilterGenres() != null) {
                strategies.add(new GenreFilterStrategy(criteria.getFilterGenres()));
            }
            if (criteria.getFilterMinRating() != null) {
                strategies.add(new RatingFilterStrategy(criteria.getFilterMinRating()));
            }

            // Filter the book using context
            LibBookFilterContext context = new LibBookFilterContext(strategies);
            if (context.filter(libBookDto)) {
                libBookDtoList.add(libBookDto);
            }
        }

        // sorting
        if (criteria.getLibrarySort() != 0) {
            LibBookSortStrategy strategy;
            if (criteria.getLibrarySort() == 1) {
                strategy = new AverageRatingSortStrategy();
            } else if (criteria.getLibrarySort() == 2) {
                strategy = new NumRatingsSortStrategy();
            } else {
                // Handle invalid sorting criteria
                throw new IllegalArgumentException("Invalid sorting criteria for libBooks");
            }

            // Sort the list using the context
            LibBookSortContext context = new LibBookSortContext(strategy);
            context.sort(libBookDtoList);
        }

        paginatedList.setResultList(libBookDtoList);
    }




    /**************************************** Strategy Pattern for Filtering ****************************************/
    /**
     * Interface for implementing filtering strategies in the context of library books.
     * Each strategy determines whether a given library book meets certain filtering criteria.
     */
    public interface LibBookFilterStrategy {
        /**
         * Determines whether the provided library book satisfies the filtering criteria.
         * 
         * @param libBookDto The library book to be evaluated.
         * @return true if the book satisfies the filtering criteria, otherwise false.
         */
        boolean filter(LibBookDto libBookDto);
    }

    /**
     * A filtering strategy based on the author(s) of a library book.
     */
    public class AuthorFilterStrategy implements LibBookFilterStrategy {
        private List<String> authors;

        /**
         * Constructs an AuthorFilterStrategy object with the specified author(s).
         * 
         * @param authors The author(s) to filter by.
         */
        public AuthorFilterStrategy(List<String> authors) {
            this.authors = authors;
        }

        @Override
        public boolean filter(LibBookDto libBookDto) {
            return authors.contains(libBookDto.getAuthor());
        }
    }

    /**
     * A filtering strategy based on the genre(s) of a library book.
     */
    public class GenreFilterStrategy implements LibBookFilterStrategy {
        private List<String> genres;

        /**
         * Constructs a GenreFilterStrategy object with the specified genre(s).
         * 
         * @param genres The genre(s) to filter by.
         */
        public GenreFilterStrategy(List<String> genres) {
            this.genres = genres;
        }

        private boolean hasCommonElement(List<String> list1, List<String> list2) {
            for (String element : list1) {
                if (list2.contains(element)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean filter(LibBookDto libBookDto) {
            return hasCommonElement(genres, getLibBookGenres(libBookDto));
        }
    }


    /**
     * A filtering strategy based on the minimum rating of a library book.
     */
    public class RatingFilterStrategy implements LibBookFilterStrategy {
        private float minRating;

        /**
         * Constructs a RatingFilterStrategy object with the specified minimum rating.
         * 
         * @param minRating The minimum rating required for a book to pass the filter.
         */
        public RatingFilterStrategy(float minRating) {
            this.minRating = minRating;
        }

        @Override
        public boolean filter(LibBookDto libBookDto) {
            return getAverageRating(libBookDto) >= minRating;
        }
    }

    /**
     * Represents the context in which filtering of library books is performed.
     * It applies a collection of filtering strategies to determine whether a book meets certain criteria.
     */
    public class LibBookFilterContext {
        private List<LibBookFilterStrategy> strategies;

        /**
         * Constructs a LibBookFilterContext object with the specified filtering strategies.
         * 
         * @param strategies A collection of filtering strategies to be applied.
         */
        public LibBookFilterContext(List<LibBookFilterStrategy> strategies) {
            this.strategies = strategies;
        }

        /**
         * Filters the provided library book based on the configured strategies.
         * 
         * @param libBookDto The library book to be evaluated.
         * @return true if the book satisfies all filtering criteria, otherwise false.
         */
        public boolean filter(LibBookDto libBookDto) {
            for (LibBookFilterStrategy strategy : strategies) {
                if (!strategy.filter(libBookDto)) {
                    return false; // If any strategy fails, return false
                }
            }
            return true; // All strategies passed
        }
    }




    /**************************************** Strategy Pattern for Filtering ****************************************/
    /**
     * Interface for implementing sorting strategies in the context of library books.
     * Each strategy defines a specific way of sorting library books.
     */
    public interface LibBookSortStrategy {
        /**
         * Sorts the provided list of library books based on the implemented sorting strategy.
         * 
         * @param list The list of library books to be sorted.
         */
        void sort(List<LibBookDto> list);
    }


    /**
     * A sorting strategy based on the average rating of library books in descending order.
     */
    public class AverageRatingSortStrategy implements LibBookSortStrategy {
        // Comparator for sorting by average rating in descending order
        private Comparator<LibBookDto> averageRatingComparatorDescending = new Comparator<LibBookDto>() {
            @Override
            public int compare(LibBookDto book1, LibBookDto book2) {
                return Float.compare(getAverageRating(book2), getAverageRating(book1));
            }
        };

        @Override
        public void sort(List<LibBookDto> list) {
            Collections.sort(list, averageRatingComparatorDescending);
        }
    }

    /**
     * A sorting strategy based on the number of ratings of library books in descending order.
     */
    public class NumRatingsSortStrategy implements LibBookSortStrategy {
        // Comparator for sorting by number of ratings in descending order
        private Comparator<LibBookDto> numRatingsComparatorDescending = new Comparator<LibBookDto>() {
            @Override
            public int compare(LibBookDto book1, LibBookDto book2) {
                return Integer.compare(getNumRatings(book2), getNumRatings(book1));
            }
        };

        @Override
        public void sort(List<LibBookDto> list) {
            Collections.sort(list, numRatingsComparatorDescending);
        }
    }
    
    /**
     * Represents the context in which sorting of library books is performed.
     * It applies a specific sorting strategy to sort the list of library books.
     */
    public class LibBookSortContext {
        private LibBookSortStrategy strategy;

        /**
         * Constructs a LibBookSortContext object with the specified sorting strategy.
         * 
         * @param strategy The sorting strategy to be applied.
         */
        public LibBookSortContext(LibBookSortStrategy strategy) {
            this.strategy = strategy;
        }

        /**
         * Sorts the provided list of library books based on the configured strategy.
         * 
         * @param list The list of library books to be sorted.
         */
        public void sort(List<LibBookDto> list) {
            strategy.sort(list);
        }
    }


    /**************************************** Some other Misc. Functions ****************************************/
    /**
     * Retrieves the average rating of a library book.
     * 
     * @param libBookDto The library book for which the average rating is to be calculated.
     * @return The average rating of the book.
     */
    private static Float getAverageRating(LibBookDto libBookDto) {
        LibBookRatingDao ratingDao = new LibBookRatingDao();
        List<LibBookRatingDto> ratingDtoList = ratingDao.getByLibBookId(libBookDto.getId());
        List<Float> ratings = new ArrayList<>();
        for (LibBookRatingDto libBookRatingDto : ratingDtoList) {
            ratings.add(libBookRatingDto.getRating());
        }
        int numRatings = ratings.size();
        float sumRatings = 0;
        for (Float rating : ratings) {
            sumRatings += rating;
        }
        float averageRating = (numRatings > 0) ? sumRatings / numRatings : 0;
        return averageRating;
    }

    /**
     * Retrieves the number of ratings received by a library book.
     * 
     * @param libBookDto The library book for which the number of ratings is to be calculated.
     * @return The number of ratings received by the book.
     */
    private static Integer getNumRatings(LibBookDto libBookDto) {
        LibBookRatingDao ratingDao = new LibBookRatingDao();
        List<LibBookRatingDto> ratingDtoList = ratingDao.getByLibBookId(libBookDto.getId());
        List<Float> ratings = new ArrayList<>();
        for (LibBookRatingDto libBookRatingDto : ratingDtoList) {
            ratings.add(libBookRatingDto.getRating());
        }
        int numRatings = ratings.size();
        return numRatings;
    }

    /**
     * Retrieves the genres associated with a library book.
     * 
     * @param libBookDto The library book for which the genres are to be retrieved.
     * @return The genres associated with the book.
     */
    private static List<String> getLibBookGenres(LibBookDto libBookDto) {
        LibBookGenreDao genreDao = new LibBookGenreDao();
        List<LibBookGenreDto> genreDtoList = genreDao.getByLibBookId(libBookDto.getId());
        List<String> genres = new ArrayList<>();
        for (LibBookGenreDto libBookGenreDto : genreDtoList) {
            genres.add(libBookGenreDto.getGenreName());
        }
        return genres;
    }
}
