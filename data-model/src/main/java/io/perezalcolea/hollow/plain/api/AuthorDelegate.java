package io.perezalcolea.hollow.plain.api;

import com.netflix.hollow.api.objects.delegate.HollowObjectDelegate;


@SuppressWarnings("all")
public interface AuthorDelegate extends HollowObjectDelegate {

    public long getId(int ordinal);

    public Long getIdBoxed(int ordinal);

    public int getAuthorNameOrdinal(int ordinal);

    public AuthorTypeAPI getTypeAPI();

}