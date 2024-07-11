package com.sismics.books.core.model.context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.sismics.books.core.listener.async.BookImportAsyncListener;
import com.sismics.books.core.listener.async.UserAppCreatedAsyncListener;
import com.sismics.books.core.listener.sync.DeadEventListener;
import com.sismics.books.core.service.BookDataService;
import com.sismics.books.core.service.FacebookService;
import com.sismics.books.core.service.AudioContent.AudioContentFactory;
import com.sismics.books.core.service.AudioContent.SpotifyAudioFactory;
import com.sismics.books.core.service.AudioContent.iTunesAudioFactory;
import com.sismics.util.EnvironmentUtil;

/**
 * Global application context.
 *
 * @author jtremeaux 
 */
public class AppContext {
    /**
     * Singleton instance.
     */
    /**
     * Event bus.
     */
    private EventBus eventBus;
    
    /**
     * Generic asynchronous event bus.
     */
    private EventBus asyncEventBus;

    /**
     * Asynchronous event bus for mass imports.
     */
    private EventBus importEventBus;
    
    /**
     * Service to fetch book informations.
     */
    private BookDataService bookDataService;

    /**
     * Service to fetch audio book informations.
     */
    private AudioContentFactory AudioContentFactory;

    
    /**
     * Facebook interaction service.
     */
    private FacebookService facebookService;
    
    /**
     * Asynchronous executors.
     */
    private List<ExecutorService> asyncExecutorList;
    
    /**
     * Private constructor.
     */
    private AppContext() {
        resetEventBus();
        
        bookDataService = new BookDataService();
        bookDataService.startAndWait();
        
        facebookService = new FacebookService();
        facebookService.startAndWait();
    }
    
    /**
     * (Re)-initializes the event buses.
     */
    private void resetEventBus() {
        eventBus = new EventBus();
        eventBus.register(new DeadEventListener());
        
        asyncExecutorList = new ArrayList<ExecutorService>();
        
        asyncEventBus = newAsyncEventBus();
        asyncEventBus.register(new UserAppCreatedAsyncListener());
        
        importEventBus = newAsyncEventBus();
        importEventBus.register(new BookImportAsyncListener());
    }

    /**
     * Returns a single instance of the application context.
     * 
     * @return Application context
     */
    public static AppContext getInstance() {
        return AppContextHolder.INSTANCE;
    }

    private static class AppContextHolder {
        private static final AppContext INSTANCE = new AppContext();
    }
    
    /**
     * Creates a new asynchronous event bus.
     * 
     * @return Async event bus
     */
    private EventBus newAsyncEventBus() {
        if (EnvironmentUtil.isUnitTest()) {
            return new EventBus();
        } else {
            ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>());
            asyncExecutorList.add(executor);
            return new AsyncEventBus(executor);
        }
    }

    /**
     * Getter of eventBus.
     *
     * @return eventBus
     */
    public EventBus getEventBus() {
        return eventBus;
    }

    /**
     * Getter of asyncEventBus.
     *
     * @return asyncEventBus
     */
    public EventBus getAsyncEventBus() {
        return asyncEventBus;
    }
    
    /**
     * Getter of importEventBus.
     *
     * @return importEventBus
     */
    public EventBus getImportEventBus() {
        return importEventBus;
    }

    /**
     * Getter of bookDataService.
     * 
     * @return bookDataService
     */
    public BookDataService getBookDataService() {
        return bookDataService;
    }

    /**
     * Getter of AudioContentFactory.
     * 
     * @return AudioContentFactory
     */
    public AudioContentFactory setAudioContentFactory(String factory) {
        if (factory.equals("itunes")) {
            System.out.println("Setting AudioContentFactory to iTunes");
            this.AudioContentFactory = new iTunesAudioFactory();
        } 
        else if (factory.equals("spotify")) {
            System.out.println("Setting AudioContentFactory to Spotify");
            this.AudioContentFactory = new SpotifyAudioFactory();
        }
        return this.AudioContentFactory;
    }
    
    /**
     * Getter of AudioContentFactory.
     * 
     * @return AudioContentFactory
     */
    public AudioContentFactory getAudioContentFactory() {
        return this.AudioContentFactory;
    }
    
    /**
     * Getter of facebookService.
     * 
     * @return facebookService
     */
    public FacebookService getFacebookService() {
        return facebookService;
    }
}
