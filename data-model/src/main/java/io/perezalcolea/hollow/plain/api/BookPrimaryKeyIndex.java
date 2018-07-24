package io.perezalcolea.hollow.plain.api;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.api.consumer.index.AbstractHollowUniqueKeyIndex;
import com.netflix.hollow.core.schema.HollowObjectSchema;

@SuppressWarnings("all")
public class BookPrimaryKeyIndex extends AbstractHollowUniqueKeyIndex<BooksAPI, Book> {

    public BookPrimaryKeyIndex(HollowConsumer consumer) {
        this(consumer, true);
    }

    public BookPrimaryKeyIndex(HollowConsumer consumer, boolean isListenToDataRefresh) {
        this(consumer, isListenToDataRefresh, ((HollowObjectSchema)consumer.getStateEngine().getNonNullSchema("Book")).getPrimaryKey().getFieldPaths());
    }

    public BookPrimaryKeyIndex(HollowConsumer consumer, String... fieldPaths) {
        this(consumer, true, fieldPaths);
    }

    public BookPrimaryKeyIndex(HollowConsumer consumer, boolean isListenToDataRefresh, String... fieldPaths) {
        super(consumer, "Book", isListenToDataRefresh, fieldPaths);
    }

    public Book findMatch(Object... keys) {
        int ordinal = idx.getMatchingOrdinal(keys);
        if(ordinal == -1)
            return null;
        return api.getBook(ordinal);
    }

}