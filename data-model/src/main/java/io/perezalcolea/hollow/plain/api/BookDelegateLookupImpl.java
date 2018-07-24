package io.perezalcolea.hollow.plain.api;

import com.netflix.hollow.api.objects.delegate.HollowObjectAbstractDelegate;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;
import com.netflix.hollow.core.schema.HollowObjectSchema;

@SuppressWarnings("all")
public class BookDelegateLookupImpl extends HollowObjectAbstractDelegate implements BookDelegate {

    private final BookTypeAPI typeAPI;

    public BookDelegateLookupImpl(BookTypeAPI typeAPI) {
        this.typeAPI = typeAPI;
    }

    public long getBookId(int ordinal) {
        return typeAPI.getBookId(ordinal);
    }

    public Long getBookIdBoxed(int ordinal) {
        return typeAPI.getBookIdBoxed(ordinal);
    }

    public int getTitleOrdinal(int ordinal) {
        return typeAPI.getTitleOrdinal(ordinal);
    }

    public int getIsbnOrdinal(int ordinal) {
        return typeAPI.getIsbnOrdinal(ordinal);
    }

    public int getPublisherOrdinal(int ordinal) {
        return typeAPI.getPublisherOrdinal(ordinal);
    }

    public int getLanguageOrdinal(int ordinal) {
        return typeAPI.getLanguageOrdinal(ordinal);
    }

    public int getAuthorsOrdinal(int ordinal) {
        return typeAPI.getAuthorsOrdinal(ordinal);
    }

    public BookTypeAPI getTypeAPI() {
        return typeAPI;
    }

    @Override
    public HollowObjectSchema getSchema() {
        return typeAPI.getTypeDataAccess().getSchema();
    }

    @Override
    public HollowObjectTypeDataAccess getTypeDataAccess() {
        return typeAPI.getTypeDataAccess();
    }

}