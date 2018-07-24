package io.perezalcolea.hollow.enhanced.api;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.api.consumer.data.AbstractHollowDataAccessor;
import com.netflix.hollow.core.index.key.PrimaryKey;
import com.netflix.hollow.core.read.engine.HollowReadStateEngine;

@SuppressWarnings("all")
public class PublisherDataAccessor extends AbstractHollowDataAccessor<Publisher> {

    public static final String TYPE = "Publisher";
    private BooksAPI api;

    public PublisherDataAccessor(HollowConsumer consumer) {
        super(consumer, TYPE);
        this.api = (BooksAPI)consumer.getAPI();
    }

    public PublisherDataAccessor(HollowReadStateEngine rStateEngine, BooksAPI api) {
        super(rStateEngine, TYPE);
        this.api = api;
    }

    public PublisherDataAccessor(HollowReadStateEngine rStateEngine, BooksAPI api, String ... fieldPaths) {
        super(rStateEngine, TYPE, fieldPaths);
        this.api = api;
    }

    public PublisherDataAccessor(HollowReadStateEngine rStateEngine, BooksAPI api, PrimaryKey primaryKey) {
        super(rStateEngine, TYPE, primaryKey);
        this.api = api;
    }

    @Override public Publisher getRecord(int ordinal){
        return api.getPublisher(ordinal);
    }

}