package io.perezalcolea.hollow.plain.api;

import com.netflix.hollow.api.objects.delegate.HollowObjectAbstractDelegate;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;
import com.netflix.hollow.core.schema.HollowObjectSchema;
import com.netflix.hollow.api.custom.HollowTypeAPI;
import com.netflix.hollow.api.objects.delegate.HollowCachedDelegate;

@SuppressWarnings("all")
public class BookDelegateCachedImpl extends HollowObjectAbstractDelegate implements HollowCachedDelegate, BookDelegate {

    private final Long bookId;
    private final int titleOrdinal;
    private final int isbnOrdinal;
    private final int publisherOrdinal;
    private final int languageOrdinal;
    private final int authorsOrdinal;
    private BookTypeAPI typeAPI;

    public BookDelegateCachedImpl(BookTypeAPI typeAPI, int ordinal) {
        this.bookId = typeAPI.getBookIdBoxed(ordinal);
        this.titleOrdinal = typeAPI.getTitleOrdinal(ordinal);
        this.isbnOrdinal = typeAPI.getIsbnOrdinal(ordinal);
        this.publisherOrdinal = typeAPI.getPublisherOrdinal(ordinal);
        this.languageOrdinal = typeAPI.getLanguageOrdinal(ordinal);
        this.authorsOrdinal = typeAPI.getAuthorsOrdinal(ordinal);
        this.typeAPI = typeAPI;
    }

    public long getBookId(int ordinal) {
        if(bookId == null)
            return Long.MIN_VALUE;
        return bookId.longValue();
    }

    public Long getBookIdBoxed(int ordinal) {
        return bookId;
    }

    public int getTitleOrdinal(int ordinal) {
        return titleOrdinal;
    }

    public int getIsbnOrdinal(int ordinal) {
        return isbnOrdinal;
    }

    public int getPublisherOrdinal(int ordinal) {
        return publisherOrdinal;
    }

    public int getLanguageOrdinal(int ordinal) {
        return languageOrdinal;
    }

    public int getAuthorsOrdinal(int ordinal) {
        return authorsOrdinal;
    }

    @Override
    public HollowObjectSchema getSchema() {
        return typeAPI.getTypeDataAccess().getSchema();
    }

    @Override
    public HollowObjectTypeDataAccess getTypeDataAccess() {
        return typeAPI.getTypeDataAccess();
    }

    public BookTypeAPI getTypeAPI() {
        return typeAPI;
    }

    public void updateTypeAPI(HollowTypeAPI typeAPI) {
        this.typeAPI = (BookTypeAPI) typeAPI;
    }

}