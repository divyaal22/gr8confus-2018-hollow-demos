package io.perezalcolea.hollow.enhanced.api;

import com.netflix.hollow.api.objects.delegate.HollowObjectDelegate;


@SuppressWarnings("all")
public interface BookDelegate extends HollowObjectDelegate {

    public long getBookId(int ordinal);

    public Long getBookIdBoxed(int ordinal);

    public String getTitle(int ordinal);

    public boolean isTitleEqual(int ordinal, String testValue);

    public String getIsbn(int ordinal);

    public boolean isIsbnEqual(int ordinal, String testValue);

    public int getPublisherOrdinal(int ordinal);

    public int getLanguageOrdinal(int ordinal);

    public int getAuthorsOrdinal(int ordinal);

    public BookTypeAPI getTypeAPI();

}