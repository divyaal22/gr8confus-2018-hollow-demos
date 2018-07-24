package io.perezalcolea.hollow.enhanced.api;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.api.consumer.index.AbstractHollowUniqueKeyIndex;
import com.netflix.hollow.core.schema.HollowObjectSchema;

@SuppressWarnings("all")
public class LanguagePrimaryKeyIndex extends AbstractHollowUniqueKeyIndex<BooksAPI, Language> {

    public LanguagePrimaryKeyIndex(HollowConsumer consumer) {
        this(consumer, true);
    }

    public LanguagePrimaryKeyIndex(HollowConsumer consumer, boolean isListenToDataRefresh) {
        this(consumer, isListenToDataRefresh, ((HollowObjectSchema)consumer.getStateEngine().getNonNullSchema("Language")).getPrimaryKey().getFieldPaths());
    }

    public LanguagePrimaryKeyIndex(HollowConsumer consumer, String... fieldPaths) {
        this(consumer, true, fieldPaths);
    }

    public LanguagePrimaryKeyIndex(HollowConsumer consumer, boolean isListenToDataRefresh, String... fieldPaths) {
        super(consumer, "Language", isListenToDataRefresh, fieldPaths);
    }

    public Language findMatch(Object... keys) {
        int ordinal = idx.getMatchingOrdinal(keys);
        if(ordinal == -1)
            return null;
        return api.getLanguage(ordinal);
    }

}