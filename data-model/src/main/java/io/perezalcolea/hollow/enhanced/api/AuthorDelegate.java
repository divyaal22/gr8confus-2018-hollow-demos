package io.perezalcolea.hollow.enhanced.api;

import com.netflix.hollow.api.objects.delegate.HollowObjectDelegate;


@SuppressWarnings("all")
public interface AuthorDelegate extends HollowObjectDelegate {

    public long getId(int ordinal);

    public Long getIdBoxed(int ordinal);

    public String getAuthorName(int ordinal);

    public boolean isAuthorNameEqual(int ordinal, String testValue);

    public AuthorTypeAPI getTypeAPI();

}