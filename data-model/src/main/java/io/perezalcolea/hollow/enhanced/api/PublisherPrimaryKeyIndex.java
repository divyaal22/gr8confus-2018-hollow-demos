package io.perezalcolea.hollow.enhanced.api;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.api.consumer.index.AbstractHollowUniqueKeyIndex;
import com.netflix.hollow.core.schema.HollowObjectSchema;

@SuppressWarnings("all")
public class PublisherPrimaryKeyIndex extends AbstractHollowUniqueKeyIndex<BooksAPI, Publisher> {

    public PublisherPrimaryKeyIndex(HollowConsumer consumer) {
        this(consumer, true);
    }

    public PublisherPrimaryKeyIndex(HollowConsumer consumer, boolean isListenToDataRefresh) {
        this(consumer, isListenToDataRefresh, ((HollowObjectSchema)consumer.getStateEngine().getNonNullSchema("Publisher")).getPrimaryKey().getFieldPaths());
    }

    public PublisherPrimaryKeyIndex(HollowConsumer consumer, String... fieldPaths) {
        this(consumer, true, fieldPaths);
    }

    public PublisherPrimaryKeyIndex(HollowConsumer consumer, boolean isListenToDataRefresh, String... fieldPaths) {
        super(consumer, "Publisher", isListenToDataRefresh, fieldPaths);
    }

    public Publisher findMatch(Object... keys) {
        int ordinal = idx.getMatchingOrdinal(keys);
        if(ordinal == -1)
            return null;
        return api.getPublisher(ordinal);
    }

}