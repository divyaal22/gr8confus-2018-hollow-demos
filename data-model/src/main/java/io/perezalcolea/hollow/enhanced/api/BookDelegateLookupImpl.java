package io.perezalcolea.hollow.enhanced.api;

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

    public String getTitle(int ordinal) {
        return typeAPI.getTitle(ordinal);
    }

    public boolean isTitleEqual(int ordinal, String testValue) {
        return typeAPI.isTitleEqual(ordinal, testValue);
    }

    public String getIsbn(int ordinal) {
        return typeAPI.getIsbn(ordinal);
    }

    public boolean isIsbnEqual(int ordinal, String testValue) {
        return typeAPI.isIsbnEqual(ordinal, testValue);
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