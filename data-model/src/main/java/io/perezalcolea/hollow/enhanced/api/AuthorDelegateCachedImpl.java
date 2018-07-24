package io.perezalcolea.hollow.enhanced.api;

import com.netflix.hollow.api.objects.delegate.HollowObjectAbstractDelegate;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;
import com.netflix.hollow.core.schema.HollowObjectSchema;
import com.netflix.hollow.api.custom.HollowTypeAPI;
import com.netflix.hollow.api.objects.delegate.HollowCachedDelegate;

@SuppressWarnings("all")
public class AuthorDelegateCachedImpl extends HollowObjectAbstractDelegate implements HollowCachedDelegate, AuthorDelegate {

    private final Long id;
    private final String authorName;
    private AuthorTypeAPI typeAPI;

    public AuthorDelegateCachedImpl(AuthorTypeAPI typeAPI, int ordinal) {
        this.id = typeAPI.getIdBoxed(ordinal);
        this.authorName = typeAPI.getAuthorName(ordinal);
        this.typeAPI = typeAPI;
    }

    public long getId(int ordinal) {
        if(id == null)
            return Long.MIN_VALUE;
        return id.longValue();
    }

    public Long getIdBoxed(int ordinal) {
        return id;
    }

    public String getAuthorName(int ordinal) {
        return authorName;
    }

    public boolean isAuthorNameEqual(int ordinal, String testValue) {
        if(testValue == null)
            return authorName == null;
        return testValue.equals(authorName);
    }

    @Override
    public HollowObjectSchema getSchema() {
        return typeAPI.getTypeDataAccess().getSchema();
    }

    @Override
    public HollowObjectTypeDataAccess getTypeDataAccess() {
        return typeAPI.getTypeDataAccess();
    }

    public AuthorTypeAPI getTypeAPI() {
        return typeAPI;
    }

    public void updateTypeAPI(HollowTypeAPI typeAPI) {
        this.typeAPI = (AuthorTypeAPI) typeAPI;
    }

}