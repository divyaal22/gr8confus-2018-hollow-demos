package io.perezalcolea.hollow.enhanced.api;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.api.consumer.index.AbstractHollowUniqueKeyIndex;
import com.netflix.hollow.core.schema.HollowObjectSchema;

@SuppressWarnings("all")
public class AuthorPrimaryKeyIndex extends AbstractHollowUniqueKeyIndex<BooksAPI, Author> {

    public AuthorPrimaryKeyIndex(HollowConsumer consumer) {
        this(consumer, true);
    }

    public AuthorPrimaryKeyIndex(HollowConsumer consumer, boolean isListenToDataRefresh) {
        this(consumer, isListenToDataRefresh, ((HollowObjectSchema)consumer.getStateEngine().getNonNullSchema("Author")).getPrimaryKey().getFieldPaths());
    }

    public AuthorPrimaryKeyIndex(HollowConsumer consumer, String... fieldPaths) {
        this(consumer, true, fieldPaths);
    }

    public AuthorPrimaryKeyIndex(HollowConsumer consumer, boolean isListenToDataRefresh, String... fieldPaths) {
        super(consumer, "Author", isListenToDataRefresh, fieldPaths);
    }

    public Author findMatch(Object... keys) {
        int ordinal = idx.getMatchingOrdinal(keys);
        if(ordinal == -1)
            return null;
        return api.getAuthor(ordinal);
    }

}