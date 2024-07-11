package com.sismics.books.core.service.AudioContent;

import com.sismics.books.core.model.jpa.AudioBook;
import com.sismics.books.core.model.jpa.Podcast;
import java.util.List;

public abstract class AudioContentFactory {
    public abstract List<AudioBook> getAudioBooks(String query);
    public abstract List<Podcast> getPodcasts(String query);
}
