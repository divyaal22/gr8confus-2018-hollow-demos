package io.perezalcolea.hollow.plain.api;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.api.consumer.data.AbstractHollowDataAccessor;
import com.netflix.hollow.core.index.key.PrimaryKey;
import com.netflix.hollow.core.read.engine.HollowReadStateEngine;

@SuppressWarnings("all")
public class BookDataAccessor extends AbstractHollowDataAccessor<Book> {

    public static final String TYPE = "Book";
    private BooksAPI api;

    public BookDataAccessor(HollowConsumer consumer) {
        super(consumer, TYPE);
        this.api = (BooksAPI)consumer.getAPI();
    }

    public BookDataAccessor(HollowReadStateEngine rStateEngine, BooksAPI api) {
        super(rStateEngine, TYPE);
        this.api = api;
    }

    public BookDataAccessor(HollowReadStateEngine rStateEngine, BooksAPI api, String ... fieldPaths) {
        super(rStateEngine, TYPE, fieldPaths);
        this.api = api;
    }

    public BookDataAccessor(HollowReadStateEngine rStateEngine, BooksAPI api, PrimaryKey primaryKey) {
        super(rStateEngine, TYPE, primaryKey);
        this.api = api;
    }

    @Override public Book getRecord(int ordinal){
        return api.getBook(ordinal);
    }

}