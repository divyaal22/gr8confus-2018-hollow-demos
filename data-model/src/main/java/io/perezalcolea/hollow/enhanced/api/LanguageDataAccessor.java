package io.perezalcolea.hollow.enhanced.api;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.api.consumer.data.AbstractHollowDataAccessor;
import com.netflix.hollow.core.index.key.PrimaryKey;
import com.netflix.hollow.core.read.engine.HollowReadStateEngine;

@SuppressWarnings("all")
public class LanguageDataAccessor extends AbstractHollowDataAccessor<Language> {

    public static final String TYPE = "Language";
    private BooksAPI api;

    public LanguageDataAccessor(HollowConsumer consumer) {
        super(consumer, TYPE);
        this.api = (BooksAPI)consumer.getAPI();
    }

    public LanguageDataAccessor(HollowReadStateEngine rStateEngine, BooksAPI api) {
        super(rStateEngine, TYPE);
        this.api = api;
    }

    public LanguageDataAccessor(HollowReadStateEngine rStateEngine, BooksAPI api, String ... fieldPaths) {
        super(rStateEngine, TYPE, fieldPaths);
        this.api = api;
    }

    public LanguageDataAccessor(HollowReadStateEngine rStateEngine, BooksAPI api, PrimaryKey primaryKey) {
        super(rStateEngine, TYPE, primaryKey);
        this.api = api;
    }

    @Override public Language getRecord(int ordinal){
        return api.getLanguage(ordinal);
    }

}