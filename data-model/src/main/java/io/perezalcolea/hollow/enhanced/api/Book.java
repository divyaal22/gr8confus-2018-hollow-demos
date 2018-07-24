package io.perezalcolea.hollow.enhanced.api;

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

    public String getTitle() {
        return delegate().getTitle(ordinal);
    }

    public boolean isTitleEqual(String testValue) {
        return delegate().isTitleEqual(ordinal, testValue);
    }

    public String getIsbn() {
        return delegate().getIsbn(ordinal);
    }

    public boolean isIsbnEqual(String testValue) {
        return delegate().isIsbnEqual(ordinal, testValue);
    }

    public Publisher getPublisher() {
        int refOrdinal = delegate().getPublisherOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getPublisher(refOrdinal);
    }

    public Language getLanguage() {
        int refOrdinal = delegate().getLanguageOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getLanguage(refOrdinal);
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