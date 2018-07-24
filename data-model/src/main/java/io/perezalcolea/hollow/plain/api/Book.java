package io.perezalcolea.hollow.plain.api;

import com.netflix.hollow.api.objects.HollowObject;
import com.netflix.hollow.core.schema.HollowObjectSchema;

import com.netflix.hollow.tools.stringifier.HollowRecordStringifier;

@SuppressWarnings("all")
public class Book extends HollowObject {

    public Book(BookDelegate delegate, int ordinal) {
        super(delegate, ordinal);
    }

    public long getBookId() {
        return delegate().getBookId(ordinal);
    }

    public Long getBookIdBoxed() {
        return delegate().getBookIdBoxed(ordinal);
    }

    public HString getTitle() {
        int refOrdinal = delegate().getTitleOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getHString(refOrdinal);
    }

    public HString getIsbn() {
        int refOrdinal = delegate().getIsbnOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getHString(refOrdinal);
    }

    public HString getPublisher() {
        int refOrdinal = delegate().getPublisherOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getHString(refOrdinal);
    }

    public HString getLanguage() {
        int refOrdinal = delegate().getLanguageOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getHString(refOrdinal);
    }

    public SetOfAuthor getAuthors() {
        int refOrdinal = delegate().getAuthorsOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getSetOfAuthor(refOrdinal);
    }

    public BooksAPI api() {
        return typeApi().getAPI();
    }

    public BookTypeAPI typeApi() {
        return delegate().getTypeAPI();
    }

    protected BookDelegate delegate() {
        return (BookDelegate)delegate;
    }

}