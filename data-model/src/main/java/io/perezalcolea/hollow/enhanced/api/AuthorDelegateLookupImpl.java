package io.perezalcolea.hollow.enhanced.api;

import com.netflix.hollow.api.objects.delegate.HollowObjectAbstractDelegate;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;
import com.netflix.hollow.core.schema.HollowObjectSchema;

@SuppressWarnings("all")
public class AuthorDelegateLookupImpl extends HollowObjectAbstractDelegate implements AuthorDelegate {

    private final AuthorTypeAPI typeAPI;

    public AuthorDelegateLookupImpl(AuthorTypeAPI typeAPI) {
        this.typeAPI = typeAPI;
    }

    public long getId(int ordinal) {
        return typeAPI.getId(ordinal);
    }

    public Long getIdBoxed(int ordinal) {
        return typeAPI.getIdBoxed(ordinal);
    }

    public String getAuthorName(int ordinal) {
        return typeAPI.getAuthorName(ordinal);
    }

    public boolean isAuthorNameEqual(int ordinal, String testValue) {
        return typeAPI.isAuthorNameEqual(ordinal, testValue);
    }

    public AuthorTypeAPI getTypeAPI() {
        return typeAPI;
    }

    @Override
    public HollowObjectSchema getSchema() {
        return typeAPI.getTypeDataAccess().getSchema();
    }

    @Override
    public HollowObjectTypeDataAccess getTypeDataAccess() {
        return typeAPI.getTypeDataAccess();
    }

}