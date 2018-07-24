package io.perezalcolea.hollow.plain.api;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.api.consumer.data.AbstractHollowDataAccessor;
import com.netflix.hollow.core.index.key.PrimaryKey;
import com.netflix.hollow.core.read.engine.HollowReadStateEngine;

@SuppressWarnings("all")
public class AuthorDataAccessor extends AbstractHollowDataAccessor<Author> {

    public static final String TYPE = "Author";
    private BooksAPI api;

    public AuthorDataAccessor(HollowConsumer consumer) {
        super(consumer, TYPE);
        this.api = (BooksAPI)consumer.getAPI();
    }

    public AuthorDataAccessor(HollowReadStateEngine rStateEngine, BooksAPI api) {
        super(rStateEngine, TYPE);
        this.api = api;
    }

    public AuthorDataAccessor(HollowReadStateEngine rStateEngine, BooksAPI api, String ... fieldPaths) {
        super(rStateEngine, TYPE, fieldPaths);
        this.api = api;
    }

    public AuthorDataAccessor(HollowReadStateEngine rStateEngine, BooksAPI api, PrimaryKey primaryKey) {
        super(rStateEngine, TYPE, primaryKey);
        this.api = api;
    }

    @Override public Author getRecord(int ordinal){
        return api.getAuthor(ordinal);
    }

}