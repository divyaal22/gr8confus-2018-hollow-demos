package io.perezalcolea.hollow.enhanced.api;

import com.netflix.hollow.api.objects.HollowObject;
import com.netflix.hollow.core.schema.HollowObjectSchema;

import com.netflix.hollow.tools.stringifier.HollowRecordStringifier;

@SuppressWarnings("all")
public class Author extends HollowObject {

    public Author(AuthorDelegate delegate, int ordinal) {
        super(delegate, ordinal);
    }

    public long getId() {
        return delegate().getId(ordinal);
    }

    public Long getIdBoxed() {
        return delegate().getIdBoxed(ordinal);
    }

    public String getAuthorName() {
        return delegate().getAuthorName(ordinal);
    }

    public boolean isAuthorNameEqual(String testValue) {
        return delegate().isAuthorNameEqual(ordinal, testValue);
    }

    public BooksAPI api() {
        return typeApi().getAPI();
    }

    public AuthorTypeAPI typeApi() {
        return delegate().getTypeAPI();
    }

    protected AuthorDelegate delegate() {
        return (AuthorDelegate)delegate;
    }

}