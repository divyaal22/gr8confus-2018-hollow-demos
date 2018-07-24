package io.perezalcolea.hollow.enhanced.api;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.core.index.HollowHashIndexResult;
import java.util.Collections;
import java.lang.Iterable;
import com.netflix.hollow.api.consumer.index.AbstractHollowHashIndex;
import com.netflix.hollow.api.consumer.data.AbstractHollowOrdinalIterable;


@SuppressWarnings("all")
public class BooksAPIHashIndex extends AbstractHollowHashIndex<BooksAPI> {

    public BooksAPIHashIndex(HollowConsumer consumer, String queryType, String selectFieldPath, String... matchFieldPaths) {
        super(consumer, true, queryType, selectFieldPath, matchFieldPaths);
    }

    public BooksAPIHashIndex(HollowConsumer consumer, boolean isListenToDataRefresh, String queryType, String selectFieldPath, String... matchFieldPaths) {
        super(consumer, isListenToDataRefresh, queryType, selectFieldPath, matchFieldPaths);
    }

    public Iterable<Author> findAuthorMatches(Object... keys) {
        HollowHashIndexResult matches = idx.findMatches(keys);
        if(matches == null) return Collections.emptySet();

        return new AbstractHollowOrdinalIterable<Author>(matches.iterator()) {
            public Author getData(int ordinal) {
                return api.getAuthor(ordinal);
            }
        };
    }

    public Iterable<Language> findLanguageMatches(Object... keys) {
        HollowHashIndexResult matches = idx.findMatches(keys);
        if(matches == null) return Collections.emptySet();

        return new AbstractHollowOrdinalIterable<Language>(matches.iterator()) {
            public Language getData(int ordinal) {
                return api.getLanguage(ordinal);
            }
        };
    }

    public Iterable<Publisher> findPublisherMatches(Object... keys) {
        HollowHashIndexResult matches = idx.findMatches(keys);
        if(matches == null) return Collections.emptySet();

        return new AbstractHollowOrdinalIterable<Publisher>(matches.iterator()) {
            public Publisher getData(int ordinal) {
                return api.getPublisher(ordinal);
            }
        };
    }

    public Iterable<SetOfAuthor> findSetOfAuthorMatches(Object... keys) {
        HollowHashIndexResult matches = idx.findMatches(keys);
        if(matches == null) return Collections.emptySet();

        return new AbstractHollowOrdinalIterable<SetOfAuthor>(matches.iterator()) {
            public SetOfAuthor getData(int ordinal) {
                return api.getSetOfAuthor(ordinal);
            }
        };
    }

    public Iterable<Book> findBookMatches(Object... keys) {
        HollowHashIndexResult matches = idx.findMatches(keys);
        if(matches == null) return Collections.emptySet();

        return new AbstractHollowOrdinalIterable<Book>(matches.iterator()) {
            public Book getData(int ordinal) {
                return api.getBook(ordinal);
            }
        };
    }

}