package io.perezalcolea.hollow.plain.api;

import com.netflix.hollow.api.custom.HollowObjectTypeAPI;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;

@SuppressWarnings("all")
public class BookTypeAPI extends HollowObjectTypeAPI {

    private final BookDelegateLookupImpl delegateLookupImpl;

    public BookTypeAPI(BooksAPI api, HollowObjectTypeDataAccess typeDataAccess) {
        super(api, typeDataAccess, new String[] {
            "bookId",
            "title",
            "isbn",
            "publisher",
            "language",
            "authors"
        });
        this.delegateLookupImpl = new BookDelegateLookupImpl(this);
    }

    public long getBookId(int ordinal) {
        if(fieldIndex[0] == -1)
            return missingDataHandler().handleLong("Book", ordinal, "bookId");
        return getTypeDataAccess().readLong(ordinal, fieldIndex[0]);
    }

    public Long getBookIdBoxed(int ordinal) {
        long l;
        if(fieldIndex[0] == -1) {
            l = missingDataHandler().handleLong("Book", ordinal, "bookId");
        } else {
            boxedFieldAccessSampler.recordFieldAccess(fieldIndex[0]);
            l = getTypeDataAccess().readLong(ordinal, fieldIndex[0]);
        }
        if(l == Long.MIN_VALUE)
            return null;
        return Long.valueOf(l);
    }



    public int getTitleOrdinal(int ordinal) {
        if(fieldIndex[1] == -1)
            return missingDataHandler().handleReferencedOrdinal("Book", ordinal, "title");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[1]);
    }

    public StringTypeAPI getTitleTypeAPI() {
        return getAPI().getStringTypeAPI();
    }

    public int getIsbnOrdinal(int ordinal) {
        if(fieldIndex[2] == -1)
            return missingDataHandler().handleReferencedOrdinal("Book", ordinal, "isbn");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[2]);
    }

    public StringTypeAPI getIsbnTypeAPI() {
        return getAPI().getStringTypeAPI();
    }

    public int getPublisherOrdinal(int ordinal) {
        if(fieldIndex[3] == -1)
            return missingDataHandler().handleReferencedOrdinal("Book", ordinal, "publisher");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[3]);
    }

    public StringTypeAPI getPublisherTypeAPI() {
        return getAPI().getStringTypeAPI();
    }

    public int getLanguageOrdinal(int ordinal) {
        if(fieldIndex[4] == -1)
            return missingDataHandler().handleReferencedOrdinal("Book", ordinal, "language");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[4]);
    }

    public StringTypeAPI getLanguageTypeAPI() {
        return getAPI().getStringTypeAPI();
    }

    public int getAuthorsOrdinal(int ordinal) {
        if(fieldIndex[5] == -1)
            return missingDataHandler().handleReferencedOrdinal("Book", ordinal, "authors");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[5]);
    }

    public SetOfAuthorTypeAPI getAuthorsTypeAPI() {
        return getAPI().getSetOfAuthorTypeAPI();
    }

    public BookDelegateLookupImpl getDelegateLookupImpl() {
        return delegateLookupImpl;
    }

    @Override
    public BooksAPI getAPI() {
        return (BooksAPI) api;
    }

}