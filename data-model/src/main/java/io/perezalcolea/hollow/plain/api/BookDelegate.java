package io.perezalcolea.hollow.plain.api;

import com.netflix.hollow.api.objects.delegate.HollowObjectDelegate;


@SuppressWarnings("all")
public interface BookDelegate extends HollowObjectDelegate {

    public long getBookId(int ordinal);

    public Long getBookIdBoxed(int ordinal);

    public int getTitleOrdinal(int ordinal);

    public int getIsbnOrdinal(int ordinal);

    public int getPublisherOrdinal(int ordinal);

    public int getLanguageOrdinal(int ordinal);

    public int getAuthorsOrdinal(int ordinal);

    public BookTypeAPI getTypeAPI();

}