package io.perezalcolea.hollow.plain.api;

import com.netflix.hollow.api.objects.HollowSet;
import com.netflix.hollow.core.schema.HollowSetSchema;
import com.netflix.hollow.api.objects.delegate.HollowSetDelegate;
import com.netflix.hollow.api.objects.generic.GenericHollowRecordHelper;

@SuppressWarnings("all")
public class SetOfAuthor extends HollowSet<Author> {

    public SetOfAuthor(HollowSetDelegate delegate, int ordinal) {
        super(delegate, ordinal);
    }

    @Override
    public Author instantiateElement(int ordinal) {
        return (Author) api().getAuthor(ordinal);
    }

    @Override
    public boolean equalsElement(int elementOrdinal, Object testObject) {
        return GenericHollowRecordHelper.equalObject(getSchema().getElementType(), elementOrdinal, testObject);
    }

    public BooksAPI api() {
        return typeApi().getAPI();
    }

    public SetOfAuthorTypeAPI typeApi() {
        return (SetOfAuthorTypeAPI) delegate.getTypeAPI();
    }

}