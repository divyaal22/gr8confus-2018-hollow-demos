package io.perezalcolea.hollow.enhanced.api;

import com.netflix.hollow.api.objects.HollowObject;
import com.netflix.hollow.core.schema.HollowObjectSchema;

import com.netflix.hollow.tools.stringifier.HollowRecordStringifier;

@SuppressWarnings("all")
public class Publisher extends HollowObject {

    public Publisher(PublisherDelegate delegate, int ordinal) {
        super(delegate, ordinal);
    }

    public String getValue() {
        return delegate().getValue(ordinal);
    }

    public boolean isValueEqual(String testValue) {
        return delegate().isValueEqual(ordinal, testValue);
    }

    public BooksAPI api() {
        return typeApi().getAPI();
    }

    public PublisherTypeAPI typeApi() {
        return delegate().getTypeAPI();
    }

    protected PublisherDelegate delegate() {
        return (PublisherDelegate)delegate;
    }

}