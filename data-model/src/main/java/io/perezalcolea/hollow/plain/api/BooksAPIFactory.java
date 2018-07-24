package io.perezalcolea.hollow.plain.api;

import com.netflix.hollow.api.client.HollowAPIFactory;
import com.netflix.hollow.api.custom.HollowAPI;
import com.netflix.hollow.api.objects.provider.HollowFactory;
import com.netflix.hollow.core.read.dataaccess.HollowDataAccess;
import java.util.Collections;
import java.util.Set;

@SuppressWarnings("all")
public class BooksAPIFactory implements HollowAPIFactory {

    private final Set<String> cachedTypes;

    public BooksAPIFactory() {
        this(Collections.<String>emptySet());
    }

    public BooksAPIFactory(Set<String> cachedTypes) {
        this.cachedTypes = cachedTypes;
    }

    @Override
    public HollowAPI createAPI(HollowDataAccess dataAccess) {
        return new BooksAPI(dataAccess, cachedTypes);
    }

    @Override
    public HollowAPI createAPI(HollowDataAccess dataAccess, HollowAPI previousCycleAPI) {
        if (!(previousCycleAPI instanceof BooksAPI)) {
            throw new ClassCastException(previousCycleAPI.getClass() + " not instance of BooksAPI");        }
        return new BooksAPI(dataAccess, cachedTypes, Collections.<String, HollowFactory<?>>emptyMap(), (BooksAPI) previousCycleAPI);
    }

}